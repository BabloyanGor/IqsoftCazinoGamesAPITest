package testCases.WebCasinoGames_Tests;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import org.apache.logging.log4j.core.config.Configurator;
import org.testng.SkipException;
import org.testng.annotations.*;
import pageObjects.*;
import utilities.ReadConfig;
import java.awt.*;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class BaseTest {

    public BasePage basePage = new BasePage();
    public static final int MINUTES_PER_HOUR = 60;
    public static final int SECONDS_PER_MINUTE = 60;
    public static final int SECONDS_PER_HOUR = SECONDS_PER_MINUTE * MINUTES_PER_HOUR;
    public static Logger logger;


    public static int partnerID;
    public static String getGamesAPIUrl;
    public static String getGamesAPIUrlBO;
    public static String getURLAPIUrl;
    //    public int getUserID;
    public static String getGamesOrigin;
    public static String getGamesOriginBO;
    public static String getPrematchTreeOrigin;
    public static String getMarketByIDOrigin;
    public static String getGamesResource;
    public static String getGamesPartnerName;
    public static String getGamesBaseURL;
    public static String loginClient;
    public static String getAllLifeGames;
    public static String getPreMatchTree;


    public String getPrematchMatchesMarketsByID(int gameID) {
        return getMarketByID + gameID + "&OddsType=0";
    }

    public String getMarketByID;


    //    public CraftBet_01_Header_Page craftBet_01_header_page;
//    public CraftBet_03_Login_PopUp_Page craftBet_03_login_popUp_page;
    public ReadConfig readConfig = new ReadConfig();
    public int partnerConfigNum = readConfig.partnerConfigNum();
    int getGamesOnOneCall = readConfig.getGamesCountOnOneCall();
    int asyncMaxTimeMinutes = readConfig.getAsyncMaxTimeMinutes();

    public int userIdBO = readConfig.getUserIdBO();
    public String ApiKeyBO = readConfig.getAPIKeyBO();

//    public String isHeadless = readConfig.isHeadless();
//    public String browser = readConfig.getBrowser();
//    public String username = readConfig.getUsername();
//    public String password = readConfig.getPassword();
//    public String language = readConfig.getLanguage();
//    public int DimensionHeight = readConfig.getDimensionHeight();
//    public int DimensionWidth = readConfig.getDimensionWidth();


    //endregion
    public BaseTest() throws AWTException {
    }




    @BeforeSuite
    public void setup() {
//        logger = Logger.getLogger("craftBetWorld");
//        PropertyConfigurator.configure(System.getProperty("user.dir") + "\\Log4j.properties");

        logger = LogManager.getLogger(BaseTest.class);
        Configurator.initialize(null, "log4j2.properties");

        switch (partnerConfigNum) {

            case 1: {
                //                getUserID = 1630845;
                partnerID = 1;
                getGamesAPIUrl = "https://websitewebapi.craftbet.com/1/api/Main/GetGames";
                getURLAPIUrl = "https://websitewebapi.craftbet.com/1/api/Main/GetProductUrl";
                getGamesOrigin = "https://craftbet.com";
                getGamesResource = "https://resources.craftbet.com/products/";
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
                getGamesResource = "https://resources.pokies2go.io/products/";
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
                getGamesResource = "https://resources.tigerbet001.com/products/";
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
                getGamesResource = "https://resources.graciazz.com/products/";
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
                getGamesResource = "https://resources.crypto-casino.games/products/";
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
                getGamesResource = "https://resources.https://betvito.com/products/";
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
                getGamesResource = "https://resources.vikwin.com/products/";
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
                getGamesResource = "https://resources.bravowin.com/products/";
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
                getGamesResource = "https://resources.tether.bet/products/";
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
                getGamesResource = "https://resources.bet2win.vip/products/";
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
                getGamesResource = "https://resources.craftbet.la/products/";
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
                getGamesResource = "https://resources.oceanbet.io/products/";
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
                getGamesResource = "https://resources.play.baqto.com/products/";
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
                getGamesResource = "https://resources.winmatch.win/products/";
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
                getGamesResource = "https://resources.kontrbet.com/products/";
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
                getGamesResource = "https://resources.zorrobet365.com/products/";
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
                getGamesResource = "https://resources.winsroyal.com/products/";
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
                getGamesResource = "https://resources.slots.inc/products/";
                getGamesPartnerName = "slots.inc";
                getGamesBaseURL = "https://slots.inc";
                loginClient = "https://websitewebapi.slots.inc/63/api/Main/LoginClient";
                break;
            }
            case 19: {
                partnerID = 43;
                getGamesAPIUrl = "https://websitewebapi.oceanbet.io/43/api/Main/GetGames";
                getURLAPIUrl = "https://websitewebapi.oceanbet.io/43/api/Main/GetProductUrl";
                getGamesOrigin = "https://rivierabet.com";
                getGamesResource = "https://resources.oceanbet.io/products/";
                getGamesPartnerName = "rivierabet";
                getGamesBaseURL = "https://rivierabet.com";
                loginClient = "https://websitewebapi.oceanbet.io/43/api/Main/LoginClient";
                break;
            }
            case 20: {
                partnerID = 28;
                getGamesAPIUrl = "https://websitewebapi.woopio.com/28/api/Main/GetGames";
                getURLAPIUrl = "https://websitewebapi.woopio.com/28/api/Main/GetProductUrl";
                getGamesOrigin = "https://woopio.com";
                getGamesResource = "https://resources.woopio.com/products/";
                getGamesPartnerName = "woopio";
                getGamesBaseURL = "https://woopio.com";
                loginClient = "https://websitewebapi.woopio.com/28/api/Main/LoginClient";
                break;
            }


            case 21: {
                partnerID = 2;
                getGamesAPIUrl = "https://websitewebapi.totox-stage.com/2/api/Main/GetGames";
                getURLAPIUrl = "https://websitewebapi.totox-stage.com/2/api/Main/GetProductUrl";
                getGamesOrigin = "https://totox-stage.com";
                getGamesResource = "https://resources.totox-stage.com/products/";
                getGamesPartnerName = "totox";
                getGamesBaseURL = "https://totox-stage.com";
                loginClient = "https://websitewebapi.totox-stage.com/2/api/Main/LoginClient";
                break;
            }

            case 22: {
                partnerID = 62;
                getGamesAPIUrl = "https://websitewebapi.galaxygoldrush.com/62/api/Main/GetGames";
                getURLAPIUrl = "https://websitewebapi.galaxygoldrush.com/62/api/Main/GetProductUrl";
                getGamesOrigin = "https://galaxygoldrush.com";
                getGamesResource = "https://resources.galaxygoldrush.com/products/";
                getGamesPartnerName = "galaxygoldrush";
                getGamesBaseURL = "https://galaxygoldrush.com";
                loginClient = "https://websitewebapi.galaxygoldrush.com/62/api/Main/LoginClient";
                break;
            }
            case 23: {
                //                getUserID = 1947176;
                partnerID = 73;
                getGamesAPIUrl = "https://websitewebapi.triplex.bet/73/api/Main/GetGames";
                getURLAPIUrl = "https://websitewebapi.triplex.bet/73/api/Main/GetProductUrl";
                getGamesOrigin = "https://triplex.bet";
                getGamesResource = "https://resources.triplex.bet/products/";
                getGamesPartnerName = "triplex";
                getGamesBaseURL = "https://triplex.bet";
                loginClient = "https://websitewebapi.triplex.bet/73/api/Main/LoginClient";
                break;
            }
            case 24: {
                //                getUserID = 1947176;
                partnerID = 5;
                getGamesAPIUrl = "https://websitewebapi.assos365.com/5/api/Main/GetGames";
                getURLAPIUrl = "https://websitewebapi.assos365.com/5/api/Main/GetProductUrl";
                getGamesOrigin = "https://assos365.com";
                getGamesResource = "https://resources.assos365.com/products/";
                getGamesPartnerName = "assos365";
                getGamesBaseURL = "https://assos365.com";
                loginClient = "https://websitewebapi.assos365.com/5/api/Main/LoginClient";
                break;
            }
            case 25: {
                //                getUserID = 1947176;
                partnerID = 74;
                getGamesAPIUrl = "https://websitewebapi.oceanbet.casino/74/api/Main/GetGames";
                getURLAPIUrl = "https://websitewebapi.oceanbet.casino/74/api/Main/GetProductUrl";
                getGamesOrigin = "https://oceanbet.casino";
                getGamesResource = "https://resources.oceanbet.casino/products/";
                getGamesPartnerName = "oceanbetCasino";
                getGamesBaseURL = "https://oceanbet.casino";
                loginClient = "https://websitewebapi.oceanbet.casino/74/api/Main/LoginClient";
                break;
            }
            case 26: {
                //                getUserID = 1947176;
                partnerID = 25;
                getGamesAPIUrl = "https://websitewebapi.zuraplay.com/25/api/Main/GetGames";
                getURLAPIUrl = "https://websitewebapi.zuraplay.com/25/api/Main/GetProductUrl";
                getGamesOrigin = "https://zuraplay.com";
                getGamesResource = "https://resources.zuraplay.com/products/";
                getGamesPartnerName = "zuraplay";
                getGamesBaseURL = "https://zuraplay.com";
                loginClient = "https://websitewebapi.zuraplay.com/25/api/Main/LoginClient";
                break;
            }
            case 27: {
                //                getUserID = 1947176;
                partnerID = 78;
                getGamesAPIUrl = "https://websitewebapi.rocketbet.com/78/api/Main/GetGames";
                getURLAPIUrl = "https://websitewebapi.rocketbet.com/78/api/Main/GetProductUrl";
                getGamesOrigin = "https://rocketbet.com";
                getGamesResource = "https://resources.rocketbet.com/products/";
                getGamesPartnerName = "Rocketbet";
                getGamesBaseURL = "https://rocketbet.com";
                loginClient = "https://websitewebapi.rocketbet.com/78/api/Main/LoginClient";
                break;
            }

            case 28: {
                //                getUserID = 1630845;
                partnerID = 79;
                getGamesAPIUrl = "https://websitewebapi.craftbet.io/79/api/Main/GetGames";
                getURLAPIUrl = "https://websitewebapi.craftbet.io/79/api/Main/GetProductUrl";
                getGamesOrigin = "https://craftbet.io";
                getGamesResource = "https://resources.craftbet.io/products/";
                getGamesPartnerName = "CraftbetTurkey";
                getGamesBaseURL = "https://craftbet.io";
                loginClient = "https://websitewebapi.craftbet.io/79/api/Main/LoginClient";

                break;
            }
            case 29: {
                partnerID = 18;
                getGamesAPIUrl = "https://websitewebapi.angliabet.com/18/api/Main/GetGames";
                getURLAPIUrl = "https://websitewebapi.angliabet.com/18/api/Main/GetProductUrl";
                getGamesOrigin = "https://angliabet.com";
                getGamesResource = "https://resources.angliabet.com/products/";
                getGamesPartnerName = "angliBet";
                getGamesBaseURL = "https://angliabet.com";
                loginClient = "https://websitewebapi.angliabet.com/18/api/Main/LoginClient";
                break;
            }
            case 30: {
                partnerID = 123;
                getGamesAPIUrl = "https://websitewebapi.betcenter777.com/123/api/Main/GetGames";
                getURLAPIUrl = "https://websitewebapi.betcenter777.com/123/api/Main/GetProductUrl";
                getGamesOrigin = "https://betcenter777.com";
                getGamesResource = "https://resources.betcenter777.com/products/";
                getGamesPartnerName = "betcenter777";
                getGamesBaseURL = "https://betcenter777.com";
                loginClient = "https://websitewebapi.betcenter777.com/123/api/Main/LoginClient";

                break;
            }
            case 31: {
                partnerID = 2;
                getGamesAPIUrl = "https://websitewebapi.betr.game/2/api/Main/GetGames";
                getURLAPIUrl = "https://websitewebapi.betr.game/2/api/Main/GetProductUrl";
                getGamesOrigin = "https://betr.game";
                getGamesResource = "https://resources.betr.game/products/";
                getGamesPartnerName = "betrGame";
                getGamesBaseURL = "https://betr.game";
                loginClient = "https://websitewebapi.betr.game/2/api/Main/LoginClient";

                break;
            }
            case 32: {
                partnerID = 77;
                getGamesAPIUrl = "https://websitewebapi.betonet365.com/77/api/Main/GetGames";
                getURLAPIUrl = "https://websitewebapi.betonet365.com/77/api/Main/GetProductUrl";
                getGamesOrigin = "https://betonet365.com";
                getGamesResource = "https://resources.betonet365.com/products/";
                getGamesPartnerName = "betonet365";
                getGamesBaseURL = "https://betonet365.com";
                loginClient = "https://websitewebapi.betonet365.com/77/api/Main/LoginClient";

                break;
            }
            case 33: {
                partnerID = 125;
                getGamesAPIUrl = "https://websitewebapi.betfusions.co.uk/125/api/Main/GetGames";
                getURLAPIUrl = "https://websitewebapi.betfusions.co.uk/125/api/Main/GetProductUrl";
                getGamesOrigin = "https://betfusions.co.uk";
                getGamesResource = "https://resources.betfusions.co.uk/products/";
                getGamesPartnerName = "betfusions";
                getGamesBaseURL = "https://betfusions.co.uk";
                loginClient = "https://websitewebapi.betfusions.co.uk/125/api/Main/LoginClient";

                break;
            }
            case 34: {
                partnerID = 28;
                getGamesAPIUrl = "https://websitewebapi.zolobet.com/28/api/Main/GetGames";
                getURLAPIUrl = "https://websitewebapi.zolobet.com/28/api/Main/GetProductUrl";
                getGamesOrigin = "https://zolobet.com";
                getGamesResource = "https://resources.zolobet.com/products/";
                getGamesPartnerName = "zolobet";
                getGamesBaseURL = "https://zolobet.com";
                loginClient = "https://websitewebapi.zolobet.com/28/api/Main/LoginClient";

                break;
            }

            case 35: {
                partnerID = 20;
                getGamesAPIUrl = "https://websitewebapi.betfoxx.com/20/api/Main/GetGames";
                getURLAPIUrl = "https://websitewebapi.betfoxx.com/20/api/Main/GetProductUrl";
                getGamesOrigin = "https://betfoxx.com";
                getGamesResource = "https://resources.betfoxx.com/products/";
                getGamesPartnerName = "betfox";
                getGamesBaseURL = "https://betfoxx.com";
                loginClient = "https://websitewebapi.betfoxx.com/20/api/Main/LoginClient";

                break;
            }
            case 36: {
                partnerID = 131;
                getGamesAPIUrl = "https://websitewebapi.nightcity.bet/131/api/Main/GetGames";
                getURLAPIUrl = "https://websitewebapi.nightcity.bet/131/api/Main/GetProductUrl";
                getGamesOrigin = "https://nightcity.bet";
                getGamesResource = "https://resources.nightcity.bet/products/";
                getGamesPartnerName = "nightcity";
                getGamesBaseURL = "https://nightcity.bet";
                loginClient = "https://websitewebapi.nightcity.bet/131/api/Main/LoginClient";

                break;
            }
            case 37: {
                partnerID = 136;
                getGamesAPIUrl = "https://websitewebapi.commando.bet/136/api/Main/GetGames";
                getURLAPIUrl = "https://websitewebapi.commando.bet/136/api/Main/GetProductUrl";
                getGamesOrigin = "https://commando.bet";
                getGamesResource = "https://resources.commando.bet/products/";
                getGamesPartnerName = "nightcity";
                getGamesBaseURL = "https://commando.bet";
                loginClient = "https://websitewebapi.commando.bet/136/api/Main/LoginClient";

                break;
            }
            case 38: {
                partnerID = 137;
                getGamesAPIUrl = "https://websitewebapi.slotsamigo.com/137/api/Main/GetGames";
                getURLAPIUrl = "https://websitewebapi.slotsamigo.com/137/api/Main/GetProductUrl";
                getGamesOrigin = "https://slotsamigo.com";
                getGamesResource = "https://resources.slotsamigo.com/products/";
                getGamesPartnerName = "slotsamigo";
                getGamesBaseURL = "https://slotsamigo.com";
                loginClient = "https://websitewebapi.slotsamigo.com/137/api/Main/LoginClient";

                break;
            }

            case 100: {
                //                getUserID = 2;
                partnerID = 1;
                getGamesAPIUrl = "https://websitewebapi.exclusivebet-stage.com/1/api/Main/GetGames";
                getURLAPIUrl = "https://websitewebapi.exclusivebet-stage.com/1/api/Main/GetProductUrl";
                getGamesOrigin = "https://exclusivebet-stage.com";
                getGamesResource = "https://resources.exclusivebet-stage.com/products/";
                getGamesPartnerName = "Exclusivebet";
                getGamesBaseURL = "https://exclusivebet-stage.com";
                loginClient = "https://websitewebapi.exclusivebet-stage.com/1/api/Main/LoginClient";
                break;
            }

            case 101: {
                //                getUserID = 254304;
                partnerID = 1;
                getGamesAPIUrl = "https://websitewebapi.betzmark.com/1/api/Main/GetGames";
                getURLAPIUrl = "https://websitewebapi.betzmark.com/1/api/Main/GetProductUrl";
                getGamesOrigin = "https://betzmark.com";
                getGamesResource = "https://resources.betzmark.com/products/";
                getGamesPartnerName = "Betzmark";
                getGamesBaseURL = "https://betzmark.com";
                loginClient = "https://websitewebapi.betzmark.com/1/api/Main/LoginClient";
                break;
            }
            case 102: {
                //                getUserID = 254304;
                partnerID = 1;
                getGamesAPIUrl = "https://websitewebapi.totox-stage.com/2/api/Main/GetGames";
                getURLAPIUrl = "https://websitewebapi.totox-stage.com/2/api/Main/GetProductUrl";
                getGamesOrigin = "https://totox-stage.com";
                getGamesResource = "https://resources.totox-stage.com/products/";
                getGamesPartnerName = "Totox";
                getGamesBaseURL = "https://totox-stage.com";
                loginClient = "https://websitewebapi.totox-stage.com/2/api/Main/LoginClient";
                break;
            }

            case 1000: {
                //                getUserID = 254304;
                partnerID = 40;
                getGamesAPIUrlBO = "https://adminwebapi.iqsoftllc.com/api/Main/ApiRequest";
                getGamesOriginBO = "https://admin.iqsoftllc.com";
                getGamesResource = "https://resources.gamingart.tech/products/";
                getGamesPartnerName = "GamingArt";
                break;
            }
            default: {
                logger.error("Wrong Partner ID: From config.properties file insert the right PartnerID Please");
                throw new SkipException("From config.properties choose the right PartnerID Please");
            }
        }
        logger.info("Test Url: " + getGamesBaseURL + " partnerID: " + partnerID);

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
        if (partnerConfigNum < 1000) {
            getGamesInfo(false);
        } else {
            getGamesInfoBO(false);
        }

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
        logger.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<  Test finished   " + BasePage.dateTimeNow());
    }

    @AfterSuite
    public void Finish() {
        logger.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<  Test Suite finished  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>  ");
    }


    static ArrayList<String> productIDs = new ArrayList<>();
    static ArrayList<String> gameNames = new ArrayList<>();
    static ArrayList<String> gameProviders = new ArrayList<>();
    static ArrayList<String> errorSrcXl = new ArrayList<>();
    static ArrayList<String> srces = new ArrayList<>();
    boolean isPassed = false;


    public void getGamesInfo(boolean IsForMobile) {
        int gamesCount = 0;
        try {
            Unirest.config().reset();
            Unirest.config().connectTimeout(60000);
            Unirest.config().socketTimeout(60000);
            HttpResponse<String> response = Unirest.post(getGamesAPIUrl)
                    .header("content-type", "application/json")
                    .header("origin", getGamesOrigin)
                    .body("{\"PageIndex\":0,\"PageSize\":10,\"WithWidget\":false,\"CategoryId\":null,\"ProviderIds\":null,\"IsForMobile\":" +
                            IsForMobile +
                            ",\"Name\":\"\"," +
                            "\"LanguageId\":\"en\",\"Token\":null,\"ClientId\":0,\"TimeZone\":4}")
                    .asString();

            logger.info("Get games Api call was sent");
            JSONObject jsonObjectBody = new JSONObject(response.getBody());
            JSONObject jsonObjectResponseObject = new JSONObject(jsonObjectBody.get("ResponseObject").toString());
            gamesCount = Integer.parseInt(jsonObjectResponseObject.get("TotalGamesCount").toString());
            logger.info("Get games Api call: TotalGamesCount = " + gamesCount);

        } catch (Exception e) {
            logger.info("Get Games Call for TotalGamesCount has an exception " + e);
        }
        JSONObject jsonObjectBody;
        JSONObject jsonObjectResponseObject;
        JSONArray jsonArrayGames = null;
        int circleCount = gamesCount / getGamesOnOneCall + 1;
//        circleCount = 1;
        for (int m = 0; m < circleCount; m++) {//circleCount
            try {
                try {
                    Unirest.config().reset();
                    Unirest.config().connectTimeout(60000);
                    Unirest.config().socketTimeout(60000);
                    HttpResponse<String> response = Unirest.post(getGamesAPIUrl)
                            .header("content-type", "application/json")
                            .header("origin", getGamesOrigin)
                            .body("{\"PageIndex\":" +
                                    m +
                                    ",\"PageSize\":" +
                                    getGamesOnOneCall +
                                    ",\"WithWidget\":false,\"CategoryId\":null,\"ProviderIds\":null,\"IsForMobile\":" +
                                    IsForMobile +
                                    "," +
                                    "\"Name\":\"\",\"LanguageId\":\"en\",\"Token\":null,\"ClientId\":0,\"TimeZone\":4}")
                            .asString();
                    jsonObjectBody = new JSONObject(response.getBody());
                    jsonObjectResponseObject = new JSONObject(jsonObjectBody.get("ResponseObject").toString());
                    jsonArrayGames = jsonObjectResponseObject.getJSONArray("Games");
                    logger.info("From getGamesAPIUrl call body captured: " + m);
                } catch (Exception e1) {
                    try {
                        Unirest.config().reset();
                        Unirest.config().connectTimeout(60000);
                        Unirest.config().socketTimeout(60000);
                        HttpResponse<String> response = Unirest.post(getGamesAPIUrl)
                                .header("content-type", "application/json")
                                .header("origin", getGamesOrigin)
                                .body("{\"PageIndex\":" +
                                        m +
                                        ",\"PageSize\":" +
                                        getGamesOnOneCall +
                                        ",\"WithWidget\":false,\"CategoryId\":null,\"ProviderIds\":null,\"IsForMobile\":" +
                                        IsForMobile +
                                        "," +
                                        "\"Name\":\"\",\"LanguageId\":\"en\",\"Token\":null,\"ClientId\":0,\"TimeZone\":4}")
                                .asString();
                        jsonObjectBody = new JSONObject(response.getBody());
                        jsonObjectResponseObject = new JSONObject(jsonObjectBody.get("ResponseObject").toString());
                        jsonArrayGames = jsonObjectResponseObject.getJSONArray("Games");
                        logger.info("From getGamesAPIUrl call body captured: " + m);
                    } catch (Exception e) {
                        logger.fatal("getGamesAPIUrl call has an exception " + e);
                    }
                }


                try {
                    if (jsonArrayGames != null) {
                        for (int j = 0; j < jsonArrayGames.length(); j++) {
                            String first = String.valueOf(jsonArrayGames.get(j));
                            JSONObject jsonObjectGame = new JSONObject(first);
                            String i = String.valueOf(jsonObjectGame.get("i"));   // Game src
                            String p = String.valueOf(jsonObjectGame.get("p"));   //Game ID
                            String n = String.valueOf(jsonObjectGame.get("n"));   //Game Name
                            String sp = String.valueOf(jsonObjectGame.get("sp")); //Provider Name
                            productIDs.add(p);
                            gameNames.add(n);
                            gameProviders.add(sp);
                            if (i.contains("http") && i.contains(" ")) {
                                String change = i.replace(" ", "%20");
                                srces.add(change);
                            } else if (!i.contains("http") && !i.contains(" ")) {
                                srces.add(getGamesResource + i);
                            } else if (!i.contains("http") && i.contains(" ")) {
                                String change = getGamesResource + i.replace(" ", "%20");
                                srces.add(change);
                            } else {
                                srces.add(i);
                            }
                        }
                    }

                } catch (Exception c) {
//                    logger.fatal(j + "P  " + p +" n  " + n +" sp  " + sp  );
                    logger.fatal("");
                }


                logger.info("From getGamesAPIUrl call productIDes and Names was captured Games count: " + productIDs.size());


            } catch (Exception ee) {
                logger.fatal("getGamesAPIUrl call has an exception " + ee);
            } finally {
//                Unirest.shutdown();
            }

        }
    }

    public void getGamesInfoBO(boolean IsForMobile) {
        int gamesCount = 0;
        try {
            Unirest.config().reset();
            Unirest.config().connectTimeout(60000);
            Unirest.config().socketTimeout(60000);
            HttpResponse<String> response = Unirest.post(getGamesAPIUrlBO)
                    .header("content-type", "application/json")
                    .header("origin", getGamesOriginBO)
                    .body("{\"Controller\":\"Product\",\"Method\":\"GetPartnerProductSettings\",\"RequestObject\":{\"Controller\":\"Product\"," +
                            "\"Method\":\"GetPartnerProductSettings\",\"SkipCount\":0,\"TakeCount\":10,\"OrderBy\":null," +
                            "\"FieldNameToOrderBy\":\"\",\"PartnerId\":" +
                            partnerID +
                            "},\"UserID\": \"" +
                            userIdBO +
                            "\",\"APIKey\": \"" +
                            ApiKeyBO +
                            "\"}")
                    .asString();

            logger.info("Get games from BO Api call was sent");
            JSONObject jsonObjectBody = new JSONObject(response.getBody());
            JSONObject jsonObjectResponseObject = new JSONObject(jsonObjectBody.get("ResponseObject").toString());
            gamesCount = Integer.parseInt(jsonObjectResponseObject.get("Count").toString());
            logger.info("Get games Api call: TotalGamesCount = " + gamesCount);

        } catch (Exception e) {
            logger.info("Get Games Call for TotalGamesCount has an exception " + e);
        }
        JSONObject jsonObjectBody;
        JSONObject jsonObjectResponseObject;
        JSONArray jsonArrayGames;
        int circleCount = gamesCount / getGamesOnOneCall + 1;
//        circleCount = 1;
        for (int m = 0; m < circleCount; m++) {//circleCount
            try {
                HttpResponse<String> response = Unirest.post(getGamesAPIUrlBO)
                        .header("content-type", "application/json")
                        .header("origin", getGamesOriginBO)
                        .body("{\"Controller\":\"Product\",\"Method\":\"GetPartnerProductSettings\",\"RequestObject\":{\"Controller\":\"Product\"," +
                                "\"Method\":\"GetPartnerProductSettings\",\"SkipCount\":" +
                                m +
                                ",\"TakeCount\":" +
                                getGamesOnOneCall +
                                ",\"OrderBy\":null," +
                                "\"FieldNameToOrderBy\":\"\",\"PartnerId\":" +
                                partnerID +
                                "},\"UserID\": \"" +
                                userIdBO +
                                "\",\"APIKey\": \"" +
                                ApiKeyBO +
                                "\"}")
                        .asString();
                jsonObjectBody = new JSONObject(response.getBody());
                jsonObjectResponseObject = new JSONObject(jsonObjectBody.get("ResponseObject").toString());
                jsonArrayGames = jsonObjectResponseObject.getJSONArray("Entities");
                logger.info("From getGamesAPIUrl call body captured: " + m);


                if (jsonArrayGames != null) {
                    for (int j = 0; j < jsonArrayGames.length(); j++) {
                        String first = String.valueOf(jsonArrayGames.get(j));
                        JSONObject jsonObjectGame = new JSONObject(first);
                        String i;
                        if (IsForMobile) {
                            i = jsonObjectGame.get("WebImageUrl").toString();   // Game Web img src
                        } else {
                            i = jsonObjectGame.get("MobileImageUrl").toString();   // Game Mobile img src
                        }
                        String p = jsonObjectGame.get("ProductId").toString();   //Game ID
                        String n = jsonObjectGame.get("ProductName").toString();   //ProductName
                        String sp = jsonObjectGame.get("GameProviderName").toString(); //Provider Name


                        productIDs.add(p);
                        gameNames.add(n);
                        gameProviders.add(sp);
                        if (i.contains("http") && i.contains(" ")) {
                            String change = i.replace(" ", "%20");
                            srces.add(change);
                        } else if (!i.contains("http") && !i.contains(" ")) {
                            srces.add(getGamesResource + i);
                        } else if (!i.contains("http") && i.contains(" ")) {
                            String change = getGamesResource + i.replace(" ", "%20");
                            srces.add(change);
                        } else {
                            srces.add(i);
                        }
                    }
                }
                logger.info("From getGamesAPIUrl call productIDes and Names was captured Games count: " + productIDs.size());
            } catch (Exception ee) {
                logger.fatal("getGamesAPIUrl call has an exception " + ee);
            } finally {
//                Unirest.shutdown();
            }

        }
    }






}



