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

    public String getBrowser()
    {
        String browser=pro.getProperty("browser");
        return browser;
    }
    public String isHeadless()
    {
        String isHeadless=pro.getProperty("headless");
        return isHeadless;
    }

    public String getApplicationURL()
    {
        String url=pro.getProperty("baseURL");
        return url;
    }

    public String getUsername()
    {
        String username=pro.getProperty("username");
        return username;
    }

    public String getPassword()
    {
        String password=pro.getProperty("password");
        return password;
    }

    public String getLanguage()
    {
        String language=pro.getProperty("Language");
        return language;
    }

    public int getDimensionWidth()
    {
        int DimensionWidth= Integer.parseInt(pro.getProperty("DimensionWidth"));
        return DimensionWidth;
    }
    public int getDimensionHeight()
    {
        int DimensionHeight= Integer.parseInt(pro.getProperty("DimensionHeight"));
        return DimensionHeight;
    }
}
