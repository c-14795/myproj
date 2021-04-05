import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import java.io.IOException;

import java.sql.SQLException;
import java.util.Objects;




public class SendRequests {

    private static final Tasks task = new Tasks();


    public String getXmlData(String URL, String filepath) throws IOException {

        return task.getXmlFromJson(URL, filepath);
    }


    public String postData(String sql, String url, String tableName, String idColName,
                           String colMapFilepath) throws IOException {

        String[] urlpost = new String[1];
        try {
            if (!Objects.equals(colMapFilepath, "")
                    ) {
                urlpost[0] = colMapFilepath;
                task.SendPostReq(sql, url, tableName, idColName, urlpost);
            } else {
                task.SendPostReq(sql, url, tableName, idColName);
            }


        } catch (SQLException e) {
            e.getErrorCode();
            System.out.println(e.getErrorCode());
            e.printStackTrace();
        }
        return "string";
    }
}
