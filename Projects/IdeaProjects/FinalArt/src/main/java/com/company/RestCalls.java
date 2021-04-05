package com.company;

import com.google.gson.Gson;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class RestCalls {


    public static void HttpPostCallWithoutResp(String addr, HashMap<String, Object> json_map, HashMap<String, String> headers)
            throws IOException {
        URL url = new URL(addr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        // -------------adding headers----------------//
        for (Map.Entry m : headers.entrySet()) {
            conn.setRequestProperty(m.getKey().toString(), m.getValue().toString());
        }
        String input = new Gson().toJson(json_map);
        System.out.println("input json is " + input);
        OutputStream os = conn.getOutputStream();
        os.write(input.getBytes("utf-8"));
        os.flush();
        conn.connect();

        if (conn.getResponseCode() != HttpURLConnection.HTTP_NO_CONTENT) {
            System.out.println("some error occurred");
            System.out.println(conn.getResponseMessage() + " --> " + conn.getResponseCode());
        }


        System.out.println("closing connection");
        conn.disconnect();
        System.out.println("connection closed");
    }


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
        System.out.println("input json is " + input);
        OutputStream os = conn.getOutputStream();
        os.write(input.getBytes("utf-8"));
        os.flush();
        conn.connect();

        if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
            //throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            System.out.println("some error occurred");
            System.out.println(conn.getResponseMessage() + " --> " + conn.getResponseCode());
        }

        BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

        System.out.println("Parsing  output");

        Gson gson = new Gson();
        Map<String, Object> respmap = gson.fromJson(br.readLine(), Map.class);
        System.out.println("---------------- json response ----------------");
        for (Map.Entry res : respmap.entrySet()) {
            System.out.println(res.getKey() + ":" + res.getValue());

        }
        System.out.println("---------------- json response ----------------");
        System.out.println("closing connection");
        conn.disconnect();
        System.out.println("connection closed");
        return respmap;
    }


    public static Map HttpGetCall(String addr, HashMap<String, Object> json_map, HashMap<String, String> headers) throws IOException {
        URL url = new URL(addr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("GET");
        //conn.setRequestProperty("Content-Type", "application/json");
        //-------------adding headers----------------//
        for (Map.Entry m : headers.entrySet()) {
            conn.setRequestProperty(m.getKey().toString(), m.getValue().toString());
        }
        String input = new Gson().toJson(json_map);
        System.out.println("input json is " + input);
        conn.connect();

        if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
            //throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            System.out.println("some error occurred");
            System.out.println(conn.getResponseMessage() + " --> " + conn.getResponseCode());
        }

        Scanner br = new Scanner(conn.getInputStream());

        System.out.println("Parsing  output");

        Gson gson = new Gson();
        StringBuilder s = new StringBuilder();
        while (br.hasNext()) {
            s.append(br.nextLine().trim());
        }
        System.out.println(s);
        Map<String, Object> respmap = gson.fromJson(s.toString(), Map.class);
        System.out.println("---------------- json response ----------------");
        for (Map.Entry res : respmap.entrySet()) {
            System.out.println(res.getKey() + ":" + res.getValue());

        }
        System.out.println("---------------- json response ----------------");
        System.out.println("closing connection");
        conn.disconnect();
        System.out.println("connection closed");
        return respmap;
    }

    public static Map HttpFormPostCall(String addr, HashMap<String, Object> json_map, HashMap<String, String> headers) throws IOException {
        {
            String https_url = addr;
            URL url;

            url = new URL(https_url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("charset", "utf-8");
            for (Map.Entry m : headers.entrySet()) {
                conn.setRequestProperty(m.getKey().toString(), m.getValue().toString());
            }
            String urlParameters = "data=" + new Gson().toJson(json_map);
            byte[] postData = urlParameters.getBytes("UTF-8");
            OutputStream os = conn.getOutputStream();
            os.write(postData);
            os.close();
            conn.connect();
            //System.out.println(conn.getResponseCode());
            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                //throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
                System.out.println("some error occurred");
                System.out.println(conn.getResponseMessage() + " --> " + conn.getResponseCode());
            }
            String output;
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            output = br.readLine();

            System.out.println("Parsing  output ----> " + output);

            Gson gson = new Gson();
            Map<String, Object> respmap = gson.fromJson(output, Map.class);
            System.out.println("---------------- json response ----------------");
            for (Map.Entry res : respmap.entrySet()) {
                System.out.println(res.getKey() + ":" + res.getValue());

            }
            System.out.println("---------------- json response ----------------");
            System.out.println("closing connection");
            conn.disconnect();
            System.out.println("connection closed");


            return respmap;


        }
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
        System.out.println("input to data " + input);
        //System.out.println(input);
        OutputStream os = conn.getOutputStream();
        os.write(input.getBytes("utf-8"));
        os.close();
        conn.connect();
        //System.out.println(conn.getResponseCode());
        if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
            //throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            System.out.println("some error occurred");
            System.out.println(conn.getResponseMessage() + " --> " + conn.getResponseCode());
        }
        String output;
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        output = br.readLine();

        System.out.println("Parsing  output ----> " + output);

        Gson gson = new Gson();
        Map<String, Object> respmap = gson.fromJson(output, Map.class);
        System.out.println("---------------- json response ----------------");
        for (Map.Entry res : respmap.entrySet()) {
            System.out.println(res.getKey() + ":" + res.getValue());

        }
        System.out.println("---------------- json response ----------------");
        System.out.println("closing connection");
        conn.disconnect();
        System.out.println("connection closed");


        return respmap;
    }

}