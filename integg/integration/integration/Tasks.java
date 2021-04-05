package com.timex.bt.integration;

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

    /**
     * fetches json from the url.
     * Converts it to xml format
     * Writes into the given file.
     * returns the converted xml data
     *
     * @param filePath file path where the converted xml from the json should be stored.
     * @param url      url for fetching json.
     * @return xmlData xmlString converted from json.
     */

    public String getXmlFromJson(String url,
                                 String filePath) throws IOException {

        Map<String, Object> respmap =
                RestCalls.HttpGetCall(url, new HashMap<String, Object>(),
                        new HashMap<String, String>());
        String xmlData = map2Xml(respmap, "something");
        writeToFile(xmlData, filePath);
        return xmlData;

    }

    /**
     * sends Postreq to the specified url.
     * Fetches data from the sql database based on the given sql statement.
     * Also updates the two cols in the table, based on the unique row identifier col
     *
     * @param sql            sql statement for fetching the data.
     * @param url            url for sending the fetched data.
     * @param tableName      tableName,this is used in updating the table.
     * @param idColName      name of unique row identifier  used for updating the col in specific row.
     * @param colMapFilePath optional parameter, if provided will change the colNames in the json data accordingly.Should be a file path, file should be in properties file format.
     */


    public String SendPostReq(String sql, String url, String tableName, String idColName,
                              String... colMapFilePath) throws IOException,
            SQLException {
        if (colMapFilePath.length == 1) {
            System.out.println("IF STATEMENT EXECUTED");
            return sendPostReq(sql, url, tableName, idColName, true, new HashMap<String, String>(),
                    colMapFilePath);

        } else {
            System.out.println("ELSE STATEMENT EXECUTED");
            return sendPostReq(sql, url, tableName, idColName, false, new HashMap<String, String>());

        }
    }


    /**
     * sends Get req to the specified url.
     * Fetches data from the sql database based on the given sql statement.
     * Also updates the two cols in the table, based on the unique row identifier col
     *
     * @param sql            sql statement for fetching the data.
     * @param url            url for sending the fetched data.
     * @param tableName      tableName,this is used in updating the table.
     * @param idColName      name of unique row identifier  used for updating the col in specific row.
     * @param colMapFilePath optional parameter, if provided will change the colNames in the json data accordingly.Should be a file path, file should be in properties file format.
     */


    public void SendGetReq(String sql, String url, String tableName, String idColName,
                           String... colMapFilePath) throws IOException,
            SQLException {
        if (colMapFilePath.length == 1) {
            System.out.println("IF STATEMENT EXECUTED");
            sendGetReq(sql, url, tableName, idColName, true, new HashMap<String, String>(),
                    colMapFilePath);

        } else {
            System.out.println("ELSE STATEMENT EXECUTED");
            sendGetReq(sql, url, tableName, idColName, false, new HashMap<String, String>());

        }
    }


    private String sendPostReq(String sql, String url, String tableName, String idColName, boolean colMapEnabled,
                               HashMap<String, String> headers,
                               String... colMap) throws SQLException,
            IOException {
        StringBuilder response = new StringBuilder();
        JDBCDriverConnection connection = new JDBCDriverConnection();
        ResultSet rs = connection.executeQuery(sql);
        ResultSetMetaData metaData = rs.getMetaData();
        Properties properties =
                new Props().getColNamesFromPropertiesFile(colMap[0]);


        while (rs.next()) {

            HashMap<String, Object> json_map = new HashMap<String, Object>();

            if (colMapEnabled) {

                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    String colName = metaData.getColumnName(i);

                    //json_map.put(properties.getProperty(colName, colName),properties.getProperty(rs.getObject(metaData.getColumnName(i)).toString()));
                    json_map.put(properties.getProperty(colName, colName),
                            colName.equalsIgnoreCase(properties.getProperty("CHANGE_COL")) ? properties.getProperty(rs.getObject(metaData.getColumnName(i)).toString()) : rs.getObject(metaData.getColumnName(i)));


                }

            } else {
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    String colName = metaData.getColumnName(i);

                    //json_map.put(colName.replace("_", ""), rs.getObject(metaData.getColumnName(i)));
                    json_map.put(colName,
                            rs.getObject(metaData.getColumnName(i)));

                }
            }


            Map<String, Object> resp = RestCalls.HttpFormPostCall(url, RestCalls.ConvertJsonToFormData(new String[]{"data"}, new int[]{1}, json_map), headers);
            String Attr1 = Boolean.parseBoolean(resp.get("success").toString()) ? "Y" : "E";
            com.google.gson.internal.LinkedTreeMap<String, Object> res = ((com.google.gson.internal.LinkedTreeMap<String, Object>) ((ArrayList) resp.get("data")).get(0));
            String Attr2 = res.get("message").toString();
            String sku_id = res.get("sku_id").toString();
            System.out.println(Attr1 + "!@#$%\n\n" + Attr2);
            connection.update(json_map.get(properties.getProperty(idColName)).toString(), Attr1, Attr2, tableName, idColName);
            if (Attr1.equals("E")) {
                response.append(sku_id + "=" + Attr2);
                response.append(",");
            }

        }
        connection.closeConnection();
        return response.toString();
    }


    private void sendGetReq(String sql, String url, String tableName, String idColName, boolean colMapEnabled,
                            HashMap<String, String> headers,
                            String... colMap) throws SQLException,
            IOException {
        JDBCDriverConnection connection = new JDBCDriverConnection();
        ResultSet rs = connection.executeQuery(sql);
        ResultSetMetaData metaData = rs.getMetaData();


        while (rs.next()) {

            HashMap<String, Object> json_map = new HashMap<String, Object>();
            Properties properties =
                    new Props().getColNamesFromPropertiesFile(colMap[0]);

            if (colMapEnabled) {

                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    String colName = metaData.getColumnName(i);

                    json_map.put(properties.getProperty(colName, colName),
                            rs.getObject(metaData.getColumnName(i)));

                }

            } else {
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    String colName = metaData.getColumnName(i);

                    //json_map.put(colName.replace("_", ""), rs.getObject(metaData.getColumnName(i)));
                    json_map.put(colName,
                            rs.getObject(metaData.getColumnName(i)));

                }
            }


            Map<String, Object> resp = RestCalls.HttpGetCall(url, RestCalls.ConvertJsonToFormData(new String[]{"data"}, new int[]{0}, json_map), headers);
            //String token = resp.get("token").toString();
            //System.out.println(token + "!@#$%");
            //connection.updateOneCol(json_map.get(properties.getProperty(idColName)).toString(),token,tableName,idColName,"Attribute20");

        }
        connection.closeConnection();
    }


    private String arrayList2xml(ArrayList o, String... arrayMapValues) {
        StringBuilder resp = new StringBuilder();
        boolean mapVals = arrayMapValues.length > 0;
        for (Object anO : o) {
            if (!(anO instanceof Map))
                resp.append(anO.toString());
            else {
                if (mapVals) {
                    resp.append("<").append(arrayMapValues[0]).append(">\n");
                }
                resp.append(map2Xml((Map<String, Object>) anO, null));

                if (mapVals) {
                    resp.append("</").append("ElementData").append(">\n");

                }
            }
        }
        return resp.toString();
    }

    private String map2Xml(Map<String, Object> respmap, String cons) {
        boolean end = true;
        StringBuilder resp = new StringBuilder();
        for (Map.Entry res : respmap.entrySet()) {
            if (res.getKey().equals("item_attributes") || res.getKey().equals("extra_info")) continue;
            resp.append("<").append(res.getKey());
            if (res.getValue() instanceof Map) {
                resp.append(">\n").append(map2Xml((Map<String, Object>) res.getValue(),
                        null));
            } else if (res.getValue() instanceof ArrayList) {
                if (res.getKey().equals("data")) {
                    resp.append(">\n").append(arrayList2xml((ArrayList) res.getValue(), "ElementData"));
                } else {

                    resp.append(">\n").append(arrayList2xml((ArrayList) res.getValue()));
                }
            } else {
                if (res.getValue() != null) {
                    resp.append(">").append(res.getValue().toString());

                } else {
                    end = false;
                    resp.append("/>\n");
                }
            }
            if (end) {


                resp.append("</").append(res.getKey()).append(">\n");

            }
            end = true;

        }


        return cons != null ? "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
                "<root xmlns=\"http://www.timex.com\" >\n" +
                resp.toString() + "\n" +
                "</root>" : resp.toString();
    }

    private void writeToFile(String s, String filePath) throws IOException {
        File f = new File(filePath);
        if (f.isFile() && !f.exists())
            f.createNewFile();
        PrintWriter printWriter = new PrintWriter(f);
        printWriter.write(s);
        printWriter.flush();
        printWriter.close();

    }
}
