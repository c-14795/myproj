import sys
import logging
from datetime import datetime

from app.common.utils.app_logger import initialize_logging
from app.data_warehouse.main import pipelines as dw_pipelines
from app.staging.main import pipelines as stg_pipelines

# python main.py <pipeline> <table> <table_config_file> <log_folder_path_same>
# add credentials config ini files to system path using sys.path.append('<folder path or file path>')

if __name__ == '__main__':
    args = sys.argv[1:]
    initialize_logging(app_name=args[0],
                       local_log_path=args[3],
                       log_file_name='-'.join([args[0], args[1], str(datetime.now().date()), '.log']),
                       log_level=logging.DEBUG)

    pipelines = {
        "STG_PIPELINE":stg_pipelines,
        "DW_PIPELINE":dw_pipelines,
        "DR_PIPELINE" : None
    }

    if not pipelines.get(args[0]):
        raise ValueError(
            "Invalid first argument passed {0}, should  be one of these 'STG_PIPELINE','DW_PIPELINE','DR_PIPELINE'")

    exec_log = logging.getLogger("Execution")

    try:
        pipelines[args[0]][args[1]](args[2]).start()
    except Exception as e:
        exec_log.info(str(e))
        exec_log.info(str(e.__class__))