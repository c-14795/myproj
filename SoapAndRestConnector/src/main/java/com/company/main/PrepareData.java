package com.company.main;

import org.apache.log4j.Logger;

import static  com.company.utils.RestCalls.HttpPostCall;
import static  com.company.utils.RestCalls.HttpsPostCall;
import static com.company.utils.Variables.RestApiAddr;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PrepareData {

    String findCountryCode(String something) throws IOException {
        HashMap<String,Object> payload = new HashMap<String, Object>();
        payload.put("Something",something);
        HashMap<String,String> headers = new HashMap<String, String>();
        headers.put("Accept", "application/json");
        Map result = RestApiAddr.contains("https")?HttpsPostCall(RestApiAddr,payload,headers):HttpPostCall(RestApiAddr,payload,headers);
        return (String) result.get("Country");
    }
}
