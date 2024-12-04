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
import java.util.List;
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
                    .body("{\"Data\":\"tyjYAzl8thEqNNprRDpUjeV1xZ3WxNpZAmkklQ+vQXnNASx8mquFFIPMq+CLQtmPUMI8u1M+kLdxjD76OTHNbRprUAPj3HD9G/8NH546lmM8w+jTQIIsoP0zyEP3B6yXgw7BMzpNIRuE/PgmmNhIkcpc8T676pz3JJO+kZIs4HrWOtda4N/SJfFJIuc3/aXMRxh/MRAGyiJ2uEbs43wJRHMutep7m+Q8KuVJONniQGSjZvj9CKEMEMICIA6i8khA91dK6x2qQhW3FEv5ip6nIwCiJaB8qr/SvQUwhkFYM6vvAsdGn96KUvZBJ3LJo9erHIDuUSh5wAjY2H2Mqjv92w==\",\"TimeZone\":4}")
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


    ArrayList<String> errSrces = new ArrayList<>();
    ArrayList<String> errGameNames = new ArrayList<>();
    ArrayList<String> errGameIDes = new ArrayList<>();
    ArrayList<String> errGameProviderNames = new ArrayList<>();


    private int k = 1;
    private int errCount = 1;



    @Test
    public void getSlotGamesUrlAPITest() throws JSONException {
        try {
            if (partnerConfigNum < 1000) {
                Assert.assertTrue(checkGamesUrl(userID, partnerID));
            } else {
                throw new SkipException("PartnerId = " + partnerID + " Is External Partner");
            }
        } catch (Exception e) {
            logger.fatal("getUrlAPITest has an exception" + e);
            Assert.fail();
        }
    }


    private boolean checkGamesUrl(int userID, int partnerId) throws JSONException, IOException {
        Unirest.config()
                .reset()
                .connectTimeout(120000)
                .socketTimeout(120000)
                .concurrency(100, 5) // Adjust thread pool size
                .enableCookieManagement(false);

        int countAsyncEveryCycle = 100;
        int batchCount = (int) Math.ceil((double) productIDs.size() / countAsyncEveryCycle);


        for (int batch = 0; batch < batchCount; batch++) {

            List<CompletableFuture<HttpResponse<JsonNode>>> batchFutures = new ArrayList<>();

            for (int i = 0; i < countAsyncEveryCycle; i++) {
                int requestId = batch * countAsyncEveryCycle + i;
                if (productIDs.size()<=requestId){
                    break;
                }
                // Retrieve productId, gameName, and providerName
                int productId = Integer.parseInt(productIDs.get(requestId));
                String gameName = gameNames.get(requestId);
                String providerName = gameProviders.get(requestId);

                // Construct JSON body safely
                JSONObject requestBody = new JSONObject();
                requestBody.put("IsForMobile", false);
                requestBody.put("LanguageId", "en");
                requestBody.put("PartnerId", partnerId);
                requestBody.put("ProductId", productId);
                requestBody.put("Position", "casino");
                requestBody.put("Token", token);
                requestBody.put("ClientId", userID);
                requestBody.put("IsForDemo", false);
                requestBody.put("TimeZone", 4);

                // Create async HTTP request
                CompletableFuture<HttpResponse<JsonNode>> future = Unirest.post(getURLAPIUrl)
                        .header("Content-Type", "application/json")
                        .header("origin", getGamesOrigin)
                        .body(requestBody)
                        .asJsonAsync()
                        .thenApply(response -> {
                            JSONObject jsonObject = response.getBody().getObject();

                            String code = jsonObject.optString("ResponseCode");
                            String description = jsonObject.optString("Description");
                            String url = jsonObject.optString("ResponseObject");

                            if (!code.equals("0") || url == null || url.length() < 20 || !url.contains("https://")) {
                                String errMessage = dateTimeNow() + " ID = " + productId + " GameName: " + gameName
                                        + " ProviderName: " + providerName + "  ResponseCode = " + code
                                        + " description = " + description + " ResponseObject = " + url;

                                synchronized (this) {
                                    errSrces.add("ResponseCode = " + code + "  Description = " + description + "  ResponseObject = " + url);
                                    errGameNames.add(gameName);
                                    errGameIDes.add(String.valueOf(productId));
                                    errGameProviderNames.add(providerName);
                                    errorSrcXl.add(errMessage);
                                    errCount++;
                                }
                            }
                            // Process response
                            return response;
                        })
                        .exceptionally(ex -> {
                            System.err.println("Request " + requestId + " failed: " + ex.getMessage());
                            retryRequest(requestBody); // Retry on failure
                            return null;
                        });


                batchFutures.add(future);
            }

            CompletableFuture.allOf(batchFutures.toArray(new CompletableFuture[0])).join();
            System.out.println("Batch " + (batch + 1) + " completed.   " + dateTimeNow());

        }

        Unirest.shutDown();

        // Write errors to Excel
        String filePath = "/src/test/java/CraftBet_001_APICasinoGamesBrokenData/" + readConfig.partnerConfigNum() + getGamesPartnerName + "BrokenData.xlsx";
        basePage.writeDataToExcel(filePath, errGameIDes, errGameNames, errGameProviderNames, errSrces, "GamesBrokenURL");

        isPassed = errGameIDes.isEmpty();
        return isPassed;
    }

    private HttpResponse<JsonNode> retryRequest(JSONObject requestBody) {
        for (int attempt = 1; attempt <= 3; attempt++) {
            try {
                HttpResponse<JsonNode> response = Unirest.post(getURLAPIUrl)
                        .header("Content-Type", "application/json")
                        .header("origin", getGamesOrigin)
                        .body(requestBody)
                        .asJson();
                return response; // Return response if successful
            } catch (Exception e) {
                System.err.println("Retry " + attempt + " failed: " + e.getMessage());
            }
        }
        return null; // Return null after max retries
    }





}
