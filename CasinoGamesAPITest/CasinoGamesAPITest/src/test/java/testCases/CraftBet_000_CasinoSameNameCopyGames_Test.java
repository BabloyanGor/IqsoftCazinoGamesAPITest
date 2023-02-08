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
import java.util.ArrayList;
import java.util.regex.Pattern;

public class CraftBet_000_CasinoSameNameCopyGames_Test extends BaseTest {




    public boolean getALLGamesAPICheckCopyGames(String getGamesAPIUrl, String origin, String partnerName)
            throws UnirestException, JSONException, IOException {

        boolean isPassed;
        int k = 1;
        ArrayList<String> gameNames = new ArrayList<>();
        ArrayList<String> gameProviderNames = new ArrayList<>();
        ArrayList<String> gameProviderNamesList = new ArrayList<>();
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
            String n = jsonObjectGame.getString("n");    //Game Name
            String sp = jsonObjectGame.getString("sp");  //Provider Name
            gameProviderNames.add(sp);
            gameNames.add(n);


//            if (!gameProviderNamesList.contains(sp)){                     For Providers List
//                gameProviderNamesList.add(sp);
//            }
        }

        for (int i = 0; i < gameNames.size(); i++) {
            String name = gameNames.get(i);
            for (int j = i + 1; j < gameNames.size(); j++) {
                String x = gameNames.get(j);
                if (name.equals(x)) {
                    logger.info("Dupliicate game Name =  " + gameNames.get(j));
                    errorSrcXl.add(k + "  This game has duplicate Please check it :  Name =  " + gameNames.get(j));
                    k++;
                }
            }
        }
        logger.info("Duplicate games are:  " + errorSrcXl.size());
        if (errorSrcXl.size() == 0) {
            isPassed = true;
        } else {
            writeInExel(errorSrcXl, "/src/test/java/CraftBet_001_APICasinoGamesBrokenData/" + readConfig.partnerConfigNum() + partnerName + "DuplicateGamesList.xlsx", "CopyGames");
            isPassed = false;
        }
        // writeInExel(gameProviderNamesList, "/src/test/java/CraftBet_001_APICasinoGamesBrokenData/" + readConfig.partnerConfigNum() + partnerName + "ProvidersList.xlsx", "Providers");
        // For Providers List
        return isPassed;
    }

    @Test
    public void getDuplicateGames() throws UnirestException, JSONException, IOException {

        if (getALLGamesAPICheckCopyGames(getGamesAPIUrl, getGamesOrigin, getGamesPartnerName)) {
            Assert.assertTrue(true);
        } else {
            Assert.assertTrue(false);
        }
    }

    ArrayList<String> casinoGameNames = new ArrayList<>();
    public void getALLGamesNames(String getGamesAPIUrl, String origin, String partnerName)
            throws UnirestException, JSONException, IOException {
        ArrayList<String> errorSrcXl = new ArrayList<>();
        ArrayList<String> errorSrcXl2 = new ArrayList<>();
        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.post(getGamesAPIUrl)
                .header("content-type", "application/json")
                .header("origin", origin)
                .body("{\"PageIndex\":0,\"PageSize\":20000,\"WithWidget\":false,\"CategoryId\":null,\"ProviderIds\":null,\"IsForMobile\":false,\"Name\":\"\",\"LanguageId\":\"ja\",\"Token\":null,\"ClientId\":0,\"TimeZone\":4}")
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
            String n = jsonObjectGame.getString("n");    //Game Name
            String sp = jsonObjectGame.getString("sp");  //Provider Name
            casinoGameNames.add(n);
            if (regexCheck("^[^A-Za-z]+$",n)){
//                if (regexCheck("[a-zA-Z. .,!&'™:\n0-9]{1,300}",n)){
                    errorSrcXl.add(n);
            }
            else{
                errorSrcXl2.add(n);
            }

        }
        System.out.println("Games with japan " + errorSrcXl.size());
        System.out.println("Games withOut japan " + errorSrcXl2.size());
        if (errorSrcXl.size() == 0) {
            System.out.println("");
        } else {
            writeInExel(errorSrcXl, "/src/test/java/CraftBet_001_APICasinoGamesBrokenData/" + readConfig.partnerConfigNum() + partnerName + "GamesJapanTranslation.xlsx", "Games");
        }
        if (errorSrcXl2.size() == 0) {
            System.out.println("");
        } else {
            writeInExel(errorSrcXl2, "/src/test/java/CraftBet_001_APICasinoGamesBrokenData/" + readConfig.partnerConfigNum() + partnerName + "GamesNotTranslated.xlsx", "Games");
        }

    }
    public boolean regexCheck(String regex, String matcher){

//        isRegexTrue = Pattern.compile(regex).matcher(matcher).matches();

//        Pattern p = Pattern.compile(".s");//. represents single character
//        Matcher m = p.matcher("as");
//        boolean b = m.matches();

        boolean isRegexTrue = Pattern.matches(regex,matcher);
        return isRegexTrue;
    }
//    @Test
//    public void getCasinoGamesThatContainEnglishLetter() throws UnirestException, IOException {
//        getALLGamesNames(getGamesAPIUrl, getGamesOrigin, getGamesPartnerName);
//
//    }
}
