package com.company;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.net.UnknownHostException;
import java.util.*;
import java.net.InetAddress;

import oracle.jdbc.OracleConnection;

public class DataSourceClient {
    public static void main(String[] args) {
        OracleConnection conn = null;
        javax.sql.DataSource ds = null;
        Hashtable<String, String> env = new Hashtable<>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");

        try {
            env.put(Context.PROVIDER_URL, "t3://" + InetAddress.getLocalHost().getHostAddress() + ":7214");
            Context context = new InitialContext(env);
            ds = (javax.sql.DataSource) context.lookup("yourname");
            conn = (OracleConnection) ds.getConnection();
            System.out.println("Connection object details : " + conn);
            conn.close();
        } catch (Exception ex) {
            //handle the exception
            ex.printStackTrace();
        }
    }
}

