package com.company;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class RestConnector {

	@WebMethod
	String getCountryCode(String something)
	{
		String countryCode = "IN";
		
		return countryCode;
	}
	
}