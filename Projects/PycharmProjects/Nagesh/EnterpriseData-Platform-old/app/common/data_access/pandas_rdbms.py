import pandas as pd
from app.constants import db2_query_constants


def fetch_data_as_df(connection, query):
    data = pd.read_sql(query, connection)
    return data if data else False


def select_distinct_data_as_df(connection, columns, schema, table, filter_exp=None):
    sql = db2_query_constants.SELECT_DISTINCT_WITH_WHERE.format(columns, schema, table, filter_exp) \
        if filter_exp else db2_query_constants.SELECT_DISTINCT_COLUMN.format(columns, schema, table)
    return fetch_data_as_df(connection=connection, query=sql)


def insert_df_into_table(connection, schema, table, columns, values, tuple_val):
    insert_sql = db2_query_constants.INSERT_INTO_MANY.format(schema, table, columns, values)
    cursor = connection.cursor()
    cursor.fast_executemany = True
    cursor.executemany(insert_sql, tuple_val)
    cursor.commit()
    rows = cursor.rowcount
    return rows
