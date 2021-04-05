import logging
import os
import sys
from datetime import datetime

logging.basicConfig()

generic_logger_name = "unnamed_logger"


def get_logger(name=generic_logger_name):
    """ Returns a logger with the specified name, creating it if necessary. The default value for the log name is "unnamed_logger".

    Only outputs log to Stream/Console, hence otherwise please use the initialize_logging() method for logging to file logs.

    - :param name: name of logging object (to be printed as a part of log output). Default="unnamed_logger"
    - :return: logger, simple logging object
    """
    logger = logging.getLogger(name)
    formatter = log_formatter(app_name=name)
    handler = console_handler(formatter)
    logger.addHandler(handler)
    return logger


def initialize_logging(app_name,
                       local_log_path,
                       log_file_name=None,
                       log_level=logging.DEBUG,
                       third_party_loggers=None,
                       workflow_name=None,
                       workflow_id=None,
                       workflow_instance_id=None):
    """
    Return two loggers for state and execution logging.
    State logger in order to log high level changes and updates to log (operational status changes).
    Execution logger in order to log low level output from executing code.

    By default this will output logging to console/stream and log file. If no filename suggested then,
    the default logging file name will be chosen.

    :param app_name: name of the application and/or project.
    :param local_log_path: absolute path to where log files should be saved.
    :param log_file_name: name of the log file produced.
    :param log_level: log level object from logging library (e.g logging.DEBUG). Default is DEBUG level.
    :param third_party_loggers: (optional) if any other loggers exist in application, this will ensure their log output matches overall format,
    log level and to the same console or log file. Please note this should be the name of the logger(s).
    :param workflow_name: name of the workflow
    :param workflow_id: (optional) unique id of the Enterprise data platform/renovate factory.
    :param workflow_instance_id:  (optional) instance of the workflow
    :return: state_logger, exec_logger: two logging objects
    """

    def validate_inputs():
        """
        Runs a set of validation rules on inputs before execution

        :return: True if all rules passed or false if one rile fails
        """
        # Circular class dependency issue here where the functions from until_functions cannot be used.##

        from app.common.utils.util_functions import not_none, not_empty
        return (
            not_none(param=app_name) and
            not_none(param=local_log_path) and
            not_empty(param=app_name) and
            not_empty(param=local_log_path)

        )

    def create_logger():

        state_logger = logging.getLogger('State')
        exec_logger = logging.getLogger('Execution')

        if not third_party_loggers:
            third_party_found_loggers = []
        else:
            third_party_found_loggers = [logging.getLogger(logger) for logger in third_party_loggers]

        loggers = [state_logger, exec_logger] + third_party_found_loggers

        for logger in loggers:
            if isinstance(log_level, str):
                logger.setLevel(log_level.upper())
            else:
                logger.setLevel(log_level)

        formatter = log_formatter(app_name=app_name, log_level=log_level, workflow_name=workflow_name,
                                  workflow_id=workflow_id, workflow_instance_id=workflow_instance_id)
        log_file_handler = file_handler(formatter, local_log_path, log_file_name)
        console_print_handler = console_handler(formatter)

        for handler in [log_file_handler, console_print_handler]:
            for logger in loggers:
                logger.addHandler(handler)
                logger.propagate = False
        return state_logger, exec_logger

    return (
        create_logger()
        if validate_inputs()
        else sys.exit("Function initialize_logging input(s) not in compliance with function requirements.")
    )


def console_handler(formatter):
    """
    Logging handler for output to stream/console.

    :param formatter: Logging formatter object
    :return: handler: StreamHandler object
    """
    handler = logging.StreamHandler()
    handler.setFormatter(formatter)
    return handler


def file_handler(formatter, path, filename=None):
    """
    Logging handler for log output to a file.

    :param formatter: logging formatter object
    :param path: absolute path to where the log filename needs to be saved.
    :param filename: (optional) filename for log file. IF not set, then default log file name of date will be used.
    :return: handler: FileHandler object
    """
    if not filename:
        filename = datetime.now().strftime(('%Y-%m-%d_%H-%M-%S') + '.log')
    absolute_path_logfile = os.path.join(path, filename)
    handler = logging.FileHandler(absolute_path_logfile)
    handler.setFormatter(formatter)
    return handler


def log_formatter(app_name=generic_logger_name, log_level=logging.DEBUG, workflow_name=None, workflow_id=None,
                  workflow_instance_id=None):
    """
    Return the formatting of how the logger will output logs.
    If anything else but logging.DEBUG, then there will be no details on ode execution stack output. Otherwise if logging.DEBUG chosen then you will get execution stack output.
    :param app_name: name of the application and/or project.
    :param log_level: log level object from logging library (e.g logging.DEBUG). Default is DEBUG level.
    :param workflow_name: (optional) name of the workflow
    :param workflow_id: (optional) unique id of the Enterprise data platform/renovate factory.
    :param workflow_instance_id:  (optional) instance of the workflow
    :return: format_obj: logging.Formatter object
    """

    format_string_config = "%(asctime)s | %(name)s | " + app_name
    simple_string_config = "%(levelname)s | %(message)s"
    debug_string_config = "{%(pathname)s.%(funcName)s:%(lineno)d} | %(levelname)s | %(message)s"
    config_separator = " | "
    current_config = format_string_config
    if workflow_name is not None:
        current_config = current_config + config_separator + workflow_name
    if workflow_id is not None:
        current_config = current_config + config_separator + workflow_id
    if workflow_id is not None:
        current_config = current_config + config_separator + workflow_id
    if workflow_instance_id is not None:
        current_config = current_config + config_separator + workflow_instance_id
    if log_level is logging.DEBUG:
        current_config = current_config + config_separator + debug_string_config
    else:
        current_config = current_config + config_separator + simple_string_config

    return logging.Formatter(current_config)
