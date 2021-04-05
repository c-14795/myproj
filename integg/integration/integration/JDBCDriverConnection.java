package com.timex.bt.integration;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import java.io.PrintWriter;
import java.io.StringWriter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;

import javax.sql.DataSource;


/*
* Provides database connection and all database related stuff happens here.
* */
public class JDBCDriverConnection {

    Connection conn = null;
    private Connection connect() {

        if(conn!=null) return conn;
        DataSource ds = null;
        Props prop = new Props();
        java.util.Properties props =
            prop.getColNamesFromPropertiesFile("/tmp/propsfile/BTDBproject.properties");
        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY,
                "weblogic.jndi.WLInitialContextFactory");
        env.put(Context.PROVIDER_URL,
                //   "t3://sgapps8.americas.corp.timex.com:8001");
                props.getProperty("ContextProviderUrl"));

        try {
            // System.setOut(new PrintStream(new BufferedOutputStream(new FileOutputStream("D:\\FileNameM1.txt")), true));
            Context context = new InitialContext(env);
            ds = (DataSource)context.lookup(props.getProperty("JNDIName"));
            conn = ds.getConnection();


            System.out.println("Connection object details : " + conn);

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(getStackTrace(ex));
        }
        return conn;
    }
    /*OLD Code with IP Address
    private Connection connect() {
        Connection conn = null;
        javax.sql.DataSource ds = null;
        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY,
                "weblogic.jndi.WLInitialContextFactory");
        env.put(Context.PROVIDER_URL,"t3://10.1.250.168:8001");
     //   "t3://sgapps8.americas.corp.timex.com:8001");

        try {
            System.setOut(new PrintStream(new BufferedOutputStream(new FileOutputStream("/tmp/propsfile/FileNameM1.txt")), true));
            Context context = new InitialContext();
        //    ds = (DataSource)context.lookup("jdbc/timexappsDataSource");
   ds = (javax.sql.DataSource)context.lookup("jndi/testappstgdvj");

            conn = ds.getConnection();
          //  java.util.Properties prop = new java.util.Properties();

            System.out.println("Connection object details : " + conn);

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(getStackTrace(ex));
        }
        return conn;
    }
 Old Code with IP Address*/


    /**
     * Executes the sql query and returns the result set.
     *
     * @param sql sql query to be executed.
     */

    ResultSet executeQuery(String sql) throws SQLException {
        Connection conn = this.connect();
        if (conn == null) {
            File F = new File("/tmp/propsfile/error.txt");
            boolean b;
            try {
                b = F.createNewFile();
            } catch (IOException e) {
            }
        }
        Statement stmt = conn.createStatement();
        return stmt.executeQuery(sql);


    }


    /**
     * Specific  method for updating two columns(Attribute 1 , Attribute2) in the specified table with the unique identifier for the row in that table.
     *
     * @param Attr1     Value for the Attribute1 col
     * @param Attr2     Value for the Attribute2 col
     * @param id        Unique row identifier value
     * @param tableName table name in which it should be updated.
     * @param idColName unique col Name.
     */
    void update(String id, String Attr1, String Attr2, String tableName,
                String idColName) throws SQLException {
        String sql =
            "UPDATE " + tableName + " SET Attribute1 = ? , " + "Attribute2 = ? " +
            "WHERE " + idColName + " = ?";


        Connection conn = this.connect();
        /*
        if(conn == null)
        {
            File F = new File("D:\\tmp\\propsfile\\error.txt");
            boolean b;
            try {
                b = F.createNewFile();
            } catch (IOException e) {
            }
        }
*/
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, Attr1);
        pstmt.setString(2, Attr2);
        pstmt.setString(3, id);
        pstmt.executeUpdate();
//        conn.close();

    }


    /**
     * Specific  method for updating one col in any table.
     *
     * @param Attr1     Value for the Attribute1 col
     * @param id        Unique row identifier value
     * @param tableName table name in which it should be updated.
     * @param idColName unique col Name.
     */
    void updateOneCol(String id, String Attr1, String tableName,
                      String idColName,
                      String updateColName) throws SQLException {
        String sql =
            "UPDATE " + tableName + " SET " + updateColName + " = ? , " +
            "WHERE " + idColName + " = ?";

        Connection conn = this.connect();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, Attr1);
        pstmt.setString(2, id);
        pstmt.executeUpdate();
      //  conn.close();


    }


    public String getStackTrace(final Throwable throwable) {
        final StringWriter stringWriter = new StringWriter();
        final PrintWriter printWriter = new PrintWriter(stringWriter, true);
        throwable.printStackTrace(printWriter);
        return stringWriter.getBuffer().toString();
    }
    
    
    public  void closeConnection()
    {
           if(conn!=null)
           {
               try {
                   conn.close();
               } catch (SQLException e) {
                   e.printStackTrace();
               }
           }
    }



}
