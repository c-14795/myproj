package com.company.utils;


import java.util.Properties;


public class Variables {



    static {
        Props.PropertiesFile();

    }
    private static Properties properties = Props.props;
    static final String logPath = properties.getProperty("logPath", System.getProperty("user.home"));
    static final String logLevel = properties.getProperty("logLevel", "Error");
    public static final String RestApiAddr = properties.getProperty("RestApiAddr","https://httpbin.org/post");






}