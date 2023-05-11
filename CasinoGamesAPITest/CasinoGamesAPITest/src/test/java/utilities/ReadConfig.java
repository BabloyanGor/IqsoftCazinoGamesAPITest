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
            System.out.println("Exception is " + e.getMessage());
        }
    }
    public int partnerConfigNum()
    {
        return Integer.parseInt(pro.getProperty("id"));
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
