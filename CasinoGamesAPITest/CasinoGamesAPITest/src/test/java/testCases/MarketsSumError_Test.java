package testCases;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class MarketsSumError_Test extends BaseTest{
    public boolean getLimitOutGamesApiCheck(String getGamesAPIUrl, String partnerName)
            throws UnirestException, JSONException, IOException {

        boolean isPassed;
        int k = 1;
        ArrayList<String> gameID = new ArrayList<>();
        ArrayList<String> leagueType = new ArrayList<>();
        ArrayList<String> gameType = new ArrayList<>();
        ArrayList<String> gameStartTime = new ArrayList<>();
        ArrayList<String> gameStatus1 = new ArrayList<>();
        ArrayList<String> gameStatus2 = new ArrayList<>();
        ArrayList<String> gameStatus3 = new ArrayList<>();
        ArrayList<String> gameStatus4 = new ArrayList<>();
        ArrayList<String> teamOne = new ArrayList<>();
        ArrayList<String> teamTwo = new ArrayList<>();

        ArrayList<String> errorSrcXl = new ArrayList<>();



        Unirest.setTimeouts(0, 0);
        HttpResponse<String> response = Unirest.get(getGamesAPIUrl).asString();


        logger.info("Get games Api call was sent");
        JSONObject jsonObjectBody = new JSONObject(response.getBody());
        JSONArray jsonArrayMatches = jsonObjectBody.getJSONArray("Ms");
        Unirest.shutdown();
        logger.info("Games are " + jsonArrayMatches.length());
        logger.info("Get Life games Api Response was captured");

        for (int j = 0; j < jsonArrayMatches.length(); j++) {

            String arrayObject = String.valueOf(jsonArrayMatches.get(j));
            JSONObject jsonObjectGame = new JSONObject(arrayObject);
            String MI = jsonObjectGame.get("MI").toString();  // Game ID
            String CN = jsonObjectGame.get("CN").toString();  //League Name
            String SN = jsonObjectGame.get("SN").toString();    // Game Type
            String ST = jsonObjectGame.get("ST").toString();    //Game start Time
            String status1 = jsonObjectGame.get("S").toString();    //Status 1 (0 - prematch, 1 - Life, 2 -finished game)

            JSONArray jsonArrayTeams = jsonObjectGame.getJSONArray("Cs");
            JSONObject jsonObjectTeam1 = jsonArrayTeams.getJSONObject(0);
            JSONObject jsonObjectTeam2 = jsonArrayTeams.getJSONObject(1);
            String team1 = jsonObjectTeam1.get("TN").toString();
            String team2 = jsonObjectTeam2.get("TN").toString();

//            System.out.println("team1 " + team1 + "team2" + team2);

            JSONArray jsonArrayTMs = jsonObjectGame.getJSONArray("TMs");
            JSONObject TMs1;
            JSONObject TMs2;
            JSONObject TMs3;
            try {
                TMs1 = jsonArrayTMs.getJSONObject(0);
                TMs2 = jsonArrayTMs.getJSONObject(1);
                TMs3 = jsonArrayTMs.getJSONObject(2);
            } catch (Exception e) {

            }


//            String status2 = TMs1.getString("S");    //Status 2 (0 - prematch, 1 - Life, 2 -finished game)
//            String status3 = TMs2.getString("S");    //Status 3 (0 - prematch, 1 - Life, 2 -finished game)
//            String status4 = TMs3.getString("S");    //Status 4 (0 - prematch, 1 - Life, 2 -finished game)

//            System.out.println(status2 + status3+status4);

            gameID.add(MI);
            leagueType.add(CN);
            gameType.add(SN);
            gameStartTime.add(ST);
            gameStatus1.add(status1);
//            gameStatus2.add(status2);
//            gameStatus3.add(status3);
//            gameStatus4.add(status4);
            teamOne.add(team1);
            teamTwo.add(team2);
            k++;
        }











        logger.info("Error Markets are:  " + errorSrcXl.size());
        if (errorSrcXl.size() == 0) {
            isPassed = true;
        } else {

            writeInExel(errorSrcXl, "/src/test/java/timeOutGames/" + readConfig.partnerConfigNum() + partnerName + "DataErrorMarketsList.xlsx", "ErrorMarkets");
            isPassed = false;
        }
        return isPassed;
    }
    }
