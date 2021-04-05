package com.timex.bt.integration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TestOLABI {

    public static void main(String[] args) throws Exception {
        HashMap<String, String> headers = new HashMap<String, String>();
        HashMap<String, Object> payLoad = new HashMap<String, Object>();
        payLoad.put("user_name", "TIMEXAPI@TGIL");
        payLoad.put("password", "TIMEX");
        Tasks task = new Tasks();
        Map response1 = RestCalls.HttpsPostCall("https://qc.olabi.ooo/api/o2o/v1/authenticate", payLoad, headers);
        HashMap<String, Object> payLoad2 = new HashMap<String, Object>();
        ArrayList<HashMap<String, String>> stocks = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> stock1 = new HashMap<String, String>();
        stock1.put("barcode", "691464571498");
        stock1.put("sku_code", "FS4552");
        stock1.put("qty", "20");
        stock1.put("item_cost", "5082.20340");
        HashMap<String, String> stock2 = new HashMap<String, String>();
        stock2.put("barcode", "691464571498");
        stock2.put("sku_code", "FS4552");
        stock2.put("qty", "20");
        stock2.put("item_cost", "5082.20340");
        stocks.add(stock1);
        stocks.add(stock2);
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
