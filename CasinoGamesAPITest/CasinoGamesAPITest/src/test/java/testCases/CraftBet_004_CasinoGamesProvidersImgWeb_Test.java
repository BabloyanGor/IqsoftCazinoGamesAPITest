package testCases;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class CraftBet_004_CasinoGamesProvidersImgWeb_Test extends BaseTest {


    public boolean getGamesAPICheckProvidersPictures(String getGamesAPIUrl, String origin, String recurse, String partnerName)
            throws UnirestException, JSONException, IOException {

        boolean isPassed;
        int k = 0;
        ArrayList<String> providerIDes = new ArrayList<>();
        ArrayList<String> providerNames = new ArrayList<>();
        ArrayList<String> providersGameCount = new ArrayList<>();
        ArrayList<String> providersOrder = new ArrayList<>();
        ArrayList<String> srces = new ArrayList<>();
        ArrayList<String> errorSrcXl = new ArrayList<>();

        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.post(getGamesAPIUrl)
                .header("content-type", "application/json")
                .header("origin", origin)
                .body("{\"PageIndex\":0,\"PageSize\":20000,\"WithWidget\":false,\"CategoryId\":null,\"ProviderIds\":null,\"IsForMobile\":false," +
                        "\"Name\":\"\",\"LanguageId\":\"en\",\"Token\":null,\"ClientId\":0,\"TimeZone\":4}")

                .asString();

        logger.info("Get games Api call was sent");
        JSONObject jsonObjectBody = new JSONObject(response.getBody());
        logger.info("Get games Api Response was captured");
        Unirest.shutdown();
        JSONObject jsonObjectResponseObject = new JSONObject(jsonObjectBody.get("ResponseObject").toString());
        JSONArray jsonArrayProviders = jsonObjectResponseObject.getJSONArray("Providers");



        for (int j = 0; j < jsonArrayProviders.length(); j++) {
            String provider = String.valueOf(jsonArrayProviders.get(j));
            JSONObject jsonObjectGame = new JSONObject(provider);
            String Id = jsonObjectGame.get("Id").toString();    // Provider ID
            String Name = jsonObjectGame.get("Name").toString();    //Provider Name
            String GamesCount = jsonObjectGame.get("GamesCount").toString();  //Provider games count
            String Order = jsonObjectGame.get("Order").toString();   //Provider ID

            providerIDes.add(Id) ;
            providerNames.add(Name) ;
            providersGameCount.add(GamesCount);
            providersOrder.add(Order);
            srces.add(getGamesBaseURL + "/assets/images/providers/" + Id + ".png");
           // https://craftbet.com/assets/images/providers/1150.png
        }


        logger.info("All captured providers data  was added into ArrayLists Count: " + providerNames.size());
        int count = 1;
        for (String src : srces) {
                int cod;

                try {
                    URL img = new URL(src);
                    HttpURLConnection connection = (HttpURLConnection) img.openConnection();
                    connection.connect();
                    cod = connection.getResponseCode();

                    if (cod >= 400) {
                        logger.error(count + "  Provider ID = " + providerIDes.get(k) + "   Provider Name = " + providerNames.get(k) + " :   "  + "cod = " + cod + ":   src = " + src);
                        errorSrcXl.add(count + "  Provider ID = " + providerIDes.get(k) + "   Provider Name = " + providerNames.get(k) + " :   "  + "cod = " + cod + ":   src = " + src);
                    }
                } catch (Exception e) {
                    try {
                        URL img = new URL(src);
                        HttpURLConnection connection = (HttpURLConnection) img.openConnection();
                        connection.connect();
                        cod = connection.getResponseCode();

                        if (cod >= 400) {
                            logger.error(count + "  Provider ID = " + providerIDes.get(k) + "   Provider Name = " + providerNames.get(k) + " :   "  + "cod = " + cod + ":   src = " + src);
                            errorSrcXl.add(count + "  Provider ID = " + providerIDes.get(k) + "   Provider Name = " + providerNames.get(k) + " :   "  + "cod = " + cod + ":   src = " + src);
                        }
                    } catch (Exception ex) {
                        logger.error(count + "  Provider ID = " + providerIDes.get(k) + "   Provider Name = " + providerNames.get(k) + " :   "  + ":   src = " + src);
                        errorSrcXl.add(count + "  Provider ID = " + providerIDes.get(k) + "   Provider Name = " + providerNames.get(k) + " :   "  +  ":   src = " + src);

                    }
                }

            k++;
                count++;
        }




        logger.info("Broken Providers images are:  " + errorSrcXl.size());
        if (errorSrcXl.size() == 0) {
            isPassed = true;
        } else {
            writeInExel(errorSrcXl, "/src/test/java/CraftBet_001_APICasinoGamesBrokenData/" + readConfig.partnerConfigNum() + partnerName + "ProvidersBrokenIMGListWeb.xlsx", "ProvidersBrokenIMGWeb");
            isPassed = false;
        }
        return isPassed;
    }


    @Test
    public void gamesImgTest() throws UnirestException, JSONException, IOException {

        if (getGamesAPICheckProvidersPictures(getGamesAPIUrl, getGamesOrigin, getGamesRecurse, getGamesPartnerName)) {
            Assert.assertTrue(true);
        } else {
            Assert.fail();
        }
    }


}






