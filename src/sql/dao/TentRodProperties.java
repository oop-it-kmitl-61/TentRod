package sql.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

public class TentRodProperties {
    InputStream inputStream;

    public String getPropValues(String propName) {
        String result = "";

        try {
            Properties prop = new Properties();
            String propFileName = "config.properties";

            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }

            Date time = new Date(System.currentTimeMillis());

            // get the property value and print it out

            result = prop.getProperty(propName);

        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
            }
        }
        return result;
    }

    public String getSysEmail() {
        return getPropValues("SYSTEM_EMAIL");
    }

    public String getSysPass() {
        return getPropValues("SYSTEM_EMAIL_PASSWORD");
    }

    public String getNotiEmail() {
        return getPropValues("TENTOWNER_TO_EMAIL");
    }

    public String getDBConnection() {
        return getPropValues("DBCONNECTION");
    }

    public String getDBUser() {
        return getPropValues("DBUSER");
    }

    public String getDBPassword() {
        return getPropValues("DBPASSWORD");
    }

    public static void main (String[] args) {
        try {
            TentRodProperties tprop = new TentRodProperties();
            System.out.println(tprop.getPropValues("DBCONNECTION"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
