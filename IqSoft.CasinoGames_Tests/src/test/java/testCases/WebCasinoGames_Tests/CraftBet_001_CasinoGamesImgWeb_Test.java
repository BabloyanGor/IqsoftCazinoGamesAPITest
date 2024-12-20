package testCases.WebCasinoGames_Tests;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.json.JSONException;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.awt.*;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static pageObjects.BasePage.dateTimeNow;

public class CraftBet_001_CasinoGamesImgWeb_Test extends BaseTest {


    public CraftBet_001_CasinoGamesImgWeb_Test() throws AWTException {
    }

    private int k = 1;
    private int errCount = 1;
    //    CompletableFuture<Void> future;
    private CompletableFuture<Void>[] futures;

    private int i = 0;


    ArrayList<String> errSrces = new ArrayList<>();
    ArrayList<String> errGameNames = new ArrayList<>();
    ArrayList<String> errGameIDes = new ArrayList<>();
    ArrayList<String> errGameProviderNames = new ArrayList<>();
    private boolean checkWebGamesImagesUrlAsync() throws JSONException, IOException {

        try {
            Unirest.shutDown();
            futures = new CompletableFuture[srces.size()];
            for (; i < srces.size(); i++) {

                int productId = Integer.parseInt(productIDs.get(i));
                String gameName = gameNames.get(i);
                String providerName = gameProviders.get(i);
                String src = srces.get(i);

                futures[i] = CompletableFuture.runAsync(() -> {
                    try {
                        AsyncCall(productId, gameName, providerName, src);
                    } catch (Exception e) {
                        // Handle exception if needed
                        e.printStackTrace();
                        futures[i].completeExceptionally(e);
                    }
                });
//                futures[i] = future;
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
            basePage.writeDataToExcel("/src/test/java/CraftBet_001_APICasinoGamesBrokenData/" + readConfig.partnerConfigNum()+
                    getGamesPartnerName + "BrokenData.xlsx",  errGameIDes,errGameNames,errGameProviderNames,errSrces,"GamesBrokenImgWeb");
            isPassed = true;
        } else {
            basePage.writeDataToExcel("/src/test/java/CraftBet_001_APICasinoGamesBrokenData/" + readConfig.partnerConfigNum()+
                    getGamesPartnerName + "BrokenData.xlsx",  errGameIDes,errGameNames,errGameProviderNames,errSrces,"GamesBrokenImgWeb");
            isPassed = false;
        }
        return isPassed;
    }





    private void AsyncCall(int productId, String gameName, String providerName, String src) {

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
                        if (!src.endsWith(".bin")){
                        URL img = new URL(src);
                        connection = (HttpURLConnection) img.openConnection();
                        connection.connect();
                        cod = connection.getResponseCode();
                        String contentType = connection.getContentType();
                        if (cod >= 400 || !contentType.contains("image")) {
                            logger.error(dateTimeNow() + "  >>>  " + errCount + "  " + k + "  Game ID = " + productId + "   Game Provider Name = " + providerName + " :   " + "Game Name =  " + gameName + " :   " + "cod = " + cod + ":   src = " + src);

                            errSrces.add(src);
                            errGameNames.add(gameName);
                            errGameIDes.add(String.valueOf(productId));
                            errGameProviderNames.add(providerName);

                            errCount++;
                        }}
                    } catch (Exception e) {
                        try {
                            if (!src.endsWith(".bin")){

                                URL img = new URL(src);
                            connection = (HttpURLConnection) img.openConnection();
                            connection.connect();
                            cod = connection.getResponseCode();
                            String contentType = connection.getContentType();
                            if (cod >= 400 || !contentType.contains("image")) {
                                logger.error(dateTimeNow() + "  >>>  " + errCount + "  " + k + "  Game ID = " + productId + "   Game Provider Name = " + providerName + " :   " + "Game Name =  " + gameName + " :   " + "cod = " + cod + ":   src = " + src);

                                errSrces.add(src);
                                errGameNames.add(gameName);
                                errGameIDes.add(String.valueOf(productId));
                                errGameProviderNames.add(providerName);

                                errCount++;
                            }}
                        } catch (Exception ee) {

                            logger.error( "  >>>>>>>>>>>>>>>>>  " +  "  " + k + "  Game ID = " + productId + " Game Provider Name = " + providerName + " :   " + "Game Name = " + gameName + " :  " + "   src = " + src + "   " + e);
//
//                            errSrces.add(src);
//                            errGameNames.add(gameName);
//                            errGameIDes.add(String.valueOf(productId));
//                            errGameProviderNames.add(providerName);
//
//                            errCount++;
                        }

                    } finally {
                        if (connection != null){
                            connection.disconnect();}
                    }
                    k++;
                    break;
                }

            } catch (Exception a) {
                logger.fatal("jsonObjectCheckGamesImagesURL has an exception " + a);
                futures[i].completeExceptionally(a);
                currentRetry++;
            }
        } while (currentRetry < maxRetries);
    }






    @Test
    public void getSlotGamesWebImgTest() throws JSONException {
        try {
                Assert.assertTrue(checkWebGamesImagesUrlAsync());
                //            Assert.assertTrue(getGamesAPICheckPictures(getGamesAPIUrl, getGamesOrigin, getGamesRecurse, getGamesPartnerName));


        } catch (Exception e) {
            logger.fatal("checkGamesImagesUrlAsync() has an exception" + e);
            Assert.fail();
        }

    }




















//    @Test
//    public void test() throws JSONException, IOException {
//        logger = Logger.getLogger("craftBetWorld");
//        PropertyConfigurator.configure( System.getProperty("user.dir") + "\\Log4j.properties");
//        UrlAsync();
//
//    }
//
//
//    private boolean UrlAsync() throws JSONException, IOException {
//
//        try {
//            Unirest.shutDown();
//            futures = new CompletableFuture[3];
//            for (; i < 3; i++) {
//
//
//                int finalI = i;
//                futures[i] = CompletableFuture.runAsync(() -> {
//                    try {
//                        AsyncCall();
//                    } catch (Exception e) {
//                        // Handle exception if needed
//                        e.printStackTrace();
//                        futures[finalI].completeExceptionally(e);
//                    }
//                });
////                futures[i] = future;
//            }
//            CompletableFuture<Void> allOf = CompletableFuture.allOf(futures);
//            allOf.get(asyncMaxTimeMinutes, TimeUnit.MINUTES); // This will block until all CompletableFuture are completed
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            for (CompletableFuture<Void> future : futures) {
//                if (!future.isDone()) {
//                    future.cancel(true); // Cancel incomplete futures
//                }
////            future.completeExceptionally(e);
//            }
//        }
//
//        //Write errors into exel shite
//
//        logger.info("Broken GamesImages are:  " + errGameIDes.size() + " of " + srces.size());
//
//        if (errGameIDes.size() == 0) {
//            basePage.writeDataToExcel("/src/test/java/CraftBet_001_APICasinoGamesBrokenData/" + readConfig.partnerConfigNum()+
//                    getGamesPartnerName + "BrokenData.xlsx",  errGameIDes,errGameNames,errGameProviderNames,errSrces,"GamesBrokenImgWeb");
//            isPassed = true;
//        } else {
//            basePage.writeDataToExcel("/src/test/java/CraftBet_001_APICasinoGamesBrokenData/" + readConfig.partnerConfigNum()+
//                    getGamesPartnerName + "BrokenData.xlsx",  errGameIDes,errGameNames,errGameProviderNames,errSrces,"GamesBrokenImgWeb");
//            isPassed = false;
//        }
//        return isPassed;
//    }
//
//
//    private void AsyncCall() {
//
//        try{
//        Unirest.config().reset();
//        Unirest.config().connectTimeout(60000);
//        Unirest.config().socketTimeout(60000);
//        HttpResponse<String> response = Unirest.post("https://websitewebapi.craftbet.com/1/api/Main/LoginClient")
//                .header("Origin", "https://craftbet.com")
//                .header("Content-Type", "application/json")
//                .body("{\"Data\":\"MZ5E1NPdkt/atqAIQBfzo+Rr+kX+8LxQxUNrZOiG+BwVoYjs+l4sCpWrKA86FnVUt/x06yehSGHnq8Qiwr2mHPUkFaCjj9hHMYpBxNOTa5tAb/qq2Dr2qRBwzyJqNgKm52OR+n3Gtf0qUTP6s3/pO0BoK2IhfOjU1ynxRaqNS0djH5fkWY8okKUrw6LJkn01QkzT8gau641aXKPNz/lVWKzE8TyrT2/KpLpTUiuP0ims10aC5q7yX1J8SmL1iR0ou4FdKTS/O9BAla4o0+A9R0dfp4tlnmLwg+XbODPUYKiknt0jdWA3Elnc4KYawoNYmIM8fKODJFzagGI0PiP50g==\"}")
//                .asString();
//        System.out.println(response.getBody());
//            } catch (Exception a) {
//                logger.fatal("jsonObjectCheckGamesImagesURL has an exception " + a);
//                futures[i].completeExceptionally(a);
//
//            }
//        }








    }


//    public void writeDataToExcel(String fileName, ArrayList<String> productId, ArrayList<String> gameName, ArrayList<String> providerName, ArrayList<String> src) {
//        Workbook workbook = new XSSFWorkbook();
//        Sheet sheet = workbook.createSheet("Products");
//
//        // Header Row
//        Row headerRow = sheet.createRow(0);
//        headerRow.createCell(0).setCellValue("Product ID");
//        headerRow.createCell(1).setCellValue("Game Name");
//        headerRow.createCell(2).setCellValue("Provider Name");
//        headerRow.createCell(3).setCellValue("Src");
//
//        // Write data
//        for (int i = 0; i < errGameIDes.size(); i++) {
//            Row row = sheet.createRow(i + 1);
//            row.createCell(0).setCellValue(errGameIDes.get(i));
//            row.createCell(1).setCellValue(errGameNames.get(i));
//            row.createCell(2).setCellValue(errGameProviderNames.get(i));
//            row.createCell(3).setCellValue(errSrces.get(i));
//        }
//
//        // Auto-size columns
//        for (int i = 0; i < 4; i++) {
//            sheet.autoSizeColumn(i);
//        }
//
//        // Write the output to a file
//        try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
//            workbook.write(fileOut);
//            System.out.println("Excel file created successfully: " + fileName);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                workbook.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }














//    public boolean getGamesAPICheckPictures(String getGamesAPIUrl, String origin, String recurse, String partnerName)
//            throws JSONException, IOException {
//
//        Integer gamesCount = 1;
//
//        try {
//            Unirest.setTimeouts(0, 0);
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
//        int circleCount = gamesCount / getGamesOnOneCall + 1;
//        for (int m = 0; m < circleCount; m++) {
//            try {
//                Unirest.setTimeouts(0, 0);
//                HttpResponse<String> response = Unirest.post(getGamesAPIUrl)
//                        .header("content-type", "application/json")
//                        .header("origin", origin)
//                        .body("{\"PageIndex\":" +
//                                m +
//                                ",\"PageSize\":" +
//                                gamesCount +
//                                ",\"WithWidget\":false,\"CategoryId\":null,\"ProviderIds\":null,\"IsForMobile\":false,\"Name\":\"\"," +
//                                "\"LanguageId\":\"en\",\"Token\":null,\"ClientId\":0,\"TimeZone\":4}")
//
//                        .asString();
//
//
//                JSONObject jsonObjectBody = new JSONObject(response.getBody());
//                JSONObject jsonObjectResponseObject = new JSONObject(jsonObjectBody.get("ResponseObject").toString());
//                JSONArray jsonArrayGames = jsonObjectResponseObject.getJSONArray("Games");
//                logger.info("Get games Api Response was captured: " + m + 1);
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
//
//                errSrces.add(src);
//                errGameNames.add( gameNames.get(k));
//                errGameIDes.add(gameIDes.get(k));
//                errGameProviderNames.add( gameProviderNames.get(k));
//                count++;
//
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
//
//
//
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
//
//
//
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
//    public boolean getGamesAPICCheckPicturesParallel(String getGamesAPIUrl, String origin, String recurse, String partnerName)
//            throws JSONException, IOException {
//
//        Integer gamesCount = 1;
//        try {
//            Unirest.setTimeouts(0, 0);
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
//
//
//        boolean isPassed;
//        ArrayList<String> srces = new ArrayList<>();
//        ArrayList<String> gameNames = new ArrayList<>();
//        ArrayList<String> gameIDes = new ArrayList<>();
//        ArrayList<String> gameProviderNames = new ArrayList<>();
//        ArrayList<String> errorSrcXl = new ArrayList<>();
//
//        int circleCount = gamesCount / getGamesOnOneCall + 1;
//        for (int m = 0; m < circleCount; m++) {
//
//            try {
//                Unirest.setTimeouts(0, 0);
//                HttpResponse<String> response = Unirest.post(getGamesAPIUrl)
//                        .header("content-type", "application/json")
//                        .header("origin", origin)
//                        .body("{\"PageIndex\":" +
//                                m +
//                                ",\"PageSize\":" +
//                                getGamesOnOneCall +
//                                ",\"WithWidget\":false,\"CategoryId\":null,\"ProviderIds\":null,\"IsForMobile\":false,\"Name\":\"\"," +
//                                "\"LanguageId\":\"en\",\"Token\":null,\"ClientId\":0,\"TimeZone\":4}")
//                        .asString();
//
//                JSONObject jsonObjectBody = new JSONObject(response.getBody());
//                JSONObject jsonObjectResponseObject = new JSONObject(jsonObjectBody.get("ResponseObject").toString());
//                JSONArray jsonArrayGames = jsonObjectResponseObject.getJSONArray("Games");
//                logger.info("Get games Api Response was captured: " + m + 1);
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
//        logger.info("All captured games images links was added into ArrayList: " + srces.size());
//        final int[] count = {1};
//
//        srces.parallelStream().forEach(src -> {
//            int srcIndex;
//            if (src == null || src.isEmpty()) {
//                srcIndex = srces.indexOf(src);
//                logger.info(count[0] + "  " + srcIndex + "  Game ID = " + gameIDes.get(srcIndex) + "   Game Provider Name = " + gameProviderNames.get(srcIndex) + "  " + "Game Name = " + gameNames.get(srcIndex) + " :   " + ":   src = " + src);
//                errorSrcXl.add(srcIndex + "  Game ID = " + gameIDes.get(srcIndex) + "  Game Provider Name = " + gameProviderNames.get(srcIndex) + "  " + "Game Name = " + gameNames.get(srcIndex) + "  " + ":   src = " + src);
//
//
//
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
















//public class ExcelWriter {
//
//    public static void main(String[] args) {
//        // Sample data
//        List<Integer> productId = List.of(1, 2, 3, 4);
//        List<String> gameName = List.of("Game1", "Game2", "Game3", "Game4");
//        List<String> providerName = List.of("Provider1", "Provider2", "Provider3", "Provider4");
//        List<String> src = List.of("Src1", "Src2", "Src3", "Src4");
//
//        writeDataToExcel("Products.xlsx", productId, gameName, providerName, src);
//    }
//
//    public static void writeDataToExcel(String fileName, List<Integer> productId, List<String> gameName, List<String> providerName, List<String> src) {
//        Workbook workbook = new XSSFWorkbook();
//        Sheet sheet = workbook.createSheet("Products");
//
//        // Header Row
//        Row headerRow = sheet.createRow(0);
//        headerRow.createCell(0).setCellValue("Product ID");
//        headerRow.createCell(1).setCellValue("Game Name");
//        headerRow.createCell(2).setCellValue("Provider Name");
//        headerRow.createCell(3).setCellValue("Src");
//
//        // Write data
//        for (int i = 0; i < productId.size(); i++) {
//            Row row = sheet.createRow(i + 1);
//            row.createCell(0).setCellValue(productId.get(i));
//            row.createCell(1).setCellValue(gameName.get(i));
//            row.createCell(2).setCellValue(providerName.get(i));
//            row.createCell(3).setCellValue(src.get(i));
//        }
//
//        // Auto-size columns
//        for (int i = 0; i < 4; i++) {
//            sheet.autoSizeColumn(i);
//        }
//
//        // Write the output to a file
//        try (FileOutputStream fileOut = new FileOutputStream(fileName)) {
//            workbook.write(fileOut);
//            System.out.println("Excel file created successfully: " + fileName);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                workbook.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//}



























