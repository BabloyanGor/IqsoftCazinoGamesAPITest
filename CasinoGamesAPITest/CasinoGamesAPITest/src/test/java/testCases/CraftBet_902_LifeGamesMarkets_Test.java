package testCases;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

import static java.lang.Double.parseDouble;

public class CraftBet_902_LifeGamesMarkets_Test extends BaseTest {

    public CraftBet_902_LifeGamesMarkets_Test() throws AWTException {
    }

    public JSONArray apiCallUnirest(String marketOrigin, String baseUrl) throws UnirestException, IOException {
        Unirest.setTimeouts(0, 0);
        HttpResponse<String> responseLifeGames = Unirest.get(baseUrl)
                .header("origin", marketOrigin)
                .asString();
        JSONObject jsonObjectMarketsResponseBody = new JSONObject(responseLifeGames.getBody());
        JSONArray jsonArrayAllMarkets = jsonObjectMarketsResponseBody.getJSONArray("Markets");
        Unirest.shutdown();
        return jsonArrayAllMarkets;
    }

    public boolean getLifeMatchGamesAPICheckMarkets(String getAllLifeGamesURL, String marketOrigin, String partnerName)
            throws UnirestException, JSONException, IOException {

        boolean isPassed;
        int k = 1;
        int pointCount = 1;
        ArrayList<String> gameIDs = new ArrayList<>();
        ArrayList<String> StartTime = new ArrayList<>();

        List nameMarketArray = new List();
        ArrayList<String> selectorPointArray = new ArrayList<>();
        ArrayList<String> nameSelectorArray = new ArrayList<>();
        ArrayList<String> isBlockedForSelectorArray = new ArrayList<>();

        ArrayList<String> errorSrcXl = new ArrayList<>();

        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.get(getAllLifeGamesURL).asString();

        logger.info("Get games Api call was sent");
        JSONObject jsonObjectBody = new JSONObject(response.getBody());
        JSONArray jsonArrayGames = jsonObjectBody.getJSONArray("Ms");

        Unirest.shutdown();
        logger.info("Games are " + jsonArrayGames.length());

        logger.info("Get Life games Api Response was captured");

        for (int j = 0; j < jsonArrayGames.length(); j++) {

            String arrayObject = String.valueOf(jsonArrayGames.get(j));
            JSONObject jsonObjectGame = new JSONObject(arrayObject);
            String MI = jsonObjectGame.get("MI").toString();  // Game ID
            gameIDs.add(MI);
        }

        logger.info("Games Ides = " + gameIDs.size());

//        craftBet_01_header_page.waitAction(2000);
        for (String gameId : gameIDs) {
            JSONArray jsonArrayAllMarkets = apiCallUnirest(marketOrigin, "https://sportsbookwebsitewebapi.craftbet.com/website/getmarketsbymatchid?LanguageId=en&TimeZone=4&MatchId=" + gameId + "&OddsType=0");

//            Unirest.setTimeouts(0, 0);
//            HttpResponse<String> responseLifeGames = Unirest.get("https://sportsbookwebsitewebapi.craftbet.com/website/getmarketsbymatchid?LanguageId=en&TimeZone=4&MatchId="+gameId+"&OddsType=0")
//                    .header("origin", marketOrigin)
//                    .asString();
//            JSONObject jsonObjectMarketsResponseBody = new JSONObject(responseLifeGames.getBody());
//            JSONArray jsonArrayAllMarkets = jsonObjectMarketsResponseBody.getJSONArray("Markets");
//            Unirest.shutdown();


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

//                    System.out.println(selectorPoint+"     "+nameSelector+"     "+isBlockedSelector);
                    try {
                        double point = parseDouble(selectorPoint);
                        if (point <= 1 && isBlockedSelector.equals("false")) {
                            // String errMessage = "GameID  = " + gameIDs.get(t) + "   MarketName = " + nameMarket + "   Selector = " + selectorPoint  + "   SelectorName" + nameSelector + "   Selector IsBlocked = " + isBlockedSelector;
                            String errMessage = "GameID  = " + gameIDs.get(t) + "   selectorPoint = " + selectorPoint;
                            errorSrcXl.add(errMessage);
                            logger.error(errMessage);
                        }
                    } catch (Exception e) {
                        logger.info("Cant parse to int selectorPoint:  " + selectorPoint);

                    }
                    pointCount++;
                }
            }
        }

        logger.info("Point count =  " + pointCount);
        logger.info("Games with selector >= 1:  " + errorSrcXl.size());
        if (errorSrcXl.size() == 0) {
            isPassed = true;
        } else {
            basePage.writeInExel(errorSrcXl, "/src/test/java/CraftBet_004_SelectorsHaveEqualOrLessOneValue/" + readConfig.partnerConfigNum() + partnerName + "DataPreMatchMarketsEqual1.xlsx", "brokenPrematchMarkets");
            isPassed = false;
        }
        return isPassed;
    }


    @Test
    public void gatLifeMatchGamesMarkets() throws UnirestException, JSONException, IOException {
        if (getGamesPartnerName.equals("Craftbet")) {
            if (getLifeMatchGamesAPICheckMarkets(getAllLifeGames, getMarketByIDOrigin, getGamesPartnerName)) {
                Assert.assertTrue(true);
            } else {
                Assert.fail();
            }
        } else {
            logger.error("Please provide CraftBet id  as test Partner ");
            Assert.assertTrue(false);
        }
    }
}
