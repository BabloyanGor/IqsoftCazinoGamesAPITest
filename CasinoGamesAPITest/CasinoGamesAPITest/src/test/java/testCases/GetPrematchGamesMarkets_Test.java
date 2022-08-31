package testCases;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import static java.lang.Double.parseDouble;

public class GetPrematchGamesMarkets_Test extends BaseTest{


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
                        String matchID = jsonObjectMatchID.getString("MI");
                        String startTime = jsonObjectMatchID.getString("ST");
                        String isBlocked = jsonObjectMatchID.getString("IB");
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
        for (String gameId : gameIDs){

            Unirest.setTimeouts(0, 0);
            HttpResponse<String> response = Unirest.get("https://sportsbookwebsitewebapi.craftbet.com/website/getmarketsbymatchid?LanguageId=en&TimeZone=4&MatchId="+gameId+"&OddsType=0")
                    .header("origin", getMarketsAPI)
                    .asString();
            JSONObject jsonObjectMarketsResponseBody = new JSONObject(response.getBody());
//            System.out.println(jsonObjectMarketsResponseBody.toString());
            JSONArray jsonArrayAllMarkets = jsonObjectMarketsResponseBody.getJSONArray("Markets");
            System.out.println(k);
            k--;
            for (int c = 0; c < jsonArrayAllMarkets.length(); c++) {
                String oneMarket = String.valueOf(jsonArrayAllMarkets.get(c));
//            System.out.println(oneSport);
//            System.out.println();
                JSONObject jsonObjectMarket = new JSONObject(oneMarket);
                String nameMarket = jsonObjectMarket.getString("N");
                nameMarketArray.add(nameMarket);
//                System.out.println(nameMarket);
                JSONArray jsonArrayMarket = jsonObjectMarket.getJSONArray("Ss");

                for (int t = 0; t < jsonArrayMarket.length(); t++) {

                    String oneSelector = String.valueOf(jsonArrayMarket.get(t));
                    JSONObject jsonObjectSelector = new JSONObject(oneSelector);
                    String selectorPoint = jsonObjectSelector.getString("C");
                    String nameSelector = jsonObjectSelector.getString("N");
                    String isBlockedSelector = jsonObjectSelector.getString("IB");

                    selectorPointArray.add(selectorPoint);
                    nameSelectorArray.add(nameSelector);
                    isBlockedForSelectorArray.add(isBlockedSelector);

//                    System.out.println(selectorPoint+"     "+nameSelector+"     "+isBlockedSelector);
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
            String target = System.getProperty("user.dir") + "/src/test/java/APICasinoGamesCasinoImagesBrokenData/" + readConfig.partnerConfigNum() + partnerName + "DataMarketsEqual1.xlsx";
            XSSFWorkbook workbook = new XSSFWorkbook();
            FileOutputStream file = new FileOutputStream(target);
            XSSFSheet sheet = workbook.createSheet("brokenIMG");
            sheet.setColumnWidth(0, 20000);
            int l = 0;
            for (String err : errorSrcXl) {
                XSSFRow row = sheet.createRow(l);
                row.createCell(0).setCellValue(err);
                l++;
            }
            workbook.write(file);
            workbook.close();
            isPassed = false;
        }
        return isPassed;
    }
    @Test
    public void gatPreMatchGamesMarket() throws UnirestException, JSONException, IOException {
        if (getGamesPartnerName.equals("Craftbet")){
            if (getPreMatchGamesAPICheckMarkets(getPreMatchTree, getMarketByID, getPrematchTreeOrigin,getGamesPartnerName)){
                Assert.assertTrue(true);
            }
            else {
                Assert.fail();
            }
        }
        else{
            logger.error("Please provide Craftbet id  as test Partner ");
            Assert.fail();
        }
    }


}
