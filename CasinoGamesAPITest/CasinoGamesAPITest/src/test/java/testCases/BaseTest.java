package testCases;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.support.PageFactory;
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



    public String APIUrl ;
    public String origin ;
    public String recurse ;
    public String partnerName;
    public String baseURL ;







    public static Logger logger;

    //region <Page Class Instances  >

    public CraftBet_01_Header_Page craftBet_01_header_page;
    public CraftBet_03_Login_PopUp_Page craftBet_03_login_popUp_page;

    public Casino_GetGames_API_Request Cazino_Img_API_Request;


    //endregion
    public BaseTest() {
    }

    @BeforeMethod
    public void setup() throws InterruptedException {
        logger = Logger.getLogger("craftBet");
        PropertyConfigurator.configure("Log4j.properties");

        switch (partnerID){
            case 1: {
                APIUrl = "https://websitewebapi.craftbet.com/1/api/Main/GetGames";
                origin = "https://craftbet.com";
                recurse = "https://resources.craftbet.com/products/";
                partnerName = "Craftbet";
                baseURL = "https://craftbet.com";
                break;
            }
            case 56: {
                APIUrl = "https://websitewebapi.pokies2go.io/56/api/Main/GetGames";
                origin = "https://pokies2go.io";
                recurse = "https://resources.pokies2go.io/products/";
                partnerName = "Pokies2go";
                baseURL = "https://pokies2go.io";
                break;
            }

            default:{
                APIUrl = "";
                origin = "";
                recurse = "";
                partnerName = "";
            }
        }


        try {
            super.initDriver(baseURL, browser, isHeadless);
            Dimension d = new Dimension(DimensionWidth, DimensionHeight);  //
            if (DimensionWidth > 1250  &&  DimensionHeight > 750){
                driver.manage().window().setSize(d);
                logger.info("Window size is: "+ DimensionWidth + " x " + DimensionHeight);
            }

        }



        catch (org.openqa.selenium.TimeoutException exception) {
            super.initDriver(baseURL, browser, isHeadless);

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
//        craftBet_header_page.selectEnglishLanguageFromDropDown();
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
