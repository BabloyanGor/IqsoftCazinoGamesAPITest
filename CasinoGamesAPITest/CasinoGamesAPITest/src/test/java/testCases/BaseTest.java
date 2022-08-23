package testCases;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.support.PageFactory;
import org.testng.SkipException;
import org.testng.annotations.*;
import pageObjects.*;
import utilities.DriverFactory;
import utilities.ReadConfig;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;


public class BaseTest extends DriverFactory {

    ReadConfig readConfig = new ReadConfig();

    public String isHeadless = readConfig.isHeadless();
    public String browser = readConfig.getBrowser();
    public String username = readConfig.getUsername();
    public String password = readConfig.getPassword();
    public String language = readConfig.getLanguage();
    public int DimensionHeight = readConfig.getDimensionHeight();
    public int DimensionWidth = readConfig.getDimensionWidth();
    public int partnerConfigNum = readConfig.partnerConfigNum();



    public int partnerID;
    public String getGamesAPIUrl;
    public String getURLAPIUrl;
    public int getUserID;
    public String getGamesOrigin;
    public String getGamesRecurse;
    public String getGamesPartnerName;
    public String getGamesBaseURL;

    public static Logger logger;

    //region <Page Class Instances  >

    public CraftBet_01_Header_Page craftBet_01_header_page;
    public CraftBet_03_Login_PopUp_Page craftBet_03_login_popUp_page;

    public boolean getGamesAPICheckPictures(String getGamesAPIUrl, String origin, String recurse, String partnerName) throws UnirestException, JSONException, IOException {

        boolean isPassed;
        int k = 0;
        ArrayList<String> srces = new ArrayList<>();
        ArrayList<String> gameNames = new ArrayList<>();
        ArrayList<String> gameProviderNames = new ArrayList<>();
        ArrayList<String> errorSrcXl = new ArrayList<>();

        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.post(getGamesAPIUrl)
                .header("content-type", "application/json")
                .header("origin", origin)
                .body("{\"PageIndex\":0,\"PageSize\":20000,\"WithWidget\":false,\"CategoryId\":null,\"ProviderIds\":null,\"IsForMobile\":false,\"Name\":\"\",\"LanguageId\":\"en\",\"Token\":null,\"ClientId\":0,\"TimeZone\":4}")
                .asString();

        JSONObject jsonObjectBody = new JSONObject(response.getBody());
        JSONObject jsonObjectResponseObject = new JSONObject(jsonObjectBody.getString("ResponseObject"));
        JSONArray jsonArrayGames = jsonObjectResponseObject.getJSONArray("Games");


        for (int j = 0; j < jsonArrayGames.length(); j++) {

            String first = String.valueOf(jsonArrayGames.get(j));
            JSONObject jsonObjectGame = new JSONObject(first);
            String i = jsonObjectGame.getString("i");    // Game src
            String n = jsonObjectGame.getString("n");    //Game Name
            String sp = jsonObjectGame.getString("sp");  //Provider Name
            gameNames.add(n);
            gameProviderNames.add(sp);

            if (i.contains("http") && !i.contains(" ")) {
                srces.add(i);
            } else if (i.contains(" Catalog image/image.jpg")) {
                String change = i.replace(" ", "%20");
                srces.add(change);
            } else {
                srces.add(recurse + i);
            }
        }

        for (String src : srces) {
            if (src == null || src.isEmpty()) {
                logger.info(k + "   Game Provider Name = " + gameProviderNames.get(k) + "  " + "Game Name = " + gameNames.get(k) + " :   " + ":   src = " + src + " :" + " this games image src has empty/null value");
                errorSrcXl.add(k + "  Game Provider Name = " + gameProviderNames.get(k) + "  " + "Game Name = " + gameNames.get(k) + "  " + ":   src = " + src + " " + " ----->  this games image src has empty/null value");
            } else {
                int cod;
                try {
                    URL img = new URL(src);
                    HttpURLConnection connection = (HttpURLConnection) img.openConnection();
                    connection.connect();
                    cod = connection.getResponseCode();

                    if (cod >= 400) {
                        logger.error(k + "   Game Provider Name = " + gameProviderNames.get(k) + " :   " + "Game Name =  " + gameNames.get(k) + " :   " + "cod = " + cod + ":   src = " + src);
                        errorSrcXl.add(k + "  Game Provider Name = " + gameProviderNames.get(k) + "   " + "Game Name =  " + gameNames.get(k) + "  " + "cod = " + cod + "   src = " + src);
                    }
                } catch (Exception e) {
                    try {
                        URL img = new URL(src);
                        HttpURLConnection connection = (HttpURLConnection) img.openConnection();
                        connection.connect();
                        cod = connection.getResponseCode();

                        if (cod >= 400) {
                            logger.error(k + "  Game Provider Name = " + gameProviderNames.get(k) + " :   " + "Game Name =  " + gameNames.get(k) + " :   " + "cod = " + cod + ":   src = " + src);
                            errorSrcXl.add(k + "  Game Provider Name = " + gameProviderNames.get(k) + "   " + "Game Name =  " + gameNames.get(k) + "  " + "cod = " + cod + "   src = " + src);
                        }
                    } catch (Exception ex) {
                        logger.error(k + " Game Provider Name = " + gameProviderNames.get(k) + " :                    " + "Game Name = " + gameNames.get(k) + " :                    " + "   src = " + src + "         " + e);
                        errorSrcXl.add(k + "  Game Provider Name = " + gameProviderNames.get(k) + "   " + "Game Name =  " + gameNames.get(k) + "  " + "src = " + src);
                    }
                }
            }
            k++;
        }
        logger.info("Broken images are:  " + errorSrcXl.size());
        if (errorSrcXl.size() == 0) {
            isPassed = true;
        } else {
            String target = System.getProperty("user.dir") + "/src/test/java/APICasinoGamesCasinoImagesBrokenData/" + partnerName + "DataBrokenIMGList.xlsx";
            XSSFWorkbook workbook = new XSSFWorkbook();
            FileOutputStream file = new FileOutputStream(target);
            XSSFSheet sheet = workbook.createSheet("brokenIMG");
            sheet.setColumnWidth(0, 20000);
            int l = 0;
            for (String err : errorSrcXl) {
                XSSFRow row = sheet.createRow(l);
                row.createCell(0).setCellValue(err);
                l++;
            }
            workbook.write(file);
            workbook.close();
            isPassed = false;
        }
        return isPassed;
    }

    public boolean getUrlCheckGamesUrl(String getGamesAPIUrl, String origin, String getURLAPIurl, String token, int userID, int partnerID, String partnerName) throws JSONException, IOException, UnirestException {
        boolean isPassed;
        ArrayList<String> productID = new ArrayList<>();
        ArrayList<String> name = new ArrayList<>();
        ArrayList<String> provider = new ArrayList<>();
        ArrayList<String> brokenURL = new ArrayList<>();

        int errCount = 1;
        int k = 0;

        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.post(getGamesAPIUrl)
                .header("content-type", "application/json")
                .header("origin", origin)
                .body("{\"PageIndex\":0,\"PageSize\":20000,\"WithWidget\":false,\"CategoryId\":null,\"ProviderIds\":null,\"IsForMobile\":false,\"Name\":\"\",\"LanguageId\":\"en\",\"Token\":null,\"ClientId\":0,\"TimeZone\":4}")
                .asString();
        logger.info("Get All games API call ");

        JSONObject jsonObjectBody = new JSONObject(response.getBody());
        JSONObject jsonObjectResponseObject = new JSONObject(jsonObjectBody.getString("ResponseObject"));
        JSONArray jsonArrayGames = jsonObjectResponseObject.getJSONArray("Games");
        logger.info("From Get All games API call body captured ");


        for (int j = 0; j < jsonArrayGames.length(); j++) {
            String first = String.valueOf(jsonArrayGames.get(j));
            JSONObject jsonObjectGame = new JSONObject(first);
            String p = jsonObjectGame.getString("p");
            String n = jsonObjectGame.getString("n");
            String sp = jsonObjectGame.getString("sp");
            productID.add(p);
            name.add(n);
            provider.add(sp);
        }
        logger.info("From Get All games API productIDes and Names was captured ");

        for (String id : productID) {
            int proID = Integer.parseInt(id);

            Unirest.setTimeouts(0, 0);
            HttpResponse<String> responseUrl = Unirest.post(getURLAPIurl)
                    .header("Content-Type", "application/json")
                    .header("origin", origin)
                    .body("{\"Loader\":true,\"PartnerId\":" + partnerID + ",\"TimeZone\":4,\"LanguageId\":\"en\",\"ProductId\":" + proID + ",\"Method\":\"GetProductUrl\",\"Controller\":\"Main\",\"CategoryId\":null,\"PageIndex\":0,\"PageSize\":100,\"ProviderIds\":[],\"Index\":null,\"ActivationKey\":null,\"MobileNumber\":null,\"Code\":null,\"RequestData\":\"{}\",\"IsForDemo\":false,\"IsForMobile\":false,\"Position\":\"\",\"DeviceType\":1,\"ClientId\":" + userID + ",\"Token\":\"" + token + "\"}")
                    .asString();


            JSONObject jsonObjectGetUrl = new JSONObject(responseUrl.getBody());

            String code = jsonObjectGetUrl.getString("ResponseCode");
            String description = jsonObjectGetUrl.getString("Description");
            String url = jsonObjectGetUrl.getString("ResponseObject");
            String s;
            try {
                if (!code.equals("0") || url == null || url.length() < 10) {
                    s = errCount + " " + k + " ID=" + id + " Provider=" + provider.get(k) + " Name=" + name.get(k) + " cod=" + code + " description=" + description + " ResponseObject=" + url;
                    logger.info(s);
                    brokenURL.add(s);
                    errCount++;
                }
            } catch (Exception e) {
                logger.info(k + " Unirest Exception ");
            }
            k++;
        }
        logger.info("From Get URL API broken games are captured");

        logger.info("Broken url-es are:  " + brokenURL.size());
        if (brokenURL.size() == 0) {
            logger.info("Broken Games are null");
            isPassed = true;

        } else {
            String target = System.getProperty("user.dir") + "/src/test/java/APICasinoGamesCasinoImagesBrokenData/" + partnerName + "DataBrokenURLList.xlsx";
            FileOutputStream file = new FileOutputStream(target);
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("brokenURL");
            sheet.setColumnWidth(0, 20000);
            int l = 0;
            for (String err : brokenURL) {
                XSSFRow row = sheet.createRow(l);
                row.createCell(0).setCellValue(err);
                l++;
            }
            workbook.write(file);
            workbook.close();
            isPassed = false;
        }
        return isPassed;
    }

    //endregion
    public BaseTest() {
    }

    @BeforeMethod
    public void setup() throws InterruptedException {
        logger = Logger.getLogger("craftBet");
        PropertyConfigurator.configure("Log4j.properties");

        switch (partnerConfigNum){
            case 1: {
                partnerID=1;
                getGamesAPIUrl = "https://websitewebapi.craftbet.com/1/api/Main/GetGames";
                getURLAPIUrl = "https://websitewebapi.craftbet.com/1/api/Main/GetProductUrl";
                getUserID =1630845;
                getGamesOrigin = "https://craftbet.com";
                getGamesRecurse = "https://resources.craftbet.com/products/";
                getGamesPartnerName = "Craftbet";
                getGamesBaseURL = "https://craftbet.com";
                break;
            }
            case 2: {
                partnerID=56;
                getGamesAPIUrl = "https://websitewebapi.pokies2go.io/56/api/Main/GetGames";
                getURLAPIUrl = "https://websitewebapi.pokies2go.io/56/api/Main/GetProductUrl";
                getUserID =1650272;
                getGamesOrigin = "https://pokies2go.io";
                getGamesRecurse = "https://resources.pokies2go.io/products/";
                getGamesPartnerName = "Pokies2go";
                getGamesBaseURL = "https://pokies2go.io";
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


                default:{
                logger.error("Wrong Partner ID: From config.properties file insert the right PartnerID Please");
                throw new SkipException("From config.properties file choose the right PartnerID Please");
            }
        }


        try {
            super.initDriver(getGamesBaseURL, browser, isHeadless);
            logger.info("URL: "+ getGamesBaseURL + "  Browser: "+ browser);
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
