package JsonToXml;

import com.my.RestCalls;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class App {
    public static void main(String[] args) throws IOException {
        System.out.println("Hello World!");
        String Addr = "http://services.groupkt.com/country/get/iso2code/IN";
        HashMap<String,String> headers = new HashMap<String, String>();
        //headers.put("Content-Type", "application/json");
        //headers.put("Cache-Control", "no-cache");
        Map<String, Object> respmap = RestCalls.GetHttpPostCall(Addr, new HashMap<String, Object>(), new HashMap<String, String>() );
        System.out.println();
        writeToFile(map2Xml(respmap,"something"));


    }

    private static String arrayList2xml(ArrayList o) {
        StringBuilder resp = new StringBuilder();
        for (Object anO : o) {
            if (!(anO instanceof Map)) resp.append(anO.toString());
            else resp.append(map2Xml((Map<String, Object>) anO, null));
        }
        return resp.toString();
    }

    private static String map2Xml(Map<String, Object> respmap,String cons) {
        boolean end = true;
        StringBuilder resp = new StringBuilder();
        for (Map.Entry res : respmap.entrySet()) {
            resp.append("<").append(res.getKey());
            if (res.getValue() instanceof Map) {
                resp.append(">\n").append(map2Xml((Map<String, Object>) res.getValue(),null));
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


        return cons!=null?"<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
                "<root>\n"+resp.toString()+"\n" +
                "</root>": resp.toString();
    }

    private static  void writeToFile(String s) throws IOException {
        File f = new File("gen_xml.xml");
        if(f.isFile() && !f.exists()) f.createNewFile();
        PrintWriter printWriter = new PrintWriter(f);
        printWriter.write(s);
        printWriter.flush();
        printWriter.close();

    }


}
