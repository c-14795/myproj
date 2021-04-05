import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.InitialContext;


/*
* Provides database connection and all database related stuff happens here.
* */
public class JDBCDriverConnection {


    private Connection connect() {
        Connection conn = null;
        javax.sql.DataSource ds = null;
        Props prop = new Props();
        java.util.Properties props = prop.getColNamesFromPropertiesFile("project.properties");
        Hashtable env = new Hashtable();
        env.put(Context.INITIAL_CONTEXT_FACTORY,
                "weblogic.jndi.WLInitialContextFactory");
        env.put(Context.PROVIDER_URL,
                //   "t3://sgapps8.americas.corp.timex.com:8001");
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


    /**
     * Specific  method for updating two columns(Attribute 1 , Attribute2) in the specified table with the unique identifier for the row in that table.
     *
     * @param Attr1     Value for the Attribute1 col
     * @param Attr2     Value for the Attribute2 col
     * @param id        Unique row identifier value
     * @param tableName table name in which it should be updated.
     */
    void update(String id, String Attr1, String Attr2, String tableName) throws SQLException {
        String sql = "UPDATE " + tableName + " SET Attribute1 = ? , "
                + "Attribute2 = ? "
                + "WHERE Warehouse_id = ?";


        Connection conn = this.connect();
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, Attr1);
        pstmt.setString(2, Attr2);
        pstmt.setString(3, id);
        pstmt.executeUpdate();


    }

}
