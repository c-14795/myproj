import logging
import sys
from app.common.utils.emailer import *

log = logging.getLogger("email_handler")


def error_exit_program(message, success=False):
    if success:
        log.info(message)
    else:
        log.error(message)
        sys.exit("The application was terminated {0}".format(message))


def exit_with_notification(message, sender, recipient, subject, send_email=True, success=False, file_attachment=None):
    """
    Sends an email before terminating the application
    :param message: Email message
    :param sender: Sender email
    :param recipient: Recipient email
    :param subject: Subject of the email
    :param send_email: Controls if the email should be sent
    :param success: If success mail notification and message to send in mail otherwise false for failure case
    :param file_attachment: File path of the file to be attached in email
    :return:
    """
    if send_email:
        log.info('Sending email with message: ' + message + ' to ' + recipient)
        send(sender, recipient, subject, message, file_attachment)
    return error_exit_program(message, success)
