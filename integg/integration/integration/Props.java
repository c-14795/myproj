package com.timex.bt.integration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import java.util.Properties;

class Props {
/**
 * Read the properties file and returns the properties object.
 * @param filePath Filepath for the properties file to be read.
* */

    Properties getColNamesFromPropertiesFile(String filePath) {
        File file = null;
        Properties properties = new Properties();
        try {
            file = new File(filePath);
            if (!file.exists()) {
                System.err.println("properties file not found in the below path");
                throw new FileNotFoundException(file.getAbsolutePath());

            }

            FileInputStream fileInput = new FileInputStream(file);
            properties.load(fileInput);

        } catch (Exception e) {
            System.out.println("Exception " + file.getAbsolutePath());
            System.out.println("properties file not found in the above path");
            //            System.exit(1);
        }

        return properties;

    }


}
