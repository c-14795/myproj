import java.sql.*;

import javax.sql.DataSource;

import javax.naming.Context;
import javax.naming.InitialContext;

import java.util.*;

import java.io.*;

import oracle.jdbc.OracleConnection;
import oracle.jdbc.xa.client.OracleXADataSource;


public class DataSourceClient {

    public static void main(String[] args) {
        Connection conn = null;
        DataSource ds = null;
        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY,
                "weblogic.jndi.WLInitialContextFactory");
        env.put(Context.PROVIDER_URL,
                //  "t3://sgapps8.americas.corp.timex.com:8001");
                "t3://192.168.2.7:7214");
        // "t3://124.123.41.79:7214");
        // "t3://10.100.52.162:8001");

        try {
            Context context = new InitialContext(env);
            ds =
                    (DataSource) context.lookup("JNDI/testCustomerDS");
            //(javax.sql.DataSource)context.lookup("jdbc/LocalSvcTblDataSource");
            conn = ds.getConnection();
            Properties prop = new Properties();

            System.out.println("Connection object details : " + conn);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
