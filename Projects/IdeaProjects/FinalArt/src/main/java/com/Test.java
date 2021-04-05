package com;

import com.google.gson.Gson;
import com.timex.bt.integration.RestCalls;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Test {


    public static void main(String[] args) throws IOException {
        Test t = new Test();
        HashMap<String,Object> s = new HashMap<String, Object>();
        HashMap<String,Object> ss = new HashMap<String, Object>();
        ss.put("orders",new String[]{"123","333"});
        s.put("data",ss);
        System.out.println((RestCalls.ConvertJsonToFormData(new String[]{"data"}, new int[]{}, ss)));

//        for(Map.Entry<String,Object> map:s.entrySet())
//        {
//
//            System.out.println(map.getValue());
//            System.out.println(map.getValue() instanceof HashMap);
//            System.out.println((HashMap)map.getValue());
//            System.out.println(map.getKey());
//        }
        //String content = new Scanner(new File("/home/c14795/Desktop/integg/ORDERS.txt")).useDelimiter("\\Z").next();
        //System.out.println(content);
        //t.getXmlFromJson(content, "orders.xml");
    }


    public String getXmlFromJson(String output,
                                 String filePath) throws IOException {

        Gson gson = new Gson();
        Map<String, Object> respmap = gson.fromJson(output, Map.class);
        String xmlData = map2Xml(respmap, "something");
        writeToFile(xmlData, filePath);
        return xmlData;

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
                "<root>\n" +
                resp.toString() + "\n" +
                "</root>" : resp.toString();
    }

}
