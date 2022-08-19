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
    public String baseURL = readConfig.getApplicationURL();
    public String isHeadless = readConfig.isHeadless();
    public String browser = readConfig.getBrowser();
    public String username = readConfig.getUsername();
    public String password = readConfig.getPassword();
    public String language = readConfig.getLanguage();
    public int DimensionHeight = readConfig.getDimensionHeight();
    public int DimensionWidth = readConfig.getDimensionWidth();
    public static Logger logger;

    //region <Page Class Instances  >

    public CraftBet_01_Header_Page craftBet_01_header_page;
    public CraftBet_03_Login_PopUp_Page craftBet_03_login_popUp_page;
    public CraftBet_02_Footer_Page craftBet_02_footer_page;
    public CraftBet_04_PasswordRecovery_Page craftBet_04_passwordRecovery_page;
    public CraftBet_05_SignUp_PopUp_Page craftBet_05_signUp_popUp_page;
    public CraftBet_11_Sports_Page craftBet_11_sports_page;
    public CraftBet_12_Life_Page craftBet_12_life_page;
    public CraftBet_13_AsianSport_Page craftBet_13_asianSport_page;
    public CraftBet_14_VirtualSport_Page craftBet_14_virtualSport_page;
    public CraftBet_15_Casino_Page craftBet_15_casino_page;
    public CraftBet_16_LifeCasino_Page craftBet_16_lifeCasino_page;
    public CraftBet_17_VirtualGames_Page craftBet_17_virtualGames_page;
    public CraftBet_18_SkillGames_Page craftBet_18_skillGames_page;
    public CraftBet_19_Keno_Page craftBet_19_keno_page;
    public CraftBet_20_HighLow_Page craftBet_20_highLow_page;
    public CraftBet_21_Crash_Page craftBet_21_crash_page;
    public CraftBet_22_Lottery_Page craftBet_22_lottery_page;
    public CraftBet_23_Promotions_Page craftBet_23_promotions_page;
    public CraftBet_24_News_Page craftBet_24_news_page;
    public CraftBet_25_MobileApps_Page craftBet_25_mobileApps_page;
    public CraftBet_06_Deposit_Page craftBet_06_deposit_page;
    public Casino_GetGames_API_Request Cazino_Img_API_Request;


    //endregion


    public BaseTest() {
    }

    @BeforeMethod
    public void setup() throws InterruptedException {
        logger = Logger.getLogger("craftBet");
        PropertyConfigurator.configure("Log4j.properties");

        try {
            super.initDriver(baseURL, browser, isHeadless);
            Dimension d = new Dimension(DimensionWidth, DimensionHeight);  //
            if (DimensionWidth > 1250  &&  DimensionHeight > 750){
                driver.manage().window().setSize(d);
                logger.info("Window size is: "+ DimensionWidth + " x " + DimensionHeight);
            }

        } catch (org.openqa.selenium.TimeoutException exception) {
            super.initDriver(baseURL, browser, isHeadless);

        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        //region <Page Class Instance Initialization >
        craftBet_01_header_page = PageFactory.initElements(this.driver, CraftBet_01_Header_Page.class);
        craftBet_02_footer_page = PageFactory.initElements(this.driver, CraftBet_02_Footer_Page.class);
        craftBet_03_login_popUp_page = PageFactory.initElements(this.driver, CraftBet_03_Login_PopUp_Page.class);
        craftBet_04_passwordRecovery_page = PageFactory.initElements(this.driver, CraftBet_04_PasswordRecovery_Page.class);
        craftBet_05_signUp_popUp_page = PageFactory.initElements(this.driver, CraftBet_05_SignUp_PopUp_Page.class);
        craftBet_11_sports_page = PageFactory.initElements(this.driver, CraftBet_11_Sports_Page.class);
        craftBet_12_life_page = PageFactory.initElements(this.driver, CraftBet_12_Life_Page.class);
        craftBet_13_asianSport_page = PageFactory.initElements(this.driver, CraftBet_13_AsianSport_Page.class);
        craftBet_14_virtualSport_page = PageFactory.initElements(this.driver, CraftBet_14_VirtualSport_Page.class);
        craftBet_15_casino_page = PageFactory.initElements(this.driver, CraftBet_15_Casino_Page.class);
        craftBet_16_lifeCasino_page = PageFactory.initElements(this.driver, CraftBet_16_LifeCasino_Page.class);
        craftBet_17_virtualGames_page = PageFactory.initElements(this.driver, CraftBet_17_VirtualGames_Page.class);
        craftBet_18_skillGames_page = PageFactory.initElements(this.driver, CraftBet_18_SkillGames_Page.class);
        craftBet_19_keno_page = PageFactory.initElements(this.driver, CraftBet_19_Keno_Page.class);
        craftBet_20_highLow_page = PageFactory.initElements(this.driver, CraftBet_20_HighLow_Page.class);
        craftBet_21_crash_page = PageFactory.initElements(this.driver, CraftBet_21_Crash_Page.class);
        craftBet_22_lottery_page = PageFactory.initElements(this.driver, CraftBet_22_Lottery_Page.class);
        craftBet_23_promotions_page = PageFactory.initElements(this.driver, CraftBet_23_Promotions_Page.class);
        craftBet_24_news_page = PageFactory.initElements(this.driver, CraftBet_24_News_Page.class);
        craftBet_25_mobileApps_page = PageFactory.initElements(this.driver, CraftBet_25_MobileApps_Page.class);
        craftBet_06_deposit_page = PageFactory.initElements(this.driver, CraftBet_06_Deposit_Page.class);

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
