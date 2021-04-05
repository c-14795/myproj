package com.timex.bt.integration;

import com.google.gson.Gson;

import javax.net.ssl.HttpsURLConnection;

import java.io.*;

import java.net.HttpURLConnection;
import java.net.URL;

import java.net.URLEncoder;
import java.util.*;


/*
* Sends requests to the RestApi
*
* */
public class RestCalls {


    private static boolean contains(final int[] array, final int v) {
 
        for (final int  e : array){
                if (e == v)
                    return true;
        }

        return false;
    }

    public static HashMap<String, Object> ConvertJsonToFormData(String[] keys_for_formData, int[] indicesOfArrayParams, HashMap<String, Object>... json_map) {

        if
                (keys_for_formData.length != json_map.length) {
            throw new RuntimeException("The length of the keys " +
                    "and values should be same.\n keys_for_formData's length is " + keys_for_formData.length
                    + " and json_map's length is " + json_map.length);
        }
        HashMap<String, Object> respMap = new HashMap<String, Object>();
        int count = 0;
        System.out.println("contains"+ RestCalls.contains(indicesOfArrayParams,1));
        for (HashMap<String, Object> map : json_map) {
            String value = new Gson().toJson(map);
            value=contains(indicesOfArrayParams,count+1)?"["+value+"]":value;
            respMap.put(keys_for_formData[count], value);
            count++;
        }

        System.out.println(respMap);
        return respMap;
    }


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
            URL url;

            url = new URL(addr);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("charset", "utf-8");
            for (Map.Entry m : headers.entrySet()) {
                conn.setRequestProperty(m.getKey().toString(), m.getValue().toString());
            }
            String urlParameters = getUrlParameters(json_map);
            System.out.println("some \n"+urlParameters);
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
         /*   for (Map.Entry res : respmap.entrySet()) 
         {
                System.out.println(res.getKey() + ":" + res.getValue());

            }*/
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

        String input =  getUrlParameters(json_map);

        System.out.println("input  is " + input);
        String http_url = addr;
        http_url = input.length() == 0?http_url:http_url+ "?"+input;
        System.out.println("url json is " + http_url);
        URL url = new URL(http_url);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("GET");
        //conn.setRequestProperty("Content-Type", "application/json");
        //-------------adding headers----------------//
        for (Map.Entry m : headers.entrySet()) {
            conn.setRequestProperty(m.getKey().toString(),
                    m.getValue().toString());
        }

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

    private static String getUrlParameters(HashMap<String, Object> json_map) throws UnsupportedEncodingException {

        StringBuilder formData = new StringBuilder();
        for(Map.Entry<String,Object> map:json_map.entrySet())
        {
            formData.append(map.getKey());
            formData.append("=");
            formData.append(map.getValue().toString());
            formData.append("&");

        }
        return formData.length()>0? formData.toString().substring(0, formData.length() - 1):"";
    }

}
