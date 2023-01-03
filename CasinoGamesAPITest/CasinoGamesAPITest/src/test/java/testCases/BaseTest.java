package testCases;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.support.PageFactory;
import org.testng.SkipException;
import org.testng.annotations.*;
import pageObjects.*;
import utilities.DriverFactory;
import utilities.ReadConfig;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;





public class BaseTest extends DriverFactory {

    static final int MINUTES_PER_HOUR = 60;
    static final int SECONDS_PER_MINUTE = 60;
    static final int SECONDS_PER_HOUR = SECONDS_PER_MINUTE * MINUTES_PER_HOUR;
    public static Logger logger;
    public int partnerID;
    public String getGamesAPIUrl;
    public String getURLAPIUrl;
    public int getUserID;
    public String getGamesOrigin;
    public String getPrematchTreeOrigin;
    public String getMarketByIDOrigin;
    public String getGamesRecurse;
    public String getGamesPartnerName;
    public String getGamesBaseURL;
    public String getAllLifeGames;

    public String getPreMatchTree;


    public String getPrematchMatchesMarketsByID(int gameID){
        return getMarketByID + gameID + "&OddsType=0";
    }

    public String getMarketByID;



    public CraftBet_01_Header_Page craftBet_01_header_page;
    public CraftBet_03_Login_PopUp_Page craftBet_03_login_popUp_page;
    ReadConfig readConfig = new ReadConfig();
    public String isHeadless = readConfig.isHeadless();
    public String browser = readConfig.getBrowser();
    public String username = readConfig.getUsername();
    public String password = readConfig.getPassword();
    public String language = readConfig.getLanguage();
    public int DimensionHeight = readConfig.getDimensionHeight();
    //region <Page Class Instances  >
    public int DimensionWidth = readConfig.getDimensionWidth();
    public int partnerConfigNum = readConfig.partnerConfigNum();

    //endregion
    public BaseTest() {
    }


    public void writeInExel(ArrayList<String> errorSrcXl , String src, String shitName) throws IOException {
        String target = System.getProperty("user.dir") +src;
        XSSFWorkbook workbook = new XSSFWorkbook();
        FileOutputStream file = new FileOutputStream(target);
        XSSFSheet sheet = workbook.createSheet(shitName);
        sheet.setColumnWidth(0, 20000);
        int l = 0;
        for (String err : errorSrcXl) {
            XSSFRow row = sheet.createRow(l);
            row.createCell(0).setCellValue(err);
            l++;
        }
        workbook.write(file);
        workbook.close();
    }


    public static void saveImage(String imageUrl, String destinationFile) throws IOException {
        URL url = new URL(imageUrl);
        InputStream is = url.openStream();
        OutputStream os = Files.newOutputStream(Paths.get(destinationFile));

        byte[] b = new byte[2048];
        int length;

        while ((length = is.read(b)) != -1) {
            os.write(b, 0, length);
        }

        is.close();
        os.close();
    }



    @BeforeMethod
    public void setup() throws InterruptedException {
        logger = Logger.getLogger("craftBetWorld");
        PropertyConfigurator.configure("Log4j.properties");

        switch (partnerConfigNum) {

            case 1: {
                partnerID = 1;
                getGamesAPIUrl = "https://websitewebapi.craftbet.com/1/api/Main/GetGames";
                getURLAPIUrl = "https://websitewebapi.craftbet.com/1/api/Main/GetProductUrl";
                getUserID = 1630845;
                getGamesOrigin = "https://craftbet.com";
                getGamesRecurse = "https://resources.craftbet.com/products/";
                getGamesPartnerName = "Craftbet";
                getGamesBaseURL = "https://craftbet.com";

                getAllLifeGames = "https://sportsbookwebsitewebapi.craftbet.com/website/getlivematchesoverview?LanguageId=en&TimeZone=4&origin=https://sportsbookwebsite.craftbet.com";

                getPreMatchTree = "https://sportsbookwebsitewebapi.craftbet.com/website/getprematchtree?LanguageId=en&TimeZone=4";
                getPrematchTreeOrigin = "https://sportsbookwebsite.craftbet.com";
                getMarketByID = "https://sportsbookwebsitewebapi.craftbet.com/website/getmarketsbymatchid?LanguageId=en&TimeZone=4&MatchId="; //3713041&OddsType=0
                getMarketByIDOrigin = "https://sportsbookwebsite.craftbet.com";

                break;
            }
            case 2: {
                partnerID = 56;
                getGamesAPIUrl = "https://websitewebapi.pokies2go.io/56/api/Main/GetGames";
                getURLAPIUrl = "https://websitewebapi.pokies2go.io/56/api/Main/GetProductUrl";
                getUserID = 1650272;
                getGamesOrigin = "https://pokies2go.io";
                getGamesRecurse = "https://resources.pokies2go.io/products/";
                getGamesPartnerName = "Pokies2go";
                getGamesBaseURL = "https://pokies2go.io";
                break;
            }

            case 3: {
                partnerID = 59;
                getGamesAPIUrl = "https://websitewebapi.tigerbet001.com/59/api/Main/GetGames";
                getURLAPIUrl = "https://websitewebapi.tigerbet001.com/59/api/Main/GetProductUrl";
                getUserID = 1650824;
                getGamesOrigin = "https://tigerbet001.com";
                getGamesRecurse = "https://resources.tigerbet001.com/products/";
                getGamesPartnerName = "tigerbet001";
                getGamesBaseURL = "https://tigerbet001.com";
                break;
            }

            case 4: {
                partnerID = 52;
                getGamesAPIUrl = "https://websitewebapi.graciazz.com/52/api/Main/GetGames";
                getURLAPIUrl = "https://websitewebapi.graciazz.com/52/api/Main/GetProductUrl";
                getUserID = 1650804;
                getGamesOrigin = "https://graciazz.com";
                getGamesRecurse = "https://resources.graciazz.com/products/";
                getGamesPartnerName = "graciazz";
                getGamesBaseURL = "https://graciazz.com";
                break;
            }

            case 5: {
                partnerID = 47;
                getGamesAPIUrl = "https://websitewebapi.crypto-casino.games/47/api/Main/GetGames";
                getURLAPIUrl = "https://websitewebapi.crypto-casino.games/47/api/Main/GetProductUrl";
                getUserID = 1650805;
                getGamesOrigin = "https://crypto-casino.games";
                getGamesRecurse = "https://resources.crypto-casino.games/products/";
                getGamesPartnerName = "cryptoCasino";
                getGamesBaseURL = "https://crypto-casino.games";
                break;
            }

            case 6: {
                partnerID = 48;
                getGamesAPIUrl = "https://websitewebapi.betvito.com/48/api/Main/GetGames";
                getURLAPIUrl = "https://websitewebapi.betvito.com/48/api/Main/GetProductUrl";
                getUserID = 1650817;
                getGamesOrigin = "https://betvito.com";
                getGamesRecurse = "https://resources.https://betvito.com/products/";
                getGamesPartnerName = "Betvito";
                getGamesBaseURL = "https://betvito.com";
                break;
            }
            case 7: {
                partnerID = 41;
                getGamesAPIUrl = "https://websitewebapi.vikwin.com/41/api/Main/GetGames";
                getURLAPIUrl = "https://websitewebapi.vikwin.com/41/api/Main/GetProductUrl";
                getUserID = 1650818;
                getGamesOrigin = "https://vikwin.com";
                getGamesRecurse = "https://resources.vikwin.com/products/";
                getGamesPartnerName = "Vikwin";
                getGamesBaseURL = "https://vikwin.com";
                break;
            }
            case 8: {
                partnerID = 38;
                getGamesAPIUrl = "https://websitewebapi.bravowin.com/38/api/Main/GetGames";
                getURLAPIUrl = "https://websitewebapi.bravowin.com/38/api/Main/GetProductUrl";
                getUserID = 1650819;
                getGamesOrigin = "https://bravowin.com";
                getGamesRecurse = "https://resources.bravowin.com/products/";
                getGamesPartnerName = "Bravowin";
                getGamesBaseURL = "https://bravowin.com";
                break;
            }
            case 9: {
                partnerID = 54;
                getGamesAPIUrl = "https://websitewebapi.play.tether.bet/54/api/Main/GetGames";
                getURLAPIUrl = "https://websitewebapi.play.tether.bet/54/api/Main/GetProductUrl";
                getUserID = 1650822;
                getGamesOrigin = "https://play.tether.bet";
                getGamesRecurse = "https://resources.tether.bet/products/";
                getGamesPartnerName = "Tetherbet";
                getGamesBaseURL = "https://play.tether.bet";


                getAllLifeGames = "https://sportsbookwebsitewebapi.play.tether.bet/website/getlivematchesoverview?LanguageId=en&TimeZone=4&origin=https://sportsbookwebsite.play.tether.bet";

                getPreMatchTree = "https://sportsbookwebsitewebapi.play.tether.bet/website/getprematchtree?LanguageId=en&TimeZone=4";

                getPrematchTreeOrigin = "https://sportsbookwebsite.play.tether.bet";
                /////getPrematchTreeOrigin = " https://sportsbookwebsitewebapi.play.tether.bet/website/getprematchtree?LanguageId=en&TimeZone=4";

                getMarketByID = "https://sportsbookwebsitewebapi.play.tether.bet/website/getmarketsbymatchid?LanguageId=en&TimeZone=4&MatchId="; //3956272&OddsType=0

                getMarketByIDOrigin = "https://sportsbookwebsite..play.tether.bet";
                break;
            }

            case 10: {
                partnerID = 45;
                getGamesAPIUrl = "https://websitewebapi.bet2win.vip/45/api/Main/GetGames";
                getURLAPIUrl = "https://websitewebapi.bet2win.vip/45/api/Main/GetProductUrl";
                getUserID = 1650845;
                getGamesOrigin = "https://bet2win.vip";
                getGamesRecurse = "https://resources.bet2win.vip/products/";
                getGamesPartnerName = "bet2win";
                getGamesBaseURL = "https://bet2win.vip";
                break;
            }

            case 11: {
                partnerID = 57;
                getGamesAPIUrl = "https://websitewebapi.craftbet.la/57/api/Main/GetGames";
                getURLAPIUrl = "https://websitewebapi.craftbet.la/57/api/Main/GetProductUrl";
                getUserID = 1651023;
                getGamesOrigin = "https://craftbet.la";
                getGamesRecurse = "https://resources.craftbet.la/products/";
                getGamesPartnerName = "CraftLA";
                getGamesBaseURL = "https://craftbet.la";
                break;
            }
            case 12: {
                partnerID = 43;
                getGamesAPIUrl = "https://websitewebapi.oceanbet.io/43/api/Main/GetGames";
                getURLAPIUrl = "https://websitewebapi.oceanbet.io/43/api/Main/GetProductUrl";
                getUserID = 1651063;
                getGamesOrigin = "https://oceanbet.io";
                getGamesRecurse = "https://resources.oceanbet.io/products/";
                getGamesPartnerName = "oceanbet";
                getGamesBaseURL = "https://oceanbet.io";
                break;
            }
            case 13: {
                partnerID = 55;
                getGamesAPIUrl = "https://websitewebapi.play.baqto.com/55/api/Main/GetGames";
                getURLAPIUrl = "https://websitewebapi.play.baqto.com/55/api/Main/GetProductUrl";
                getUserID = 1651687;
                getGamesOrigin = "https://play.baqto.com";
                getGamesRecurse = "https://resources.play.baqto.com/products/";
                getGamesPartnerName = "baqto";
                getGamesBaseURL = "https://play.baqto.com";
                break;
            }

            case 14: {
                partnerID = 14;
                getGamesAPIUrl = "https://websitewebapi.winmatch.win/14/api/Main/GetGames";
                getURLAPIUrl = "https://websitewebapi.winmatch.win/14/api/Main/GetProductUrl";
                getUserID = 1656377;
                getGamesOrigin = "https://winmatch.win";
                getGamesRecurse = "https://resources.winmatch.win/products/";
                getGamesPartnerName = "winmatch";
                getGamesBaseURL = "https://winmatch.win";
                break;
            }
            case 15: {
                partnerID = 66;
                getGamesAPIUrl = "https://websitewebapi.kontrbet.com/66/api/Main/GetGames";
                getURLAPIUrl = "https://websitewebapi.kontrbet.com/66/api/Main/GetProductUrl";
                getUserID = 1676748;
                getGamesOrigin = "https://kontrbet.com";
                getGamesRecurse = "https://resources.kontrbet.com/products/";
                getGamesPartnerName = "kontrbet";
                getGamesBaseURL = "https://kontrbet.com";
                break;
            }


            case 100: {
                partnerID = 1;
                getGamesAPIUrl = "https://websitewebapi.exclusivebet-stage.com/1/api/Main/GetGames";
                getURLAPIUrl = "https://websitewebapi.exclusivebet-stage.com/1/api/Main/GetProductUrl";
                getUserID = 2;
                getGamesOrigin = "https://exclusivebet-stage.com";
                getGamesRecurse = "https://resources.exclusivebet-stage.com/products/";
                getGamesPartnerName = "Exclusivebet";
                getGamesBaseURL = "https://exclusivebet-stage.com";
                break;
            }


            default: {
                logger.error("Wrong Partner ID: From config.properties file insert the right PartnerID Please");
                throw new SkipException("From config.properties file choose the right PartnerID Please");
            }
        }


        try {
            super.initDriver(getGamesBaseURL, browser, isHeadless);
            logger.info("URL: " + getGamesBaseURL + "  Browser: " + browser);
            Dimension d = new Dimension(DimensionWidth, DimensionHeight);  //
            if (DimensionWidth > 100 && DimensionHeight > 100) {
                driver.manage().window().setSize(d);
                logger.info("Window size is: " + DimensionWidth + " x " + DimensionHeight);
            }
        } catch (org.openqa.selenium.TimeoutException exception) {
            super.initDriver(getGamesBaseURL, browser, isHeadless);

        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        //region <Page Class Instance Initialization >
        craftBet_01_header_page = PageFactory.initElements(driver, CraftBet_01_Header_Page.class);
        craftBet_03_login_popUp_page = PageFactory.initElements(driver, CraftBet_03_Login_PopUp_Page.class);

        logger.info("All Page elements are initialized");

        //endregion

        craftBet_01_header_page.setItem("lang", language);
        craftBet_01_header_page.navigateRefresh();
        logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>  Test started ");
    }



    @AfterMethod
    public void tearDown() {
        try {
            driver.quit();
            logger.info("Browser closed");
        } catch (Exception exception) {
            driver.quit();
            logger.info("Browser close_order has an exception");
        }
        logger.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<  Test finished ");
    }

    @AfterSuite
    public void Finish() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<  Test Suite finished  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>  ");
    }
}
