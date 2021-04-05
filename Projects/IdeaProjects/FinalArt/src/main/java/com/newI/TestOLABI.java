package com.newI;


import java.util.*;
import java.util.regex.Pattern;

public class TestOLABI {

    public static void main(String[] args) {
        System.out.println(Arrays.asList("a,b,c,d,e,f".split(",")));

        int fff = 0;
        HashMap<String ,Object> s = new HashMap<String,Object>();
        ArrayList a2 = new ArrayList<>();
        while (fff <= 3)
        {

            ArrayList a = new ArrayList();
            s.put("a","b");
            a.addAll(Arrays.asList("a,b,c,d,e,f".split(",")));
            s.put("c",a);
            a= new ArrayList<String>();
            a.addAll(Arrays.asList("a,b,c,d,ef,f".split(",")));
            s.put("d",a);
            a=new ArrayList<String>();

            a2.add(s);
            System.out.println(a2);
            fff++;
            s= new HashMap<String, Object>();
        }
        System.out.println(a2.size());

    }

    public static void main(int args) {
        ArrayList<Map<String, Object>> stocks = new ArrayList<Map<String, Object>>();
        ArrayList<HashMap<String,String>> ras= new ArrayList<>();
        HashMap<String,String> prod1 = new HashMap<String,String>();
        HashMap<String,String> prod2 = new HashMap<String,String>();
        HashMap<String,String> prod3 = new HashMap<String,String>();
        prod1.put("prod_id","1");
        prod1.put("sta_id","1");
        prod2.put("prod_id","2");
        prod2.put("sta_id","1");
        prod3.put("prod_id","3");
        prod3.put("sta_id","1");
        ras.add(prod1);
        ras.add(prod2);
        ras.add(prod3);
        int current_header_count = 0;
        ListIterator<HashMap<String,String>> rs = ras.listIterator();
        List<String> prod_attr = Arrays.asList("prod_id".split(Pattern.quote(",")));
        Map<String, Object> staDetails = new HashMap<String, Object>();
        String prevHeader = "";
        String currentHeader = "";

        ArrayList<Map<String, Object>> products = new ArrayList<Map<String, Object>>();

        while (rs.hasNext()) {

            Map<String, Object> product = new HashMap<String, Object>();
            //Object obj = rs.getObject(1);
            HashMap<String ,String> a =rs.next();
            //currentHeader = obj.toString();
            currentHeader = a.get("sta_id");
            if (!Objects.equals(prevHeader, currentHeader)) {
                staDetails.put("products", products);
                stocks.add(staDetails);
                staDetails.clear();
                products.clear();
                current_header_count = 0;
            } else {
                current_header_count++;

            }

//            for (int i = 1; i <= ras.size(); i++) {
//                String colName = metaData.getColumnName(i);
//                if (prod_attr.contains(colName)) {
//                    product.put(properties.getProperty(colName, colName),
//                            rs.getObject(metaData.getColumnName(i)));
//                } else {
//                    if (current_header_count > 1) continue;
//                    if (colName.equals("HEADER_ID")) {
//                        staDetails.put(properties.getProperty(colName, colName), obj);
//
//                    } else {
//                        staDetails.put(properties.getProperty(colName, colName),
//                                rs.getObject(metaData.getColumnName(i)));
//                    }
//                }
//            }

            products.add(product);

            prevHeader = currentHeader;


        }

    }

    public static void main(String args) throws Exception {
        HashMap<String, String> headers = new HashMap<String, String>();
        HashMap<String, Object> payLoad = new HashMap<String, Object>();
        payLoad.put("user_name", "TIMEXAPI@TGIL");
        payLoad.put("password", "TIMEX");
        Tasks task = new Tasks();
        Map response1 = RestCalls.HttpsPostCall("https://qc.olabi.ooo/api/o2o/v1/authenticate", payLoad, headers);
        HashMap<String, Object> payLoad2 = new HashMap<String, Object>();
        ArrayList<HashMap<String, Object>> stocks = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> stock1 = new HashMap<String, Object>();
        stock1.put("barcode", "691464571498");
        stock1.put("sku_code", "FS4552");
        stock1.put("qty", "20");
        stock1.put("item_cost", "5082.20340");
        HashMap<String, Object> stock2 = new HashMap<String, Object>();
        stock2.put("barcode", "691464571498");
        stock2.put("sku_code", "FS4552");
        stock2.put("qty", "20");
        stock2.put("item_cost", "5082.20340");
        stocks.add(stock1);
//        stocks.add(stock2);
        payLoad2.put("token", response1.get("access_token"));
        payLoad2.put("warehouse_code", "TGIL01");
        payLoad2.put("stock", stocks);
        Map response2 = RestCalls.HttpsPostCall("https://qc.olabi.ooo/api/v1/timex/update-stock-on-hand", payLoad2, headers);
        HashMap<String, Object> payLoad3 = new HashMap<String, Object>();
        payLoad3.put("token", response1.get("access_token"));
        payLoad3.put("indent_date_from", "2005-03-15");
        Map response3 = RestCalls.HttpsPostCall("https://qc.olabi.ooo/api/v1/timex/get-purchase-indent-details", payLoad3, headers);
        System.out.println(task.getXmlFromMap(response1));
        System.out.println(task.getXmlFromMap(response2));
        System.out.println(task.getXmlFromMap(response3));

    }
}
