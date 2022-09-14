package testCases;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import org.json.JSONArray;
import org.json.JSONException;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class CraftBet_001_CasinoGamesImgWeb_Test extends BaseTest {


    public boolean getGamesAPICheckPictures(String getGamesAPIUrl, String origin, String recurse, String partnerName)
            throws UnirestException, JSONException, IOException {

        boolean isPassed;
        int k = 1;
        ArrayList<String> srces = new ArrayList<>();
        ArrayList<String> gameNames = new ArrayList<>();
        ArrayList<String> gameProviderNames = new ArrayList<>();
        ArrayList<String> errorSrcXl = new ArrayList<>();


        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.post(getGamesAPIUrl)
                .header("content-type", "application/json")
                .header("origin", origin)
                .body("{\"PageIndex\":0,\"PageSize\":20000,\"WithWidget\":false,\"CategoryId\":null,\"ProviderIds\":null,\"IsForMobile\":false,\"Name\":\"\",\"LanguageId\":\"en\",\"Token\":null,\"ClientId\":0,\"TimeZone\":4}")
                .asString();

        logger.info("Get games Api call was sent");
        JSONObject jsonObjectBody = new JSONObject(response.getBody());
        Unirest.shutdown();
        JSONObject jsonObjectResponseObject = new JSONObject(jsonObjectBody.get("ResponseObject").toString());
        JSONArray jsonArrayGames = jsonObjectResponseObject.getJSONArray("Games");
        logger.info("Get games Api Response was captured");


        for (int j = 0; j < jsonArrayGames.length(); j++) {

            String first = String.valueOf(jsonArrayGames.get(j));
            JSONObject jsonObjectGame = new JSONObject(first);
            String i = jsonObjectGame.getString("i");    // Game src
            String n = jsonObjectGame.getString("n");    //Game Name
            String sp = jsonObjectGame.getString("sp");  //Provider Name
            gameNames.add(n);
            gameProviderNames.add(sp);

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
        logger.info("All captured games images was added into ArrayList");
        for (String src : srces) {
            if (src == null || src.isEmpty()) {
                logger.info(k + "   Game Provider Name = " + gameProviderNames.get(k) + "  " + "Game Name = " + gameNames.get(k) + " :   " + ":   src = " + src + " :" + " this games image src has empty/null value");
                errorSrcXl.add(k + "  Game Provider Name = " + gameProviderNames.get(k) + "  " + "Game Name = " + gameNames.get(k) + "  " + ":   src = " + src + " " + " ----->  this games image src has empty/null value");
            } else {
                int cod;
                try {
                    URL img = new URL(src);
                    HttpURLConnection connection = (HttpURLConnection) img.openConnection();
                    connection.connect();
                    cod = connection.getResponseCode();

                    if (cod >= 400) {
                        logger.error(k + "   Game Provider Name = " + gameProviderNames.get(k) + " :   " + "Game Name =  " + gameNames.get(k) + " :   " + "cod = " + cod + ":   src = " + src);
                        errorSrcXl.add(k + "  Game Provider Name = " + gameProviderNames.get(k) + "   " + "Game Name =  " + gameNames.get(k) + "  " + "cod = " + cod + "   src = " + src);
                    }
                } catch (Exception e) {
                    try {
                        URL img = new URL(src);
                        HttpURLConnection connection = (HttpURLConnection) img.openConnection();
                        connection.connect();
                        cod = connection.getResponseCode();

                        if (cod >= 400) {
                            logger.error(k + "  Game Provider Name = " + gameProviderNames.get(k) + " :   " + "Game Name =  " + gameNames.get(k) + " :   " + "cod = " + cod + ":   src = " + src);
                            errorSrcXl.add(k + "  Game Provider Name = " + gameProviderNames.get(k) + "   " + "Game Name =  " + gameNames.get(k) + "  " + "cod = " + cod + "   src = " + src);
                        }
                    } catch (Exception ex) {
                        logger.error(k + " Game Provider Name = " + gameProviderNames.get(k) + " :                    " + "Game Name = " + gameNames.get(k) + " :                    " + "   src = " + src + "         " + e);
                        errorSrcXl.add(k + "  Game Provider Name = " + gameProviderNames.get(k) + "   " + "Game Name =  " + gameNames.get(k) + "  " + "src = " + src);
                    }
                }
            }
            k++;
        }

        logger.info("Broken images are:  " + errorSrcXl.size());
        if (errorSrcXl.size() == 0) {
            isPassed = true;
        } else {
            writeInExel(errorSrcXl,"/src/test/java/CraftBet_001_APICasinoGamesBrokenData/" + readConfig.partnerConfigNum() + partnerName + "DataBrokenIMGList.xlsx" , "brokenIMG");
            isPassed = false;
        }
        return isPassed;
    }



    @Test
    public void gamesImgTest() throws UnirestException, JSONException, IOException {

        if (getGamesAPICheckPictures(getGamesAPIUrl, getGamesOrigin, getGamesRecurse, getGamesPartnerName)){
            Assert.assertTrue(true);
        }
        else {
            Assert.fail();
        }
    }
}






