package testCases;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.support.PageFactory;
import org.testng.SkipException;
import org.testng.annotations.*;
import pageObjects.*;
import utilities.DriverFactory;
import utilities.ReadConfig;

import java.time.Duration;


public class BaseTest extends DriverFactory {

    ReadConfig readConfig = new ReadConfig();

    public String isHeadless = readConfig.isHeadless();
    public String browser = readConfig.getBrowser();
    public String username = readConfig.getUsername();
    public String password = readConfig.getPassword();
    public String language = readConfig.getLanguage();
    public int DimensionHeight = readConfig.getDimensionHeight();
    public int DimensionWidth = readConfig.getDimensionWidth();
    public int partnerID = readConfig.getPartnerID();



    public String getGamesAPIUrl;
    public String getURLAPIUrl;
    public int getUsertID;
    public String getGamesOrigin;
    public String getGamesRecurse;
    public String getGamesPartnerName;
    public String getGamesBaseURL;

    public static Logger logger;

    //region <Page Class Instances  >

    public CraftBet_01_Header_Page craftBet_01_header_page;
    public CraftBet_03_Login_PopUp_Page craftBet_03_login_popUp_page;



    //endregion
    public BaseTest() {
    }

    @BeforeMethod
    public void setup() throws InterruptedException {
        logger = Logger.getLogger("craftBet");
        PropertyConfigurator.configure("Log4j.properties");

        switch (partnerID){
            case 1: {
                getGamesAPIUrl = "https://websitewebapi.craftbet.com/1/api/Main/GetGames";
                getURLAPIUrl = "https://websitewebapi.craftbet.com/1/api/Main/GetProductUrl";
                getUsertID=1630845;
                getGamesOrigin = "https://craftbet.com";
                getGamesRecurse = "https://resources.craftbet.com/products/";
                getGamesPartnerName = "Craftbet";
                getGamesBaseURL = "https://craftbet.com";
                break;
            }
            case 56: {
                getGamesAPIUrl = "https://websitewebapi.pokies2go.io/56/api/Main/GetGames";
                getURLAPIUrl = "https://websitewebapi.pokies2go.io/56/api/Main/GetProductUrl";
                getUsertID=1650272;
                getGamesOrigin = "https://pokies2go.io";
                getGamesRecurse = "https://resources.pokies2go.io/products/";
                getGamesPartnerName = "Pokies2go";
                getGamesBaseURL = "https://pokies2go.io";
                break;
            }

            default:{
                logger.error("Wrong Partner ID: From config.properties file insert the right PartnerID Please");
                throw new SkipException("From config.properties file choose the right PartnerID Please");
            }
        }


        try {
            super.initDriver(getGamesBaseURL, browser, isHeadless);
            Dimension d = new Dimension(DimensionWidth, DimensionHeight);  //
            if (DimensionWidth > 1250  &&  DimensionHeight > 750){
                driver.manage().window().setSize(d);
                logger.info("Window size is: "+ DimensionWidth + " x " + DimensionHeight);
            }

        }


        catch (org.openqa.selenium.TimeoutException exception) {
            super.initDriver(getGamesBaseURL, browser, isHeadless);

        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        //region <Page Class Instance Initialization >
        craftBet_01_header_page = PageFactory.initElements(this.driver, CraftBet_01_Header_Page.class);
        craftBet_03_login_popUp_page = PageFactory.initElements(this.driver, CraftBet_03_Login_PopUp_Page.class);

//        Casino_Img_API_Request = PageFactory.initElements(this.driver, Casino_Img_API_Request.class);

        logger.info("All Page elements are initialized");

        //endregion

        craftBet_01_header_page.setItem("lang", language);
        craftBet_01_header_page.navigateRefresh();
        logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>  Test started ");
    }

    @AfterMethod
    public void tearDown() {
        try {
            this.driver.quit();
            logger.info("Browser closed");
        } catch (Exception exception) {
            this.driver.quit();
            logger.info("Browser close_order has an exception");
        }
        logger.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<  Test finished ");
    }

    @AfterSuite
    public void Finish() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<  Test Suite finished  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>  ");
    }
}
