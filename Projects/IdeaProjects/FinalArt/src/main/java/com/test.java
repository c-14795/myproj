package com;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class test {
    public static void main(String[] args) {
        String jsonResp = "{\n" +
                "    \"success\": true,\n" +
                "    \"data\": {\n" +
                "        \"manifests\": [\n" +
                "            {\n" +
                "                \"id\": \"262186\",\n" +
                "                \"orders\": [\n" +
                "                    \"3358117527\"\n" +
                "                ],\n" +
                "                \"bt_manifest\": \"https://btesimages.s3.amazonaws.com/PdfManifestFiles/manifest-262186.pdf\",\n" +
                "                \"manifest_status_id\": 2,\n" +
                "                \"tokenMessage\": \"This channel does not support get marketplace manifest.\"\n" +
                "            },\n" +
                "            {\n" +
                "                \"id\": \"262187\",\n" +
                "                \"orders\": [\n" +
                "                    \"3358123867\"\n" +
                "                ],\n" +
                "                \"bt_manifest\": \"https://btesimages.s3.amazonaws.com/PdfManifestFiles/manifest-262187.pdf\",\n" +
                "                \"manifest_status_id\": 2,\n" +
                "                \"tokenMessage\": \"This channel does not support get marketplace manifest.\"\n" +
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "}";


        Gson gson = new Gson();
        Map<String, Object> respmap = gson.fromJson(jsonResp, Map.class);
        Boolean success = (Boolean) respmap.get("success");
        System.out.println(success);
        List<Map<String, Object>> s = (List<Map<String, Object>>) ((Map) respmap.get("data")).get("manifests");
        for (Map<String, Object> map : s) {
            List<String> orders = (List<String>) map.get("orders");
            String manifestID = (String) map.get("id");
            for (String order : orders) {
                System.out.println(order);
            }
            System.out.println(map.get("bt_manifest"));
        }

        System.out.println(respmap);
    }
}
