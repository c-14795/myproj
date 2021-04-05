
from cryptography.fernet import Fernet

class Encryption(object):
    @staticmethod
    def generate_new_key():
        key = Fernet.generate_key()
        # file = open('./common/config_files/key/key.key', 'wb')
        file = open("C:\\Users\\3.NMaddineni.3a\\git\\enterprise-data-platform\\app\\config_files\\key\\key.key", 'wb')
        file.write(key)
        file.close()
    @staticmethod
    def get_key():
        # file = open('./common/config_files/key/key.key', 'rb')
        file = open("C:\\Users\\3.NMaddineni.3a\\git\\enterprise-data-platform\\app\\config_files\\key\\key.key", 'rb')
        key = file.read()
        file.close()
        return key
    @staticmethod
    def encrypt(input_str):
        str_to_encrypt = input_str.encode() 
        f = Fernet(Encryption.get_key())
        encrypted = f.encrypt(str_to_encrypt)
        return str(encrypted, 'utf-8')
    @staticmethod
    def decrypt(encoded_str):
        f = Fernet(Encryption.get_key())
        decrypted = f.decrypt(encoded_str, ttl= None)
        decoded_str = str(decrypted, 'utf-8') 
        return decoded_str

