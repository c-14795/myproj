import pyodbc

from app.constants import db2_query_constants


class LastRun:
    """This is a doc string"""

    def __init__(self, connection, program_id):
        self.cursor = connection.cursor()
        self.connection = connection
        self.program_id = program_id

    def get_last_run_ts(self):
        """Get the Last run timestamp value from STGE.LAST_RUN"""

        sql = db2_query_constants.SELECT_LAST_RUN_TS.format(self.program_id)
        self.cursor.execute(sql)
        last_run_ts = self.cursor.fetchone()[0]
        return last_run_ts if last_run_ts else False

    def update_last_run(self, last_run_ts):
        """update the Last run timestamp value in STGE.LAST_RUN based on Program ID"""

        sql = db2_query_constants.UPDATE_LAST_RUN_TS.format(last_run_ts, self.program_id)
        self.cursor.execute(sql)
        self.cursor.commit()

    def get_row_commit_frequency(self):
        """Get the Row Commit Frequency value from STGE.LAST_RUN"""

        sql = db2_query_constants.SELECT_ROW_COMMIT_FREQUENCY.format(self.program_id)
        self.cursor.execute(sql)
        row_commit_frequency_q = self.cursor.fetchone()[0]
        return row_commit_frequency_q if row_commit_frequency_q else False

    def get_recovery_data(self):
        """Get the Recovery Data value from STGE.LAST_RUN"""

        sql = db2_query_constants.SELECT_RECOVERY_DATA.format(self.program_id)
        self.cursor.execute(sql)
        recovery_data = self.cursor.fetchone()[0]
        return recovery_data if recovery_data else False

    def update_recovery_data(self, recovery_data):
        """update RecoveryData value in STGE.LAST_RUN based on Program ID,
         incase of job failure as a recovery mechanism"""

        sql = db2_query_constants.UPDATE_RECOVERY_DATA.format(recovery_data,self.program_id)
        self.cursor.execute(sql)
        self.cursor.commit()
