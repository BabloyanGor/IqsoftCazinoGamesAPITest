package testCases;

import TestDataJson.AlarmMatchesCountJsonVariables;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import java.io.File;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class AlarmMatchesCountJson {
    AlarmMatchesCountJsonVariables alarmMatchesCountJsonVariables = new AlarmMatchesCountJsonVariables();

    public static Logger logger;


//    int preMatchMatchesCount = 0;
    int responseCod = 0;
    String description = null;
    boolean alarmOn = false;




    @BeforeClass
    public void beforeClass() {
        logger = Logger.getLogger("craftBetWorld");
        PropertyConfigurator.configure("Log4j.properties");
    }




    public ArrayList<String> getUpcomingSportsIDs() {
        ArrayList<String> upcomingSportsIDs = new ArrayList<>();
        ArrayList<String> upcomingSportsNames = new ArrayList<>();
        try {
            Unirest.setTimeouts(0, 0);
            HttpResponse<String> response = Unirest.get("https://sportsbookwebsitewebapi.craftbet.com/website/getupcomingsports?LanguageId=en&TimeZone=4&Id=11955")
                    .header("content-type", "application/json; charset=utf-8")
                    .header("origin", "https://sportsbookwebsite.craftbet.com")
                    .asString();

            JSONObject jsonObjectBody = new JSONObject(response.getBody());
            Unirest.shutdown();


            JSONObject jsonObjectResponseObject = new JSONObject(jsonObjectBody.toString());
            JSONArray jsonArraySports = jsonObjectResponseObject.getJSONArray("ResponseObject");
            for (int i = 0; i <jsonArraySports.length();i++ ){
                String first = String.valueOf(jsonArraySports.get(i));
                JSONObject jsonObjectGame = new JSONObject(first);
                String id = String.valueOf(jsonObjectGame.get("Id"));
                String name = String.valueOf(jsonObjectGame.get("Name"));
                upcomingSportsIDs.add(id);
                upcomingSportsNames.add(name);
            }
            return upcomingSportsIDs;

        } catch (Exception e) {
            try {
                Unirest.setTimeouts(0, 0);
                HttpResponse<String> response = Unirest.get("https://sportsbookwebsitewebapi.craftbet.com/website/getupcomingsports?LanguageId=en&TimeZone=4&Id=11955")
                        .header("content-type", "application/json; charset=utf-8")
                        .header("origin", "https://sportsbookwebsite.craftbet.com")
                        .asString();

                JSONObject jsonObjectBody = new JSONObject(response.getBody());
                Unirest.shutdown();

                JSONObject jsonObjectResponseObject = new JSONObject(jsonObjectBody.toString());
                JSONArray jsonArraySports = jsonObjectResponseObject.getJSONArray("ResponseObject");
                for (int i = 0; i <jsonArraySports.length();i++ ){
                    String first = String.valueOf(jsonArraySports.get(i));
                    JSONObject jsonObjectGame = new JSONObject(first);
                    String id = jsonObjectGame.getString("Id");
                    upcomingSportsIDs.add(id);
                }
                return upcomingSportsIDs;
            } catch (Exception k) {
                logger.warn("Upcoming Sports Exception  " + e);
                return null;
            }
        }
    }


    public int getUpcomingMatchesCount(String sportID) {

        int count = 0;
        try {
            Unirest.setTimeouts(0, 0);
            HttpResponse<String> response = Unirest.get("https://sportsbookwebsitewebapi.craftbet.com/website/getupcomingmatches?LanguageId=en&TimeZone=4&SportId="+sportID)
                    .header("content-type", "application/json; charset=utf-8")
                    .header("origin", "https://sportsbookwebsite.craftbet.com")
                    .asString();
            JSONObject jsonObjectBody = new JSONObject(response.getBody());
            Unirest.shutdown();


            JSONObject jsonObjectResponseObject = new JSONObject(jsonObjectBody.toString());
            JSONArray jsonArrayGames = jsonObjectResponseObject.getJSONArray("Ms");
            count = jsonArrayGames.length();
            return count;
        } catch (Exception e) {
            try {
                Unirest.setTimeouts(0, 0);
                HttpResponse<String> response = Unirest.get("https://sportsbookwebsitewebapi.craftbet.com/website/getupcomingmatches?LanguageId=en&TimeZone=4&SportId="+sportID)
                        .header("content-type", "application/json; charset=utf-8")
                        .header("origin", "https://sportsbookwebsite.craftbet.com")
                        .asString();
                JSONObject jsonObjectBody = new JSONObject(response.getBody());
                Unirest.shutdown();

                JSONObject jsonObjectResponseObject = new JSONObject(jsonObjectBody.toString());
                JSONArray jsonArrayGames = jsonObjectResponseObject.getJSONArray("Ms");
                count = jsonArrayGames.length();
                return count;
            } catch (Exception k) {
                logger.warn("Count Exception  " + e);
                return count;
            }
        }
    }



    public ArrayList<String> getLiveSportsIDs() {
        ArrayList<String> liveSportsIDs = new ArrayList<>();
        ArrayList<String> liveSportsNames = new ArrayList<>();
        try {
            Unirest.setTimeouts(0, 0);
            HttpResponse<String> response = Unirest.get("https://sportsbookwebsitewebapi.craftbet.com/website/getlivesports?LanguageId=en&TimeZone=4")
                    .header("content-type", "application/json; charset=utf-8")
                    .header("origin", "https://sportsbookwebsite.craftbet.com")
                    .asString();

            JSONObject jsonObjectBody = new JSONObject(response.getBody());
            Unirest.shutdown();


            JSONObject jsonObjectResponseObject = new JSONObject(jsonObjectBody.toString());
            JSONArray jsonArraySports = jsonObjectResponseObject.getJSONArray("ResponseObject");
            for (int i = 0; i <jsonArraySports.length();i++ ){
                String first = String.valueOf(jsonArraySports.get(i));
                JSONObject jsonObjectGame = new JSONObject(first);
                String id = String.valueOf(jsonObjectGame.get("Id"));
                String name = String.valueOf(jsonObjectGame.get("Name"));
                liveSportsIDs.add(id);
                liveSportsNames.add(name);


            }
            return liveSportsIDs;

        } catch (Exception e) {
            try {
                Unirest.setTimeouts(0, 0);
                HttpResponse<String> response = Unirest.get("https://sportsbookwebsitewebapi.craftbet.com/website/getlivesports?LanguageId=en&TimeZone=4")
                        .header("content-type", "application/json; charset=utf-8")
                        .header("origin", "https://sportsbookwebsite.craftbet.com")
                        .asString();

                JSONObject jsonObjectBody = new JSONObject(response.getBody());
                Unirest.shutdown();

                JSONObject jsonObjectResponseObject = new JSONObject(jsonObjectBody.toString());
                JSONArray jsonArraySports = jsonObjectResponseObject.getJSONArray("ResponseObject");
                for (int i = 0; i <jsonArraySports.length();i++ ){
                    String first = String.valueOf(jsonArraySports.get(i));
                    JSONObject jsonObjectGame = new JSONObject(first);
                    String id = jsonObjectGame.getString("Id");
                    liveSportsIDs.add(id);
                }
                return liveSportsIDs;
            } catch (Exception k) {
                logger.warn("Live Sports Exception  " + e);
                return null;
            }
        }
    }


    public int getLifeMatchesCount(String sportID) {

        int count = 0;
        try {
            Unirest.setTimeouts(0, 0);
            HttpResponse<String> response = Unirest.get("https://sportsbookwebsitewebapi.craftbet.com/website/gettoplivematches?LanguageId=en&TimeZone=4&SportId=" + sportID)
                    .header("origin", "https://sportsbookwebsite.craftbet.com")
                    .header("content-type", "application/json")
                    .asString();
            JSONObject jsonObjectBody = new JSONObject(response.getBody());
            Unirest.shutdown();

            JSONObject jsonObjectResponseObject = new JSONObject(jsonObjectBody.toString());
            JSONArray jsonArrayGames = jsonObjectResponseObject.getJSONArray("Ms");
            count = jsonArrayGames.length();
            return count;
        } catch (Exception e) {
            try {
                Unirest.setTimeouts(0, 0);
                HttpResponse<String> response = Unirest.get("https://sportsbookwebsitewebapi.craftbet.com/website/gettoplivematches?LanguageId=en&TimeZone=4&SportId="+ sportID)
                        .header("origin", "https://sportsbookwebsite.craftbet.com")
                        .header("content-type", "application/json")
                        .asString();
                JSONObject jsonObjectBody = new JSONObject(response.getBody());
                Unirest.shutdown();

                JSONObject jsonObjectResponseObject = new JSONObject(jsonObjectBody.toString());
                JSONArray jsonArrayGames = jsonObjectResponseObject.getJSONArray("Ms");
                count = jsonArrayGames.length();
                return count;
            } catch (Exception k) {
                logger.warn("Count Exception  " + e);
                return count;
            }
        }
    }




















//    public int getPreMatchMatchesCount() {
//
//        int count = 0;
//        try {
//            Unirest.setTimeouts(0, 0);
//            HttpResponse<String> response = Unirest.get("https://sportsbookwebsitewebapi.craftbet.com/website/getprematchtree?LanguageId=en&TimeZone=4")
//                    .header("origin", "https://sportsbookwebsite.craftbet.com")
//                    .header("content-type", "application/json")
//                    .asString();
//            JSONObject jsonObjectBody = new JSONObject(response.getBody());
//            Unirest.shutdown();
//
//            JSONObject jsonObjectResponseObject = new JSONObject(jsonObjectBody.toString());
//            JSONArray jsonArrayGames = jsonObjectResponseObject.getJSONArray("Ss");
//            count = jsonArrayGames.length();
//            return count;
//        } catch (Exception e) {
//            try {
//                Unirest.setTimeouts(0, 0);
//                HttpResponse<String> response = Unirest.get("https://sportsbookwebsitewebapi.craftbet.com/website/getprematchtree?LanguageId=en&TimeZone=4")
//                        .header("origin", "https://sportsbookwebsite.craftbet.com")
//                        .header("content-type", "application/json")
//                        .asString();
//                JSONObject jsonObjectBody = new JSONObject(response.getBody());
//                Unirest.shutdown();
//
//                JSONObject jsonObjectResponseObject = new JSONObject(jsonObjectBody.toString());
//                JSONArray jsonArrayGames = jsonObjectResponseObject.getJSONArray("Ss");
//                count = jsonArrayGames.length();
//                return count;
//            } catch (Exception k) {
//                logger.warn("Count Exception  " + e);
//                return count;
//            }
//        }
//    }

    public void playSound() {
        String path = System.getProperty("user.dir") + "\\src\\test\\java\\mp3\\1.wav";
        try {
//            String bip = path;
//            Media hit = new Media(new File(bip).toURI().toString());
//            com.sun.javafx.application.PlatformImpl.startup(()->{});
//
//            MediaPlayer mediaPlayer = new MediaPlayer(hit);
//            mediaPlayer.play();
//            com.sun.javafx.application.PlatformImpl.exit();
            int i = 0;
            while (i<3){
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(path).getAbsoluteFile());
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.start();
                TimeUnit.SECONDS.sleep(7);
                i++;
            }

        } catch (Exception ex) {
            logger.warn("Error with playing sound.");
            ex.printStackTrace();
        }
    }

    public String currentTime(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime dateTimeNow = LocalDateTime.now();
        String dateTime = dtf.format(dateTimeNow);
        return dateTime;
    }


    @Test
    public void getMatchesCount()  {

        while (true) {
            int upcomingMatchesCount = 0;
            int liveMatchesCount = 0;
            try{
                ArrayList<String> upcomingSportsLocal = getUpcomingSportsIDs();
                for (String id : upcomingSportsLocal) {
                    upcomingMatchesCount += getUpcomingMatchesCount(id);
                }

                ArrayList<String> liveSportsLocal = getLiveSportsIDs();
                for (String id : liveSportsLocal) {
                    liveMatchesCount += getLifeMatchesCount(id);

                }

                SoftAssert softAssert = new SoftAssert();

                int compareUpcomingMatchesCount = 5;
                int compareLifeMatchesCount = 1;


                if (upcomingMatchesCount < compareUpcomingMatchesCount) {
                    responseCod = 1000;
                    description = "Upcoming Matches count is less then: " + compareUpcomingMatchesCount;
                    alarmOn = true;
                    softAssert.fail("Upcoming Matches count is: " + upcomingMatchesCount);
                } else if (liveMatchesCount < compareLifeMatchesCount) {
                    responseCod = 1001;
                    description = "Life Matches count is less then: " + compareLifeMatchesCount;
                    alarmOn = true;
                    softAssert.fail("Life Matches count is: " + liveMatchesCount);
                }
                else {
                    responseCod = 0;
                    description = "null";
                    alarmOn = false;
                    softAssert.assertTrue(true);
                }


                alarmMatchesCountJsonVariables.setUpcomingMatchesCount(upcomingMatchesCount);
                alarmMatchesCountJsonVariables.setLifeMatchesCount(liveMatchesCount);

                alarmMatchesCountJsonVariables.setResponseCode(responseCod);
                alarmMatchesCountJsonVariables.setDescription(description);
                alarmMatchesCountJsonVariables.setAlarmOn(alarmOn);

                JSONObject jsonObjectMain = new JSONObject();
                JSONObject jsonObjectResponseObject = new JSONObject();
//        JSONArray jsonArray = new JSONArray();

                JSONObject jsonResponseObject = new JSONObject();

                jsonObjectResponseObject.put("UpcomingMatchesCount", alarmMatchesCountJsonVariables.getUpcomingMatchesCount());
                jsonObjectResponseObject.put("LifeMatchesCount", alarmMatchesCountJsonVariables.getLifeMatchesCount());


                jsonResponseObject.put("ResponseCod", alarmMatchesCountJsonVariables.getResponseCode());
                jsonResponseObject.put("Description", alarmMatchesCountJsonVariables.getDescription());
                jsonResponseObject.put("ResponseObject", jsonObjectResponseObject);

//        jsonArray.put(jsonResponseObject);
//        jsonObjectMain.put("UpcomingMatches",jsonArray);
                jsonObjectMain.put("AlarmOn", alarmOn);
                jsonObjectMain.put("DateTime", currentTime());
                jsonObjectMain.put("MatchesCount", jsonResponseObject);
                logger.info(jsonObjectMain);

                if (alarmOn) {
                    playSound();
                }
                TimeUnit.SECONDS.sleep(300);
            }
            catch (Exception e){
                logger.info("Exception on while loop: " + e);
            }

        }
    }





}
