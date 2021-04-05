package com.company;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class Tasks {

    public String getXmlFromJson(String url, String filePath) throws IOException {

        Map<String, Object> respmap = RestCalls.HttpGetCall(url, new HashMap<String, Object>(), new HashMap<String, String>());
        String xmlData = map2Xml(respmap, "something");
        writeToFile(xmlData, filePath);
        return xmlData;

    }


    public void SendPostReq(String sql, String url,String tableName,String idColName,String... colMapFilePath) throws IOException, SQLException {
        if (colMapFilePath.length == 1)
            sendPostReq(sql, url,tableName,idColName, true, new HashMap<String, String>(), colMapFilePath);
        else sendPostReq(sql, url,tableName,idColName, false,new HashMap<String, String>());
    }


    private ResultSet getResultSet(String sql) throws SQLException {
        return new JDBCDriverConnection().executeQuery(sql);

    }

    private void sendPostReq(String sql, String url, String tableName,String idColName,boolean colMapEnabled, HashMap<String, String> headers, String... colMap) throws SQLException, IOException {

        JDBCDriverConnection connection = new JDBCDriverConnection();
        Properties properties = new Props().getColNamesFromPropertiesFile(colMap[0]);
        ResultSet rs = connection.executeQuery(sql);
        ResultSetMetaData metaData = rs.getMetaData();


        while (rs.next()) {

            HashMap<String, Object> json_map = new HashMap<String, Object>();

            if (colMapEnabled) {

                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    String colName = metaData.getColumnName(i);

                    json_map.put(properties.getProperty(colName, colName), rs.getObject(metaData.getColumnName(i)));

                }

            } else {
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    String colName = metaData.getColumnName(i);

                    //json_map.put(colName.replace("_", ""), rs.getObject(metaData.getColumnName(i)));
                    json_map.put(colName, rs.getObject(metaData.getColumnName(i)));

                }
            }

            Map<String,Object> resp = RestCalls.HttpFormPostCall(url, json_map, headers);
            String Attr1 = resp.get("success").toString()=="true"?"Y":"E";

            String Attr2 = ((com.google.gson.internal.LinkedTreeMap<String,Object>)((ArrayList)resp.get("data")).get(0)).get("message").toString();
            connection.update(json_map.get(idColName).toString(),Attr1,Attr2,tableName);
        }

    }


    private String arrayList2xml(ArrayList o) {
        StringBuilder resp = new StringBuilder();
        for (Object anO : o) {
            if (!(anO instanceof Map)) resp.append(anO.toString());
            else resp.append(map2Xml((Map<String, Object>) anO, null));
        }
        return resp.toString();
    }

    private String map2Xml(Map<String, Object> respmap, String cons) {
        boolean end = true;
        StringBuilder resp = new StringBuilder();
        for (Map.Entry res : respmap.entrySet()) {
            resp.append("<").append(res.getKey());
            if (res.getValue() instanceof Map) {
                resp.append(">\n").append(map2Xml((Map<String, Object>) res.getValue(), null));
            } else if (res.getValue() instanceof ArrayList) {
                resp.append(">\n").append(arrayList2xml((ArrayList) res.getValue()));
            } else {
                if (res.getValue() != null) {
                    resp.append(">").append(res.getValue().toString());

                } else {
                    end = false;
                    resp.append("/>\n");
                }
            }
            if (end) resp.append("</").append(res.getKey()).append(">\n");
            end = true;

        }


        return cons != null ? "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
                "<root>\n" + resp.toString() + "\n" +
                "</root>" : resp.toString();
    }

    private void writeToFile(String s, String filePath) throws IOException {
        File f = new File(filePath);
        if (f.isFile() && !f.exists()) f.createNewFile();
        PrintWriter printWriter = new PrintWriter(f);
        printWriter.write(s);
        printWriter.flush();
        printWriter.close();

    }
}
