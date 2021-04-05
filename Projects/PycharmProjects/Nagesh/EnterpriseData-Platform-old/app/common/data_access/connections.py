import pyodbc
import configparser

from app.common.data_access.encrypt_text import Encryption

config = configparser.ConfigParser()
config.read('./commonFiles/config/cnxn.ini')

print(config.sections()


class OBDConnection(object):
    def __init__(self, connection):
        self.connection = connection
        self.user = config.get(self.connection, 'username')
        self.enc_pass = config.get(self.connection, 'password')
        self.DSN = config.get(self.connection, 'dsn')

    def connect_to_db(self):
        password = Encryption.decrypt(bytes(self.enc_pass, 'utf-8'))
        return pyodbc.connect(DSN=self.DSN, UID=self.user, PWD=password)


