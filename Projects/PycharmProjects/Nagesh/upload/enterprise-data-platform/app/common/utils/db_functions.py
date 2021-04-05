from app.constants import db2_query_constants


def execute_query(cursor, query):
    cursor.execute(query)
    return cursor


def fetch_top_record(cursor):
    return cursor.fetchone()


def check_multiple_table_exists_in_db2(connection, *table_names_with_schema):
    tables_not_in_db = []
    for table in table_names_with_schema:
        schema, name = table.split(".")
        result = fetch_top_record(
            execute_query(connection.cursor(), db2_query_constants.CHECK_TABLE_EXISTS.format(schema, table)))
        if result is None or name != result[1].strip():
            tables_not_in_db.append(table)
    return tables_not_in_db


def table_count(connection, schema, table):
    return int(
        fetch_top_record(execute_query(connection.cursor(), db2_query_constants.COUNT_OF_TABLE.format(schema, table)))[
            0])


def table_count_with_filter(connection, schema, table, filter_expression):
    return int(fetch_top_record(
        execute_query(connection.cursor(),
                      db2_query_constants.COUNT_OF_TABLE_WITH_FILTER.format(schema, table, filter_expression)))[0])


def select_distinct_from_table(connection, schema, table, columns):
    sql = db2_query_constants.SELECT_DISTINCT_COLUMN.format(columns, schema, table)
    cursor = execute_query(connection.cursor(), sql)
    rows = cursor.fetchall()
    return rows
