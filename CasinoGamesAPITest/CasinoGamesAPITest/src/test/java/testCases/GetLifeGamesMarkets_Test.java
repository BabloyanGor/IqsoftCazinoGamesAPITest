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

public class GetLifeGamesMarkets_Test extends BaseTest{


    public boolean getLifeMatchGamesAPICheckMarkets(String getAllLifeGames, String marketOrigin,  String partnerName)
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

        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.get(getAllLifeGames).asString();

        logger.info("Get games Api call was sent");
        JSONObject jsonObjectBody = new JSONObject(response.getBody());
        JSONArray jsonArrayGames = jsonObjectBody.getJSONArray("Ms");

        logger.info("Games are " + jsonArrayGames.length());

        logger.info("Get Life games Api Response was captured");

        for (int j = 0; j < jsonArrayGames.length(); j++) {

            String arrayObject = String.valueOf(jsonArrayGames.get(j));
            JSONObject jsonObjectGame = new JSONObject(arrayObject);
            String MI = jsonObjectGame.getString("MI");  // Game ID
            gameIDs.add(MI);
        }

        logger.info("Games Ides = " + gameIDs.size());

//        craftBet_01_header_page.waitAction(2000);
        for (String gameId : gameIDs){

            Unirest.setTimeouts(0, 0);
            HttpResponse<String> responseLifeGames = Unirest.get("https://sportsbookwebsitewebapi.craftbet.com/website/getmarketsbymatchid?LanguageId=en&TimeZone=4&MatchId="+gameId+"&OddsType=0")
                    .header("origin", marketOrigin)
                    .asString();
            JSONObject jsonObjectMarketsResponseBody = new JSONObject(responseLifeGames.getBody());
//            System.out.println(jsonObjectMarketsResponseBody.toString());
            JSONArray jsonArrayAllMarkets = jsonObjectMarketsResponseBody.getJSONArray("Markets");

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
                        if (point<=1 && isBlockedSelector.equals("false")){
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
    public void gatLifeMatchGamesMarkets() throws UnirestException, JSONException, IOException {
        if (getGamesPartnerName.equals("Craftbet")){
            if (getLifeMatchGamesAPICheckMarkets(getAllLifeGames, getMarketByIDOrigin,getGamesPartnerName)){
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
