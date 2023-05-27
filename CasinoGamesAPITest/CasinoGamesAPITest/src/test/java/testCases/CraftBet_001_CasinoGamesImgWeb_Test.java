package testCases;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.awt.*;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class CraftBet_001_CasinoGamesImgWeb_Test extends BaseTest {


    public CraftBet_001_CasinoGamesImgWeb_Test() throws AWTException {
    }

    public boolean getGamesAPICheckPictures(String getGamesAPIUrl, String origin, String recurse, String partnerName)
            throws JSONException, IOException {

        boolean isPassed;
        int k = 0;
        ArrayList<String> srces = new ArrayList<>();
        ArrayList<String> gameNames = new ArrayList<>();
        ArrayList<String> gameIDes = new ArrayList<>();
        ArrayList<String> gameProviderNames = new ArrayList<>();
        ArrayList<String> errorSrcXl = new ArrayList<>();

        try {
            Unirest.setTimeouts(0, 0);
            HttpResponse<String> response = Unirest.post(getGamesAPIUrl)
                    .header("content-type", "application/json")
                    .header("origin", origin)
                    .body("{\"PageIndex\":0,\"PageSize\":20000,\"WithWidget\":false,\"CategoryId\":null,\"ProviderIds\":null,\"IsForMobile\":false,\"Name\":\"\",\"LanguageId\":\"en\",\"Token\":null,\"ClientId\":0,\"TimeZone\":4}")

                    .asString();

            logger.info("Get games Api call was sent");
            JSONObject jsonObjectBody = new JSONObject(response.getBody());
            JSONObject jsonObjectResponseObject = new JSONObject(jsonObjectBody.get("ResponseObject").toString());
            JSONArray jsonArrayGames = jsonObjectResponseObject.getJSONArray("Games");
            logger.info("Get games Api Response was captured");

            for (int j = 0; j < jsonArrayGames.length(); j++) {
                String first = String.valueOf(jsonArrayGames.get(j));
                JSONObject jsonObjectGame = new JSONObject(first);
                String i = jsonObjectGame.getString("i");    // Game src
                String n = jsonObjectGame.getString("n");    //Game Name
                String sp = jsonObjectGame.getString("sp");  //Provider Name
                String p = jsonObjectGame.get("p").toString();   //Game ID

                gameNames.add(n);
                gameProviderNames.add(sp);
                gameIDes.add(p);
                if (i.contains("http") && i.contains(" ")) {
                    String change = i.replace(" ", "%20");
                    srces.add(change);
                } else if (!i.contains("http") && !i.contains(" ")) {
                    srces.add(recurse + i);
                } else if (!i.contains("http") && i.contains(" ")) {
                    String change = recurse + i.replace(" ", "%20");
                    srces.add(change);
                } else {
                    srces.add(i);
                }
            }
        } catch (Exception e) {
            logger.info("Get games Api Request has an Exception");
        } finally {
            Unirest.shutdown();
        }

        logger.info("All captured games images links was added into ArrayList: " + srces.size());
        int count = 1;
        HttpURLConnection connection = null;
        for (String src : srces) {
            if (src == null || src.isEmpty()) {
                logger.info(count + "  " + k + "  Game ID = " + gameIDes.get(k) + "   Game Provider Name = " + gameProviderNames.get(k) + "  " + "Game Name = " + gameNames.get(k) + " :   " + ":   src = " + src + " :" + " this games image src has empty/null value");
                errorSrcXl.add(k + "  Game ID = " + gameIDes.get(k) + "  Game Provider Name = " + gameProviderNames.get(k) + "  " + "Game Name = " + gameNames.get(k) + "  " + ":   src = " + src + " " + " ----->  this games image src has empty/null value");
                count++;
            } else {
                int cod;
                try {
                    URL img = new URL(src);
                    connection = (HttpURLConnection) img.openConnection();
                    connection.connect();
                    cod = connection.getResponseCode();
                    String contentType = connection.getContentType();
                    if (cod >= 400 || !contentType.contains("image")) {
                        logger.error(count + "  " + k + "  Game ID = " + gameIDes.get(k) + "   Game Provider Name = " + gameProviderNames.get(k) + " :   " + "Game Name =  " + gameNames.get(k) + " :   " + "cod = " + cod + ":   src = " + src);
                        errorSrcXl.add(k + "  Game ID = " + gameIDes.get(k) + "  Game Provider Name = " + gameProviderNames.get(k) + "   " + "Game Name =  " + gameNames.get(k) + "  " + "cod = " + cod + "   src = " + src);
                        count++;
                    }
                } catch (Exception e) {
                    try {
                        URL img = new URL(src);
                        connection = (HttpURLConnection) img.openConnection();
                        connection.connect();
                        cod = connection.getResponseCode();
                        String contentType = connection.getContentType();
                        if (cod >= 400 || !contentType.contains("image")) {
                            logger.error(count + "  " + k + "  Game ID = " + gameIDes.get(k) + "   Game Provider Name = " + gameProviderNames.get(k) + " :   " + "Game Name =  " + gameNames.get(k) + " :   " + "cod = " + cod + ":   src = " + src);
                            errorSrcXl.add(k + "  Game ID = " + gameIDes.get(k) + "  Game Provider Name = " + gameProviderNames.get(k) + "   " + "Game Name =  " + gameNames.get(k) + "  " + "cod = " + cod + "   src = " + src);
                            count++;
                        }
                    } catch (Exception ee) {

                        logger.error(count + "  " + k + "  Game ID = " + gameIDes.get(k) + " Game Provider Name = " + gameProviderNames.get(k) + " :   " + "Game Name = " + gameNames.get(k) + " :  " + "   src = " + src + "   " + e);
                        errorSrcXl.add(k + "  Game ID = " + gameIDes.get(k) + "  Game Provider Name = " + gameProviderNames.get(k) + "   " + "Game Name =  " + gameNames.get(k) + "  " + "src = " + src);
                        count++;
                    }
                   
                } finally {
                    connection.disconnect();
                }
                k++;
            }


        }
        logger.info("Broken images are:  " + errorSrcXl.size());
        if (errorSrcXl.size() == 0) {
            basePage.writeInExel(errorSrcXl, "/src/test/java/CraftBet_001_APICasinoGamesBrokenData/" + readConfig.partnerConfigNum() + partnerName + "BrokenData.xlsx", "GamesBrokenImgWeb");
            isPassed = true;
        } else {
            basePage.writeInExel(errorSrcXl, "/src/test/java/CraftBet_001_APICasinoGamesBrokenData/" + readConfig.partnerConfigNum() + partnerName + "BrokenData.xlsx", "GamesBrokenImgWeb");
            isPassed = false;
        }
        return isPassed;
    }


    @Test
    public void getSlotGamesWebImgTest() throws JSONException {
        try {
            Assert.assertTrue(getGamesAPICheckPictures(getGamesAPIUrl, getGamesOrigin, getGamesRecurse, getGamesPartnerName));

        } catch (Exception e) {
            System.out.println("getUrlAPITest has an exception" + e);
            Assert.fail();
        }

    }


}





