package testCases;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;

public class CraftBet_104_MarketsSumErrorLifePreMatchGames_Test extends BaseTest {
    int count = 1;
    String getAllLifeGamesURL = "https://sportsbookwebsitewebapi.craftbet.com/website/getlivematchesoverview?LanguageId=en&TimeZone=4&origin=https://sportsbookwebsite.craftbet.com";
    String getPreMatchTree = "https://sportsbookwebsitewebapi.craftbet.com/website/getprematchtree?LanguageId=en&TimeZone=4";
    String getPreMatchTreeOrigin = "https://sportsbookwebsite.craftbet.com";


    public boolean getPreMatchGamesAPICheckMarkets(String sport)
            throws UnirestException, JSONException, IOException {

        boolean isPassed;
        int k = 1;
        int pointCount = 1;

        ArrayList<String> gameIDs = new ArrayList<>();
//        ArrayList<String> marketIDs = new ArrayList<>();
//        ArrayList<String> sportTypeArrayList = new ArrayList<>();
//        ArrayList<String> soccerIDsArrayList = new ArrayList<>();
//        ArrayList<Double> selectorValueArrayList = new ArrayList<>();
//        ArrayList<String> StartTime = new ArrayList<>();
        ArrayList<String> errorSrcXl = new ArrayList<>();
        ArrayList<String> errorEmptyMarkets = new ArrayList<>();


        Unirest.setTimeouts(0, 0);
        HttpResponse<String> responseGetMatches = Unirest.get(getPreMatchTree)
                .header("origin", getPreMatchTreeOrigin)
                .asString();

        logger.info("Get Matches Api call was sent");
        JSONObject jsonObjectBody = new JSONObject(responseGetMatches.getBody());
        JSONArray jsonArrayAllSports = jsonObjectBody.getJSONArray("Ss");
        Unirest.shutdown();

        for (int j = 0; j < jsonArrayAllSports.length(); j++) {
            String oneSport = String.valueOf(jsonArrayAllSports.get(j));
            JSONObject jsonObjectSport = new JSONObject(oneSport);
            String sportType = jsonObjectSport.get("SN").toString();
            if (sportType.equals(sport)) {
                JSONArray jsonArrayRegion = jsonObjectSport.getJSONArray("Rs");  //Sport Type
                for (int m = 0; m < jsonArrayRegion.length(); m++) {
                    String oneRegion = String.valueOf(jsonArrayRegion.get(m));
                    JSONObject jsonObjectRegion = new JSONObject(oneRegion);
                    JSONArray jsonArrayCompetitions = jsonObjectRegion.getJSONArray("Cs");
                    for (int n = 0; n < jsonArrayCompetitions.length(); n++) {
                        String oneCompetition = String.valueOf(jsonArrayCompetitions.get(n));
                        JSONObject jsonObjectMatches = new JSONObject(oneCompetition);
                        JSONArray jsonArrayMatches = jsonObjectMatches.getJSONArray("Ms");

                        for (int l = 0; l < jsonArrayMatches.length(); l++) {
                            String oneMatch = String.valueOf(jsonArrayMatches.get(l));
                            JSONObject jsonObjectMatchID = new JSONObject(oneMatch);
                            String matchID = jsonObjectMatchID.get("MI").toString();

                            String startTime = jsonObjectMatchID.get("ST").toString();
                            String isBlocked = jsonObjectMatchID.get("IB").toString();
                            gameIDs.add(matchID);
//                            StartTime.add(startTime);
                        }
                    }
                }
            }
        }
        logger.info("Matches count is  = " + gameIDs.size()); //All preMatch game IDs was caught and added to array list


        for (String gameId : gameIDs) {
            k++;
            Unirest.setTimeouts(0, 0);
            HttpResponse<String> responseGetMarkets = Unirest.get("https://sportsbookwebsitewebapi.craftbet.com/website/getmarketsbymatchid?LanguageId=en&TimeZone=4&MatchId=" + gameId + "&OddsType=0")
                    .header("origin", getPreMatchTreeOrigin)
                    .asString();
            JSONObject jsonObjectMarketsResponseBody = new JSONObject(responseGetMarkets.getBody());
            Unirest.shutdown();
            JSONArray jsonArrayAllMarkets = jsonObjectMarketsResponseBody.getJSONArray("Markets");
//                logger.info( gameId+ "  "+jsonArrayAllMarkets.length());
            int blockMarketsCount = 0;
            for (int z = 0; z < jsonArrayAllMarkets.length(); z++) {
                String arrayObjectMarket = String.valueOf(jsonArrayAllMarkets.get(z));
                JSONObject jsonObjectMarket = new JSONObject(arrayObjectMarket);
//                String marketID = jsonArrayAllMarkets.get("I").toString();
                String isMarketBlocked = jsonObjectMarket.get("IB").toString();  // Market isBlocked
                String marketID = jsonObjectMarket.get("I").toString();  // Market ID
                String MarketName = jsonObjectMarket.get("N").toString();  // Market Name
                if (isMarketBlocked.equals("false")) {
                    blockMarketsCount++;
                    JSONArray jsonArraySelectors = jsonObjectMarket.getJSONArray("Ss");
                    double selectorError = 0;
                    for (int x = 0; x < jsonArraySelectors.length(); x++) {
                        String arrayObjectSelectors = String.valueOf(jsonArraySelectors.get(x));
                        JSONObject jsonObjectSelector = new JSONObject(arrayObjectSelectors);
                        String selector = jsonObjectSelector.get("C").toString();  // Selector num
                        double selectorValue = Double.parseDouble(selector);

                        if (selectorValue < 1) {
                            selectorValue = 1;
                        }
                        selectorError = selectorError + 1 / selectorValue;
                    }
                    if (selectorError < 1) {
                        if (!MarketName.contains("To Miss A Penalty")) {
                            logger.info("This market works for Client  GameID = " + gameId + "  MarketName = " + MarketName + "  MarketID = " + marketID + "  SelectorError = " + selectorError);
                            errorSrcXl.add("This market works for Client  GameID = " + gameId + "  MarketName = " + MarketName + "  MarketID = " + marketID + "  SelectorError = " + selectorError);
                        }
                    }
                }
            }
            if (blockMarketsCount == 0) {
                logger.info(gameId + "  This game has no available Markets");
//                errorSrcXl.add(gameId + "  This game has no available Markets");
                errorEmptyMarkets.add(gameId + "  This game has no available Markets");
            }

            if(k==200){
                break;
            }


        }
        logger.info("Error Markets count is:  " + errorSrcXl.size());
        logger.info("Empty Markets count is:  " + errorEmptyMarkets.size());

        if (errorSrcXl.size() > 0) {
            writeInExel(errorSrcXl, "/src/test/java/CraftBet_003_BrokenMarkets/" + readConfig.partnerConfigNum() + getGamesPartnerName + sport + "DataErrorMarketsList.xlsx", "ErrorMarkets");
            isPassed = false;
        } else if (errorEmptyMarkets.size() > 0) {
            writeInExel(errorEmptyMarkets, "/src/test/java/Craftbet_003_EmptyMarketList/" + readConfig.partnerConfigNum() + getGamesPartnerName + sport + "EmptyMarketsList.xlsx", "EmptyMarkets");
            isPassed = false;
        } else {
            isPassed = true;
        }
        return isPassed;
    }


    @Test(dataProvider = "sports")
    public void gatPreMatchMatchGamesBrokenMarkets(String sport) throws UnirestException, JSONException, IOException {
        if (getGamesPartnerName.equals("Craftbet")) {
            if (getPreMatchGamesAPICheckMarkets(sport)) {
                Assert.assertTrue(true);
            } else {
                Assert.fail();
            }
        } else {
            logger.error("Please provide Craftbet id  as test Partner ");
            Assert.fail();
        }
    }


    @DataProvider(name = "sports")
    Object[][] loginDataInvalid() {

        String[][] arr = {
                {"Handball"},
                {"Basketball"},
                {"Tennis"},
                {"Ice Hockey"},
                {"Rugby League"},
                {"Rugby Union"},
                {"Volleyball"},
                {"American Football"},
                {"Table Tennis"},
                {"Futsal"},
                {"Aussie Rules"},
                {"Cricket"},
                {"E-sports"},
                {"Baseball"},
                {"Biathlon"},
                {"Water Polo"},
                {"Boxing"},
                {"MMA"},
                {"Gaelic Football"},
                {"Lacrosse"},
                {"Darts"},
                {"Beach Soccer"},
                {"Squash"},
                {"Floorball"},
                {"Chess"},
                {"Soccer"}
        };

//        String[][] arr = { {"Basketball"}};
        return arr;
    }





    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////



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
        ArrayList<String> selectorNameArrayList = new ArrayList<>();
        ArrayList<Double> selectorCoefficientArrayList = new ArrayList<>();

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
            String matchIsBlocked = jsonObjectGame.get("IB").toString();  // IsBlocked
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
                if (marketID.equals("0")) {
//                        logger.info(count + "  " + marketID + "  " +matchID );
//                        count++;
                } else {
                    String marketIsBlocked = jsonObjectMarkets.get("IB").toString();  // Market IsBlocked
                    String marketName = jsonObjectMarkets.get("N").toString();  // Market Name
                    marketNameArray.add(marketName);
                    marketIsBlockedArray.add(marketIsBlocked);
//                        logger.info(count + "  "+ matchID + "   " + marketID + "  " + marketIsBlocked + "  " + marketName);
//                        count++;
                    emptyMarketArrayList.add(marketName);
                    jsonArraySelectors = jsonObjectMarkets.getJSONArray("Ss");  // Selectors
                    int n = 1;
                    for (int l = 0; l < jsonArraySelectors.length(); l++) {
                        String selector = String.valueOf(jsonArraySelectors.get(l));
                        JSONObject jsonObjectSelector = new JSONObject(selector);

                        String selectorIsBlocked = jsonObjectSelector.get("IB").toString();  // selector IsBlocked
                        if (selectorIsBlocked.equals("true")) {
                            selectorCoefficientArrayList.add(1.0);
                        } else {
                            String selectorName = jsonObjectSelector.get("N").toString();  // selector Name
                            String selectorCoefficient = jsonObjectSelector.get("C").toString();  // selector Name
                            selectorNameArrayList.add(selectorName);
                            selectorCoefficientArrayList.add(Double.valueOf(selectorCoefficient));
//                            logger.info(n + "  MatchID = " + matchID + "  SportName = " + sportName +  "  Market Name = " + marketName + "  SelectorName = " + selectorName + "    - " + selectorCoefficient + "    StartTime =  " + gameStartTime);
                            n++;
                        }

                    }


                    double selectorError = 0;
                    if (selectorCoefficientArrayList.size() > 0) {
                        for (int r = 0; r < selectorCoefficientArrayList.size(); r++) {
                            double selector = selectorCoefficientArrayList.get(r);
                            if (selector < 1) {
                                selector = 1;
                            }
                            selectorError = selectorError + 1 / selector;
                        }

                        if (selectorError <= 1) {
                            String selectors = "";
                            logger.info(matchID + "  MatchID" + marketName + "  MarketName" + "---->This market works for client ");
                            for (double selector : selectorCoefficientArrayList) {
                                selectors = selectors + selector + "  ";
                            }
                        }
                    }
                    selectorCoefficientArrayList.clear();
                }
            }
            if (emptyMarketArrayList.size() < 1) {
                errorSrcXl.add(matchID + " This matches all Markets are empty");
                logger.info(matchID + " This matches all Markets are empty");
            }
            emptyMarketArrayList.clear();
        }


        logger.info("Error Markets are:  " + errorSrcXl.size());
        if (errorSrcXl.size() == 0) {
            isPassed = true;
        } else {

//            writeInExel(errorSrcXl, "src/test/java/CraftBet_003_BrokenMarkets/" + readConfig.partnerConfigNum() + partnerName + "DataErrorMarketsList.xlsx", "ErrorMarkets");
            isPassed = false;
        }
        return isPassed;
    }


    @Test
    public void gatLifeMatchGamesMarket() throws UnirestException, JSONException, IOException {

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
