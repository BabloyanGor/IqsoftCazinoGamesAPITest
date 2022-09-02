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

public class MarketsSumError_Test extends BaseTest {
    int count = 1;
    String getAllLifeGamesURL = "https://sportsbookwebsitewebapi.craftbet.com/website/getlivematchesoverview?LanguageId=en&TimeZone=4&origin=https://sportsbookwebsite.craftbet.com";

    public boolean getMarketsOptionsSum(String partnerName)
            throws UnirestException, JSONException, IOException {

        boolean isPassed;
        ArrayList<String> matchIDArray = new ArrayList<>();
        ArrayList<String> sportIDArray = new ArrayList<>();
        ArrayList<String> sportNameArray = new ArrayList<>();
        ArrayList<String> matchIsBlockedArray = new ArrayList<>();
        ArrayList<String> gameStartTimeArray = new ArrayList<>();
        ArrayList<String> lifeScoreArray = new ArrayList<>();
        ArrayList<String> gameStatusArray = new ArrayList<>();
        ArrayList<String> teamIDArray = new ArrayList<>();
        ArrayList<String> teamNameArray = new ArrayList<>();

        ArrayList<String> marketNameArray = new ArrayList<>();
        ArrayList<String> marketIsBlockedArray = new ArrayList<>();

        ArrayList<String> errorSrcXl = new ArrayList<>();
        ArrayList<String> emptyMarketArrayList = new ArrayList<>();
        ArrayList<String> emptySelectorArrayList = new ArrayList<>();


        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.get(getAllLifeGamesURL).asString();

        logger.info("Get games Api call was sent");
        JSONObject jsonObjectBody = new JSONObject(response.getBody());
        JSONArray jsonArrayMatches = jsonObjectBody.getJSONArray("Ms"); //All life games array list
        Unirest.shutdown();
        logger.info("Games are " + jsonArrayMatches.length());
        logger.info("Get Life games Api Response was captured");

        for (int m = 0; m < jsonArrayMatches.length(); m++) {

            String arrayObject = String.valueOf(jsonArrayMatches.get(m));
            JSONObject jsonObjectGame = new JSONObject(arrayObject);

            String matchID = jsonObjectGame.get("MI").toString();  // Match ID
            String sportID = jsonObjectGame.get("SI").toString();  // Sport id ex for soccer = 1
            String sportName = jsonObjectGame.get("SN").toString();  // Sport Name
            String matchIsBlocked = jsonObjectGame.get("IB").toString();  // Sport Name
            String gameStartTime = jsonObjectGame.get("ST").toString();    //Game start Time
            String lifeScore = jsonObjectGame.get("RsI").toString();    //Match real score
            String gameStatus = jsonObjectGame.get("CPN").toString();    //Were is game now ex H1,H2, P, Started, Not Started

            JSONArray jsonArrayCompetitors = jsonObjectGame.getJSONArray("Cs"); //Competitors

//            logger.info(count + "       " + matchID + "  " + sportID + "  " + sportName + matchIsBlocked + "  " + gameStartTime + "  " + lifeScore + "  " + gameStatus);
//            count++;
            for (int j = 0; j < jsonArrayCompetitors.length(); j++) {
                String team = String.valueOf(jsonArrayCompetitors.get(j));
                JSONObject jsonObjectTeam = new JSONObject(team);

                String teamID = jsonObjectTeam.get("TI").toString();  // Team ID
                String teamName = jsonObjectTeam.get("TN").toString();  // Team Name
                teamIDArray.add(teamID);
                teamNameArray.add(teamName);
//                logger.info(count + "  " + matchID + "  " + teamName + "  " + teamID);
//                count++;
            }
                JSONArray jsonArrayTopMarkets = jsonObjectGame.getJSONArray("TMs");  // Markets



                for (int k = 0; k < jsonArrayTopMarkets.length(); k++) {
                    String markets = String.valueOf(jsonArrayTopMarkets.get(k));
                    JSONObject jsonObjectMarkets = new JSONObject(markets);

                    String marketID = jsonObjectMarkets.get("I").toString();  // Market IsBlocked
                    JSONArray jsonArraySelectors;
                    if (marketID.equals("0")){
//                        logger.info(count + "  " + marketID + "  " +matchID );
//                        count++;
                    }
                    else{
                        String marketIsBlocked = jsonObjectMarkets.get("IB").toString();  // Market IsBlocked
                        String marketName = jsonObjectMarkets.get("N").toString();  // Market Name
                        marketNameArray.add(marketName);
                        marketIsBlockedArray.add(marketIsBlocked);
//                        logger.info(count + "  "+ matchID + "   " + marketID + "  " + marketIsBlocked + "  " + marketName);
//                        count++;
                        emptyMarketArrayList.add(marketName);
                        jsonArraySelectors = jsonObjectMarkets.getJSONArray("Ss");  // Selectors

//                        for (int l = 0; l<jsonArraySelectors.length(); l++ ){
//                            String selector = String.valueOf(jsonArraySelectors.get(l));
//                            JSONObject jsonObjectSelector = new JSONObject(selector);
//
//                            String selectorIsBlocked = jsonObjectSelector.get("IB").toString();  // selector IsBlocked
//                            if (selectorIsBlocked.equals("true")){
//                                break;
//                            }
//                            else{
//                                String selectorName = jsonObjectSelector.get("N").toString();  // selector Name
//                                String selectorCoificent = jsonObjectSelector.get("C").toString();  // selector Name
//                                System.out.println();
//                            }
//                        }





                    }
                }
            if (emptyMarketArrayList.size() <1) {
                errorSrcXl.add(matchID + " This matches all Markets are empty");
                logger.info(matchID + " This matches all Markets are empty");
            }

            emptyMarketArrayList.clear();
        }







        logger.info("Error Markets are:  " + errorSrcXl.size());
        if (errorSrcXl.size() == 0) {
            isPassed = true;
        } else {

//            writeInExel(errorSrcXl, "/src/test/java/timeOutGames/" + readConfig.partnerConfigNum() + partnerName + "DataErrorMarketsList.xlsx", "ErrorMarkets");
            isPassed = false;
        }
        return isPassed;
    }


    @Test
    public void gatPreMatchGamesMarket() throws UnirestException, JSONException, IOException {
        if (getGamesPartnerName.equals("Craftbet")) {
            if (getMarketsOptionsSum(getGamesPartnerName)) {
                Assert.assertTrue(true);
            } else {
                Assert.fail();
            }
        } else {
            logger.error("Please provide Craftbet id  as test Partner ");
            Assert.fail();
        }
    }
}
