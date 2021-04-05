package com.my;

import org.json.JSONML;
import org.json.JSONObject;
import org.json.XML;

public class XmlConv

{

    public static void main(String[] args) {
String json = "{\"success\": \"true\",\"result_count\": 1,\"results_per_page\": 20,\"page\": \"1\",\"data\": [{\"Order\": {\"id\": \"107391769\",\"is_cod\": \"0\",\"order_type\": null,\"created\": \"2015-06-02 00:00:00\",\"modified\": \"2015-06-03 17:20:39\",\"gross_value\": \"521\",\"fees_value\": null,\"channel_id\": \"1703\",\"parent_id\": null,\"shipping_value\": \"0\",\"customer_id\": \"81259975\",\"shipping_address_id\": \"2716909\",\"order_status_id\": \"2440737\",\"financial_status_id\": \"2\",\"fulfillment_status_id\": \"3\",\"marketplace_status\": null,\"shipping_batch_id\": \"22549\",\"manifest_id\": null,\"currency_id\": \"63\",\"executed_shipping_method\": \"DTDC\",\"tracking_number\": \"B19152417\",\"stock_adjusted\": \"1\",\"order_reference\": \"301151513\",\"selected_shipping_method\": null,\"uuid\": \"584770205556e897c666a270219\",\"unread\": \"1\",\"dispatch_date\": \"2015-06-03 11:50:23\",\"bt_fulfillment_status_id\": \"3\",\"bt_order_status_id\": null,\"bt_financial_status_id\": null,\"remittance_date\": null,\"remittance_note\": null,\"remitted_value\": null,\"invoice_prefix\": null,\"invoice_number\": null,\"courier_company_id\": \"9\",\"ship_label_path\": null,\"courier_status_id\": null,\"courier_status_raw\": null,\"courier_tracking_attempts\": \"0\",\"courier_tracked_on\": null,\"warehouse_id\": \"1\",\"tax_rule_id\": null,\"weight_gm\": null,\"length_mm\": null,\"breadth_mm\": null,\"height_mm\": null,\"problem_reason\": null,\"replacement_order_id\": null,\"replacement_pending\": null,\"created_on\": null,\"vat_forms\": \"\",\"marketplace_fulfilled\": true,\"is_cancellable_on_channel\": false,\"vatlinks\": \"\"},\"Channel\": {\"id\": \"1703\",\"email\": null,\"email_from_name\": null,\"email_bcc\": null,\"invoice_template\": null,\"title\": \"pepper fry\",\"stock_sync\": \"1\",\"ChannelType\": {\"favicon_path\": \"\\/img\\/icons\\/icon_offline_store.png\",\"standard_tag\": \"offline_store\"}},\"Customer\": {\"id\": \"81259975\",\"salutation\": null,\"first_name\": \"zubin mistry\",\"last_name\": \"\",\"email\": null,\"nickname\": null,\"created\": \"0000-00-00 00:00:00\",\"modified\": \"0000-00-00 00:00:00\",\"customer_reference\": null,\"channel_id\": \"1703\",\"full_name\": \"zubin mistry \",\"company_name\": null},\"ShippingAddress\": {\"id\": \"2716909\",\"address_line1\": \"202  new cd baug . next to city center shopping mall  in sai baba enclave compound\",\"address_line2\": \"Goregaon West\",\"address_line3\": \"\",\"city\": \"Mumbai\",\"state\": \"Maharashtra\",\"country\": \"India\",\"country_code\": null,\"zip\": \"400062\",\"address_name\": \"zubin mistry \",\"phone\": \"9870770880\"},\"Currency\": {\"id\": \"63\",\"abbreviation\": \"INR\",\"name\": \"Indian Rupee\",\"symbol\": \"\\u20b9\",\"created\": \"0000-00-00 00:00:00\",\"modified\": \"2015-09-15 09:24:56\",\"rate\": \"66.3212\",\"priority\": \"0\"},\"OrderStatus\": {\"id\": \"2440737\",\"title\": \"open\"},\"FinancialStatus\": {\"id\": \"2\",\"title\": \"paid\"},\"FulfillmentStatus\": {\"id\": \"3\",\"title\": \"shipped\"},\"Manifest\": {\"id\": null,\"manifest_reference\": null,\"created\": null,\"courier_company\": null,\"picked_up_at\": null,\"courier_boy_name\": null,\"courier_boy_phone\": null,\"company_id\": null,\"schedule_pickup_token\": null},\"CourierCompany\": {\"id\": \"9\",\"title\": \"Dtdc\",\"accepted_names\": \"[\\\"dtdc\\\",\\\"DTDC\\\",\\\"Dtdc\\\",\\\"dtdc\\\\\\/dtdc\\\"]\",\"standard_tag\": \"dtdc\",\"manifest_report_type_id\": \"7\"},\"CourierStatus\": {\"id\": null,\"title\": null},\"Warehouse\": {\"id\": \"1\",\"title\": \"default warehouse\",\"created\": \"0000-00-00 00:00:00\",\"modified\": \"2014-08-14 09:44:34\",\"company_id\": \"2\",\"is_default\": \"1\",\"warehousing_account_id\": null,\"last_polled\": \"0000-00-00 00:00:00\",\"external_warehouse_code\": null,\"restricted_to_channel_id\": null},\"TaxRule\": {\"id\": null,\"title\": null,\"company_id\": null,\"condition_field\": null,\"condition_value\": null,\"tax_percentage\": null,\"created\": null,\"is_default\": null},\"item_titles_orders\": {\"id\": \"109427330\",\"order_id\": \"107391769\",\"quantity\": \"1\",\"tracking_ref\": null,\"item_title_id\": \"18355001\",\"gross_value\": \"521\",\"item_url\": null,\"number\": null,\"item_options\": null,\"tax_rule_id\": \"1\",\"shipping_batch_id\": null,\"manifest_id\": null,\"packer_id\": null,\"executed_shipping_method\": null,\"tracking_number\": null,\"courier_routing_code\": null,\"selected_shipping_method\": null,\"dispatch_date\": \"0000-00-00 00:00:00\",\"courier_status_id\": null,\"courier_company_id\": null,\"courier_tracked_on\": \"0000-00-00 00:00:00\",\"courier_status_raw\": null,\"courier_tracking_attempts\": \"0\",\"ship_label_path\": null,\"fulfillment_status_id\": null,\"shipping_value\": null,\"sub_order_reference\": null,\"warehouse_id\": null,\"stock_flag\": \"1\",\"dispatch_by_date\": \"0000-00-00 00:00:00\"},\"item_titles\": {\"id\": \"18355001\",\"title\": \"Light Fish Emerald Green Glass Cone Candle Holder\",\"sku_id\": null,\"sku_code_from_channel\": \"LFCM05\",\"company_id\": \"2\",\"number\": null},\"skus\": {\"id\": null,\"readable_name\": null,\"custom_code\": null,\"image_path\": null,\"image_thumb_path\": null,\"input_image_path\": null,\"company_id\": null,\"_in_stock_quantity\": null,\"awaiting_dispatch_quantity\": null,\"length_mm\": null,\"breadth_mm\": null,\"height_mm\": null,\"weight_gm\": null,\"bundle_id\": null,\"is_disabled\": null,\"is_bundle\": null,\"default_cost_price\": null,\"least_selling_price\": null,\"created\": null,\"modified\": null,\"currency_id\": null,\"is_low_stock\": null,\"low_stock_level\": null,\"product_id\": null,\"stock_sync\": null,\"product_category_id\": null,\"tax_rule_id\": null,\"style_code\": null},\"O\": {\"ProductId\": null},\"Transaction\": [],\"ItemTitle\": [{\"id\": \"18355001\",\"title\": \"Light Fish Emerald Green Glass Cone Candle Holder\",\"sku_id\": null,\"sku_code_from_channel\": \"LFCM05\",\"company_id\": \"2\",\"number\": null,\"ItemTitlesOrder\": {\"id\": \"109427330\",\"order_id\": \"107391769\",\"quantity\": \"1\",\"tracking_ref\": null,\"item_title_id\": \"18355001\",\"gross_value\": \"521\",\"item_url\": null,\"number\": null,\"item_options\": null,\"tax_rule_id\": \"1\",\"shipping_batch_id\": null,\"manifest_id\": null,\"packer_id\": null,\"executed_shipping_method\": null,\"tracking_number\": null,\"courier_routing_code\": null,\"selected_shipping_method\": null,\"dispatch_date\": \"0000-00-00 00:00:00\",\"courier_status_id\": null,\"courier_company_id\": null,\"courier_tracked_on\": \"0000-00-00 00:00:00\",\"courier_status_raw\": null,\"courier_tracking_attempts\": \"0\",\"ship_label_path\": null,\"fulfillment_status_id\": null,\"shipping_value\": null,\"sub_order_reference\": null,\"warehouse_id\": null,\"stock_flag\": \"1\",\"dispatch_by_date\": \"0000-00-00 00:00:00\",\"TaxRule\": {\"id\": \"1\",\"title\": \"VAT 5%\",\"tax_percentage\": \"5\"}},\"Sku\": {\"id\": null,\"custom_code\": null,\"style_code\": null,\"readable_name\": null,\"length_mm\": null,\"breadth_mm\": null,\"height_mm\": null,\"weight_gm\": null,\"default_cost_price\": null},\"Product\": {\"title\": null},\"brand\": \"Raymond\"}],\"Message\": []}]}";

JSONObject jsonFileObject = new org.json.JSONObject(json);

        String xml = "<?xml version=\"1.0\" encoding=\"ISO-8859-15\"?>"

                + XML.toString(jsonFileObject);
//        String _json = JSONML.toJSONObject("<?xml version=\"1.0\" encoding=\"ISO-8859-15\"?>\n" +
//                "<data>\n" +
//                "    <employees>\n" +
//                "        <a>\n" +
//                "            <firstName>John</firstName>\n" +
//                "            <lastName>Doe</lastName>\n" +
//                "        </a>\n" +
//                "    </employees>\n" +
//                "    <employees>\n" +
//                "        <b>\n" +
//                "            <firstName>Anna</firstName>\n" +
//                "            <lastName>Smith</lastName>\n" +
//                "        </b>\n" +
//                "    </employees>\n" +
//                "    <employees>\n" +
//                "        <c>\n" +
//                "            <firstName>Peter</firstName>\n" +
//                "            <lastName>Jones</lastName>\n" +
//                "        </c>\n" +
//                "    </employees>\n" +
//                "</data>\n" +
//                "<data2>\n" +
//                "    <a>\n" +
//                "        <firstName>John</firstName>\n" +
//                "        <lastName>Doe</lastName>\n" +
//                "    </a>\n" +
//                "</data2>\n" +
//                "<data2>\n" +
//                "    <b>\n" +
//                "        <firstName>Anna</firstName>\n" +
//                "        <lastName>Smith</lastName>\n" +
//                "    </b>\n" +
//                "</data2>\n" +
//                "<data2>\n" +
//                "    <c>\n" +
//                "        <firstName>Peter</firstName>\n" +
//                "        <lastName>Jones</lastName>\n" +
//                "    </c>\n" +
//                "</data2>").toString();

        System.out.println(xml);
//        System.out.println(_json);
    }
}

