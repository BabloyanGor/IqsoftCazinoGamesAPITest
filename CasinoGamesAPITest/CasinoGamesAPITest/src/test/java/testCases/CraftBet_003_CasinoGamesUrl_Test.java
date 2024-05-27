package testCases;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.awt.*;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static pageObjects.BasePage.dateTimeNow;


public class CraftBet_003_CasinoGamesUrl_Test extends BaseTest {

    private String token;
    private int userID;

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
            Unirest.setTimeouts(20000, 20000);
            HttpResponse<String> response = Unirest.post(loginClient)
//                    .header("origin", getGamesOrigin)
                    .header("Content-Type", "application/json")
//                    .body("{\"Data\":\"M+/X9wqJp4gNfj2gGgHoNvrXY/3viig6D1SaG2xejREv7Q0TwrPilF98mne59b3vIytOitTfpNnfcwBt2f4V5LBNIbk4St8LcZKXypvuJArz3F+//z3eY8grTLhVWfctV027XZK9K+WuY3y4dYqgy6q9zqkk61wSZ1oMYv5GnnTGNdYG0wgZRMKXJFzEokN2aA8B26ScXWIAqGoDH+dEuAlpaAMFMK4CMNLNu+uo/iX1iAbo4cB5AygbnTfvqA0CWkCCca3ngoN/YVEgYVxuvM7OSpeAN0lUCY0KbbDCd1d5DhUytazzOeSwrFSE+Ti0pwl9f/DwWfM4cozyfOyHhg==\",\"TimeZone\":4}")
                    .body("{\"Data\":\"gVJyxZTOoasenz/e9nUoz6Bu2589f9STLdKT7cOEYt1zTbWZV5i8wCsm6KDEI96wh6X4RIHGOmvd/pfHAH+PL4ZpeQ7/QyM+tZP9w0qZ1oRzRhpC3WK+TG4wlTGA" +
                            "4JvbM14ihUyJ5VNojZdKv7dmEpMccCi4jB6rtRRVilfbizZCpWKkg/w1lXVuZuGiqKA3splWk8RkxFZMQpFLi77+Ckv0TIkapJM2ANDaGLrafk7Tupnj1M7xaiqrfuGjTO5Wqr" +
                            "XIOsGLMC1Bj/GhnWIdGsSumaD2aYyhJJ66ISZTvZwATGYHobqjHxms1hvmdbQB+iN2teGGQP7KkkRn4iGcaw==\",\"TimeZone\":4}")
                    .asString();
            logger.info(dateTimeNow() + "  Log In request was sent to " + getGamesBaseURL + "  >>>   " + dateTimeNow());

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
            logger.error("Login process has an Exception " + e);
        }
    }


    //    CompletableFuture<Void> future;
    CompletableFuture[] futures;
    private int i = 0;

    private boolean checkGamesUrl(int userID, int partnerId, String partnerName)
            throws JSONException, IOException {
        try {
//            getGamesInfo(false);
            futures = new CompletableFuture[productIDs.size()];
            for (; i < productIDs.size(); i++) {

                int productId = Integer.parseInt(productIDs.get(i));
                String gameName = gameNames.get(i);
                String providerName = gameProviders.get(i);

                try {
                    futures[i] = CompletableFuture.runAsync(() -> {

                        asyncCall(userID, partnerId, productId, gameName, providerName);
                    });
                } catch (Exception e) {
                    // Handle exception if needed
                    logger.error("checkGamesUrl() call Async() has an Exception " + e);
                    futures[i].completeExceptionally(e);
                }
            }

            CompletableFuture<Void> allOf = CompletableFuture.allOf(futures);
            allOf.get(asyncMaxTimeMinutes, TimeUnit.MINUTES); // This will block until all CompletableFuture are completed

        } catch (Exception e) {
            logger.error("checkGamesUrl()  call getGamesInfo() has an Exception " + e);
//            for (CompletableFuture<Void> future : futures) {
//                if (!future.isDone()) {
//                    future.cancel(true); // Cancel incomplete futures
//
//                }
////            future.completeExceptionally(e);
//            }
        }

        //Write errors into exel shite
        Unirest.shutdown();
        logger.info("Broken url-es are:  " + errorSrcXl.size() + " of " + productIDs.size());
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


    private int k = 1;
    private int errCount = 1;

    private void asyncCall(int userID, int partnerID, int productId, String gameName, String providerName) {

        int maxRetries = 10;
        int currentRetry = 0;
        do {
            try {
                Unirest.setTimeouts(20000, 20000);
                HttpResponse<JsonNode> responseUrl = Unirest.post(getURLAPIUrl)
                        .header("Content-Type", "application/json")
                        .header("origin", getGamesOrigin)
                        .body("{ \"IsForMobile\": false,   \"LanguageId\": \"en\",    \"PartnerId\": " + partnerID + "," +
                                " \"ProductId\": " + productId + ",  \"Position\": \"casino\"," +
                                " \"Token\": \"" + token + "\",    \"ClientId\":" + userID + " ," +
                                " \"IsForDemo\": false, \"TimeZone\": 4}")
                        .asJsonAsync()
                        .get();

                JSONObject jsonObjectGetUrl = responseUrl.getBody().getObject();

                String code = jsonObjectGetUrl.get("ResponseCode").toString();
                String description = jsonObjectGetUrl.get("Description").toString();
                String url = jsonObjectGetUrl.get("ResponseObject").toString();

                if (!code.equals("0") || url == null || url.length() < 20 || !url.contains("https://")) {
                    String errMessage = dateTimeNow() + " ID = " + productId + " GameName: " + gameName + " ProviderName: " + providerName + "  ResponseCode = " + code + " description = " + description + " ResponseObject = " + url;
                    logger.info(errCount + " " + k + "   " + errMessage);
//                    logger.info("   " + errMessage);

                    errorSrcXl.add(errMessage);
                    errCount++;
                } else {
//                    String message = dateTimeNow() + " ID = " + productId + " GameName: " + gameName + " ProviderName: " + providerName + "  ResponseCode = " + code + " description = " + description + " ResponseObject = " + url;
//                    logger.info(errCount + " " + k + "   " + message);
                }
                k++;
                break;
            } catch (Exception a) {
                logger.fatal(k + " jsonObjectGetUrl has an exception " + a);

//                futures[i].completeExceptionally(a);

                currentRetry++;
            }

        } while (currentRetry < maxRetries);
    }


    @Test
    public void getSlotGamesUrlAPITest() throws JSONException {
        try {
            if (partnerConfigNum<1000) {
                Assert.assertTrue(checkGamesUrl(userID, partnerID, getGamesPartnerName));
            }else{
                throw new SkipException("PartnerId = " + partnerID + " Is External Partner");
            }

        } catch (Exception e) {
            logger.fatal("getUrlAPITest has an exception" + e);
            Assert.fail();
        }
    }


}
