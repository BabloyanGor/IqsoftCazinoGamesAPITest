package testCases;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;

public class CraftBet_Casino_Game_Test_API extends BaseTest {
    String urlCraftBetGetGames = "https://websitewebapi.craftbet.com/1/api/Main/GetGames";
    String originCraftBetGetGames = "https://craftbet.com";
    String urlCraftBetGetProductUrl = "https://websitewebapi.craftbet.com/1/api/Main/GetProductUrl";
    String originCraftBetGetProductUrl = "https://craftbet.com";
    ArrayList<String> productID = new ArrayList<>();
    ArrayList<String> brokenURL = new ArrayList<>();

    String token;
    int waitTime = 300;

    @BeforeMethod
    public void logInCraftBet() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
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
    public void gamesImgTest() throws UnirestException, JSONException, IOException {
        if (craftBet_01_header_page.getGamesAPICheckPictures(APIUrl,origin,recurse,partnerName)){
            Assert.assertTrue(true);
        }
        else {
            Assert.fail();
        }
    }


    @Test
    public void getUrlTest() throws UnirestException, JSONException, IOException {
        int errCount = 1;
        int k = 0;

        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.post(urlCraftBetGetGames)
                .header("content-type", "application/json")
                .header("origin", originCraftBetGetGames)
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
            productID.add(p);
        }
        logger.info("From Get All games API productIDes captured ");

        for (String id : productID) {
            int proID = Integer.parseInt(id);

            Unirest.setTimeouts(0, 0);
            HttpResponse<String> responseTest = Unirest.post(urlCraftBetGetProductUrl)
                    .header("Content-Type", "application/json")
                    .header("origin", originCraftBetGetProductUrl)
                    .body("{\r\n    \"Loader\": true,\r\n    \"PartnerId\": 1,\r\n    \"TimeZone\": 4,\r\n    \"LanguageId\": \"en\",\r\n    \"ProductId\": " + proID + ",\r\n    \"Method\": \"GetProductUrl\",\r\n    \"Controller\": \"Main\",\r\n    \"CategoryId\": null,\r\n    \"PageIndex\": 0,\r\n    \"PageSize\": 100,\r\n    \"ProviderIds\": [],\r\n    \"Index\": null,\r\n    \"ActivationKey\": null,\r\n    \"MobileNumber\": null,\r\n    \"Code\": null,\r\n    \"RequestData\": \"{}\",\r\n    \"IsForDemo\": false,\r\n    \"IsForMobile\": false,\r\n    \"Position\": \"\",\r\n    \"DeviceType\": 1,\r\n    \"ClientId\": 1630845,\r\n    \"Token\":\"" + token + "\"\r\n}")
                    .asString();
            JSONObject jsonObjectGetUrl = new JSONObject(responseTest.getBody());

            String code = jsonObjectGetUrl.getString("ResponseCode");
            String description = jsonObjectGetUrl.getString("Description");
            String url = jsonObjectGetUrl.getString("ResponseObject");

            if (!code.equals("0") || url.isEmpty() || url.length() < 10) {
                String s = errCount + " _ " + k + " ProductID = " + id + ":  cod = " + code + ":  description = " + description + ":  ResponseObject = " + url;
                logger.fatal(s);
                brokenURL.add(s);
                errCount++;
            }
            k++;
        }
        logger.info("From Get URL API broken games are captured");



        logger.warn("Broken url-es are:  " + brokenURL.size());
        if (brokenURL.size() == 0) {
            logger.info("Broken Games are null");
            Assert.assertTrue(true);

        } else {
            String target = System.getProperty("user.dir") + "/src/test/java/APICasinoGamesCasinoImagesBrokenData/craftBetDataBrokenURLList.xlsx";
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
            logger.info("Broken Games info was written on exel sheet craftBetDataBrokenURLList.xlsx");
            Assert.assertTrue(false);
        }
    }

}






