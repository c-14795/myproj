import abc


class StagePipeline(object):
    __metaclass__ = abc.ABCMeta

    def __init__(self):
        self.src_connection = None
        self.src_cursor = None
        self.tgt_connection = None
        self.tgt_cursor = None
        self.last_run_details = None
        self.last_run_ts = None
        self.last_run_recovery_data = None
        self.row_commit_frequency = None
        self.src_data_frame = None
        self.tgt_data_frame = None
        self.final_df = None
        self.config = None

    @abc.abstractmethod
    def setup_source_connection(self):
        pass

    @abc.abstractmethod
    def setup_target_connection(self):
        pass

    @abc.abstractmethod
    def transform_and_load(self):
        pass

    @abc.abstractmethod
    def start(self):
        pass
