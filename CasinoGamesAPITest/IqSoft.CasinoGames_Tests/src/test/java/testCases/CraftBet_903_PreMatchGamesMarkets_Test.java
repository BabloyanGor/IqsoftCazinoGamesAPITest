package testCases;


import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONException;
import kong.unirest.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import testCases.WebCasinoGames_Tests.BaseTest;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

import static java.lang.Double.parseDouble;

public class CraftBet_903_PreMatchGamesMarkets_Test extends BaseTest {


    public CraftBet_903_PreMatchGamesMarkets_Test() throws AWTException {
    }

    public boolean getPreMatchGamesAPICheckMarkets(String getMatchIDAPI, String getMarketsAPI, String origin, String partnerName)
            throws UnirestException, JSONException, IOException {

        boolean isPassed;
        int k = 1;
        int pointCount = 1;
        ArrayList<String> gameIDs = new ArrayList<>();
        ArrayList<String> StartTime = new ArrayList<>();

        ArrayList<String> nameMarketArray = new ArrayList<>();
        ArrayList<String> selectorPointArray = new ArrayList<>();
        ArrayList<String> nameSelectorArray = new ArrayList<>();
        ArrayList<String> isBlockedForSelectorArray = new ArrayList<>();

        ArrayList<String> errorSrcXl = new ArrayList<>();


//        Unirest.setTimeouts(0, 0);
        HttpResponse<String> responseGetMatches = Unirest.get(getMatchIDAPI)
                .header("origin", origin)
                .asString();

        logger.info("Get Matches Api call was sent");
        JSONObject jsonObjectBody = new JSONObject(responseGetMatches.getBody());
        JSONArray jsonArrayAllSports = jsonObjectBody.getJSONArray("Ss");

        Unirest.shutDown(true);
        for (int j = 0; j < jsonArrayAllSports.length(); j++) {
            String oneSport = String.valueOf(jsonArrayAllSports.get(j));
//            System.out.println(oneSport);
//            System.out.println();
            JSONObject jsonObjectSport = new JSONObject(oneSport);
            JSONArray jsonArrayRegion = jsonObjectSport.getJSONArray("Rs");

            for (int m = 0; m < jsonArrayRegion.length(); m++) {
                String oneRegion = String.valueOf(jsonArrayRegion.get(m));
//                System.out.println(oneRegion);
//                System.out.println();
                JSONObject jsonObjectRegion = new JSONObject(oneRegion);
                JSONArray jsonArrayCompetitions = jsonObjectRegion.getJSONArray("Cs");

                for (int n = 0; n < jsonArrayCompetitions.length(); n++) {
                    String oneCompetition = String.valueOf(jsonArrayCompetitions.get(n));
//                    System.out.println(oneCompetition);
//                    System.out.println();
                    JSONObject jsonObjectMatches = new JSONObject(oneCompetition);
                    JSONArray jsonArrayMatches = jsonObjectMatches.getJSONArray("Ms");

                    for (int l = 0; l < jsonArrayMatches.length(); l++) {
                        String oneMatch = String.valueOf(jsonArrayMatches.get(l));
                        JSONObject jsonObjectMatchID = new JSONObject(oneMatch);
                        String matchID = jsonObjectMatchID.get("MI").toString();
                        String startTime = jsonObjectMatchID.get("ST").toString();
                        String isBlocked = jsonObjectMatchID.get("IB").toString();
//                        System.out.println(oneMatch );
//                        System.out.println(matchID);
//                        System.out.println();
                        gameIDs.add(matchID);
                        StartTime.add(startTime);
                        //System.out.println(k + "  matchID = " + matchID + "   startTimeIs = " + startTime + "   isBlocked = " + isBlocked );
                        k++;
                    }
                }
            }
        }

//        craftBet_01_header_page.waitAction(2000);
        int gameIDCount = 0;
        for (String gameId : gameIDs){
            gameIDCount++;
            Unirest.shutDown(true);;

            HttpResponse<String> response = Unirest.get("https://sportsbookwebsitewebapi.craftbet.com/website/getmarketsbymatchid?LanguageId=en&TimeZone=4&MatchId="+gameId+"&OddsType=0")
                    .header("origin", getMarketsAPI)
                    .asString();
            JSONObject jsonObjectMarketsResponseBody = new JSONObject(response.getBody());
//            System.out.println(jsonObjectMarketsResponseBody.toString());
            JSONArray jsonArrayAllMarkets = jsonObjectMarketsResponseBody.getJSONArray("Markets");
            Unirest.shutDown(true);
//            System.out.println(k);
            k--;
            for (int c = 0; c < jsonArrayAllMarkets.length(); c++) {
                String oneMarket = String.valueOf(jsonArrayAllMarkets.get(c));
//            System.out.println(oneSport);
//            System.out.println();
                JSONObject jsonObjectMarket = new JSONObject(oneMarket);
                String nameMarket = jsonObjectMarket.get("N").toString();
                nameMarketArray.add(nameMarket);
//                System.out.println(nameMarket);
                JSONArray jsonArrayMarket = jsonObjectMarket.getJSONArray("Ss");

                for (int t = 0; t < jsonArrayMarket.length(); t++) {

                    String oneSelector = String.valueOf(jsonArrayMarket.get(t));
                    JSONObject jsonObjectSelector = new JSONObject(oneSelector);
                    String selectorPoint = jsonObjectSelector.get("C").toString();
                    String nameSelector = jsonObjectSelector.get("N").toString();
                    String isBlockedSelector = jsonObjectSelector.get("IB").toString();

                    selectorPointArray.add(selectorPoint);
                    nameSelectorArray.add(nameSelector);
                    isBlockedForSelectorArray.add(isBlockedSelector);
                    if (isBlockedSelector.equals("true")){
                        break;
                    }
                    try {
                        double point = parseDouble(selectorPoint);
                        if (point<=1){
                            // String errMessage = "GameID  = " + gameIDs.get(t) + "   MarketName = " + nameMarket + "   Selector = " + selectorPoint  + "   SelectorName" + nameSelector + "   Selector IsBlocked = " + isBlockedSelector;
                            String errMessage = "GameID  = " + gameIDs.get(t) + "   selectorPoint = " + selectorPoint;
                            errorSrcXl.add(errMessage);
                            logger.error(errMessage);
                        }
                    }
                    catch (Exception e){
                        logger.info("Cant parse to int selectorPoint:  " + selectorPoint );
                        errorSrcXl.add("Cant parse to int selectorPoint:  " + selectorPoint );

                    }
//                    System.out.println(pointCount);
                    pointCount++;
                }
            }
            System.out.println("GameID number  " + gameIDCount + "  of  " + k);
        }


        logger.info("Point count =  " + pointCount);
        logger.info("Games with selector >= 1:  " + errorSrcXl.size());
        if (errorSrcXl.size() == 0) {
            isPassed = true;
        } else {
            basePage.writeInExel(errorSrcXl,"/src/test/java/CraftBet_004_SelectorsHaveEqualOrLessOneValue/" + readConfig.partnerConfigNum() + partnerName + "DataLifeMarketsEqual1.xlsx" ,"brokenLifeMarkets");
            isPassed = false;
        }
        return isPassed;
    }
    @Test
    public void gatPreMatchGamesMarket() throws UnirestException, JSONException, IOException {
        if (getGamesPartnerName.equals("Craftbet")||getGamesPartnerName.equals("Tetherbet")){
            if (getPreMatchGamesAPICheckMarkets(getPreMatchTree, getMarketByID, getPrematchTreeOrigin,getGamesPartnerName)){
                Assert.assertTrue(true);
            }
            else {
                Assert.fail();
            }
        }
        else{
            logger.error("Please provide Craftbet/Tetherbet id  as test Partner ");
            Assert.assertTrue(false);
        }
    }
}
