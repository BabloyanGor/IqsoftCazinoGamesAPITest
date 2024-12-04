package testCases.WebCasinoGames_Tests;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONException;
import kong.unirest.json.JSONObject;

import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;

import java.awt.*;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class CraftBet_004_CasinoGamesProvidersImgWeb_Test extends BaseTest {

    public CraftBet_004_CasinoGamesProvidersImgWeb_Test() throws AWTException {
    }


    ArrayList<String> errSrces = new ArrayList<>();
    ArrayList<String> errGameNames = new ArrayList<>();
    ArrayList<String> errGameIDes = new ArrayList<>();
    ArrayList<String> errGameProviderNames = new ArrayList<>();


    public boolean getGamesAPICheckProvidersPictures(String getGamesAPIUrl, String origin, String partnerName)
            throws JSONException, IOException {

        boolean isPassed;
        int k = 0;
        ArrayList<String> providerIDes = new ArrayList<>();
        ArrayList<String> providerNames = new ArrayList<>();
        ArrayList<String> providersGameCount = new ArrayList<>();
        ArrayList<String> providersOrder = new ArrayList<>();
        ArrayList<String> srces = new ArrayList<>();

        try {
            Unirest.config().reset();
            Unirest.config().connectTimeout(60000);
            Unirest.config().socketTimeout(60000);
            HttpResponse<String> response = Unirest.post(getGamesAPIUrl)
                    .header("content-type", "application/json")
                    .header("origin", origin)
                    .body("{\"PageIndex\":0,\"PageSize\":10,\"WithWidget\":false,\"CategoryId\":null,\"ProviderIds\":null,\"IsForMobile\":false," +
                            "\"Name\":\"\",\"LanguageId\":\"en\",\"Token\":null,\"ClientId\":0,\"TimeZone\":4}")
                    .asString();

            logger.info("Get games Api call was sent");
            JSONObject jsonObjectBody = new JSONObject(response.getBody());
            logger.info("Get games Api Response was captured");
            JSONObject jsonObjectResponseObject = new JSONObject(jsonObjectBody.get("ResponseObject").toString());
            JSONArray jsonArrayProviders = jsonObjectResponseObject.getJSONArray("Providers");


            for (int j = 0; j < jsonArrayProviders.length(); j++) {
                String provider = String.valueOf(jsonArrayProviders.get(j));
                JSONObject jsonObjectGame = new JSONObject(provider);
                String Id = jsonObjectGame.get("Id").toString();    // Provider ID
                String Name = jsonObjectGame.get("Name").toString();    //Provider Name
                String GamesCount = jsonObjectGame.get("GamesCount").toString();  //Provider games count
                String Order = jsonObjectGame.get("Order").toString();   //Provider ID

                providerIDes.add(Id);
                providerNames.add(Name);
                providersGameCount.add(GamesCount);
                providersOrder.add(Order);
                srces.add(getGamesBaseURL + "/assets/images/providers/" + Id + ".png");
                // https://craftbet.com/assets/images/providers/1150.png
            }
        } catch (Exception e) {
            logger.info("Get games Api Response has an Exception: " + e);
        } finally {
            Unirest.shutDown();
        }


        logger.info("All captured providers data  was added into ArrayLists Count: " + providerNames.size());
        int count = 1;
        for (String src : srces) {
            int cod;
            HttpURLConnection connection = null;
            try {
                URL img = new URL(src);
                connection = (HttpURLConnection) img.openConnection();
                connection.connect();
                cod = connection.getResponseCode();
                String contentType = connection.getContentType();
                if (cod >= 400 || !contentType.contains("image")) {
                    logger.error(count + "  Provider ID = " + providerIDes.get(k) + "   Provider Name = " + providerNames.get(k) + " :   " + "cod = " + cod + ":   src = " + src);

                    errSrces.add(src);
                    errGameNames.add(String.valueOf(cod));
                    errGameIDes.add(String.valueOf(providerIDes.get(k) ));
                    errGameProviderNames.add(providerNames.get(k) );


                }
            } catch (Exception e) {
                try {
                    URL img = new URL(src);
                    connection = (HttpURLConnection) img.openConnection();
                    connection.connect();
                    cod = connection.getResponseCode();
                    String contentType = connection.getContentType();
                    if (cod >= 400 || !contentType.contains("image")) {
                        logger.error(count + "  Provider ID = " + providerIDes.get(k) + "   Provider Name = " + providerNames.get(k) + " :   " + "cod = " + cod + ":   src = " + src);

                        errSrces.add(src);
                        errGameNames.add(String.valueOf(cod));
                        errGameIDes.add(String.valueOf(providerIDes.get(k) ));
                        errGameProviderNames.add(providerNames.get(k) );

                    }
                } catch (Exception ee) {

                    logger.error(count + "  Provider ID = " + providerIDes.get(k) + "   Provider Name = " + providerNames.get(k) + " :   " + ":   src = " + src);
                    errorSrcXl.add(count + "  Provider ID = " + providerIDes.get(k) + "   Provider Name = " + providerNames.get(k) + " :   " + ":   src = " + src);
                }
            }
            finally {
                try {
                    connection.disconnect();
                }
                catch (Exception e){}
            }
            k++;
            count++;
        }
        logger.info("Broken Providers images are:  " + errSrces.size());

        if (errSrces.size() == 0) {
            basePage.writeDataToExcel("/src/test/java/CraftBet_001_APICasinoGamesBrokenData/" + readConfig.partnerConfigNum() + partnerName + "BrokenData.xlsx",  errGameIDes,errGameNames,errGameProviderNames,errSrces,"GamesProvidersBrokenImgWeb");
            isPassed = true;
        } else {
            basePage.writeDataToExcel("/src/test/java/CraftBet_001_APICasinoGamesBrokenData/" + readConfig.partnerConfigNum() + partnerName + "BrokenData.xlsx",  errGameIDes,errGameNames,errGameProviderNames,errSrces,"GamesProvidersBrokenImgWeb");
            isPassed = false;
        }


        return isPassed;
    }


    @Test
    public void providersImgTest() throws JSONException {

        try {
            if (partnerConfigNum<1000){
                Assert.assertTrue(getGamesAPICheckProvidersPictures(getGamesAPIUrl, getGamesOrigin, getGamesPartnerName));
            }else{
                throw new SkipException("PartnerId = " + partnerID + " Is External Partner");
            }

        } catch (Exception e) {
            logger.info("gamesImgTest has an Exception: " + e);
            Assert.fail();
        }

    }


}






