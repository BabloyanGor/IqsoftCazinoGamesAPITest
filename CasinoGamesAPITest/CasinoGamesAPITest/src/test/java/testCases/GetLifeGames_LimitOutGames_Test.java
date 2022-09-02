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
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;

public class GetLifeGames_LimitOutGames_Test extends BaseTest{


    private static Period getPeriod(LocalDateTime dob, LocalDateTime now) {
        return Period.between(dob.toLocalDate(), now.toLocalDate());
    }

    private static long[] getTime(LocalDateTime dob, LocalDateTime now) {
        LocalDateTime today = LocalDateTime.of(now.getYear(),
                now.getMonthValue(), now.getDayOfMonth(), dob.getHour(), dob.getMinute(), dob.getSecond());
        Duration duration = Duration.between(today, now);

        long seconds = duration.getSeconds();

        long hours = seconds / SECONDS_PER_HOUR;
        long minutes = ((seconds % SECONDS_PER_HOUR) / SECONDS_PER_MINUTE);
        long secs = (seconds % SECONDS_PER_MINUTE);

        return new long[]{hours, minutes, secs};
    }
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
        JSONArray jsonArrayGames = jsonObjectBody.getJSONArray("Ms");

        Unirest.shutdown();
        logger.info("Games are " + jsonArrayGames.length());
        logger.info("Get Life games Api Response was captured");

        for (int j = 0; j < jsonArrayGames.length(); j++) {

            String arrayObject = String.valueOf(jsonArrayGames.get(j));
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


        for (int i = 0; i < gameType.size(); i++) {

            String timeStart = gameStartTime.get(i);
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime much = LocalDateTime.parse(timeStart);
            Period period = getPeriod(much, now);
            long time[] = getTime(much, now);
//                System.out.println(period.getYears() + " years " +
//                        period.getMonths() + " months " +
//                        period.getDays() + " days " +
//                        time[0] + " hours " +
//                        time[1] + " minutes " +
//                        time[2] + " seconds." + gameID.get(i) + "   " + gameStartTime.get(i));

            String hoursString = String.valueOf(time[0]);
            String minutesString = String.valueOf(time[1]);
            String daysString = String.valueOf(period.getDays());
            String monthsString = String.valueOf(period.getMonths());
            String yearsString = String.valueOf(period.getYears());

            int day = parseInt(daysString);
            int hour = parseInt(hoursString);
            int minutes = parseInt(minutesString);

            int allMinutes;
            if (day == 0) {
                allMinutes = hour * 60 + minutes;
            } else {
                allMinutes = day * 24 * 60 + (hour * 60 + minutes);
            }

//            System.out.println(allMinutes + "  " + day + "  " + hour + "   " + minutes);

            int hoursForLog = allMinutes / 60;
            int minutesForLog = allMinutes % 60;

            String errMessageMinutes = gameID.get(i) + "   " + leagueType.get(i) + "   " + teamOne.get(i) + "  VS  "
                    + teamTwo.get(i) + "   " + gameType.get(i) + "   " + gameStartTime.get(i) + "  Status = "
                    + gameStatus1.get(i) + "   After game start time out = :  " + hoursForLog + " hours  "
                    + minutesForLog + " minutes";

            switch (gameType.get(i)) {
                case "Soccer": {
                    if (allMinutes >= 180) {
                        logger.error("The Time Out games are :   " + errMessageMinutes);
                        errorSrcXl.add(errMessageMinutes);
                    }
                    break;
                }
                case "Basketball": {
                    if (allMinutes > 180) {
                        logger.error("The Time Out games are :   " + errMessageMinutes);
                        errorSrcXl.add(errMessageMinutes);
                    }
                    break;
                }
                case "Baseball": {
                    if (allMinutes >= 240) {
                        logger.error("The Time Out games are :   " + errMessageMinutes);
                        errorSrcXl.add(errMessageMinutes);
                    }
                    break;
                }
                case "Tennis": {
                    if (allMinutes >= 350) {
                        logger.error("The Time Out games are :   " + errMessageMinutes);
                        errorSrcXl.add(errMessageMinutes);
                    }
                    break;
                }
                case "Volleyball": {
                    if (allMinutes >= 210) {
                        logger.error("The Time Out games are :   " + errMessageMinutes);
                        errorSrcXl.add(errMessageMinutes);
                    }
                    break;
                }
                case "Ice Hockey": {
                    if (allMinutes >= 150) {
                        logger.error("The Time Out games are :   " + errMessageMinutes);
                        errorSrcXl.add(errMessageMinutes);
                    }
                    break;
                }
                case "Table Tennis": {
                    if (allMinutes >= 120) {
                        logger.error("The Time Out games are :   " + errMessageMinutes);
                        errorSrcXl.add(errMessageMinutes);
                    }
                    break;
                }
                case "Handball": {
                    if (allMinutes > 120) {
                        logger.error("The Time Out games are :   " + errMessageMinutes);
                        errorSrcXl.add(errMessageMinutes);
                    }
                    break;
                }
                case "E-sports": {
                    if (allMinutes > 181) {
                        logger.error("The Time Out games are :   " + errMessageMinutes);
                        errorSrcXl.add(errMessageMinutes);
                    }
                    break;
                }
                case "Badminton": {
                    if (allMinutes > 150) {
                        logger.error("The Time Out games are :   " + errMessageMinutes);
                        errorSrcXl.add(errMessageMinutes);
                    }
                    break;
                }
                case "Darts": {
                    if (allMinutes >= 121) {
                        logger.error("The Time Out games are :   " + errMessageMinutes);
                        errorSrcXl.add(errMessageMinutes);
                    }
                    break;
                }
                case "Cricket": {
                    if (allMinutes >= 500) {
                        logger.error("The Time Out games are :   " + errMessageMinutes);
                        errorSrcXl.add(errMessageMinutes);
                    }
                    break;
                }
                case "Horse Racing": {
                    if (allMinutes >= 182) {
                        logger.error("The Time Out games are :   " + errMessageMinutes);
                        errorSrcXl.add(errMessageMinutes);
                    }
                    break;
                }
                case "Snooker": {
                    if (allMinutes > 240) {
                        logger.error("The Time Out games are :   " + errMessageMinutes);
                        errorSrcXl.add(errMessageMinutes);
                    }
                    break;
                }
                case "Table nE-Football": {
                    if (allMinutes >= 179) {
                        logger.error("The Time Out games are :   " + errMessageMinutes);
                        errorSrcXl.add(errMessageMinutes);
                    }
                    break;
                }

                default: {
                    if (allMinutes >= 300) {
                        logger.error("The Time Out games are :   " + errMessageMinutes);
                        errorSrcXl.add(errMessageMinutes);
                    }
                }
            }
        }

        logger.info("Time out Games are:  " + errorSrcXl.size());
        if (errorSrcXl.size() == 0) {
            isPassed = true;
        } else {

            writeInExel(errorSrcXl, "/src/test/java/timeOutGames/" + readConfig.partnerConfigNum() + partnerName + "DataTimeOutGamesList.xlsx", "TimeOutGames");
            isPassed = false;
        }
        return isPassed;
    }



    @Test
    public void gatLimitOutGames() throws UnirestException, JSONException, IOException {
        if (getGamesPartnerName.equals("Craftbet")) {
            if (getLimitOutGamesApiCheck(getAllLifeGames, getGamesPartnerName)) {
                Assert.assertTrue(true);
            } else {
                Assert.fail();
            }
        }
        else{
            logger.error("Please provide Craftbet id  as test Partner ");
            Assert.fail();
        }



    }

}
