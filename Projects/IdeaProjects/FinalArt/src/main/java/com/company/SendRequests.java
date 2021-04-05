package com.company;


import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import java.io.IOException;
import java.sql.SQLException;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class SendRequests {

    private static final Tasks task = new Tasks();

    @WebMethod
    public String getXmlData(String URL, String filepath) throws IOException {

        return task.getXmlFromJson(URL, filepath);
    }

    @WebMethod
    public void postData(String sql, String url,String tableName,String idColName, String... colMapFilepath) throws IOException, SQLException {

        task.SendPostReq(sql, url,tableName,idColName, colMapFilepath);
    }

}
