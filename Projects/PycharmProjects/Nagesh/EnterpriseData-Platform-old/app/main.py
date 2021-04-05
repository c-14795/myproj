import sys
import logging
from datetime import  datetime

from app.common.utils.app_logger import initialize_logging
from app.data_warehouse.mobile_device_pipeline import MobileDevice


# python main.py <pipeline> <table> <table_config_file> <log_folder_path_same>

if __name__ == '__main__':
    args = sys.argv[1:]
    initialize_logging(app_name=args[0],
                       local_log_path=args[3],
                       log_file_name='-'.join([args[0],args[1],str(datetime.now().date()),'.log']), log_level=logging.DEBUG)
    if args[0] == 'STG_PIPELINE':
        pass
    elif args[0] == 'DW_PIPELINE':
        if args[1] == 'MOBILE_DEVICE':
            MobileDevice(args[2]).start()
        elif args[1] == 'MOBILE_WEBSITE':
            pass
        else:
            print("no other pipelines are written")
    elif args[0] == 'DR_PIPELINE':
        pass
    else:
        raise ValueError(
            "Invalid first argument passed {0}, should  be one of these 'STG_PIPELINE','DW_PIPELINE','DR_PIPELINE'")
