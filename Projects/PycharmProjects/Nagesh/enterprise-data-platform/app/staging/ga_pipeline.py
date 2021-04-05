import configparser
import logging
import os
import pytz
from google.cloud import bigquery
from datetime import timedelta, datetime

from app.common.data_access.last_run_metadata import LastRun
from app.common.data_access.connections import OBDConnection
from app.common.data_access.pandas_rdbms import *
from app.constants.config_constants import *
from app.staging.ga_query import  query_dict

exec_log = logging.getLogger("Execution")
state_log = logging.getLogger("State")

class GaSession:
    def __init__(self, config_path):
        config = configparser.ConfigParser()
        config.read(config_path)
        self.config = config
        self.stg_connection = None
        self.stg_cursor = None
        self.last_run_details = None
        self.last_run_ts = None
        self.last_run_recovery_data = None
        self.row_commit_frequency = None
        self.big_query_client = None
        self.ga_data = None

    def start(self):
        exec_log.info("starting the pipeline")
        self.setup_stg_connection()
        self.setup_last_run_meta_data()
        self.setup_ga_auth()
        self.extract_ga_data()
        self.transform_and_load_into_file()
        exec_log.info("pipeline completed successfully")

    def setup_stg_connection(self):
        exec_log.info("setting up stage connection")
        self.stg_connection = OBDConnection(self.config.get(STAGE, DB_NAME)).connect_to_db()
        self.stg_cursor = self.stg_connection.cursor()
        exec_log.info("stage connection setup successful")

    def setup_ga_auth(self):
        exec_log.info("setting up google auth")
        os.environ["GOOGLE_APPLICATION_CREDENTIALS"] = self.config.get(GA, JSON_URL)
        self.big_query_client = bigquery.Client()
        exec_log.info("google auth setup successful")

    def extract_ga_data(self):
        exec_log.info("extracting ga data")
        from_date = self.last_run_ts.strftime('%Y%m%d')
        to_date = (self.last_run_ts + timedelta(days=7)).strftime('%Y%m%d')
        sql = query_dict[self.config.get(GA, QUERY)].format(from_date, to_date, self.last_run_ts)
        self.ga_data = self.big_query_client.query(sql).result()
        exec_log.info("successfully extracted GA data")

    def setup_last_run_meta_data(self):
        exec_log.info("fetch last run meta data")
        self.last_run_details = LastRun(self.stg_connection, self.config.get(STAGE, PROGRAM_ID))
        self.last_run_ts = self.last_run_details.get_last_run_ts()
        self.last_run_recovery_data = self.last_run_details.get_recovery_data()
        self.row_commit_frequency = self.last_run_details.get_row_commit_frequency()
        exec_log.info("last run meta data fetched  successfully")

    def transform_and_load_into_file(self):
        exec_log.info("starting writing into file")
        file_path = self.config.get(GA, TARGET_FILE_PATH)
        last_run_timestamp = pytz.UTC.localize(self.last_run_ts)

        with open(os.path.join(file_path, str(datetime.now().date()) + '.txt'), 'w') as f:
            count = 0
            for row in self.ga_data:
                val = row["SessionId"], datetime.strptime(row["Date"], "%Y%m%d").strftime("%Y-%m-%d"), row[
                    "visitStartTime"].strftime('%Y-%m-%d %H:%M:%S'), row["Sessions"], row["Bounces"], row["Goal1"], \
                      row["Transactions"], row["Revenue"], row["WebsiteVisitorsID"], row["NewWebsiteVisitors"], row[
                          "UserAccount"], row["TransactionID"], row["ChannelGrouping"], row["landingPage"][:250] if row[
                    "landingPage"] else row["landingPage"], \
                      row["PageView"], row["SessionDuration"], row["DeviceCategory"], row["mobiledevice"], row[
                          "TrafficSource"], row["TrackingID"], row["SourceSystemID"]

                if last_run_timestamp < row["visitStartTime"]:
                    last_run_timestamp = row["visitStartTime"]
                    local_datetime = last_run_timestamp.strftime('%Y-%m-%d %H:%M:%S')
                f.write("{0}\n".format(val).replace("'", '"').replace(',', '|'))
                count += 1
            exec_log.info("successfully written to file")
            self.last_run_details.update_last_run(local_datetime)
            exec_log.info("Number of Records Read/Write into File - " + str(count))
