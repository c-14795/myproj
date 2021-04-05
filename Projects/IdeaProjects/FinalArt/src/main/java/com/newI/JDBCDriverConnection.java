package com.newI;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.sql.*;
import java.util.Hashtable;


/*
* Provides database connection and all database related stuff happens here.
* */
public class JDBCDriverConnection {


    private Connection connect() {
        Connection conn = null;
        javax.sql.DataSource ds = null;

        java.util.Properties props = Tasks.getProperties("db.properties");
        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY,
                "weblogic.jndi.WLInitialContextFactory");
        env.put(Context.PROVIDER_URL,

                props.getProperty("ContextProviderUrl"));

        try {
            Context context = new InitialContext(env);
            ds =
                    (javax.sql.DataSource) context.lookup(props.getProperty("JNDIName"));
            conn = ds.getConnection();


            System.out.println("Connection object details : " + conn);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return conn;
    }


    /**
     * Executes the sql query and returns the result set.
     *
     * @param sql sql query to be executed.
     */

    ResultSet executeQuery(String sql) throws SQLException {
        Connection conn = this.connect();
        Statement stmt = conn.createStatement();
        return stmt.executeQuery(sql);

    }

}



    /*private Connection connect() {


        try {

            Class.forName("oracle.jdbc.driver.OracleDriver");

        } catch (ClassNotFoundException e) {


            e.printStackTrace();
            return null;

        }


        Connection connection = null;


        try {

            connection = DriverManager.getConnection(
                    "jdbc:oracle:thin:@localhost:1521:xe", "system", "password");

        } catch (SQLException e) {


            e.printStackTrace();
            return connection;

        }

        if (connection == null) {
            return connection;
        }

return connection;

}*/
