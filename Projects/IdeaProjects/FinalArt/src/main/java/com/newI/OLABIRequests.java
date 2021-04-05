package com.newI;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OLABIRequests {

    private static final Tasks task = new Tasks();

    public String authenticate(String username, String password,String url) throws IOException {
        HashMap<String, String> headers = new HashMap<String, String>();
        HashMap<String, Object> payLoad = new HashMap<String, Object>();
        payLoad.put("user_name", username);
        payLoad.put("password", password);
        Map response = RestCalls.HttpsPostCall(url, payLoad, headers);
        return task.getXmlFromMap(response);
    }


    public String updateStockOnHand(String sql,String token,String warehouseCode,String url,String propsFilePath) throws IOException, SQLException {
        HashMap<String, String> headers = new HashMap<String, String>();
        HashMap<String, Object> payLoad = new HashMap<String, Object>();
        payLoad.put("token", token);
        payLoad.put("warehouse_code", warehouseCode);
        ArrayList<Map<String, Object>> stocks = task.getStocks(sql,propsFilePath);
        payLoad.put("stock", stocks);
        Map response = RestCalls.HttpsPostCall(url, payLoad, headers);
        return task.getXmlFromMap(response);
    }

    public String getPurchaseIndentDetails(String token,String indentDateFrom,String url) throws IOException {
        HashMap<String, String> headers = new HashMap<String, String>();
        HashMap<String, Object> payLoad = new HashMap<String, Object>();
        payLoad.put("token", token);
        payLoad.put("indent_date_from",indentDateFrom);
        Map response = RestCalls.HttpsPostCall(url, payLoad, headers);
        return task.getXmlFromMap(response);
    }

    public String updateDispatchDetails(String token, String sql, String url, String propsFilePath) throws Exception
    {
        HashMap<String, String> headers = new HashMap<String, String>();
        HashMap<String, Object> payLoad = new HashMap<String, Object>();
        payLoad.put("token", token);
        ArrayList<Map<String, Object>> stocks = task.getSTADetails(sql,propsFilePath);
        payLoad.put("sta_details", stocks);
        Map response = RestCalls.HttpsPostCall(url, payLoad, headers);
        return task.getXmlFromMap(response);
    }

    public String updateItemMasterDetails(String token,String sql,String url,String propsFilePath) throws  Exception
    {
        HashMap<String, String> headers = new HashMap<String, String>();
        HashMap<String, Object> payLoad = new HashMap<String, Object>();
        payLoad.put("token", token);
        ArrayList<Map<String, Object>> itemMasterData = task.getItemMasterDetails(sql,propsFilePath);
        payLoad.put("item_master_data", itemMasterData);
        Map response = RestCalls.HttpsPostCall(url, payLoad, headers);
        return task.getXmlFromMap(response);
    }

    /**
     * SELECT t1.HEADER_ID, t1.ORDER_NUMBER, t1.ORDER_TYPE, t1.PO_ORDER_DATE, t1.TO_CONS_MOB_NO, t1.TO_CONS_NAME, t1.ORDER_ASSOC_CODE, t1.DESPATCH_DOC_TYPE, t1.INVOICE_ASSOC_CODE, t1.INVOICE_ASSOCIATE_NAME, t1.INVOICE_DATE, t1.INVOICE_NO, t1.INVOICE_VALUE, t1.INV_TO_ADD1, t1.INV_TO_ADD2, t1.INV_TO_ADD3, t0.RFID_TAG_NO, t0.BARCODE, t0.ITEM_CODE, t0.QTY, t0.AVG_COST, t0.ITEM_MRP, t0.BASIC_AMOUNT, t0.DISC_PERC, t0.DISC_AMOUNT, t0.SGST_RATE, t0.SGST_AMOUNT, t0.CGST_RATE, t0.CGST_AMOUNT, t0.IGST_RATE, t0.IGST_AMOUNT, t0.NETT_TAX_AMT, t0.NETT_VALUE, t0.CREATION_DATE, t0.CREATED_BY, t0.LAST_UPDATED_BY, t0.LAST_UPDATE_DATE, t0.LAST_UPDATE_LOGIN, t0.ATTRIBUTE1, t0.ATTRIBUTE2, t0.ATTRIBUTE3, t0.ATTRIBUTE4, t0.ATTRIBUTE5, t0.ATTRIBUTE6, t0.ATTRIBUTE7, t0.ATTRIBUTE8, t0.ATTRIBUTE9, t0.ATTRIBUTE10, t0.HEADER_ID FROM XXTG_TIN_INVOICE_HEADERS t1 LEFT OUTER JOIN XXTG_TIN_INVOICE_LINES t0 ON (t0.HEADER_ID = t1.HEADER_ID)
     *
     */
}
