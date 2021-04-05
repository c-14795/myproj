package com.newI;

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


/*
* Sends requests to the RestApi
*
* */
public class RestCalls {

    /**
     * sends HTTP request
     * Uses POST method
     * Submits as a form post.
     * Currently this method design is limited, will be made generic.
     * Returns a Map of response.
     * Assumes that the response is of type JSON.
     *
     * @param addr     url where data should be posted.
     * @param headers  HashMap of headers which should be sent along with the data.
     * @param json_map HashMap of data which has to be posted to the API
     * @return respmap HashMap of response.
     */

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
            String urlParameters = "data=[" + new Gson().toJson(json_map) + "]";
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


    /**
     * sends HTTP request
     * Uses POST method
     * Sends json data.
     * Works with error codes, will not expect response.
     *
     * @param addr     url where data should be posted.
     * @param headers  HashMap of headers which should be sent along with the data.
     * @param json_map HashMap of data which has to be posted to the API
     */

    public static void HttpPostCallWithoutResp(String addr,
                                               HashMap<String, Object> json_map,
                                               HashMap<String, String> headers) throws IOException {
        URL url = new URL(addr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        // -------------adding headers----------------//
        for (Map.Entry m : headers.entrySet()) {
            conn.setRequestProperty(m.getKey().toString(),
                    m.getValue().toString());
        }
        String input = new Gson().toJson(json_map);
        System.out.println("input json is " + input);
        OutputStream os = conn.getOutputStream();
        os.write(input.getBytes("utf-8"));
        os.flush();
        conn.connect();

        if (conn.getResponseCode() != HttpURLConnection.HTTP_NO_CONTENT) {
            System.out.println("some error occurred");
            System.out.println(conn.getResponseMessage() + " --> " +
                    conn.getResponseCode());
        }


        System.out.println("closing connection");
        conn.disconnect();
        System.out.println("connection closed");
    }


    /**
     * sends HTTP request
     * Uses POST method
     * Sends JSON data.
     * Returns a Map of response.
     * Assumes that the response is of type JSON.
     *
     * @param addr     url where data should be posted.
     * @param headers  HashMap of headers which should be sent along with the data.
     * @param json_map HashMap of data which has to be posted to the API
     * @return respmap HashMap of response.
     */


    public static Map HttpPostCall(String addr,
                                   HashMap<String, Object> json_map,
                                   HashMap<String, String> headers) throws IOException {
        URL url = new URL(addr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        //-------------adding headers----------------//
        for (Map.Entry m : headers.entrySet()) {
            conn.setRequestProperty(m.getKey().toString(),
                    m.getValue().toString());
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
            System.out.println(conn.getResponseMessage() + " --> " +
                    conn.getResponseCode());
        }

        BufferedReader br =
                new BufferedReader(new InputStreamReader((conn.getInputStream())));

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

    /**
     * sends HTTP request
     * Uses GET method
     * Sends JSON data.
     * Returns a Map of response.
     * Assumes that the response is of type JSON.
     *
     * @param addr     url where data should be posted.
     * @param headers  HashMap of headers which should be sent along with the data.
     * @param json_map HashMap of data which has to be posted to the API
     * @return respmap HashMap of response.
     */


    public static Map HttpGetCall(String addr,
                                  HashMap<String, Object> json_map,
                                  HashMap<String, String> headers) throws IOException {
        URL url = new URL(addr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("GET");
        //conn.setRequestProperty("Content-Type", "application/json");
        //-------------adding headers----------------//
        for (Map.Entry m : headers.entrySet()) {
            conn.setRequestProperty(m.getKey().toString(),
                    m.getValue().toString());
        }
        String input = new Gson().toJson(json_map);
        System.out.println("input json is " + input);
        conn.connect();

        if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
            //throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            System.out.println("some error occurred");
            System.out.println(conn.getResponseMessage() + " --> " +
                    conn.getResponseCode());
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

    /**
     * sends HTTPS request
     * Uses POST method
     * Sends JSON data.
     * Returns a Map of response.
     * Assumes that the response is of type JSON.
     *
     * @param addr     url where data should be posted.
     * @param headers  HashMap of headers which should be sent along with the data.
     * @param json_map HashMap of data which has to be posted to the API
     * @return respmap HashMap of response.
     */


    public static Map HttpsPostCall(String addr,
                                    HashMap<String, Object> json_map,
                                    HashMap<String, String> headers) throws IOException {

        String https_url = addr;
        URL url;


        url = new URL(https_url);
        url = new URL(null,https_url,new sun.net.www.protocol.https.Handler());
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        //-------------adding headers----------------//
        for (Map.Entry m : headers.entrySet()) {
            conn.setRequestProperty(m.getKey().toString(),
                    m.getValue().toString());
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
            System.out.println(conn.getResponseMessage() + " --> " +
                    conn.getResponseCode());
        }
        String output;
        BufferedReader br =
                new BufferedReader(new InputStreamReader(conn.getInputStream()));
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
