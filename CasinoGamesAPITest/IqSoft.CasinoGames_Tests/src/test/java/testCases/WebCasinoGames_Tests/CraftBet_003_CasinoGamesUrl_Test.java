package testCases.WebCasinoGames_Tests;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.json.JSONException;
import kong.unirest.json.JSONObject;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static pageObjects.BasePage.dateTimeNow;


public class CraftBet_003_CasinoGamesUrl_Test extends BaseTest {

    private String token;
    private int userID;
    private String currency;

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
            Unirest.config().reset();
            Unirest.config().connectTimeout(60000);
            Unirest.config().socketTimeout(60000);
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
            Unirest.shutDown();
            int ResponseCode = jsonObjectBody.getInt("ResponseCode");
            if (ResponseCode == 0) {
                token = jsonObjectBody.getString("Token");
                logger.info("Token was captured: " + token);
                userID = jsonObjectBody.getInt("Id");
                logger.info("User Id was captured: " + userID);
                currency = jsonObjectBody.getString("CurrencyId");
                logger.info("Currency was captured: " + currency);
            }
        } catch (Exception e) {
            logger.error("Login process has an Exception " + e);
        }
    }



    CompletableFuture[] futures;
    private int i = 0;
    ArrayList<String> errSrces = new ArrayList<>();
    ArrayList<String> errGameNames = new ArrayList<>();
    ArrayList<String> errGameIDes = new ArrayList<>();
    ArrayList<String> errGameProviderNames = new ArrayList<>();
    private boolean checkGamesUrl(int userID, int partnerId)
            throws JSONException, IOException {
//        ExecutorService executorService = Executors.newFixedThreadPool(productIDs.size()*2);

        try {
            futures = new CompletableFuture[productIDs.size()];
            for (; i < productIDs.size(); i++) {

                int productId = Integer.parseInt(productIDs.get(i));
                String gameName = gameNames.get(i);
                String providerName = gameProviders.get(i);

                try {
                    futures[i] = CompletableFuture.runAsync(() -> {

                        asyncCall(userID, partnerId, productId, gameName, providerName);
                    });
//                    futures[i] = CompletableFuture.runAsync(() -> {
//                        asyncCall(userID, partnerId, productId, gameName, providerName);
//                    }, executorService);
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
        }
        finally {
//            executorService.shutdown();
        }

        //Write errors into exel shite
        Unirest.shutDown();

        if (errGameIDes.size() == 0) {
            basePage.writeDataToExcel("/src/test/java/CraftBet_001_APICasinoGamesBrokenData/" + readConfig.partnerConfigNum()+ getGamesPartnerName + "BrokenData.xlsx",  errGameIDes,errGameNames,errGameProviderNames,errSrces,"GamesBrokenURL");
            isPassed = true;
        } else {
            basePage.writeDataToExcel("/src/test/java/CraftBet_001_APICasinoGamesBrokenData/" + readConfig.partnerConfigNum()+ getGamesPartnerName + "BrokenData.xlsx",  errGameIDes,errGameNames,errGameProviderNames,errSrces,"GamesBrokenURL");
            isPassed = false;
        }
        return isPassed;
    }


    private int k = 1;
    private int errCount = 1;

    private void asyncCall(int userID, int partnerID, int productId, String gameName, String providerName) {

        int maxRetries = 3;
        int currentRetry = 0;
//        long retryDelay = 2000; // Initial delay of 2 seconds between retries

        do {
            try {
                Unirest.config().reset();
                Unirest.config().connectTimeout(120000);
                Unirest.config().socketTimeout(120000);
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

                String code = String.valueOf(jsonObjectGetUrl.get("ResponseCode"));
                String description = String.valueOf(jsonObjectGetUrl.get("Description"));
                String url = String.valueOf(jsonObjectGetUrl.get("ResponseObject"));

                if (!code.equals("0") || url == null || url.length() < 20 || !url.contains("https://")) {
                    String errMessage = dateTimeNow() + " ID = " + productId + " GameName: " + gameName + " ProviderName: " + providerName + "  ResponseCode = " + code + " description = " + description + " ResponseObject = " + url;

                    errSrces.add("ResponseCode = " + code + "  Description = " + description + "  ResponseObject = "+ url);
                    errGameNames.add(gameName);
                    errGameIDes.add(String.valueOf(productId));
                    errGameProviderNames.add(providerName);

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
                currentRetry++;
                futures[i].completeExceptionally(a);

                if (currentRetry==maxRetries){
                    logger.error(k + " jsonObjectGetUrl has an exception " +  a +" Max retries reached. Failing the asyncCall for productId: " + productId);
                }
            }

        } while (currentRetry < maxRetries);
    }



    @Test
    public void getSlotGamesUrlAPITest() throws JSONException {
        try {
            if (partnerConfigNum<1000) {
                Assert.assertTrue(checkGamesUrl(userID, partnerID));
            }else{
                throw new SkipException("PartnerId = " + partnerID + " Is External Partner");
            }
        } catch (Exception e) {
            logger.fatal("getUrlAPITest has an exception" + e);
            Assert.fail();
        }
    }


}
