package testCases;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.SkipException;
import org.testng.annotations.*;
import pageObjects.*;
import utilities.ReadConfig;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;


public class BaseTest {

    static final int MINUTES_PER_HOUR = 60;
    static final int SECONDS_PER_MINUTE = 60;
    static final int SECONDS_PER_HOUR = SECONDS_PER_MINUTE * MINUTES_PER_HOUR;
    public static Logger logger;
    public int partnerID;
    public String getGamesAPIUrl;
    public String getURLAPIUrl;
    //    public int getUserID;
    public String getGamesOrigin;
    public String getPrematchTreeOrigin;
    public String getMarketByIDOrigin;
    public String getGamesRecurse;
    public String getGamesPartnerName;
    public String getGamesBaseURL;
    public String loginClient;
    public String getAllLifeGames;

    public String getPreMatchTree;


    public String getPrematchMatchesMarketsByID(int gameID) {
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
    public int DimensionWidth = readConfig.getDimensionWidth();
    public int partnerConfigNum = readConfig.partnerConfigNum();

    //endregion
    public BaseTest() {
    }


//    public void writeInExel(ArrayList<String> errorSrcXl, String src, String shitName) throws IOException {
//        String target = System.getProperty("user.dir") + src;
//        XSSFWorkbook workbook = new XSSFWorkbook();
//        FileOutputStream file = new FileOutputStream(target);
//        XSSFSheet sheet = workbook.createSheet(shitName);
//        sheet.setColumnWidth(0, 20000);
//        int l = 0;
//        for (String err : errorSrcXl) {
//            XSSFRow row = sheet.createRow(l);
//            row.createCell(0).setCellValue(err);
//            l++;
//        }
//        workbook.write(file);
//        workbook.close();
//    }


    public void writeInExel(ArrayList<String> errorSrcXl, String src, String shitName) throws IOException {

        String filePath = System.getProperty("user.dir") + src;
        Workbook workbook;
        File file = new File(filePath);
        if (file.exists()) {
            FileInputStream fis = new FileInputStream(file);
            workbook = new XSSFWorkbook(fis);
        } else {
            workbook = new XSSFWorkbook();
        }
        if (workbook.getSheet(shitName) != null) {
            workbook.removeSheetAt(workbook.getSheetIndex(shitName));
        }
        Sheet sheet = workbook.createSheet(shitName);
        if (sheet == null) {
            sheet = workbook.createSheet("Sheet1");
        }
        sheet.setColumnWidth(0, 50000);

        int l = 0;
        for (String err : errorSrcXl) {
            Row row = sheet.createRow(l);
            row.createCell(0).setCellValue(err);
            l++;
        }
        // save the workbook to the file
        FileOutputStream fos = new FileOutputStream(filePath);
        workbook.write(fos);
        // close the streams
        fos.close();
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
                //                getUserID = 1630845;
                partnerID = 1;
                getGamesAPIUrl = "https://websitewebapi.craftbet.com/1/api/Main/GetGames";
                getURLAPIUrl = "https://websitewebapi.craftbet.com/1/api/Main/GetProductUrl";
                getGamesOrigin = "https://craftbet.com";
                getGamesRecurse = "https://resources.craftbet.com/products/";
                getGamesPartnerName = "Craftbet";
                getGamesBaseURL = "https://craftbet.com";
                loginClient = "https://websitewebapi.craftbet.com/1/api/Main/LoginClient";

                getAllLifeGames = "https://sportsbookwebsitewebapi.craftbet.com/website/getlivematchesoverview?LanguageId=en&TimeZone=4&origin=https://sportsbookwebsite.craftbet.com";
                getPreMatchTree = "https://sportsbookwebsitewebapi.craftbet.com/website/getprematchtree?LanguageId=en&TimeZone=4";
                getPrematchTreeOrigin = "https://sportsbookwebsite.craftbet.com";
                getMarketByID = "https://sportsbookwebsitewebapi.craftbet.com/website/getmarketsbymatchid?LanguageId=en&TimeZone=4&MatchId="; //3713041&OddsType=0
                getMarketByIDOrigin = "https://sportsbookwebsite.craftbet.com";

                break;
            }

            case 2: {
                //                getUserID = 1650272;
                partnerID = 56;
                getGamesAPIUrl = "https://websitewebapi.pokies2go.io/56/api/Main/GetGames";
                getURLAPIUrl = "https://websitewebapi.pokies2go.io/56/api/Main/GetProductUrl";
                getGamesOrigin = "https://pokies2go.io";
                getGamesRecurse = "https://resources.pokies2go.io/products/";
                getGamesPartnerName = "Pokies2go";
                getGamesBaseURL = "https://pokies2go.io";
                loginClient = "https://websitewebapi.pokies2go.io/56/api/Main/LoginClient";
                break;
            }

            case 3: {
                //                getUserID = 1650824;
                partnerID = 59;
                getGamesAPIUrl = "https://websitewebapi.tigerbet001.com/59/api/Main/GetGames";
                getURLAPIUrl = "https://websitewebapi.tigerbet001.com/59/api/Main/GetProductUrl";
                getGamesOrigin = "https://tigerbet001.com";
                getGamesRecurse = "https://resources.tigerbet001.com/products/";
                getGamesPartnerName = "tigerbet001";
                getGamesBaseURL = "https://tigerbet001.com";
                loginClient = "https://websitewebapi.tigerbet001.com/59/api/Main/LoginClient";
                break;
            }

            case 4: {
                //                getUserID = 1650804;
                partnerID = 52;
                getGamesAPIUrl = "https://websitewebapi.graciazz.com/52/api/Main/GetGames";
                getURLAPIUrl = "https://websitewebapi.graciazz.com/52/api/Main/GetProductUrl";
                getGamesOrigin = "https://graciazz.com";
                getGamesRecurse = "https://resources.graciazz.com/products/";
                getGamesPartnerName = "graciazz";
                getGamesBaseURL = "https://graciazz.com";
                loginClient = "https://websitewebapi.graciazz.com/52/api/Main/LoginClient";
                break;
            }

            case 5: {
                //                getUserID = 1650805;
                partnerID = 47;
                getGamesAPIUrl = "https://websitewebapi.crypto-casino.games/47/api/Main/GetGames";
                getURLAPIUrl = "https://websitewebapi.crypto-casino.games/47/api/Main/GetProductUrl";
                getGamesOrigin = "https://crypto-casino.games";
                getGamesRecurse = "https://resources.crypto-casino.games/products/";
                getGamesPartnerName = "cryptoCasino";
                getGamesBaseURL = "https://crypto-casino.games";
                loginClient = "https://websitewebapi.crypto-casino.games/47/api/Main/LoginClient";
                break;
            }

            case 6: {
                //                getUserID = 1650817;
                partnerID = 48;
                getGamesAPIUrl = "https://websitewebapi.betvito.com/48/api/Main/GetGames";
                getURLAPIUrl = "https://websitewebapi.betvito.com/48/api/Main/GetProductUrl";
                getGamesOrigin = "https://betvito.com";
                getGamesRecurse = "https://resources.https://betvito.com/products/";
                getGamesPartnerName = "Betvito";
                getGamesBaseURL = "https://betvito.com";
                loginClient = "https://websitewebapi.betvito.com/48/api/Main/LoginClient";
                break;
            }
            case 7: {
                //                getUserID = 1650818;
                partnerID = 41;
                getGamesAPIUrl = "https://websitewebapi.vikwin.com/41/api/Main/GetGames";
                getURLAPIUrl = "https://websitewebapi.vikwin.com/41/api/Main/GetProductUrl";
                getGamesOrigin = "https://vikwin.com";
                getGamesRecurse = "https://resources.vikwin.com/products/";
                getGamesPartnerName = "Vikwin";
                getGamesBaseURL = "https://vikwin.com";
                loginClient = "https://websitewebapi.vikwin.com/41/api/Main/LoginClient";
                break;
            }
            case 8: {
                //                getUserID = 1650819;
                partnerID = 38;
                getGamesAPIUrl = "https://websitewebapi.bravowin.com/38/api/Main/GetGames";
                getURLAPIUrl = "https://websitewebapi.bravowin.com/38/api/Main/GetProductUrl";
                getGamesOrigin = "https://bravowin.com";
                getGamesRecurse = "https://resources.bravowin.com/products/";
                getGamesPartnerName = "Bravowin";
                getGamesBaseURL = "https://bravowin.com";
                loginClient = "https://websitewebapi.bravowin.com/38/api/Main/LoginClient";
                break;
            }
            case 9: {
                //                getUserID = 1650822;
                partnerID = 54;
                getGamesAPIUrl = "https://websitewebapi.play.tether.bet/54/api/Main/GetGames";
                getURLAPIUrl = "https://websitewebapi.play.tether.bet/54/api/Main/GetProductUrl";
                getGamesOrigin = "https://play.tether.bet";
                getGamesRecurse = "https://resources.tether.bet/products/";
                getGamesPartnerName = "Tetherbet";
                getGamesBaseURL = "https://play.tether.bet";
                loginClient = "https://websitewebapi.play.tether.bet/54/api/Main/LoginClient";


                getAllLifeGames = "https://sportsbookwebsitewebapi.play.tether.bet/website/getlivematchesoverview?LanguageId=en&TimeZone=4&origin=https://sportsbookwebsite.play.tether.bet";
                getPreMatchTree = "https://sportsbookwebsitewebapi.play.tether.bet/website/getprematchtree?LanguageId=en&TimeZone=4";
                getPrematchTreeOrigin = "https://sportsbookwebsite.play.tether.bet";
                //getPrematchTreeOrigin = " https://sportsbookwebsitewebapi.play.tether.bet/website/getprematchtree?LanguageId=en&TimeZone=4";
                getMarketByID = "https://sportsbookwebsitewebapi.play.tether.bet/website/getmarketsbymatchid?LanguageId=en&TimeZone=4&MatchId="; //3956272&OddsType=0
                getMarketByIDOrigin = "https://sportsbookwebsite..play.tether.bet";
                break;
            }

            case 10: {
                //                getUserID = 1650845;
                partnerID = 45;
                getGamesAPIUrl = "https://websitewebapi.bet2win.vip/45/api/Main/GetGames";
                getURLAPIUrl = "https://websitewebapi.bet2win.vip/45/api/Main/GetProductUrl";
                getGamesOrigin = "https://bet2win.vip";
                getGamesRecurse = "https://resources.bet2win.vip/products/";
                getGamesPartnerName = "bet2win";
                getGamesBaseURL = "https://bet2win.vip";
                loginClient = "https://websitewebapi.bet2win.vip/45/api/Main/LoginClient";
                break;
            }

            case 11: {
                //                getUserID = 1651023;
                partnerID = 57;
                getGamesAPIUrl = "https://websitewebapi.craftbet.la/57/api/Main/GetGames";
                getURLAPIUrl = "https://websitewebapi.craftbet.la/57/api/Main/GetProductUrl";
                getGamesOrigin = "https://craftbet.la";
                getGamesRecurse = "https://resources.craftbet.la/products/";
                getGamesPartnerName = "CraftLA";
                getGamesBaseURL = "https://craftbet.la";
                loginClient = "https://websitewebapi.craftbet.la/57/api/Main/LoginClient";
                break;
            }
            case 12: {
                //                getUserID = 1651063;
                partnerID = 43;
                getGamesAPIUrl = "https://websitewebapi.oceanbet.io/43/api/Main/GetGames";
                getURLAPIUrl = "https://websitewebapi.oceanbet.io/43/api/Main/GetProductUrl";
                getGamesOrigin = "https://oceanbet.io";
                getGamesRecurse = "https://resources.oceanbet.io/products/";
                getGamesPartnerName = "oceanbet";
                getGamesBaseURL = "https://oceanbet.io";
                loginClient = "https://websitewebapi.oceanbet.io/43/api/Main/LoginClient";
                break;
            }
            case 13: {
                //                getUserID = 1651687;
                partnerID = 55;
                getGamesAPIUrl = "https://websitewebapi.play.baqto.com/55/api/Main/GetGames";
                getURLAPIUrl = "https://websitewebapi.play.baqto.com/55/api/Main/GetProductUrl";
                getGamesOrigin = "https://play.baqto.com";
                getGamesRecurse = "https://resources.play.baqto.com/products/";
                getGamesPartnerName = "baqto";
                getGamesBaseURL = "https://play.baqto.com";
                loginClient = "https://websitewebapi.play.baqto.com/55/api/Main/LoginClient";
                break;
            }

            case 14: {
                //                getUserID = 1656377;
                partnerID = 14;
                getGamesAPIUrl = "https://websitewebapi.winmatch.win/14/api/Main/GetGames";
                getURLAPIUrl = "https://websitewebapi.winmatch.win/14/api/Main/GetProductUrl";
                getGamesOrigin = "https://winmatch.win";
                getGamesRecurse = "https://resources.winmatch.win/products/";
                getGamesPartnerName = "winmatch";
                getGamesBaseURL = "https://winmatch.win";
                loginClient = "https://websitewebapi.winmatch.win/14/api/Main/LoginClient";
                break;
            }
            case 15: {
                //                getUserID = 1676748;
                partnerID = 66;
                getGamesAPIUrl = "https://websitewebapi.kontrbet.com/66/api/Main/GetGames";
                getURLAPIUrl = "https://websitewebapi.kontrbet.com/66/api/Main/GetProductUrl";
                getGamesOrigin = "https://kontrbet.com";
                getGamesRecurse = "https://resources.kontrbet.com/products/";
                getGamesPartnerName = "kontrbet";
                getGamesBaseURL = "https://kontrbet.com";
                loginClient = "https://websitewebapi.kontrbet.com/66/api/Main/LoginClient";
                break;
            }
            case 16: {
                //                getUserID = 1703584;
                partnerID = 67;
                getGamesAPIUrl = "https://websitewebapi.zorrobet365.com/67/api/Main/GetGames";
                getURLAPIUrl = "https://websitewebapi.zorrobet365.com/67/api/Main/GetProductUrl";
                getGamesOrigin = "https://zorrobet365.com";
                getGamesRecurse = "https://resources.zorrobet365.com/products/";
                getGamesPartnerName = "zorrobet365";
                getGamesBaseURL = "https://zorrobet365.com";
                loginClient = "https://websitewebapi.zorrobet365.com/67/api/Main/LoginClient";
                break;
            }

            case 17: {
                //                getUserID = 1785170;
                partnerID = 70;
                getGamesAPIUrl = "https://websitewebapi.winsroyal.com/70/api/Main/GetGames";
                getURLAPIUrl = "https://websitewebapi.winsroyal.com/70/api/Main/GetProductUrl";
                getGamesOrigin = "https://winsroyal.com";
                getGamesRecurse = "https://resources.winsroyal.com/products/";
                getGamesPartnerName = "winsroyal";
                getGamesBaseURL = "https://winsroyal.com";
                loginClient = "https://websitewebapi.winsroyal.com/70/api/Main/LoginClient";
                break;
            }

            case 18: {
                partnerID = 63;
                getGamesAPIUrl = "https://websitewebapi.slots.inc/63/api/Main/GetGames";
                getURLAPIUrl = "https://websitewebapi.slots.inc/63/api/Main/GetProductUrl";
                getGamesOrigin = "https://slots.inc";
                getGamesRecurse = "https://resources.slots.inc/products/";
                getGamesPartnerName = "slots.inc";
                getGamesBaseURL = "https://slots.inc";
                loginClient = "https://websitewebapi.slots.inc/63/api/Main/LoginClient";
                break;
            }

            case 100: {
                //                getUserID = 2;
                partnerID = 1;
                getGamesAPIUrl = "https://websitewebapi.exclusivebet-stage.com/1/api/Main/GetGames";
                getURLAPIUrl = "https://websitewebapi.exclusivebet-stage.com/1/api/Main/GetProductUrl";
                getGamesOrigin = "https://exclusivebet-stage.com";
                getGamesRecurse = "https://resources.exclusivebet-stage.com/products/";
                getGamesPartnerName = "Exclusivebet";
                getGamesBaseURL = "https://exclusivebet-stage.com";
                loginClient = "https://websitewebapi.exclusivebet-stage.com/1/api/Main/LoginClient";
                break;
            }

            case 101: {
                //                getUserID = 254304;
                partnerID = 91;
                getGamesAPIUrl = "https://websitewebapi.prdbtzmrk.com/1/api/Main/GetGames";
                getURLAPIUrl = "https://websitewebapi.prdbtzmrk.com/1/api/Main/GetProductUrl";
                getGamesOrigin = "https://prdbtzmrk.com";
                getGamesRecurse = "https://resources.prdbtzmrk.com/products/";
                getGamesPartnerName = "Prdbtzmrk";
                getGamesBaseURL = "https://prdbtzmrk.com";
                loginClient = "https://websitewebapi.prdbtzmrk.com/1/api/Main/LoginClient";
                break;
            }
            default: {
                logger.error("Wrong Partner ID: From config.properties file insert the right PartnerID Please");
                throw new SkipException("From config.properties file choose the right PartnerID Please");
            }
        }


//        try {
//            super.initDriver(getGamesBaseURL, browser, isHeadless);
//            logger.info("URL: " + getGamesBaseURL + "  Browser: " + browser);
//            Dimension d = new Dimension(DimensionWidth, DimensionHeight);  //
//            if (DimensionWidth > 100 && DimensionHeight > 100) {
//                driver.manage().window().setSize(d);
//                logger.info("Window size is: " + DimensionWidth + " x " + DimensionHeight);
//            }
//        } catch (org.openqa.selenium.TimeoutException exception) {
//            super.initDriver(getGamesBaseURL, browser, isHeadless);
//        }
//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
//
//        //region <Page Class Instance Initialization >
//        craftBet_01_header_page = PageFactory.initElements(driver, CraftBet_01_Header_Page.class);
//        craftBet_03_login_popUp_page = PageFactory.initElements(driver, CraftBet_03_Login_PopUp_Page.class);
//
//        logger.info("All Page elements are initialized");
//
//        //endregion
//
//        craftBet_01_header_page.setItem("lang", language);
//        craftBet_01_header_page.navigateRefresh();
        logger.info("Test Url: " + getGamesBaseURL  + " partnerID: " + partnerID);
    }


    @AfterMethod
    public void tearDown() {
//        try {
//            driver.quit();
//            logger.info("Browser closed");
//        } catch (Exception exception) {
//            driver.quit();
//            logger.info("Browser close_order has an exception");
//        }
        logger.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<  Test finished ");
    }

    @AfterSuite
    public void Finish() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<  Test Suite finished  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>  ");
    }
}
