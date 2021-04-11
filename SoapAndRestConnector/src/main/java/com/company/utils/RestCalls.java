package com.company.utils;

import com.google.gson.Gson;
import org.apache.log4j.Logger;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


public class RestCalls {
    private static final Logger logger = Logger.getLogger(RestCalls.class);

   public static Map HttpPostCall(String addr, HashMap<String, Object> json_map, HashMap<String, String> headers) throws IOException {
        URL url = new URL(addr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        //-------------adding headers----------------//
        for (Map.Entry m : headers.entrySet()) {
            conn.setRequestProperty(m.getKey().toString(), m.getValue().toString());
        }
        String input = new Gson().toJson(json_map);
        logger.debug("input json is " + input);
        OutputStream os = conn.getOutputStream();
        os.write(input.getBytes("utf-8"));
        os.flush();
        conn.connect();

        if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
            //throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            logger.debug("some error occurred");
            logger.debug(conn.getResponseMessage() + " --> " + conn.getResponseCode());
        }

        BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

        logger.debug("Parsing  output");

        Gson gson = new Gson();
        Map<String, Object> respmap = gson.fromJson(br.readLine(), Map.class);
        logger.debug("---------------- json response ----------------");
        for (Map.Entry res : respmap.entrySet()) {
            logger.debug(res.getKey() + ":" + res.getValue());

        }
        logger.debug("---------------- json response ----------------");
        logger.debug("closing connection");
        conn.disconnect();
        logger.debug("connection closed");
        return respmap;
    }

    public static Map HttpsPostCall(String addr, HashMap<String, Object> json_map, HashMap<String, String> headers) throws IOException {

        String https_url = addr;
        URL url;


        url = new URL(https_url);
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        //-------------adding headers----------------//
        for (Map.Entry m : headers.entrySet()) {
            conn.setRequestProperty(m.getKey().toString(), m.getValue().toString());
        }
        String input = new Gson().toJson(json_map);
        logger.debug("input to data " + input);
        //System.out.println(input);
        OutputStream os = conn.getOutputStream();
        os.write(input.getBytes("utf-8"));
        os.close();
        conn.connect();
        //System.out.println(conn.getResponseCode());
        if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
            //throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            logger.debug("some error occurred");
            logger.debug(conn.getResponseMessage() + " --> " + conn.getResponseCode());
        }
        String output;
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        output = br.readLine();

        logger.debug("Parsing  output ----> " + output);

        Gson gson = new Gson();
        Map<String, Object> respmap = gson.fromJson(output, Map.class);
        logger.debug("---------------- json response ----------------");
        for (Map.Entry res : respmap.entrySet()) {
            logger.debug(res.getKey() + ":" + res.getValue());

        }
        logger.debug("---------------- json response ----------------");
        logger.debug("closing connection");
        conn.disconnect();
        logger.debug("connection closed");


        return respmap;
    }

}