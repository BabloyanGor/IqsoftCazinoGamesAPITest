package testCases;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class CraftBet_003_CasinoGamesUrl_Test extends BaseTest {

    String token;
    int userID;
    int getGamesOnOneCall = 1000;

    public CraftBet_003_CasinoGamesUrl_Test() throws AWTException {
    }

    @BeforeMethod
    public void logIn() {

//        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
//        craftBet_01_header_page.clickOnLogInButtonIfVisible();
//        craftBet_03_login_popUp_page.loginPopUpEmailOrUsernameSendKeys(username);
//        logger.info("username passed");
//        craftBet_03_login_popUp_page.loginPopUpPasswordSendKeys(password);
//        logger.info("password passed");
//        craftBet_03_login_popUp_page.clickLoginPopUpLogInButton();
//        logger.info("Log In Button was clicked");
//        if (craftBet_01_header_page.userIdLabelIsEnabled()) {
//            token = craftBet_01_header_page.getItem("token");
//            logger.info("Token was captured " + token);
//        } else if (craftBet_01_header_page.balanceLabelIsEnabled()) {
//            token = craftBet_01_header_page.getItem("token");
//            logger.info("Token was captured " + token);
//        } else {
//            craftBet_03_login_popUp_page.waitAction(2000);
//            if (craftBet_01_header_page.getItem("token")!=null){
//            token = craftBet_01_header_page.getItem("token");
//            logger.info("Token was captured " + token);
//            }
//            else{
//                logger.info("Token Cant be captured its null token: " + token);
//            }
//        }

        try {
            Unirest.setTimeouts(0, 0);
            HttpResponse<String> response = Unirest.post(loginClient)
                    .header("origin", getGamesOrigin)
                    .header("Content-Type", "application/json")
//                    .body("{\"Data\":\"M+/X9wqJp4gNfj2gGgHoNvrXY/3viig6D1SaG2xejREv7Q0TwrPilF98mne59b3vIytOitTfpNnfcwBt2f4V5LBNIbk4St8LcZKXypvuJArz3F+//z3eY8grTLhVWfctV027XZK9K+WuY3y4dYqgy6q9zqkk61wSZ1oMYv5GnnTGNdYG0wgZRMKXJFzEokN2aA8B26ScXWIAqGoDH+dEuAlpaAMFMK4CMNLNu+uo/iX1iAbo4cB5AygbnTfvqA0CWkCCca3ngoN/YVEgYVxuvM7OSpeAN0lUCY0KbbDCd1d5DhUytazzOeSwrFSE+Ti0pwl9f/DwWfM4cozyfOyHhg==\",\"TimeZone\":4}")
                    .body("{\"Data\":\"gVJyxZTOoasenz/e9nUoz6Bu2589f9STLdKT7cOEYt1zTbWZV5i8wCsm6KDEI96wh6X4RIHGOmvd/pfHAH+PL4ZpeQ7/QyM+tZP9w0qZ1oRzRhpC3WK+TG4wlTGA4JvbM14ihUyJ5VNojZdKv7dmEpMccCi4jB6rtRRVilfbizZCpWKkg/w1lXVuZuGiqKA3splWk8RkxFZMQpFLi77+Ckv0TIkapJM2ANDaGLrafk7Tupnj1M7xaiqrfuGjTO5WqrXIOsGLMC1Bj/GhnWIdGsSumaD2aYyhJJ66ISZTvZwATGYHobqjHxms1hvmdbQB+iN2teGGQP7KkkRn4iGcaw==\",\"TimeZone\":4}")

                    .asString();
            logger.info("Log In request was sent to " + getGamesBaseURL);

            JSONObject jsonObjectBody = new JSONObject(response.getBody());
            Unirest.shutdown();
            int ResponseCode = jsonObjectBody.getInt("ResponseCode");
            if (ResponseCode == 0) {
                token = jsonObjectBody.getString("Token");
                logger.info("Token was captured: " + token);
                userID = jsonObjectBody.getInt("Id");
                logger.info("User Id was captured: " + userID);
            }
        } catch (Exception e) {
            logger.fatal("Login process has an Exception " + e);
        }
    }


    public boolean getUrlCheckGamesUrlParallel(String getGamesAPIUrl, String origin, String getURLAPIurl, String token,
                                               int userID, int partnerID, String partnerName)
            throws JSONException, IOException {

        Integer gamesCount = 1;

        try {
            Unirest.setTimeouts(0, 0);
            HttpResponse<String> response = Unirest.post(getGamesAPIUrl)
                    .header("content-type", "application/json")
                    .header("origin", origin)
                    .body("{\"PageIndex\":0,\"PageSize\":10,\"WithWidget\":false,\"CategoryId\":null,\"ProviderIds\":null,\"IsForMobile\":false,\"Name\":\"\"," +
                            "\"LanguageId\":\"en\",\"Token\":null,\"ClientId\":0,\"TimeZone\":4}")
                    .asString();

            logger.info("Get games Api call was sent");
            JSONObject jsonObjectBody = new JSONObject(response.getBody());
            JSONObject jsonObjectResponseObject = new JSONObject(jsonObjectBody.get("ResponseObject").toString());
            gamesCount = Integer.valueOf(jsonObjectResponseObject.get("TotalGamesCount").toString());
            logger.info("Get games Api call: TotalGamesCount = " + gamesCount);

        } catch (Exception e) {
            logger.info("Get Games Call for TotalGamesCount has an exception " + e);

        }


        boolean isPassed;
        ArrayList<String> productID = new ArrayList<>();
        ArrayList<String> name = new ArrayList<>();
        ArrayList<String> provider = new ArrayList<>();
        ArrayList<String> errorSrcXl = new ArrayList<>();

        int errCount = 1;
        int k = 1;
        JSONObject jsonObjectBody;
        JSONObject jsonObjectResponseObject;
        JSONArray jsonArrayGames = null;
//        String contentType = null;
        int circleCount = gamesCount / getGamesOnOneCall + 1;
        for (int m = 0; m < circleCount; m++) {
        try {

            Unirest.setTimeouts(0, 0);
            HttpResponse<String> response = Unirest.post(getGamesAPIUrl)
                    .header("content-type", "application/json")
                    .header("origin", origin)
                    .body("{\"PageIndex\":" +
                            m +
                            ",\"PageSize\":" +
                            getGamesOnOneCall +
                            ",\"WithWidget\":false,\"CategoryId\":null,\"ProviderIds\":null,\"IsForMobile\":false," +
                            "\"Name\":\"\",\"LanguageId\":\"en\",\"Token\":null,\"ClientId\":0,\"TimeZone\":4}")
                    .asString();

            jsonObjectBody = new JSONObject(response.getBody());
            jsonObjectResponseObject = new JSONObject(jsonObjectBody.get("ResponseObject").toString());
            jsonArrayGames = jsonObjectResponseObject.getJSONArray("Games");
            logger.info("From getGamesAPIUrl call body captured: " + m);
        } catch (Exception ee) {
            logger.fatal("getGamesAPIUrl call has an exception " + ee);
        } finally {
            Unirest.shutdown();
        }


        if (jsonArrayGames != null) {
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
        }
        logger.info("From getGamesAPIUrl call productIDes and Names was captured Games count: " + productID.size());

    }


        for (String id : productID) {
//        productID.parallelStream().forEach(id -> {

            int idIndex = productID.indexOf(id);
            JSONObject jsonObjectGetUrl;
            String code = "null";
            String description = null;
            String url = null;
            try {
                int proID = parseInt(id);
                Unirest.setTimeouts(0, 0);
                HttpResponse<String> responseUrl = Unirest.post(getURLAPIurl)
                        .header("Content-Type", "application/json")
                        .header("origin", origin)
//                        .body("{\"Loader\":true,\"PartnerId\":" + partnerID + ",\"TimeZone\":4,\"LanguageId\":\"en\",\"ProductId\":" + proID +
//                                ",\"Method\":\"GetProductUrl\",\"Controller\":\"Main\",\"CategoryId\":null,\"PageIndex\":0,\"PageSize\":100,\"ProviderIds\":[]," +
//                                "\"Index\":null,\"ActivationKey\":null,\"MobileNumber\":null,\"Code\":null,\"RequestData\":\"{}\",\"IsForDemo\":false," +
//                                "\"IsForMobile\":false,\"Position\":\"\",\"DeviceType\":1,\"ClientId\":" + userID + ",\"Token\":\"" + token + "\"}")
                        .body("{ \"IsForMobile\": false,   \"LanguageId\": \"en\",    \"PartnerId\": "+ partnerID +"," +
                                " \"ProductId\": "+ proID +",  \"Position\": \"casino\"," +
                                " \"Token\": \"" + token + "\",    \"ClientId\":"+ userID +" ," +
                                " \"IsForDemo\": false, \"TimeZone\": 4}")
                        .asString();
//                contentType = responseUrl.getHeaders().getFirst("Content-Type");

                jsonObjectGetUrl = new JSONObject(responseUrl.getBody());
                code = jsonObjectGetUrl.get("ResponseCode").toString();
                description = jsonObjectGetUrl.get("Description").toString();
                url = jsonObjectGetUrl.get("ResponseObject").toString();
                k++;

            } catch (Exception e) {
                try {
                    int proID = parseInt(id);
                    Unirest.setTimeouts(0, 0);
                    HttpResponse<String> responseUrl = Unirest.post(getURLAPIurl)
                            .header("Content-Type", "application/json")
                            .header("origin", origin)
//                        .body("{\"Loader\":true,\"PartnerId\":" + partnerID + ",\"TimeZone\":4,\"LanguageId\":\"en\",\"ProductId\":" + proID +
//                                ",\"Method\":\"GetProductUrl\",\"Controller\":\"Main\",\"CategoryId\":null,\"PageIndex\":0,\"PageSize\":100,\"ProviderIds\":[]," +
//                                "\"Index\":null,\"ActivationKey\":null,\"MobileNumber\":null,\"Code\":null,\"RequestData\":\"{}\",\"IsForDemo\":false," +
//                                "\"IsForMobile\":false,\"Position\":\"\",\"DeviceType\":1,\"ClientId\":" + userID + ",\"Token\":\"" + token + "\"}")
                            .body("{ \"IsForMobile\": false,   \"LanguageId\": \"en\",    \"PartnerId\": "+ partnerID +"," +
                                    " \"ProductId\": "+ proID +",  \"Position\": \"casino\"," +
                                    " \"Token\": \"" + token + "\",    \"ClientId\":"+ userID +" ," +
                                    " \"IsForDemo\": false, \"TimeZone\": 4}")
                            .asString();
//                contentType = responseUrl.getHeaders().getFirst("Content-Type");

                    jsonObjectGetUrl = new JSONObject(responseUrl.getBody());
                    code = jsonObjectGetUrl.get("ResponseCode").toString();
                    description = jsonObjectGetUrl.get("Description").toString();
                    url = jsonObjectGetUrl.get("ResponseObject").toString();
                    k++;

                }catch (Exception ee){
                    logger.fatal("jsonObjectGetUrl has an exception " + ee);
                }

            } finally {
                try {
                    Unirest.shutdown();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            String errMessage;
            try {
                if (!code.equals("0") || url == null || url.length() < 10 || !url.contains("https://") ) {
                    errMessage = k + " ID=" + id + " Provider=" + provider.get(idIndex) + " Name=" + name.get(idIndex) + " cod=" + code + " description=" + description + " ResponseObject=" + url;
                    logger.info(errCount + " " + errMessage);
                    errorSrcXl.add(errMessage);
                    errCount++;

                }

            } catch (Exception e) {
                logger.info(k + " Unirest Exception ");
            }

//        });
        }


        logger.info("From Get URL API broken games are captured");

        //Write into exel shite

        logger.info("Broken url-es are:  " + errorSrcXl.size() + " of " + productID.size());
        if (errorSrcXl.size() == 0) {
            logger.info("Broken Games are null");
            basePage.writeInExel(errorSrcXl, "/src/test/java/CraftBet_001_APICasinoGamesBrokenData/" + readConfig.partnerConfigNum() + partnerName + "BrokenData.xlsx", "GamesBrokenURL");
            isPassed = true;

        } else {
            basePage.writeInExel(errorSrcXl, "/src/test/java/CraftBet_001_APICasinoGamesBrokenData/" + readConfig.partnerConfigNum() + partnerName + "BrokenData.xlsx", "GamesBrokenURL");
            isPassed = false;
        }
        return isPassed;
    }


    @Test
    public void getSlotGamesUrlAPITest() throws JSONException {
        try {
            Assert.assertTrue(getUrlCheckGamesUrlParallel(getGamesAPIUrl, getGamesOrigin, getURLAPIUrl, token, userID, partnerID, getGamesPartnerName));
        } catch (Exception e) {
            System.out.println("getUrlAPITest has an exception" + e);
            Assert.fail();
        }
    }


}