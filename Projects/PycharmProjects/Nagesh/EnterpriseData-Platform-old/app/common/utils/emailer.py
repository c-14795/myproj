import os
import smtplib
from email.mime.multipart import MIMEMultipart
from email.mime.text import MIMEText
from socket import error as SocketError
from smtplib import SMTPException

from app.common.utils.util_functions import *

from app.common.utils.app_logger import get_logger

class_logger = get_logger("Studio-Emailer")


def send(sender, recipient, subject, message, file_attachment=None):
    """
    Initialize connection and send email
    :param sender: sender
    :param recipient: recipient
    :param subject: subject
    :param message: message
    :param file_attachment: file to be attached
    """
    server = crete_email_connection()
    send_email(sender=sender, recipients=[recipient], server=server, subject=subject, message=message,
               file_attachment=file_attachment)
    return close_email_server(server)


def crete_email_connection(address="local", port=""):
    """
    Method that creates an SMTP onject that will be used to send emails.

    :param address: Address of the smtp server
    :param port: Port of smtp server
    :return: The SMTP server object from the smtplib python library
    """

    def validate_inputs():
        """
        Runs a set of validations rules on inputs before execution

        :return: True if all rules passed or false if one rule fails
        """

        return (not_none(param=address) and
                not_none(param=port) and
                not_empty(param=address) and
                not_empty(param=port) and
                check_if_string_represents_init(string=port)

                )

    def make_connection():
        """
        Method that creates an SMTP object that will be used to send emails.

        """
        try:
            class_logger.debug("Connecting to email server " + str(address) + " on port " + str(port))
            return smtplib.SMTP(address, port)
        except SocketError as e:
            class_logger.exception("Socket.error: Unable to connect to provided address %s on port %s. Details: %s" % (
                address, port, e.message))
            raise SocketError

    if validate_inputs():
        return make_connection()
    else:
        class_logger.error("Function create_email_connection inputs not in compliance with function requirements")
        raise Exception("Function create_email_connection inputs not in compliance with function requirements")


def send_email(sender, recipients, server, subject, message, file_attachment=None):
    """
    Method to send an email or without a file attachment.

    :param sender: The email that will send an email.
    :param recipients: The lists of the recipeents emails
    :param server:  The SMTP server object
    :param subject: The subject of the email.
    :param message: The message to send.
    :param file_attachment: (optional) The file to be attached to the email
    :return:
    """

    def validate_inputs():
        """
        Runs a set of validations rules on inputs before execution

        :return: True if all rule passed or false if one rule fails
        """

        return (
                not_none(param=sender) and
                not_none(param=recipients) and
                not_none(param=server) and
                not_none(param=subject) and
                not_none(param=message) and
                not_empty(param=sender) and
                not_empty(param=recipients) and
                not_empty(param=server) and
                not_empty(param=subject) and
                not_empty(param=message)
        )

    def send_the_email():
        """
        Method to send an email with or without a file attachment.

        """

        msg = MIMEMultipart()
        msg['FROM'] = sender
        msg['To'] = ";".join(recipients)
        msg['Subject'] = "Enterprise Data Platform - " + str(subject)
        msg.attach(MIMEText(message, 'plain'))

        if file_attachment:
            attachment = MIMEText(open(file_attachment).read())
            attachment.add_header("Content-Disposition", 'attachment', filename=os.path.basename(file_attachment))
            msg.attach(attachment)
        else:
            None

        class_logger.debug("sending message - " + " to " + ";".join(recipients))
        try:
            server.sendmail(sender, recipients, msg.as_string())
        except SMTPException as e:
            class_logger.exception("SMTPException: %s" % e.message)
            raise SMTPException

    if validate_inputs():
        send_the_email()
    else:
        class_logger.error("Function create_email_connection inputs not in compliance with function requirements")
        raise Exception("Function create_email_connection inputs not in compliance with function requirements")


def close_email_server(email_server_obj):
    """
    Method to close the smtp server object

    :param email_server_obj: The SMTP server object
    :return: ret_code: status of closing down connection to email server.
    """

    class_logger.debug("Closing the email server")
    try:
        ret = email_server_obj.quit()
        return ret
    except Exception as e:
        class_logger.error("Exception: %s" % e.message)
        raise Exception("Function close_email_server encountered an unexpected error.")
