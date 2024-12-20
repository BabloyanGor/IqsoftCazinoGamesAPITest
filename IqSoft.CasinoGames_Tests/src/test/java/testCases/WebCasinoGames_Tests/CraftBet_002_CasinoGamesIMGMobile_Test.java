package testCases.WebCasinoGames_Tests;

import kong.unirest.json.JSONException;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.awt.*;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.*;

import static pageObjects.BasePage.dateTimeNow;

public class CraftBet_002_CasinoGamesIMGMobile_Test extends BaseTest {


    public CraftBet_002_CasinoGamesIMGMobile_Test() throws AWTException {
    }


    int k = 1;
    int errCount = 1;
    CompletableFuture<Void> future;
    CompletableFuture<Void>[] futures;
    ArrayList<String> errSrces = new ArrayList<>();
    ArrayList<String> errGameNames = new ArrayList<>();
    ArrayList<String> errGameIDes = new ArrayList<>();
    ArrayList<String> errGameProviderNames = new ArrayList<>();

    public boolean checkMobileGamesImagesUrlAsync() throws JSONException, IOException {

        try {
//            if (partnerConfigNum < 1000){
//                getGamesInfo(true);
//            }
//            else{
//                getGamesInfoBO(true);
//            }
            futures = new CompletableFuture[srces.size()];
            for (int i = 0; i < srces.size(); i++) {

                int productId = Integer.parseInt(productIDs.get(i));
                String gameName = gameNames.get(i);
                String providerName = gameProviders.get(i);
                String src = srces.get(i);

                future = CompletableFuture.runAsync(() -> {
                    try {
                        AsyncCall(productId, gameName, providerName, src);
                    } catch (Exception e) {
                        // Handle exception if needed
                        e.printStackTrace();
                        future.completeExceptionally(e);
                    }
                });
                futures[i] = future;
            }
            CompletableFuture<Void> allOf = CompletableFuture.allOf(futures);
            allOf.get(asyncMaxTimeMinutes, TimeUnit.MINUTES); // This will block until all CompletableFuture are completed

        } catch (Exception e) {
            e.printStackTrace();
            for (CompletableFuture<Void> future : futures) {
                if (!future.isDone()) {
                    future.cancel(true); // Cancel incomplete futures
                }
//            future.completeExceptionally(e);
            }
        }

        //Write errors into exel shite


        logger.info("Broken GamesImages are:  " + errGameIDes.size() + " of " + srces.size());

        if (errGameIDes.size() == 0) {
            basePage.writeDataToExcel("/src/test/java/CraftBet_001_APICasinoGamesBrokenData/" + readConfig.partnerConfigNum() +
                    getGamesPartnerName + "BrokenData.xlsx", errGameIDes, errGameNames, errGameProviderNames, errSrces, "GamesBrokenImgWeb");
            isPassed = true;
        } else {
            basePage.writeDataToExcel("/src/test/java/CraftBet_001_APICasinoGamesBrokenData/" + readConfig.partnerConfigNum() +
                    getGamesPartnerName + "BrokenData.xlsx", errGameIDes, errGameNames, errGameProviderNames, errSrces, "GamesBrokenImgWeb");
            isPassed = false;
        }
        return isPassed;
    }

    public void AsyncCall(int productId, String gameName, String providerName, String src) {

        int maxRetries = 10;
        int currentRetry = 0;
        HttpURLConnection connection = null;
        do {
            try {


                if (src == null || src.isEmpty()) {
                    logger.info(dateTimeNow() + "  >>>  " + errCount + "  " + k + "  Game ID = " + productId + "   Game Provider Name = " + providerName + "  " + "Game Name = " + gameName + " :   " + ":   src = " + src + " :" + " this games image src has empty/null value");
                    errSrces.add(src);
                    errGameNames.add(gameName);
                    errGameIDes.add(String.valueOf(productId));
                    errGameProviderNames.add(providerName);
                    errCount++;
                } else {
                    int cod;
                    try {
                        URL img = new URL(src);
                        connection = (HttpURLConnection) img.openConnection();
                        connection.connect();
                        cod = connection.getResponseCode();
                        String contentType = connection.getContentType();
//                        if (cod >= 400 || !contentType.contains("image")||!contentType.contains("application/octet-stream")) {
                        if (cod >= 400) {

                            logger.error(dateTimeNow() + "  >>>  " + errCount + "  " + k + "  Game ID = " + productId + "   Game Provider Name = " + providerName + " :   " + "Game Name =  " + gameName + " :   " + "cod = " + cod + ":   src = " + src);
                            errSrces.add(src);
                            errGameNames.add(gameName);
                            errGameIDes.add(String.valueOf(productId));
                            errGameProviderNames.add(providerName);
                            errCount++;
                        }
                    } catch (Exception e) {
                        try {
                            URL img = new URL(src);
                            connection = (HttpURLConnection) img.openConnection();
                            connection.connect();
                            cod = connection.getResponseCode();
                            String contentType = connection.getContentType();
                            if (cod >= 400 || !contentType.contains("image")||!contentType.contains("application/octet-stream")) {
                                logger.error(dateTimeNow() + "  >>>  " + errCount + "  " + k + "  Game ID = " + productId + "   Game Provider Name = " + providerName + " :   " + "Game Name =  " + gameName + " :   " + "cod = " + cod + ":   src = " + src);
                                errSrces.add(src);
                                errGameNames.add(gameName);
                                errGameIDes.add(String.valueOf(productId));
                                errGameProviderNames.add(providerName);
                                errCount++;
                            }
                        } catch (Exception ee) {

                            logger.error(dateTimeNow() + "  >>>>>>>>>>>>>>>>>>  " + errCount + "  " + k + "  Game ID = " + productId + " Game Provider Name = " + providerName + " :   " + "Game Name = " + gameName + " :  " + "   src = " + src + "   " + e);
//                            errSrces.add(src);
//                            errGameNames.add(gameName);
//                            errGameIDes.add(String.valueOf(productId));
//                            errGameProviderNames.add(providerName);
//                            errCount++;
                        }

                    } finally {
                        connection.disconnect();
                    }
                    k++;
                    break;
                }

            } catch (Exception a) {
                logger.fatal("jsonObjectCheckGamesImagesURL has an exception " + a);
                future.completeExceptionally(a);
                currentRetry++;
            }
        } while (currentRetry < maxRetries);
    }

    @Test
    public void getSlotGamesMobileImgTest() throws JSONException {
        try {
//            Assert.assertTrue(getGamesAPICheckPicturesForMobile(getGamesAPIUrl, getGamesOrigin, getGamesRecurse, getGamesPartnerName));

            Assert.assertTrue(checkMobileGamesImagesUrlAsync());


        } catch (Exception e) {
            System.out.println("getUrlAPITest has an exception" + e);
            Assert.fail();
        }
    }


//    public boolean getGamesAPICheckPicturesForMobile(String getGamesAPIUrl, String origin, String recurse, String partnerName)
//            throws JSONException, IOException {
//
//        Integer gamesCount = 1;
//
//        try {
//            Unirest.setTimeouts(20000, 20000);
//            HttpResponse<String> response = Unirest.post(getGamesAPIUrl)
//                    .header("content-type", "application/json")
//                    .header("origin", origin)
//                    .body("{\"PageIndex\":0,\"PageSize\":10,\"WithWidget\":false,\"CategoryId\":null,\"ProviderIds\":null,\"IsForMobile\":false,\"Name\":\"\"," +
//                            "\"LanguageId\":\"en\",\"Token\":null,\"ClientId\":0,\"TimeZone\":4}")
//                    .asString();
//
//            logger.info("Get games Api call was sent");
//            JSONObject jsonObjectBody = new JSONObject(response.getBody());
//            JSONObject jsonObjectResponseObject = new JSONObject(jsonObjectBody.get("ResponseObject").toString());
//            gamesCount = Integer.valueOf(jsonObjectResponseObject.get("TotalGamesCount").toString());
//
//
//        } catch (Exception e) {
//            logger.info("Get Games Call for TotalGamesCount has an exception " + e);
//
//        }
//        boolean isPassed;
//        int k = 0;
//        ArrayList<String> srces = new ArrayList<>();
//        ArrayList<String> gameNames = new ArrayList<>();
//        ArrayList<String> gameIDes = new ArrayList<>();
//        ArrayList<String> gameProviderNames = new ArrayList<>();
//        ArrayList<String> errorSrcXl = new ArrayList<>();
//
//
//        int circleCount = gamesCount / getGamesOnOneCall + 1;
//        for (int m = 0; m < circleCount; m++) {
//            try {
//                Unirest.setTimeouts(20000, 20000);
//                HttpResponse<String> response = Unirest.post(getGamesAPIUrl)
//                        .header("content-type", "application/json")
//                        .header("origin", origin)
//                        .body("{\"PageIndex\":0,\"PageSize\":20000,\"WithWidget\":false,\"CategoryId\":null,\"ProviderIds\":null,\"IsForMobile\":false,\"Name\":\"\",\"LanguageId\":\"en\",\"Token\":null,\"ClientId\":0,\"TimeZone\":4}")
//
//                        .asString();
//
//                logger.info("Get games Api call was sent: " + m);
//                JSONObject jsonObjectBody = new JSONObject(response.getBody());
//                JSONObject jsonObjectResponseObject = new JSONObject(jsonObjectBody.get("ResponseObject").toString());
//                JSONArray jsonArrayGames = jsonObjectResponseObject.getJSONArray("Games");
//                logger.info("Get games Api Response was captured: " + m);
//
//                for (int j = 0; j < jsonArrayGames.length(); j++) {
//                    String first = String.valueOf(jsonArrayGames.get(j));
//                    JSONObject jsonObjectGame = new JSONObject(first);
//                    String i = jsonObjectGame.getString("i");    // Game src
//                    String n = jsonObjectGame.getString("n");    //Game Name
//                    String sp = jsonObjectGame.getString("sp");  //Provider Name
//                    String p = jsonObjectGame.get("p").toString();   //Game ID
//
//
//                    gameNames.add(n);
//                    gameProviderNames.add(sp);
//                    gameIDes.add(p);
//                    if (i.contains("http") && i.contains(" ")) {
//                        String change = i.replace(" ", "%20");
//                        srces.add(change);
//                    } else if (!i.contains("http") && !i.contains(" ")) {
//                        srces.add(recurse + i);
//                    } else if (!i.contains("http") && i.contains(" ")) {
//                        String change = recurse + i.replace(" ", "%20");
//                        srces.add(change);
//                    } else {
//                        srces.add(i);
//                    }
//                }
//            } catch (Exception e) {
//                logger.info("Get games Api Request has an Exception");
//            } finally {
//                Unirest.shutdown();
//            }
//        }
//
//        logger.info("All captured games images links was added into ArrayList: " + srces.size());
//        int count = 1;
//        HttpURLConnection connection = null;
//        for (String src : srces) {
//            if (src == null || src.isEmpty()) {
//                logger.info(count + "  " + k + "  Game ID = " + gameIDes.get(k) + "   Game Provider Name = " + gameProviderNames.get(k) + "  " + "Game Name = " + gameNames.get(k) + " :   " + ":   src = " + src + " :" + " this games image src has empty/null value");
//                errorSrcXl.add(k + "  Game ID = " + gameIDes.get(k) + "  Game Provider Name = " + gameProviderNames.get(k) + "  " + "Game Name = " + gameNames.get(k) + "  " + ":   src = " + src + " " + " ----->  this games image src has empty/null value");
//                count++;
//            } else {
//                int cod;
//                try {
//                    URL img = new URL(src);
//                    connection = (HttpURLConnection) img.openConnection();
//                    connection.connect();
//                    cod = connection.getResponseCode();
//                    String contentType = connection.getContentType();
//                    if (cod >= 400 || !contentType.contains("image")) {
//                        logger.error(count + "  " + k + "  Game ID = " + gameIDes.get(k) + "   Game Provider Name = " + gameProviderNames.get(k) + " :   " + "Game Name =  " + gameNames.get(k) + " :   " + "cod = " + cod + ":   src = " + src);
//                        errorSrcXl.add(k + "  Game ID = " + gameIDes.get(k) + "  Game Provider Name = " + gameProviderNames.get(k) + "   " + "Game Name =  " + gameNames.get(k) + "  " + "cod = " + cod + "   src = " + src);
//                        count++;
//                    }
//                } catch (Exception e) {
//                    try {
//                        URL img = new URL(src);
//                        connection = (HttpURLConnection) img.openConnection();
//                        connection.connect();
//                        cod = connection.getResponseCode();
//                        String contentType = connection.getContentType();
//                        if (cod >= 400 || !contentType.contains("image")) {
//                            logger.error(count + "  " + k + "  Game ID = " + gameIDes.get(k) + "   Game Provider Name = " + gameProviderNames.get(k) + " :   " + "Game Name =  " + gameNames.get(k) + " :   " + "cod = " + cod + ":   src = " + src);
//                            errorSrcXl.add(k + "  Game ID = " + gameIDes.get(k) + "  Game Provider Name = " + gameProviderNames.get(k) + "   " + "Game Name =  " + gameNames.get(k) + "  " + "cod = " + cod + "   src = " + src);
//                            count++;
//                        }
//                    } catch (Exception ee) {
//
//                        logger.error(count + "  " + k + "  Game ID = " + gameIDes.get(k) + " Game Provider Name = " + gameProviderNames.get(k) + " :   " + "Game Name = " + gameNames.get(k) + " :  " + "   src = " + src + "   " + e);
//                        errorSrcXl.add(k + "  Game ID = " + gameIDes.get(k) + "  Game Provider Name = " + gameProviderNames.get(k) + "   " + "Game Name =  " + gameNames.get(k) + "  " + "src = " + src);
//                        count++;
//                    }
//
//                } finally {
//                    connection.disconnect();
//                }
//                k++;
//            }
//
//
//        }
//        logger.info("Broken images are:  " + errorSrcXl.size());
//        if (errorSrcXl.size() == 0) {
//            basePage.writeInExel(errorSrcXl, "/src/test/java/CraftBet_001_APICasinoGamesBrokenData/" + readConfig.partnerConfigNum() + partnerName + "BrokenData.xlsx", "GamesBrokenImgWeb");
//            isPassed = true;
//        } else {
//            basePage.writeInExel(errorSrcXl, "/src/test/java/CraftBet_001_APICasinoGamesBrokenData/" + readConfig.partnerConfigNum() + partnerName + "BrokenData.xlsx", "GamesBrokenImgWeb");
//            isPassed = false;
//        }
//        return isPassed;
//    }
//
//
//    public boolean getGamesAPICCheckPicturesForMobileParallel(String getGamesAPIUrl, String origin, String recurse, String partnerName)
//            throws JSONException, IOException {
//
//        Integer gamesCount=1;
//        try {
//            Unirest.setTimeouts(20000, 20000);
//            HttpResponse<String> response = Unirest.post(getGamesAPIUrl)
//                    .header("content-type", "application/json")
//                    .header("origin", origin)
//                    .body("{\"PageIndex\":0,\"PageSize\":10,\"WithWidget\":false,\"CategoryId\":null,\"ProviderIds\":null,\"IsForMobile\":true,\"Name\":\"\"," +
//                            "\"LanguageId\":\"en\",\"Token\":null,\"ClientId\":0,\"TimeZone\":4}")
//                    .asString();
//
//            logger.info("Get games Api call was sent");
//            JSONObject jsonObjectBody = new JSONObject(response.getBody());
//            JSONObject jsonObjectResponseObject = new JSONObject(jsonObjectBody.get("ResponseObject").toString());
//            gamesCount = Integer.valueOf(jsonObjectResponseObject.get("TotalGamesCount").toString());
//
//
//        }catch (Exception e){
//            logger.info("Get Games Call for TotalGamesCount has an exception " + e);
//
//        }
//
//
//
//
//        boolean isPassed;
//        ArrayList<String> srces = new ArrayList<>();
//        ArrayList<String> gameNames = new ArrayList<>();
//        ArrayList<String> gameIDes = new ArrayList<>();
//        ArrayList<String> gameProviderNames = new ArrayList<>();
//        ArrayList<String> errorSrcXl = new ArrayList<>();
//
//        int circleCount = gamesCount/getGamesOnOneCall + 1;
//        for (int m =0; m<circleCount;m++){
//
//            try {
//                Unirest.setTimeouts(20000, 20000);
//                HttpResponse<String> response = Unirest.post(getGamesAPIUrl)
//                        .header("content-type", "application/json")
//                        .header("origin", origin)
//                        .body("{\"PageIndex\":" +
//                                m +
//                                ",\"PageSize\":" +
//                                getGamesOnOneCall+
//                                ",\"WithWidget\":false,\"CategoryId\":null,\"ProviderIds\":null,\"IsForMobile\":true,\"Name\":\"\"," +
//                                "\"LanguageId\":\"en\",\"Token\":null,\"ClientId\":0,\"TimeZone\":4}")
//                        .asString();
//
//                JSONObject jsonObjectBody = new JSONObject(response.getBody());
//                JSONObject jsonObjectResponseObject = new JSONObject(jsonObjectBody.get("ResponseObject").toString());
//                JSONArray jsonArrayGames = jsonObjectResponseObject.getJSONArray("Games");
//                logger.info("Get games Api Response was captured: "+ m);
//
//                for (int j = 0; j < jsonArrayGames.length(); j++) {
//                    String first = String.valueOf(jsonArrayGames.get(j));
//                    JSONObject jsonObjectGame = new JSONObject(first);
//                    String i = jsonObjectGame.getString("i");    // Game src
//                    String n = jsonObjectGame.getString("n");    //Game Name
//                    String sp = jsonObjectGame.getString("sp");  //Provider Name
//                    String p = jsonObjectGame.get("p").toString();   //Game ID
//
//
//                    gameNames.add(n);
//                    gameProviderNames.add(sp);
//                    gameIDes.add(p);
//                    if (i.contains("http") && i.contains(" ")) {
//                        String change = i.replace(" ", "%20");
//                        srces.add(change);
//                    } else if (!i.contains("http") && !i.contains(" ")) {
//                        srces.add(recurse + i);
//                    } else if (!i.contains("http") && i.contains(" ")) {
//                        String change = recurse + i.replace(" ", "%20");
//                        srces.add(change);
//                    } else {
//                        srces.add(i);
//                    }
//                }
//            } catch (Exception e) {
//                logger.info("Get games Api Request has an Exception");
//            } finally {
//                Unirest.shutdown();
//            }
//
//        }
//
//
//
//        logger.info("All captured games images links was added into ArrayList: " + srces.size());
//        final int[] count = {1};
//
//        srces.parallelStream().forEach(src -> {
//            int srcIndex;
//            if (src == null || src.isEmpty()) {
//                srcIndex = srces.indexOf(src);
//                logger.info(count[0] + "  " + srcIndex + "  Game ID = " + gameIDes.get(srcIndex) + "   Game Provider Name = " + gameProviderNames.get(srcIndex) + "  " + "Game Name = " + gameNames.get(srcIndex) + " :   " + ":   src = " + src);
//                errorSrcXl.add(srcIndex + "  Game ID = " + gameIDes.get(srcIndex) + "  Game Provider Name = " + gameProviderNames.get(srcIndex) + "  " + "Game Name = " + gameNames.get(srcIndex) + "  " + ":   src = " + src);
//                count[0]++;
//            } else {
//                int cod;
//                HttpURLConnection connection = null;
//                try {
//                    URL img = new URL(src);
//                    connection = (HttpURLConnection) img.openConnection();
//                    connection.connect();
//                    cod = connection.getResponseCode();
//                    String contentType = connection.getContentType();
//                    if (cod >= 400 || !contentType.contains("image")) {
//                        srcIndex = srces.indexOf(src);
//                        logger.info(count[0] + "  " + srcIndex + "  Game ID = " + gameIDes.get(srcIndex) + "   Game Provider Name = " + gameProviderNames.get(srcIndex) + "  " + "Game Name = " + gameNames.get(srcIndex) + "  cod  : " + cod + "   src = " + src);
//                        errorSrcXl.add(count[0] + "  " + srcIndex + "  Game ID = " + gameIDes.get(srcIndex) + "  Game Provider Name = " + gameProviderNames.get(srcIndex) + "  " + "Game Name = " + gameNames.get(srcIndex) + "  cod  : " + cod + "   src = " + src);
//                        count[0]++;
//                    }
//                } catch (Exception e) {
//                    try {
//                        URL img = new URL(src);
//                        connection = (HttpURLConnection) img.openConnection();
//                        connection.connect();
//                        cod = connection.getResponseCode();
//                        String contentType = connection.getContentType();
//                        if (cod >= 400 || !contentType.contains("image")) {
//                            srcIndex = srces.indexOf(src);
//                            logger.info(count[0] + "  " + srcIndex + "  Game ID = " + gameIDes.get(srcIndex) + "   Game Provider Name = " + gameProviderNames.get(srcIndex) + "  " + "Game Name = " + gameNames.get(srcIndex) + " :   " + ":   src = " + src);
//                            errorSrcXl.add(count[0] + "  " + srcIndex + "  Game ID = " + gameIDes.get(srcIndex) + "  Game Provider Name = " + gameProviderNames.get(srcIndex) + "  " + "Game Name = " + gameNames.get(srcIndex) + "  " + ":   src = " + src);
//                            count[0]++;
//                        }
//                    } catch (Exception e1) {
//                        srcIndex = srces.indexOf(src);
//                        logger.info(count[0] + " HAS AN EXCEPTION " + "  Game ID = " + gameIDes.get(srcIndex) + "   Game Provider Name = " + gameProviderNames.get(srcIndex) + "  " + "Game Name = " + gameNames.get(srcIndex) + " :   " + ":   src = " + src + "  " + e);
//                        count[0]++;
//                    } finally {
//                        connection.disconnect();
//                    }
//
//
//                } finally {
//                    connection.disconnect();
//                }
//            }
//        });
//
//
//        logger.info("Broken images are:  " + errorSrcXl.size());
//        if (errorSrcXl.size() == 0) {
//            basePage.writeInExel(errorSrcXl, "/src/test/java/CraftBet_001_APICasinoGamesBrokenData/" + readConfig.partnerConfigNum() + partnerName + "BrokenData.xlsx", "GamesBrokenImgWeb");
//            isPassed = true;
//        } else {
//            basePage.writeInExel(errorSrcXl, "/src/test/java/CraftBet_001_APICasinoGamesBrokenData/" + readConfig.partnerConfigNum() + partnerName + "BrokenData.xlsx", "GamesBrokenImgWeb");
//            isPassed = false;
//        }
//        return isPassed;
//    }


//    public void asyncExample() throws Exception {
//        ExecutorService executorService = Executors.newSingleThreadExecutor();
//
//        Future<?> future = executorService.submit(() -> {
//            // Your asynchronous task here
//            System.out.println("Async task completed");
//        });
//
//        // Do other work here
//
//        // Block and wait for the asynchronous task to complete
//        future.get();
//
//        executorService.shutdown();
//    }
//
//
//    public void async() throws ExecutionException, InterruptedException {
//        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
//            // Your asynchronous task here
//            System.out.println("Async task completed");
//        });
//
//        // Do other work here
//
//        // Block and wait for the asynchronous task to complete
//        future.get();
//    }


}
