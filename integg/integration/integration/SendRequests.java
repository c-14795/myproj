package com.timex.bt.integration;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.SQLException;

import java.util.HashMap;


public class SendRequests {

    private static final Tasks task = new Tasks();


    public String getXmlData(String url, String filepath) throws IOException {

        return task.getXmlFromJson(url, filepath);
    }


    public String postData(String sql, String url, String tableName, String idColName,
                           String colMapFilepath) throws IOException {

        String[] urlpost = new String[1];
        String resp = "";
        try {
            if (colMapFilepath != ""
                    ) {
                urlpost[0] = colMapFilepath;
                resp = task.SendPostReq(sql, url, tableName, idColName, urlpost);
            } else {
                resp = task.SendPostReq(sql, url, tableName, idColName);
            }


        } catch (SQLException e) {
            e.getErrorCode();
            System.out.println(e.getErrorCode());
            e.printStackTrace();
        }
        return resp;
    }

    //String tableNameForUpdate,String idColName
    public String sendReqAndUpdateOrders(String url) throws Exception {

        return RestCalls.HttpGetCall(url, new HashMap<String, Object>(), new HashMap<String, String>()).get("success").toString();

    }


}
