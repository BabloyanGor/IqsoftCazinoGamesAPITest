package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class ReadConfig {
    Properties pro;

    public ReadConfig()
    {
        File src = new File("./Configuration/config.properties");

        try {
            FileInputStream fis = new FileInputStream(src);
            pro = new Properties();
            pro.load(fis);
        } catch (Exception e) {
            System.out.println("ReadConfig(): Exception is " + e.getMessage());
        }
    }
    public int partnerConfigNum()
    {
        return Integer.parseInt(pro.getProperty("id"));
    }
    public int getAsyncMaxTimeMinutes()
    {
        return Integer.parseInt(pro.getProperty("AsyncMaxTimeMinutes"));
    }
    public int getGamesCountOnOneCall()
    {
        return Integer.parseInt(pro.getProperty("getGamesOnOneCall"));
    }


//    public String getBrowser()
//    {
//        return pro.getProperty("browser");
//    }
//    public String isHeadless()
//    {
//        return pro.getProperty("headless");
//    }
//
//    public String getUsername()
//    {
//        return pro.getProperty("username");
//    }
//
//    public String getPassword()
//    {
//        return pro.getProperty("password");
//    }
//
//    public String getLanguage()
//    {
//        return pro.getProperty("Language");
//    }
//
//    public int getDimensionWidth()
//    {
//        return Integer.parseInt(pro.getProperty("DimensionWidth"));
//    }
//    public int getDimensionHeight()
//    {
//        return Integer.parseInt(pro.getProperty("DimensionHeight"));
//    }


}
