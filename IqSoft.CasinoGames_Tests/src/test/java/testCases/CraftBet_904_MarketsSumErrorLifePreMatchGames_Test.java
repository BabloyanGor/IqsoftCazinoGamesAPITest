package testCases;


import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONException;
import kong.unirest.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import testCases.WebCasinoGames_Tests.BaseTest;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class CraftBet_904_MarketsSumErrorLifePreMatchGames_Test extends BaseTest {

    public CraftBet_904_MarketsSumErrorLifePreMatchGames_Test() throws AWTException {
    }

    @Test(priority = 1,dataProvider = "sports")
    public void gatPreMatchMatchGamesWithEmptyMarkets(String sport) throws UnirestException, JSONException, IOException {
        if (getGamesPartnerName.equals("Craftbet")||getGamesPartnerName.equals("Tetherbet")) {
            if (getPreMatchGamesWithEmptyMarkets(sport)) {
                Assert.assertTrue(true);
            } else {
                Assert.fail();
            }
        } else {
            logger.error("Please provide Craftbet id  as test Partner ");
            Assert.fail();
        }
    }

    public boolean getPreMatchGamesWithEmptyMarkets(String sport)
            throws UnirestException, JSONException, IOException {

        boolean isPassed;


        ArrayList<String> gameIDs = new ArrayList<>();
        ArrayList<String> gameStartTime = new ArrayList<>();
        ArrayList<String> errorEmptyMarkets = new ArrayList<>();


        Unirest.config().reset();
        Unirest.config().connectTimeout(60000);
        Unirest.config().socketTimeout(60000);
        HttpResponse<String> responseGetMatches = Unirest.get(getPreMatchTree)
                .header("origin", getPrematchTreeOrigin)
                .asString();

        logger.info("Get Matches Api call was sent url:" +  getPreMatchTree + "  Origin  " + getPrematchTreeOrigin );
        JSONObject jsonObjectBody = new JSONObject(responseGetMatches.getBody());
        JSONArray jsonArrayAllSports = jsonObjectBody.getJSONArray("Ss");
        Unirest.shutDown(true);;


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
                            gameIDs.add(matchID);
                            String startTime = jsonObjectMatchID.get("ST").toString();
                            gameStartTime.add(startTime);
                        }
                    }
                }
            }
        }

        logger.info("Matches count is  = " + gameIDs.size()); //All preMatch game IDs was caught and added to array list

        for (int i = 0; i < gameIDs.size(); i++) {
            Unirest.config().reset();
            Unirest.config().connectTimeout(60000);
            Unirest.config().socketTimeout(60000);
//            HttpResponse<String> responseGetMarkets = Unirest.get("https://sportsbookwebsitewebapi.craftbet.com/website/getmarketsbymatchid?LanguageId=en&TimeZone=4&MatchId=" + gameIDs.get(i) + "&OddsType=0")
            HttpResponse<String> responseGetMarkets = Unirest.get(getPrematchMatchesMarketsByID(Integer.parseInt(gameIDs.get(i))))
                    .header("origin", getPrematchTreeOrigin)
                    .asString();
            JSONObject jsonObjectMarketsResponseBody = new JSONObject(responseGetMarkets.getBody());
            Unirest.shutDown(true);
            JSONArray jsonArrayAllMarkets = jsonObjectMarketsResponseBody.getJSONArray("Markets");
            int marketsCount = 0;
            for (int z = 0; z < jsonArrayAllMarkets.length(); z++) {
                String arrayObjectMarket = String.valueOf(jsonArrayAllMarkets.get(z));
                JSONObject jsonObjectMarket = new JSONObject(arrayObjectMarket);
                String isMarketBlocked = jsonObjectMarket.get("IB").toString();  // Market isBlocked
                if (isMarketBlocked.equals("false")) {
                    marketsCount++;
                }
            }
            if (marketsCount == 0) {
                logger.info(i + "          " + gameIDs.get(i) + "  This game has no available Markets:  Start time = " + gameStartTime.get(i));
                errorEmptyMarkets.add(i + "          " + gameIDs.get(i) + "  This game has no available Markets:  Start time = " + gameStartTime.get(i));
            }

        }
        logger.info("Empty Markets count is:  " + errorEmptyMarkets.size());

        if (errorEmptyMarkets.size() > 0) {
            basePage.writeInExel(errorEmptyMarkets, "/src/test/java/Craftbet_003_EmptyMarketList/" + readConfig.partnerConfigNum() + getGamesPartnerName + sport + "_" + errorEmptyMarkets.size() + "_" + "EmptyMarketsList.xlsx", "EmptyMarkets");
            isPassed = false;
        } else {
            isPassed = true;
        }
        return isPassed;
    }






    @Test(priority = 2,dataProvider = "sports")
    public void gatPreMatchMatchGamesBrokenMarkets(String sport) throws UnirestException, JSONException, IOException {
        if (getGamesPartnerName.equals("Craftbet")||getGamesPartnerName.equals("Tetherbet")) {
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







    public boolean getPreMatchGamesAPICheckMarkets(String sport)
            throws UnirestException, JSONException, IOException {

        boolean isPassed;
        int breakMatchCount = 1;
        int count = 1;
        int pointCount = 1;

        ArrayList<String> gameIDs = new ArrayList<>();
//        ArrayList<String> marketIDs = new ArrayList<>();
//        ArrayList<String> sportTypeArrayList = new ArrayList<>();
//        ArrayList<String> soccerIDsArrayList = new ArrayList<>();
//        ArrayList<Double> selectorValueArrayList = new ArrayList<>();
//        ArrayList<String> StartTime = new ArrayList<>();
        ArrayList<String> errorSrcXl = new ArrayList<>();


        Unirest.config().reset();
        Unirest.config().connectTimeout(60000);
        Unirest.config().socketTimeout(60000);
        HttpResponse<String> responseGetMatches = Unirest.get(getPreMatchTree)
                .header("origin", getPrematchTreeOrigin)
                .asString();

        logger.info("Get Matches Api call was sent");
        JSONObject jsonObjectBody = new JSONObject(responseGetMatches.getBody());
        JSONArray jsonArrayAllSports = jsonObjectBody.getJSONArray("Ss");
        Unirest.shutDown(true);

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
            breakMatchCount++;
            Unirest.config().reset();
            Unirest.config().connectTimeout(60000);
            Unirest.config().socketTimeout(60000);
            HttpResponse<String> responseGetMarkets = Unirest.get("https://sportsbookwebsitewebapi.craftbet.com/website/getmarketsbymatchid?LanguageId=en&TimeZone=4&MatchId=" + gameId + "&OddsType=0")
                    .header("origin", getPrematchTreeOrigin)
                    .asString();
            JSONObject jsonObjectMarketsResponseBody = new JSONObject(responseGetMarkets.getBody());
            Unirest.shutDown(true);
            JSONArray jsonArrayAllMarkets = jsonObjectMarketsResponseBody.getJSONArray("Markets");
//                logger.info( gameId+ "  "+jsonArrayAllMarkets.length());
            int marketsCount = 0;
            for (int z = 0; z < jsonArrayAllMarkets.length(); z++) {
                String arrayObjectMarket = String.valueOf(jsonArrayAllMarkets.get(z));
                JSONObject jsonObjectMarket = new JSONObject(arrayObjectMarket);
//                String marketID = jsonArrayAllMarkets.get("I").toString();
                String isMarketBlocked = jsonObjectMarket.get("IB").toString();  // Market isBlocked
                String marketID = jsonObjectMarket.get("I").toString();  // Market ID
                String MarketName = jsonObjectMarket.get("N").toString();  // Market Name
                if (isMarketBlocked.equals("false")) {
                    marketsCount++;
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
                            logger.info(count + "  This market works for Client  GameID = " + gameId + "  MarketName = " + MarketName + "  MarketID = " + marketID + "  SelectorError = " + selectorError);
                            errorSrcXl.add(count + "  This market works for Client  GameID = " + gameId + "  MarketName = " + MarketName + "  MarketID = " + marketID + "  SelectorError = " + selectorError);
                            count++;
                        }
                    }
                }
            }

            /////////////////Break Count For Test less time///////////////
            if (breakMatchCount == 200) {
                break;
            }
            /////////////////////////////////////////////////////////////
        }
        logger.info("Error Markets count is:  " + errorSrcXl.size());

        if (errorSrcXl.size() > 0) {
            basePage.writeInExel(errorSrcXl, "/src/test/java/CraftBet_003_BrokenMarkets/" + readConfig.partnerConfigNum() + getGamesPartnerName + sport + "_" + errorSrcXl.size() + "_" + "DataErrorMarketsList.xlsx", "ErrorMarkets");
            isPassed = false;
        } else {
            isPassed = true;
        }
        return isPassed;
    }


    @DataProvider(name = "sports")
    Object[][] loginDataInvalid() {

        String[][] arr = {
                {"Basketball"},
                {"Tennis"},
                {"Formula 1"},
                {"Ice Hockey"},
                {"Table Tennis"},
                {"Rugby League"},
                {"Rugby Union"},
                {"Volleyball"},
                {"Golf"},
                {"American Football"},
                {"Futsal"},
                {"Aussie Rules"},
                {"Cricket"},
                {"Cybersport"},
                {"Handball"},
                {"Badminton"},
                {"Kabaddi"},
                {"Baseball"},
                {"Boxing"},
                {"MMA"},
                {"Darts"},
                {"Lottery"},
                {"Netball"},
                {"Politics"},
                {"E-Football"},
                {"E-Basketball"},
                {"Snooker"},
                {"Specials"},
                {"Floorball"},
                {"Chess"},
                {"E-sports"},
                {"Biathlon"},
                {"Water Polo"},
                {"Gaelic Football"},
                {"Lacrosse"},
                {"Beach Soccer"},
                {"Squash"},

                {"Soccer"}  //
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

        Unirest.config().reset();
        Unirest.config().connectTimeout(60000);
        Unirest.config().socketTimeout(60000);
        HttpResponse<String> response = Unirest.get(getAllLifeGames).asString();

        logger.info("Get games Api call was sent");
        JSONObject jsonObjectBody = new JSONObject(response.getBody());
        JSONArray jsonArrayMatches = jsonObjectBody.getJSONArray("Ms"); //All life games array list
        Unirest.shutDown(true);
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

            basePage.writeInExel(errorSrcXl, "/src/test/java/CraftBet_003_BrokenMarkets/" + readConfig.partnerConfigNum() + partnerName + "DataErrorMarketsList.xlsx", "ErrorMarkets");
            isPassed = false;
        }
        return isPassed;
    }


    @Test(priority = 3)
    public void gatLifeMatchGamesMarket() throws UnirestException, JSONException, IOException {

        if (getGamesPartnerName.equals("Craftbet")) {
            if (getMarketsOptionsSum(getGamesPartnerName)) {
                Assert.assertTrue(true);
            } else {
                Assert.fail();
            }
        } else {
            logger.error("Please provide Craftbet id  as test Partner ");
            Assert.assertTrue(false);
        }
    }


}
