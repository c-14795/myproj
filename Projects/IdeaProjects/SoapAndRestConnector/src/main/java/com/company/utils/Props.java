package com.company.utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

class Props {

    public static Properties props = null;

    static void PropertiesFile() {
        File file = null;
        Properties properties =  new Properties();
        try {
            File path = new File(System.getProperty("user.home"));
            file = new File(path + File.separator + "config.properties");
            //     logger.debug(file.getAbsolutePath() + "----> checking the config file in this path");
            if (!file.exists()) {
                System.out.println("Exception " + file.getAbsolutePath());
                System.out.println("properties file not found in the above path");
//                System.exit(1);
            }

            FileInputStream fileInput = new FileInputStream(file);
            properties.load(fileInput);

        } catch (Exception e) {
            System.out.println("Exception " + file.getAbsolutePath());
            System.out.println("properties file not found in the above path");
//            System.exit(1);
        }

        props = properties;
    }


}