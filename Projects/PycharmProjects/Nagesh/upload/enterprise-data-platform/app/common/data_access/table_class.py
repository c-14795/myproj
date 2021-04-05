import pyodbc

class DB2TableSQL:
    '''This is a doc string'''
    def __init__(self, cursor, schema, name):
        self.cursor = cursor
        self.schema = schema
        self.name = name

    def table_exists(self):
        sql = 'SELECT DISTINCT TABSCHEMA, TABNAME FROM syscat.tables WHERE TABSCHEMA = \'{0}\' AND TABNAME = \'{1}\''.format(self.schema, self.name)
        cursor = self.cursor
        cursor.execute(sql)
        test = cursor.fetchone()
        if test is None:
            # print('Table does not exist') make into logging at some point
            return False
        elif self.name == test[1].strip():
            # print('{0}.{1} Exists'.format(self.schema, self.name))
            return True
        else:
            # print("Something else")
            return False

    def count_from_table(self, column = None, operator = None, value = None):
        self.column = column
        self.operator = operator
        self.value = value
        cursor = self.cursor
        if column != None and operator != None and value != None:
            sql = 'SELECT COUNT(*) FROM {0}.{1} WHERE {2} {3} \'{4}\''.format(self.schema, self.name, self.column, self.operator, self.value)
            cursor.execute(sql)
            MyCount = int(cursor.fetchone()[0])
            return(MyCount)
        else:
            sql = 'SELECT COUNT(*) FROM {0}.{1}'.format(self.schema, self.name)
            cursor.execute(sql)
            MyCount = int(cursor.fetchone()[0])
            return(MyCount)

    def count_distinct(self, column):
        self.column = column
        cursor = self.cursor
        sql = 'SELECT COUNT(DISTINCT {0}) FROM {1}.{2}'.format(self.column, self.schema, self.name)
        cursor.execute(sql)
        MyCount = int(cursor.fetchone()[0])
        return(MyCount)

    #SELECT_FUNCTIONS

    def select_from_table(self, column = None, operator = None, value = None, limit = 100):
        self.column = column
        self.operator = operator
        self.value = value
        self.limit = limit
        cursor = self.cursor
        if column != None and operator != None and value != None:
            sql = 'SELECT * FROM {0}.{1} WHERE {2} {3} \'{4}\' LIMIT {5}'.format(self.schema, self.name, self.column, self.operator, self.value, self.limit)
            cursor.execute(sql)
            src = cursor.fetchall()
            return src
        else:
            sql = 'SELECT * FROM {0}.{1} LIMIT {2}'.format(self.schema, self.name, self.limit)
            cursor.execute(sql)
            src = cursor.fetchall()
            return src

    def select_single_value(self, columns ='*', qry_col = None, operator = None, value = None):
        self.columns = columns
        self.qry_col = qry_col
        self.operator = operator
        self.value = value
        cursor = self.cursor
        if qry_col != None and operator != None and value != None:
            sql = 'SELECT {0} FROM {1}.{2} WHERE {3} {4} \'{5}\''.format(self.columns, self.schema, self.name, self.qry_col, self.operator, self.value)
            cursor.execute(sql)
            src = cursor.fetchall()
            return src
        else:
            sql = 'SELECT {0} FROM {1}.{2}'.format(self.columns, self.schema, self.name)
            cursor.execute(sql)
            src = cursor.fetchall()
            return src            


#DELETE_FUNCTIONS
    def truncate_table(self):
        cursor = self.cursor
        sql = 'TRUNCATE TABLE {0}.{1} IMMEDIATE'.format(self.schema, self.name)
        print(sql)
        cursor.execute(sql)
        rows = cursor.rowcount
        cursor.commit()
        return(rows)

    def delete_with_where(self, column, operator, value, limit):
        '''
        Uses schema and table name provided in DB2_Table_SQL and adds 
        column, operator, value, limit to create the sql script 
        'DELETE FROM {schema}.{table} WHERE {column} {operator} \'{value}\' LIMIT {limit}'
        '''
        self.column = column
        self.operator = operator
        self.value = value
        self.limit = limit
        sql = 'DELETE FROM {0}.{1} WHERE {2} {3} \'{4}\' LIMIT {5}'.format(self.schema, self.name, self.column, self.operator, self.value, self.limit)
        cursor = self.cursor
        cursor.execute(sql)
        rows = cursor.rowcount
        cursor.commit()
        return(rows)
    
    #Insert Function
    def insert_into_table(self, column, value, tuple_val):
        '''
        Insert tuple data into a table 
         
        '''
        self.column = column
        self.value = value
        self.tuple_val = tuple_val
        insert_sql = 'INSERT INTO {0}.{1} ({2}) values ({3})'.format(self.schema, self.name,self.column,self.value)
        cursor = self.cursor
        cursor.executemany(insert_sql,tuple_val)
        cursor.commit()
        rows = cursor.rowcount
        return rows

    
    def fetch_all_from_table(self, columns):
        '''
        select columns from a table without any limit 
         
        '''
        self.columns = columns
        cursor = self.cursor
        sql = 'SELECT {0} FROM {1}.{2}'.format(self.columns,self.schema, self.name)
        cursor.execute(sql)
        src = cursor.fetchall()
        if src is None:
            return False
        else:
            return src

    
        
      

    


