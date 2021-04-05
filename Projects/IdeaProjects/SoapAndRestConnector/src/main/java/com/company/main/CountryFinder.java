package com.company.main;


import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import java.io.IOException;


@WebService
@SOAPBinding(style = Style.RPC)
public class CountryFinder {


    //    private static final Logger logger = Logger.getLogger("default_logger");
    private final PrepareData prepareData = new PrepareData();

    @WebMethod
    public String getCountryName(String something) throws IOException {

        return prepareData.findCountryCode("Hello");

    }
}
