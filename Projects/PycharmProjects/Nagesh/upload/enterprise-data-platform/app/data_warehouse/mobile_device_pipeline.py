import configparser
import multiprocessing as mp
import logging

from app.common.data_access.last_run_metadata import LastRun
from app.common.data_access.connections import OBDConnection
from app.common.data_access.pandas_rdbms import *
from app.common.utils.util_functions import chunk_ranges
from app.constants.config_constants import *
from app.data_warehouse.stg_to_dw_pipeline import DataWareHousePipeline

exec_log = logging.getLogger("Execution")
state_log = logging.getLogger("State")


class MobileDevice(DataWareHousePipeline):
    def __init__(self, config_path):
        super(MobileDevice).__init__()
        config = configparser.ConfigParser()
        config.read(config_path)
        self.config = config
        self.processes = []
        self.use_multi_processes = False

    def start(self):
        exec_log.info("started pipeline")
        self.setup_source_connection()
        self.setup_target_connection()
        self.setup_last_run_meta_data()
        self.transform_and_load()
        exec_log.info("pipeline finished successfully")

    def setup_source_connection(self):
        exec_log.info("setting up source connection")
        self.src_connection = OBDConnection(self.config.get(SOURCE, DB_NAME)).connect_to_db()
        self.src_cursor = self.src_connection.cursor()
        exec_log.info("source connection setup successful")

    def setup_last_run_meta_data(self):
        exec_log.info("fetch last run meta data")
        self.last_run_details = LastRun(self.src_connection, self.config.get(SOURCE, PROGRAM_ID))
        self.last_run_ts = self.last_run_details.get_last_run_ts()
        self.last_run_recovery_data = self.last_run_details.get_recovery_data()
        self.row_commit_frequency = self.last_run_details.get_row_commit_frequency()
        exec_log.info("last run meta data fetched  successfully")

    def setup_target_connection(self):
        exec_log.info("setting up target connection")
        self.tgt_connection = OBDConnection(self.config.get(TARGET, DB_NAME)).connect_to_db()
        self.tgt_cursor = self.tgt_connection.cursor()
        exec_log.info("target connection setup successful")

    def transform_and_load(self):
        self.read_source_data()
        self.read_target_data()
        self.delta_df()
        self.load_data_into_target()

    def read_source_data(self):
        exec_log.info("reading source data")
        filter_exp = db2_query_constants.FILTER_EXP_ABSTRACT.format(column="SESSION_TS", operator=">",
                                                                    value=self.last_run_ts)
        self.src_data_frame = select_distinct_data_as_df(self.src_connection, self.config.get(SOURCE, COLS),
                                                         self.config.get(SOURCE, SCHEMA),
                                                         self.config.get(SOURCE, TABLE), filter_exp=filter_exp)
        exec_log.info("source data read successfully")

    def read_target_data(self):
        exec_log.info("reading target data")
        self.tgt_data_frame = select_distinct_data_as_df(self.tgt_connection, self.config.get(TARGET, COLS),
                                                         self.config.get(TARGET, SCHEMA),
                                                         self.config.get(TARGET, TABLE))
        exec_log.info("target data loaded successfully")

    def delta_df(self):
        exec_log.info("finding delta")
        self.final_df = self.src_data_frame.merge(self.tgt_data_frame, how='outer', indicator=True).loc[
            lambda x: x['_merge'] == 'left_only']
        exec_log.info("delta calculation successful")

    def load_data_into_target(self):
        exec_log.info("started loading data into target ")

        target_cols = self.config.get(TARGET, COLS)
        schema = self.config.get(TARGET, SCHEMA)
        table = self.config.get(TARGET, TABLE)
        values = self.config.get(TARGET, VALUES)


        rows_list = [tuple(x) for x in self.final_df[target_cols.split(",")].values]

        if not len(rows_list) > 0:
            exec_log.info("0 records to be inserted into target hence exiting.")
            return

        exec_log.info("trying to insert {0} records into target table".format(str(len(rows_list))))

        for i in chunk_ranges(len(rows_list), self.row_commit_frequency):
            if not self.use_multi_processes:
                exec_log.info("calling upsert data")

                self.upsert_data(schema, table, target_cols, values, rows_list[i[0]:i[1]])
            else:

                job = mp.Process(target=self.upsert_data,
                                 args=(schema, table, target_cols, values, rows_list[i[0]:i[1]]))
                self.processes.append(job)
                job.start()

                exec_log.info("started process with pid {0} for the range {1}-{2}".format(str(job.pid()), str(i[0]), str(i[1])))

        if self.use_multi_processes:
            for job in self.processes:
                job.join()

            exec_log.info("all processes finished successfully")

    def upsert_data(self, schema, table, target_cols, values, data, new_correction = False):
        exec_log.info("inserting data into table")
        try:

            insert_df_into_table(self.tgt_connection if new_correction else OBDConnection(self.config.get(TARGET, DB_NAME)).connect_to_db(), schema, table, target_cols, values, data)
            self.last_run_details.update_last_run("(select max(SESSION_TS) from STGE.WEBSITE_SESSION)")

        except Exception as e:
            exec_log.info("error occurred rolling back changes")
            exec_log.info(str(e))
            exec_log.info(str(e.__class__))
            self.tgt_connection.rollback()
            self.src_connection.rollback()
            exec_log.info("changes rolled back")

        finally:
            exec_log.info("closing connections")
            self.src_connection.close()
            self.tgt_connection.close()
            exec_log.info("connections closed.")
