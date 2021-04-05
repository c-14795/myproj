import logging
import os

log = logging.getLogger("Studio-UtilFunctions")


def not_none(param):
    """
    Checks if a parameter is not None and writes to log if false


    :param param: Input parameter to check
    :return: True if not None and false if None
    """

    if param is not None:
        return True
    else:
        log.error("Error: Input must be different from None")
        return False


def not_empty(param):
    """
    Checks if a parameter is not empty and writes to log if false

    :param param: Input parameter to check
    :return: True if not empty and false if empty
    """

    if len(param) != 0:
        return True
    else:
        log.error("Error: Input must not be empty")
        return False


def check_if_string_represents_init(string):
    """
    Quick method for checking if a string is holding an int/number.
    :param string: string tupe of int number
    :return: True if int, otjerwise False
    """
    try:
        int(string)
        return True
    except ValueError:
        return False


def chunk_ranges(size_of_iterable, chunk_size):
    """split range for any iterable which can  be accessed by index
    :param size_of_iterable: of list/any iterable
    :param chunk_size: chunk size
    """
    return ((i, i + chunk_size) for i in range(0, size_of_iterable, chunk_size))
