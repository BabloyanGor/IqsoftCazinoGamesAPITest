package testCases;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class CraftBet_003_CasinoGamesUrl_Test extends BaseTest {
    String token;

    @BeforeMethod
    public void logIn() {

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            craftBet_01_header_page.clickOnLogInButtonIfVisible();
            craftBet_03_login_popUp_page.loginPopUpEmailOrUsernameSendKeys(username);
            logger.info("username passed");
            craftBet_03_login_popUp_page.loginPopUpPasswordSendKeys(password);
            logger.info("password passed");
            craftBet_03_login_popUp_page.clickLoginPopUpLogInButton();
            logger.info("Log In Button was clicked");
            if (craftBet_01_header_page.userIdLabelIsEnabled()) {
                token = craftBet_01_header_page.getItem("token");
                logger.info("Token was captured " + token);
            }
            else {
                token = craftBet_01_header_page.getItem("token");
                logger.info("Token was captured " + token);
            }
    }

    public boolean getUrlCheckGamesUrl(String getGamesAPIUrl, String origin, String getURLAPIurl, String token,
                                       int userID, int partnerID, String partnerName)
            throws JSONException, IOException, UnirestException {
        boolean isPassed;
        ArrayList<String> productID = new ArrayList<>();
        ArrayList<String> name = new ArrayList<>();
        ArrayList<String> provider = new ArrayList<>();
        ArrayList<String> errorSrcXl = new ArrayList<>();

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
        JSONObject jsonObjectResponseObject = new JSONObject(jsonObjectBody.get("ResponseObject").toString());
        JSONArray jsonArrayGames = jsonObjectResponseObject.getJSONArray("Games");
        logger.info("From Get All games API call body captured ");
        Unirest.shutdown();

        for (int j = 0; j < jsonArrayGames.length(); j++) {
            String first = String.valueOf(jsonArrayGames.get(j));
            JSONObject jsonObjectGame = new JSONObject(first);
            String p = jsonObjectGame.get("p").toString();
            String n = jsonObjectGame.get("n").toString();
            String sp = jsonObjectGame.get("sp").toString();
            productID.add(p);
            name.add(n);
            provider.add(sp);
        }
        logger.info("From Get All games API productIDes and Names was captured ");

        for (String id : productID) {
            int proID = parseInt(id);

            Unirest.setTimeouts(0, 0);
            HttpResponse<String> responseUrl = Unirest.post(getURLAPIurl)
                    .header("Content-Type", "application/json")
                    .header("origin", origin)
                    .body("{\"Loader\":true,\"PartnerId\":" + partnerID + ",\"TimeZone\":4,\"LanguageId\":\"en\",\"ProductId\":" + proID + ",\"Method\":\"GetProductUrl\",\"Controller\":\"Main\",\"CategoryId\":null,\"PageIndex\":0,\"PageSize\":100,\"ProviderIds\":[],\"Index\":null,\"ActivationKey\":null,\"MobileNumber\":null,\"Code\":null,\"RequestData\":\"{}\",\"IsForDemo\":false,\"IsForMobile\":false,\"Position\":\"\",\"DeviceType\":1,\"ClientId\":" + userID + ",\"Token\":\"" + token + "\"}")
                    .asString();


            JSONObject jsonObjectGetUrl = new JSONObject(responseUrl.getBody());

            String code = jsonObjectGetUrl.get("ResponseCode").toString();
            String description = jsonObjectGetUrl.get("Description").toString();
            String url = jsonObjectGetUrl.get("ResponseObject").toString();
            Unirest.shutdown();
            String errMessage;
            try {
                if (!code.equals("0") || url == null || url.length() < 10) {
                    errMessage = errCount + " " + k + " ID=" + id + " Provider=" + provider.get(k) + " Name=" + name.get(k) + " cod=" + code + " description=" + description + " ResponseObject=" + url;
                    logger.info(errMessage);
                    errorSrcXl.add(errMessage);
                    errCount++;
                }
            } catch (Exception e) {
                logger.info(k + " Unirest Exception ");
            }
            k++;

        }
        logger.info("From Get URL API broken games are captured");

        //Write into exel shite

        logger.info("Broken url-es are:  " + errorSrcXl.size());
        if (errorSrcXl.size() == 0) {
            logger.info("Broken Games are null");
            isPassed = true;

        } else {
            writeInExel(errorSrcXl, "/src/test/java/APICasinoGamesCasinoImagesBrokenData/" + readConfig.partnerConfigNum() + partnerName + "DataBrokenURLList.xlsx", "brokenURL");
            isPassed = false;
        }
        return isPassed;
    }

    @Test
    public void getUrlAPITest() throws UnirestException, JSONException, IOException {
        if (getUrlCheckGamesUrl(getGamesAPIUrl, getGamesOrigin, getURLAPIUrl, token, getUserID, partnerID, getGamesPartnerName)) {
            Assert.assertTrue(true);
        } else {
            Assert.fail();
        }
    }


}