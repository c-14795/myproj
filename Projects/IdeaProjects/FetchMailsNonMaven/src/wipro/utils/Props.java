package wipro.utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public final class Props {

    public static Properties PropertiesFile() {


        File file = null;
        Properties properties = null;
        try {
            File path = new File(Props.class.getProtectionDomain().getCodeSource().getLocation().toURI());
            file = new File(new File(path.getParent()).getCanonicalPath() + File.separator + "config.properties");
            //file = new File("/home/c14795/IdeaProjects/FetchMails/config.properties");

            if (!file.exists()) {
                System.out.println("Exception " + file.getAbsolutePath());
                System.out.println("properties file not found in the above path");
                System.exit(1);
            }

            FileInputStream fileInput = new FileInputStream(file);
            properties = new Properties();
            properties.load(fileInput);

        } catch (Exception e) {
            System.out.println("Exception " + file.getAbsolutePath());
            System.out.println("properties file not found in the above path");
            System.exit(1);
        }

        return properties;
    }
}
