package com.company.utils;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;

import java.io.File;

public class LogController {

    private static String Path;
    private static String LogLevel;

    private static void set_path_level() {

        Path = Variables.logPath + File.separator + "Rest_logging";
        LogLevel = Variables.logLevel;
    }


    static   String set_logger() {
        if(Path==null)
            set_path_level();
        RollingFileAppender appender = new RollingFileAppender();
        File f = new File(Path);
        if (!f.exists()) {
            f.mkdirs();
        }

        //Logger logger = Logger.getLogger(clazz);

        appender.setFile(Path+File.separator+"Logfile.log");
        appender.setMaxFileSize("5MB");
        appender.setMaxBackupIndex(10);
        appender.setLayout(new PatternLayout("%d{yyyy-MM-dd HH:mm:ss} %-5p %7C - %M:%L - %m%n"));
        appender.activateOptions();
        Logger default_logger=Logger.getRootLogger();
        default_logger.addAppender(appender);
        default_logger.setLevel(Level.toLevel(LogLevel,Level.ERROR));

        return "HI";

    }




}