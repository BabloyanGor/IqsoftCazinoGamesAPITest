package testCases;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;

public class Pokies2go_Casino_Game_Test_API extends BaseTest {
    String urlPokiesBetGetGames = "https://websitewebapi.pokies2go.io/56/api/Main/GetGames";          //need to be changed for another url
    String urlPokiesGetProductUrl = "https://websitewebapi.pokies2go.io/56/api/Main/GetProductUrl";   //need to be changed for another url
    String originPokiesHeaders = "https://pokies2go.io";   //need to be changed for another url


    ArrayList<String> srces = new ArrayList<>();
    ArrayList<String> gameNames = new ArrayList<>();
    ArrayList<String> gameProviderNames = new ArrayList<>();
    ArrayList<String> errorSrcXl = new ArrayList<>();
    ArrayList<String> productID = new ArrayList<>();
    ArrayList<String> productIDIMG = new ArrayList<>();
    ArrayList<String> name = new ArrayList<>();
    ArrayList<String> brokenURL = new ArrayList<>();

    String token;
//    int waitTime = 300;

    @BeforeMethod
    public void logInPokies2go() {

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        craftBet_01_header_page.navigateToUrl("https://pokies2go.io/home");
        craftBet_01_header_page.clickOnLogInButtonIfVisible();
        craftBet_03_login_popUp_page.loginPopUpEmailOrUsernameSendKeys(username);
        logger.info("username passed");
        craftBet_03_login_popUp_page.loginPopUpPasswordSendKeys(password);
        logger.info("password passed");
        craftBet_03_login_popUp_page.clickLoginPopUpLogInButton();
        logger.info("Log In Button was clicked");
        if (craftBet_01_header_page.userIdLabelIsEnabled()) {
            token = craftBet_01_header_page.getItem("token");
            logger.info("Token was captured ");
        }
    }

    @Test
    public void imgTest() throws UnirestException, JSONException, IOException {

        int k = 0;

        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.post(urlPokiesBetGetGames)
                .header("content-type", "application/json")
                .header("origin", originPokiesHeaders)
                .body("{\"PageIndex\":0,\"PageSize\":400,\"WithWidget\":false,\"CategoryId\":null,\"ProviderIds\":null,\"IsForMobile\":false,\"Name\":\"\",\"LanguageId\":\"en\",\"Token\":null,\"ClientId\":0,\"TimeZone\":4}")
                .asString();


        JSONObject jsonObjectBody = new JSONObject(response.getBody());
        JSONObject jsonObjectResponseObject = new JSONObject(jsonObjectBody.getString("ResponseObject"));
        JSONArray jsonArrayGames = jsonObjectResponseObject.getJSONArray("Games");


        for (int j = 0; j < jsonArrayGames.length(); j++) {

            String first = String.valueOf(jsonArrayGames.get(j));
            JSONObject jsonObjectGame = new JSONObject(first);
            String i = jsonObjectGame.getString("i");
            String n = jsonObjectGame.getString("n");
            String sp = jsonObjectGame.getString("sp");
            String p = jsonObjectGame.getString("p");
            gameNames.add(n);
            gameProviderNames.add(sp);
            productIDIMG.add(p);

            if (i.contains("http") && !i.contains(" ")) {
                srces.add(i);
            } else if (i.contains(" ")) {
                if (!i.contains("http")){
                    String change = i.replace(" ", "%20");
                    change = "https://resources.pokies2go.io/products/" + change;   //need to be changed for another url
                    srces.add(change);
                }
                else{
                    String change = i.replace(" ", "%20");
                    srces.add(change);
                }

            } else {
                srces.add("https://resources.pokies2go.io/products/" + i);      //need to be changed for another url
            }
        }


        for (String src : srces) {
            if (src == null || src.isEmpty()) {
                logger.info(k + "   Game Provider Name = " + gameProviderNames.get(k)+ "                    productID = " + productIDIMG.get(k) + " :   " + "Game Name = " + gameNames.get(k) + " :   " + ":   src = " + src + " :" + " this games image src has empty/null value");
                errorSrcXl.add(k + "                    Game Provider Name = " + gameProviderNames.get(k)+ "                    productID = " + productIDIMG.get(k) + " :                    " + "Game Name = " + gameNames.get(k) + " :                    " + ":   src = " + src + " :" + " this games image src has empty/null value");
            } else {
                try {
                    URL img = new URL(src);
                    HttpURLConnection connection = (HttpURLConnection) img.openConnection();
                    connection.connect();
                    int cod = connection.getResponseCode();

                    if (cod >= 400) {
                        logger.fatal(k + "   Game Provider Name = " + gameProviderNames.get(k) + " :   " + "Game Name = " + gameNames.get(k)+ "                    productID = " + productIDIMG.get(k) + " :   " + "cod = " + cod + ":   src = " + src);
                        errorSrcXl.add(k + "                    Game Provider Name = " + gameProviderNames.get(k)+ "                    productID = " + productIDIMG.get(k) + " :                    " + "Game Name = " + gameNames.get(k) + " :                    " + "cod = " + cod + ":   src = " + src);
                    }
                } catch (Exception e) {
                    try{
                        URL img = new URL(src);
                        HttpURLConnection connection = (HttpURLConnection) img.openConnection();
                        connection.connect();
                        int cod = connection.getResponseCode();

                        if (cod >= 400) {
                            logger.fatal(k + "   Game Provider Name = " + gameProviderNames.get(k)+ "                    productID = " + productIDIMG.get(k) + " :   " + "Game Name = " + gameNames.get(k) + " :   " + "cod = " + cod + ":   src = " + src);
                            errorSrcXl.add(k + "                    Game Provider Name = " + gameProviderNames.get(k) + "                    productID = " + productIDIMG.get(k)+ " :                    " + "Game Name = " + gameNames.get(k) + " :                    " + "cod = " + cod + ":   src = " + src);
                        }
                    }
                    catch (Exception ex){
                        logger.fatal(k + "                    Game Provider Name = " + gameProviderNames.get(k) + "                    productID = " + productIDIMG.get(k)+ " :                    " + "Game Name = " + gameNames.get(k) + " :                    " + "   src = " + src + "         " + ex);
                        errorSrcXl.add(k + "                    Game Provider Name = " + gameProviderNames.get(k)+ "                    productID = " + productIDIMG.get(k) + " :                    " + "Game Name = " + gameNames.get(k) + " :                    " + "   src = " + src);
                    }

                }
            }
            k++;
        }

        logger.warn("Broken images are:  " + errorSrcXl.size());
        if (errorSrcXl.size() == 0) {
            Assert.assertTrue(true);
        } else {
            String target = System.getProperty("user.dir") + "/src/test/java/APICasinoGamesCasinoImagesBrokenData/PokiesDataBrokenIMGList.xlsx";   //need to be changed for another url
            FileOutputStream file = new FileOutputStream(target);
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("brokenIMG");
            int l = 0;
            for (String err : errorSrcXl) {
                XSSFRow row = sheet.createRow(l);
                row.createCell(0).setCellValue(err);
                l++;
            }
            workbook.write(file);
            workbook.close();
            Assert.fail();
        }
    }


    @Test
    public void getUrlTest() throws UnirestException, JSONException, IOException {
        int errCount = 1;
        int k = 0;

        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.post(urlPokiesBetGetGames)
                .header("content-type", "application/json")
                .header("origin", originPokiesHeaders)
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
            productID.add(p);
            name.add(n);
        }
        logger.info("From Get All games API productIDes and Names was captured ");

        for (String id : productID) {
            int proID = Integer.parseInt(id);

            Unirest.setTimeouts(0, 0);
            HttpResponse<String> responseUrl = Unirest.post(urlPokiesGetProductUrl)
                    .header("Content-Type", "application/json")
                    .header("origin", originPokiesHeaders)
                    .body("{\"Loader\":true,\"PartnerId\":56,\"TimeZone\":4,\"LanguageId\":\"en\",\"ProductId\":" + proID + ",\"Method\":\"GetProductUrl\",\"Controller\":\"Main\",\"CategoryId\":null,\"PageIndex\":0,\"PageSize\":100,\"ProviderIds\":[],\"Index\":null,\"ActivationKey\":null,\"MobileNumber\":null,\"Code\":null,\"RequestData\":\"{}\",\"IsForDemo\":false,\"IsForMobile\":false,\"Position\":\"\",\"DeviceType\":1,\"ClientId\":1650272,\"Token\":\"" + token + "\"}")
                    .asString();


            JSONObject jsonObjectGetUrl = new JSONObject(responseUrl.getBody());

            String code = jsonObjectGetUrl.getString("ResponseCode");
            String description = jsonObjectGetUrl.getString("Description");
            String url = jsonObjectGetUrl.getString("ResponseObject");

            try{
                if (!code.equals("0") || url==null || url.length() < 10) {
                    String s = errCount + " _ " + k + "        ProductID = " + id + "           Name = " + name.get(k)+ ":         cod = " + code + ":        description = " + description + ":          ResponseObject = " + url;
                    logger.error(s);
                    brokenURL.add(s);
                    errCount++;
                }
            }
            catch (Exception e){
                logger.info(k + " Unirest Exception ");
            }
            k++;
        }
        logger.info("From Get URL API broken games are captured");

        logger.warn("Broken url-es are:  " + brokenURL.size());
        if (brokenURL.size() == 0) {
            logger.info("Broken Games are null");
            Assert.assertTrue(true);

        } else {
            String target = System.getProperty("user.dir") + "/src/test/java/APICasinoGamesCasinoImagesBrokenData/PokiesDataBrokenURLList.xlsx";   //need to be changed for another url
            FileOutputStream file = new FileOutputStream(target);
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("brokenURL");
            int l = 0;
            for (String err : brokenURL) {
                XSSFRow row = sheet.createRow(l);
                row.createCell(0).setCellValue(err);
                l++;
            }
            workbook.write(file);
            workbook.close();
            Assert.assertTrue(false);
        }
    }


    @Test
    public void porc(){
        craftBet_01_header_page.getBrowserErrors();
    }



}






