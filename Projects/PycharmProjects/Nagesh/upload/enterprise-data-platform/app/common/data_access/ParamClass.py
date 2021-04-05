class Params:
    '''This is a doc string'''

    def __init__(self, file, job, param):
        self.file = file
        self.job = job
        self.param = param

        import configparser
        self.config = configparser.ConfigParser()


    def get_param(self):

        self.config.read('./commonFiles/' + self.file) 
        curr_val = self.config.get(self.job, self.param)
        return curr_val


    def update_param(self, value):

        self.config.read('./commonFiles/' + self.file) 
        self.config.set(self.job, self.param , str(value))
        self.config.write(open('./commonFiles/' + self.file, 'w'))
        print('Param Updated')
