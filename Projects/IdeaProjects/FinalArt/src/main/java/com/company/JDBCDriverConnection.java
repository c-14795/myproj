package com.company;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;

public class JDBCDriverConnection {

     private Connection connect() {
        Connection conn = null;
        try {
    // this is generic you can use it for connecting to any database which uses jdbc
    // driver by changing the url user name pswd .
            String url = "jdbc:sqlite:memory";
            String user = "";
            String pswd = "";
    // create a connection to the database
            conn = DriverManager.getConnection(url, user, pswd);

            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return conn;
    }


     ResultSet executeQuery(PreparedStatement ps) throws SQLException {

        return ps.executeQuery();
    }

     ResultSet executeQuery(String sql) throws SQLException {
        Connection conn = this.connect();
        Statement stmt = conn.createStatement();
        return stmt.executeQuery(sql);

    }

    public void update(String id,String Attr1,String Attr2,String tableName) {

        String sql = "UPDATE "+tableName+" SET Attribute1 = ? , "
                + "capacity = ? "
                + "WHERE id = ?";

        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, Attr1);
            pstmt.setString(2, Attr2);
            pstmt.setString(3, id);
            // update
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


}