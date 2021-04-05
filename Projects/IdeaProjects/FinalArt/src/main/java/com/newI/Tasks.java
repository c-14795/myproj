package com.newI;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;

public class Tasks {


    public String getXmlFromMap(Map map) {
        return map2Xml(map, "\"http://www.timex.com\"");
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
                    resp.append("</").append(arrayMapValues[0]).append(">\n");

                }
            }
        }
        return resp.toString();
    }

    private String map2Xml(Map<String, Object> respmap, String namespace) {
        boolean end = true;
        StringBuilder resp = new StringBuilder();
        for (Map.Entry res : respmap.entrySet()) {
            resp.append("<").append(res.getKey());
            if (res.getValue() instanceof Map) {
                resp.append(">\n").append(map2Xml((Map<String, Object>) res.getValue(),
                        null));
            } else if (res.getValue() instanceof ArrayList) {
                resp.append(">\n").append(arrayList2xml((ArrayList) res.getValue(), res.getKey().toString()));

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


        return namespace != null ? "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
                "<root xmlns=" + namespace + " >\n" +
                resp.toString() + "\n" +
                "</root>" : resp.toString();
    }


    public static Properties getProperties(String filePath) {
        File file = null;
        Properties properties = new Properties();
        try {
            file = new File(filePath);
            if (!file.exists()) {
                System.err.println("properties file not found in the below path");
                throw new FileNotFoundException(file.getAbsolutePath());

            }

            FileInputStream fileInput = new FileInputStream(file);
            properties.load(fileInput);

        } catch (Exception e) {
            System.out.println("Exception " + file.getAbsolutePath());
            System.out.println("properties file not found in the above path");
            //            System.exit(1);
        }

        return properties;

    }

    public ArrayList<Map<String, Object>> getSTADetails(String sql, String... propsFilepath) throws Exception {

        ArrayList<Map<String, Object>> stocks = new ArrayList<Map<String, Object>>();
        JDBCDriverConnection connection = new JDBCDriverConnection();
        ResultSet rs = connection.executeQuery(sql);
        ResultSetMetaData metaData = rs.getMetaData();
        boolean colMapEnabled = propsFilepath.length > 0;
        Properties properties = colMapEnabled ? getProperties(propsFilepath[0]) : null;
        String prevHeader = "";
        String currentHeader = "";
        //boolean initial = true;
        int current_header_count = 0;
        List<String> prod_attr = Arrays.asList(properties.getProperty("product_attributes").split(","));
        Map<String, Object> staDetails = new HashMap<String, Object>();

        ArrayList<Map<String, Object>> products = new ArrayList<Map<String, Object>>();
        Map<String, Object> product = null;
        int a = 0;
        while (rs.next()) {
            a++;
            product = new HashMap<String, Object>();
            Object obj = rs.getObject(1);
            currentHeader = obj.toString();

            if ((!prevHeader.equals(currentHeader)) && a != 1) {
                staDetails.put("products", products);
                stocks.add(staDetails);
                staDetails = new HashMap<String, Object>();
                products = new ArrayList<>();
                current_header_count = 0;
            } else {
                current_header_count++;

            }


            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                String colName = metaData.getColumnName(i);
                System.out.println(colName);
                if (prod_attr.contains(colName)) {
                    product.put(properties.getProperty(colName, colName),
                            rs.getObject(metaData.getColumnName(i)));
                } else {
                    if (current_header_count > 1) continue;
                    staDetails.put(properties.getProperty(colName, colName),
                            rs.getObject(metaData.getColumnName(i)));

                }
            }

            products.add(product);

            prevHeader = currentHeader;


        }


        staDetails.put("products", products);
        stocks.add(staDetails);


        return stocks;
    }

    public ArrayList<Map<String, Object>> getStocks(String sql, String... propsFilepath) throws SQLException {

        ArrayList<Map<String, Object>> stocks = new ArrayList<Map<String, Object>>();
        JDBCDriverConnection connection = new JDBCDriverConnection();
        ResultSet rs = connection.executeQuery(sql);
        ResultSetMetaData metaData = rs.getMetaData();
        boolean colMapEnabled = propsFilepath.length > 0;
        Properties properties = colMapEnabled ? getProperties(propsFilepath[0]) : null;

        while (rs.next())

        {

            Map<String, Object> stock = new HashMap<String, Object>();

            if (colMapEnabled) {

                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    String colName = metaData.getColumnName(i);

                    stock.put(properties.getProperty(colName, colName),
                            rs.getObject(metaData.getColumnName(i)));

                }

            } else {
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    String colName = metaData.getColumnName(i);
                    stock.put(colName,
                            rs.getObject(metaData.getColumnName(i)));

                }
            }
            stocks.add(stock);
        }
        return stocks;
    }

    public ArrayList<Map<String, Object>> getItemMasterDetails(String sql, String... propsFilepath) throws SQLException {
        ArrayList<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
        JDBCDriverConnection connection = new JDBCDriverConnection();
        ResultSet rs = connection.executeQuery(sql);
        ResultSetMetaData metaData = rs.getMetaData();
        boolean colMapEnabled = propsFilepath.length > 0;
        Properties properties = colMapEnabled ? getProperties(propsFilepath[0]) : null;

        while (rs.next())

        {

            Map<String, Object> item = new HashMap<String, Object>();

            if (colMapEnabled) {

                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    String colName = metaData.getColumnName(i);

                    item.put(properties.getProperty(colName, colName),
                            rs.getObject(metaData.getColumnName(i)));

                }

            } else {
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    String colName = metaData.getColumnName(i);
                    item.put(colName,
                            rs.getObject(metaData.getColumnName(i)));

                }
            }
            items.add(item);
        }

        return items;
    }
}
