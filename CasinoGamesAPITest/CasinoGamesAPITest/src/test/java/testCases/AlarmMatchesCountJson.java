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

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class AlarmMatchesCountJson {
    AlarmMatchesCountJsonVariables alarmMatchesCountJsonVariables = new AlarmMatchesCountJsonVariables();

    public static Logger logger;
    int upcomingMatchesCount = 0;
    int lifeMatchesCount = 0;
//    int preMatchMatchesCount = 0;
    int responseCod = 0;
    String description = null;
    boolean alarmOn = false;
    ArrayList<String> upcomingSports = new ArrayList<>();
    public ArrayList<String> getUpcomingSportsIDs() {

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
                upcomingSports.add(id);
            }
            return upcomingSports;

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
                    upcomingSports.add(id);
                }
                return upcomingSports;
            } catch (Exception k) {
                System.out.println("Upcoming Sports Exception  " + e);
                return null;
            }
        }
    }


    public int getUpcomingMatchesCount(String sportID) {

        int count = 0;
        try {
            Unirest.setTimeouts(0, 0);
            HttpResponse<String> response = Unirest.get("https://sportsbookwebsitewebapi.craftbet.com/website/getupcomingmatches?LanguageId=en&TimeZone=4&SportId="+sportID+"&IncludeMarketTypeIds=1&IncludeMarketTypeIds=6021&IncludeMarketTypeIds=38")
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
                HttpResponse<String> response = Unirest.get("https://sportsbookwebsitewebapi.craftbet.com/website/getupcomingmatches?LanguageId=en&TimeZone=4&SportId=1&IncludeMarketTypeIds=1&IncludeMarketTypeIds=6021&IncludeMarketTypeIds=38")
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
                System.out.println("Count Exception  " + e);
                return count;
            }
        }
    }


    public int getLifeMatchesCount() {

        int count = 0;
        try {
            Unirest.setTimeouts(0, 0);
            HttpResponse<String> response = Unirest.get("https://sportsbookwebsitewebapi.craftbet.com/website/gettoplivematches?LanguageId=en&TimeZone=4&SportId=3&IncludeMarketTypeIds=3001&IncludeMarketTypeIds=3007&IncludeMarketTypeIds=5649")
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
                HttpResponse<String> response = Unirest.get("https://sportsbookwebsitewebapi.craftbet.com/website/gettoplivematches?LanguageId=en&TimeZone=4&SportId=3&IncludeMarketTypeIds=3001&IncludeMarketTypeIds=3007&IncludeMarketTypeIds=5649")
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
                System.out.println("Count Exception  " + e);
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
//                System.out.println("Count Exception  " + e);
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
//                TimeUnit.SECONDS.sleep(7);
                i++;
            }

        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }


    @BeforeClass
    public void beforeClass() {
        logger = Logger.getLogger("craftBetWorld");
        PropertyConfigurator.configure("Log4j.properties");
    }

    @Test
    public void getMatchesCount() throws InterruptedException {
        ArrayList<String> upcomingSportsLocal = getUpcomingSportsIDs();
        for (String id : upcomingSportsLocal) {
            upcomingMatchesCount += getUpcomingMatchesCount(id);
        }



        while (true) {
            try{
                SoftAssert softAssert = new SoftAssert();
                lifeMatchesCount = getLifeMatchesCount();

                int compareUpcomingMatchesCount = 5;
                int compareLifeMatchesCount = 1;


                if (upcomingMatchesCount < compareUpcomingMatchesCount) {
                    responseCod = 1000;
                    description = "Upcoming Matches count is less then: " + compareUpcomingMatchesCount;
                    alarmOn = true;
                    softAssert.fail("Upcoming Matches count is: " + upcomingMatchesCount);
                } else if (lifeMatchesCount < compareLifeMatchesCount) {
                    responseCod = 1001;
                    description = "Life Matches count is less then: " + compareLifeMatchesCount;
                    alarmOn = true;
                    softAssert.fail("Life Matches count is: " + getLifeMatchesCount());
                }
                else {
                    responseCod = 0;
                    description = "null";
                    alarmOn = false;
                    softAssert.assertTrue(true);
                }


                alarmMatchesCountJsonVariables.setUpcomingMatchesCount(upcomingMatchesCount);
                alarmMatchesCountJsonVariables.setLifeMatchesCount(lifeMatchesCount);

                alarmMatchesCountJsonVariables.setResponseCode(responseCod);
                alarmMatchesCountJsonVariables.setDescription(description);
                alarmMatchesCountJsonVariables.setAlarmOn(alarmOn);

                JSONObject jsonObjectMain = new JSONObject();     // Working version
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
                jsonObjectMain.put("MatchesCount", jsonResponseObject);
                logger.info(jsonObjectMain);

                if (alarmOn) {
                    playSound();
                }
                TimeUnit.MINUTES.sleep(5);
            }
            catch (Exception e){
                logger.info("Exception on while loop: " + e);
            }

        }
    }





}
