package com.company;

import com.company.fin.SendRequests;
import com.company.fin.SendRequestsService;
import com.company.main.CountryFinder;
import com.company.main.CountryFinderService;
import com.company.main.IOException_Exception;

public class Main {

    public static void main(String[] args) throws IOException_Exception, com.company.fin.IOException_Exception {
	// write your code here
//        CountryFinder countryFinder = new CountryFinderService().getCountryFinderPort();
//        System.out.println(countryFinder.getCountryName("IN"));
        SendRequests sendRequests = new SendRequestsService().getSendRequestsPort();

        //sendRequests.getXmlData("something","test.xml");
        sendRequests.postData("select OMS_ORDER_HEADER_ID,PARTNER_NAME from oms_order_headers","http://144.21.70.88:8080/orderdetails/webresources/orderdetails.omsorderheaders","/tmp/mapping.properties");
    }
}
