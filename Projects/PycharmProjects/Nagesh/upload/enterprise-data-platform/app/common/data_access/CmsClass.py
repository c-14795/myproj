
from datetime import date, datetime
import logging
import os

class CmsRetention:

    def create_log_file(self, logFilename, level):
        """ function to create log in  the name of given input"""
        curr_date = date.today()
        my_file_name = str('/applogs/CMS_Delete/' + logFilename + str(curr_date) + '.log')
        logging.addLevelName(15,'Loop_info')
        logging.basicConfig(format='%(asctime)s : %(levelname)s : %(message)s', filename = my_file_name, level = int(level), datefmt='%Y-%m-%d %H:%M:%S')

    def select_from_table(self, connection, schema, table):
        cursor = connection.cursor()
        sql = 'SELECT DISTINCT TABSCHEMA, TABNAME FROM syscat.tables WHERE TABSCHEMA = \'{0}\' AND TABNAME = \'{1}\''.format(schema, table)
        cursor.execute(sql)
        test = cursor.fetchone()
        if test is None:
            # print('Table does not exist') make into logging at some point
            return False
        elif table == test[1].strip():
            # print('{0}.{1} Exists'.format(self.schema, self.name))
            return True
        else:
            # print("Something else")
            return False

    def check_table_exists(self, connection, schema, inpList):
        """ will check if all the given table exists or not """
        for table_ in inpList:
            if "." in table_:
                splitName=table_.split(".")
                schema=splitName[0]
                table_=splitName[1]
                if self.select_from_table(connection, schema, table_)==True:
                    logging.debug('Table - {0}.{1} Exists'.format(schema, table_))
                else:
                    return table_

            else:
                if self.select_from_table(connection, schema, table_)==True:
                    logging.debug('Table - {0}.{1} Exists'.format(schema, table_))
                else:
                    return table_
                    
        return 'Completed'


    def check_single_table_exists(self, connection, schema, table):

        """ will check if all the given table exists or not """
        if self.select_from_table(connection, schema, table) == True:
            logging.debug('Table - {0}.{1} Exists'.format(schema, table))
            return 'Completed'
        else:
            return table
        

    def CheckTimeperiod(self,numberOfweeks,endTime):
        if numberOfweeks < 364 : 
            logging.warning('The time period should be atleast 250 weeks or greater')
            print({'JOB_STATUS':0})
            os._exit(1)

        now = datetime.now()
        current_time = now.strftime("%H:%M")
        if endTime <= str(current_time):
            logging.warning('Job ended due to End time is lesser than are equal to start time')
            print({'JOB_STATUS':0})
            os._exit(1)

    def CountFromTable(self,connection,schema,Table,column, date):
    
        cursor = connection.cursor()
        sql = "SELECT COUNT(*) FROM {0}.{1} WHERE {2} < '{3}'".format(schema, Table, column, date)
        cursor.execute(sql)
        MyCount = int(cursor.fetchone()[0])
        return(MyCount)
    

    def DeleteFromTableWhere(self,connection,schema,Table, column,date, limit):
        '''
        Uses schema and table name provided in DB2_Table_SQL and adds 
        column, operator, value, limit to create the sql script 
        'DELETE FROM {0}.{1} WHERE {2} {3} \'{4}\' LIMIT {5}'.format(self.schema, self.name, self.column, self.operator, self.value, self.limit)
        '''
        sql = "DELETE FROM (SELECT * FROM {0}.{1} WHERE {2} < '{3}' FETCH FIRST {4} ROWS ONLY)".format(schema,Table,column,date, limit)
        cursor = connection.cursor()
        cursor.execute(sql)
        rows = cursor.rowcount
        cursor.commit()
        return(rows)

    def primaryIds(self,connection,schema,primTab,selCol,wherCol,numberOfweeks,deletdate):
        sql ="select {}  from {}.{}  where {} < '{}' ".format(selCol,schema,primTab,wherCol,deletdate)
        #print(sql)
        cursor=connection.cursor()
        cursor.execute(sql)
        cursor.commit()
        outputList= [i[0] for i in cursor.fetchall()]
        return outputList


    def extractIds(self,connection,schema,Table,selCol,whercol,wherIdList):
        ## extract ids to be deleted 
        if len(wherIdList)>0:
            try:
                sql= "select {0} from {1}.{2} where {3} in {4} ".format(selCol,schema,Table,whercol,tuple(wherIdList))
                cursor=connection.cursor()
                cursor.execute(sql)
                cursor.commit()
                outputList=[i[0] for i in cursor.fetchall()]
                return outputList

            except Exception as e:
                logging.warning('Something went wrong on the %s.%s with error message %s', schema,Table,e)
                print({'JOB_STATUS':0})

        else:
            logging.debug('{} Record(s) to be deleted from: {}.{}'.format(0,schema,Table))
            return []

 

    def countRecords(self,connection,schema,Table,whercol,wherIdList):
        sql='select count(*) from {}.{} where {} in {}'.format(schema,Table,whercol,tuple(wherIdList))
        cursor=connection.cursor()
        cursor.execute(sql)
        result =cursor.fetchone()
        return result[0]

            
    def deleteRecordsTable(self,connection,schema,Table,whercol,wherIdList,limit):
        ## delete records from table where in list 
        Loop_number=1
        Loop=0
        deletcount=0
        if len(wherIdList)>0:
            while Loop_number==1:
                try:
                    if self.countRecords(connection,schema,Table,whercol,wherIdList)>0:
                        Loop+=1
                        sql= 'delete from (SELECT * FROM {}.{} where {} in {} fetch first {} rows only)'.format(schema,Table,whercol,tuple(wherIdList),limit)
                        cursor=connection.cursor()
                        cursor.execute(sql)
                        cursor.commit()
        
                        deletcount=deletcount+cursor.rowcount

                        logging.debug('{} Record(s) deleted successfully from: {}.{} on Loop - {}'.format(cursor.rowcount,schema,Table,Loop ))

                    else:
                        logging.log(15,'{} Total Record(s) deleted successfully from: {}.{}'.format(deletcount,schema,Table ))
                        Loop_number=0

                except Exception as e:
                    Loop_number=0
                    logging.debug('ID list: %s', wherIdList)
                    logging.warning('Something went wrong on the %s.%s on Loop %s, %s  records have been deleted. with error message %s', schema,Table,Loop, deletcount,e)
                    print({'JOB_STATUS':0})
                    os._exit(1)
            return deletcount

        else:
            logging.debug('{} Record(s) to be deleted from: {}.{}'.format(deletcount,schema,Table))
            return deletcount