package testCases;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.json.JSONArray;
import org.json.JSONObject;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


import java.io.File;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class AlarmMatchesCountJson {
    public static Logger logger;

//    AlarmMatchesCountJsonVariables alarmMatchesCountJsonVariables = new AlarmMatchesCountJsonVariables();
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
            for (int i = 0; i < jsonArraySports.length(); i++) {
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
                for (int i = 0; i < jsonArraySports.length(); i++) {
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
            HttpResponse<String> response = Unirest.get("https://sportsbookwebsitewebapi.craftbet.com/website/getupcomingmatches?LanguageId=en&TimeZone=4&SportId=" + sportID)
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
                HttpResponse<String> response = Unirest.get("https://sportsbookwebsitewebapi.craftbet.com/website/getupcomingmatches?LanguageId=en&TimeZone=4&SportId=" + sportID)
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
            for (int i = 0; i < jsonArraySports.length(); i++) {
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
                for (int i = 0; i < jsonArraySports.length(); i++) {
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

    public int getLocalLifeMatchesCount(String sportID) {
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
            } catch (Exception k) {
                logger.warn("Count Exception  " + e);
                return count;
            }
        }
    }

    public int getLifeMatchesCount() {
        int count = 0;
        try {
            Unirest.setTimeouts(0, 0);
            HttpResponse<String> response = Unirest.get("https://sportsbookwebsitewebapi.craftbet.com/website/getlivematchescount?LanguageId=en&TimeZone=4")
                    .header("content-type", "application/json")
                    .header("origin", "https://sportsbookwebsite.craftbet.com")
                    .asString();

            JSONObject jsonObjectBody = new JSONObject(response.getBody());
            Unirest.shutdown();

            JSONObject jsonObjectResponseObject = new JSONObject(jsonObjectBody.toString());
            count = jsonObjectResponseObject.getInt("TotalCount");
            return count;
        } catch (Exception e) {
            try {
                Unirest.setTimeouts(0, 0);
                HttpResponse<String> response = Unirest.get("https://sportsbookwebsitewebapi.craftbet.com/website/getlivematchescount?LanguageId=en&TimeZone=4")
                        .header("content-type", "application/json")
                        .header("origin", "https://sportsbookwebsite.craftbet.com")
                        .asString();

                JSONObject jsonObjectBody = new JSONObject(response.getBody());
                Unirest.shutdown();

                JSONObject jsonObjectResponseObject = new JSONObject(jsonObjectBody.toString());
                count = jsonObjectResponseObject.getInt("TotalCount");
                return count;
            } catch (Exception k) {
                logger.warn("Count Exception  " + e);
                return count;
            }
        }
    }

    public int getPreMatchMatchesCount() {
        int count = 0;
        try {
            Unirest.setTimeouts(0, 0);
            HttpResponse<String> response = Unirest.get("https://sportsbookwebsitewebapi.craftbet.com/website/getprematchtree?LanguageId=en&TimeZone=4")
                    .header("origin", "https://sportsbookwebsite.craftbet.com")
                    .header("content-type", "application/json")
                    .asString();
            JSONObject jsonObjectBody = new JSONObject(response.getBody());
            Unirest.shutdown();

            JSONObject jsonObjectResponseObject = new JSONObject(jsonObjectBody.toString());
            JSONArray jsonArrayGames = jsonObjectResponseObject.getJSONArray("Ss");
            for (int i = 0; i< jsonArrayGames.length(); i++){
                JSONObject sportJson = new JSONObject(jsonArrayGames.get(i).toString());
                JSONArray sportArray = sportJson.getJSONArray("Rs");
                for (int j = 0 ; j < sportArray.length(); j++ ){
                    JSONObject competitionJson = new JSONObject(sportArray.get(j).toString());
                    JSONArray competitionArray = competitionJson.getJSONArray("Cs");
                    for (int k = 0; k<competitionArray.length(); k++){
                        JSONObject matchJson = new JSONObject(competitionArray.get(k).toString());
                        JSONArray matchArray = matchJson.getJSONArray("Ms");
                        count+=matchArray.length();
                    }
                }
            }
            return count;

        } catch (Exception e) {
            try {
                Unirest.setTimeouts(0, 0);
                HttpResponse<String> response = Unirest.get("https://sportsbookwebsitewebapi.craftbet.com/website/getprematchtree?LanguageId=en&TimeZone=4")
                        .header("origin", "https://sportsbookwebsite.craftbet.com")
                        .header("content-type", "application/json")
                        .asString();
                JSONObject jsonObjectBody = new JSONObject(response.getBody());
                Unirest.shutdown();

                JSONObject jsonObjectResponseObject = new JSONObject(jsonObjectBody.toString());
                JSONArray jsonArrayGames = jsonObjectResponseObject.getJSONArray("Ss");
                for (int i = 0; i< jsonArrayGames.length(); i++){
                    JSONObject sportJson = new JSONObject(jsonArrayGames.get(i));
                    JSONArray sportArray = sportJson.getJSONArray("Rs");
                    count+=sportArray.length();
                }
                return count;
            } catch (Exception k) {
                logger.warn("Count Exception  " + e);
                return count;
            }
        }
    }

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
            while (i < 2) {
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

    public String currentTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime dateTimeNow = LocalDateTime.now();
        String dateTime = dtf.format(dateTimeNow);
        return dateTime;
    }

//    public static void runGraph()throws Exception{
//        double phase = 0;
//        double[][] initdata = getSineData(phase);
//
//        // Create Chart
//        final XYChart chart = QuickChart.getChart("Upcoming matches count", "Time", "Data", "sine", initdata[0], initdata[1]);
//
//        // Show it
//        final SwingWrapper<XYChart> sw = new SwingWrapper<XYChart>(chart);
//        sw.displayChart();
//
//        while (true) {
//
////            phase += 2 * Math.PI * 2 / 20.0;
//
//            Thread.sleep(100);
//
//            final double[][] data = getSineData(phase);
//
//            chart.updateXYSeries("sine", data[0], data[1], null);
//            sw.repaintChart();
//        }
//    }


//    private static double[][] getSineData(double[] upcomingArray,double[] upcomingArrayX) {

//        double[] xData = new double[10];
//        double[] yData = new double[10];
//        for (int i = 0; i < xData.length; i++) {
//            xData[i] = upcoming;
//            yData[i] = i;
//        }
//        return new double[][] { upcomingArrayX, upcomingArray  };
//    }


    @Test(description = "Show Graph count of Upcoming, LocalLive, Live, Pre Matches")
    @Severity(SeverityLevel.BLOCKER)
    public void getMatchesCount() {
        SoftAssert softAssert = new SoftAssert();
        int upcomingMatchesCount;
        int localLiveMatchesCount;
        int liveMatchesCount;
        int preMatchesCount;

        final int xyAxisLength = 20;
        final int timeDelaySeconds = 5;
        float timeDelayMinutesVisualisation =(float) timeDelaySeconds/60;
        DecimalFormat df = new DecimalFormat("#.#");


        final int compareUpcomingMatchesCount = 5;
        final int compareLocalLifeMatchesCount = 1;
        int p = 0;

        double[] matchesCountArrayXAxis = new double[xyAxisLength];

        double[] upcomingMatchesCountArray = new double[xyAxisLength];
        double[][] initDataUpcoming = new double[][]{matchesCountArrayXAxis, upcomingMatchesCountArray};

        double[] localLiveMatchesCountArray = new double[xyAxisLength];
        double[][] initDataLocalLive = new double[][]{matchesCountArrayXAxis, upcomingMatchesCountArray};

        double[] liveMatchesCountArray = new double[xyAxisLength];
        double[][] initDataLive = new double[][]{matchesCountArrayXAxis, upcomingMatchesCountArray};

        double[] preMatchMatchesCountArray = new double[xyAxisLength];
        double[][] initDataPreMatch = new double[][]{matchesCountArrayXAxis, upcomingMatchesCountArray};


        // Create Charts
        XYChart chartUpcoming = QuickChart.getChart("Upcoming matches count StartTime: " + currentTime(), "X: "+df.format(timeDelayMinutesVisualisation) + " min", "Value", "Upcoming Matches", initDataUpcoming[0], initDataUpcoming[1]);
        XYChart chartLocalLive = QuickChart.getChart("Local Live matches count StartTime: " + currentTime(), "X: "+df.format(timeDelayMinutesVisualisation) + " min", "Value", "Local Live Matches", initDataLocalLive[0], initDataLocalLive[1]);
        XYChart chartLive = QuickChart.getChart("Live matches count StartTime: " + currentTime(), "X: "+df.format(timeDelayMinutesVisualisation) + " min", "Value", "Live Matches", initDataLive[0], initDataLive[1]);
        XYChart chartPreMatches = QuickChart.getChart("Pre matches count StartTime: " + currentTime(), "X: "+df.format(timeDelayMinutesVisualisation) + " min", "Value", "Pre Matches", initDataPreMatch[0], initDataPreMatch[1]);


        // Show it

        SwingWrapper<XYChart> swUpcoming = new SwingWrapper<XYChart>(chartUpcoming);
        SwingWrapper<XYChart> swLocalLive = new SwingWrapper<XYChart>(chartLocalLive);
        SwingWrapper<XYChart> swLive = new SwingWrapper<XYChart>(chartLive);
        SwingWrapper<XYChart> swPreMatch = new SwingWrapper<XYChart>(chartPreMatches);


        swUpcoming.displayChart();
        swLocalLive.displayChart();
        swLive.displayChart();
        swPreMatch.displayChart();

        while (true) {
            upcomingMatchesCount = 0;
            localLiveMatchesCount = 0;
            liveMatchesCount = 0;
            preMatchesCount = 0;

            try {
                ArrayList<String> upcomingSportsLocal = getUpcomingSportsIDs();
                for (String id : upcomingSportsLocal) {
                    upcomingMatchesCount += getUpcomingMatchesCount(id);
                }
                upcomingMatchesCountArray[p] = upcomingMatchesCount;


                ArrayList<String> liveSportsLocal = getLiveSportsIDs();
                for (String id : liveSportsLocal) {
                    localLiveMatchesCount += getLocalLifeMatchesCount(id);
                }
                localLiveMatchesCountArray[p] = localLiveMatchesCount;

                liveMatchesCount = getLifeMatchesCount();
                liveMatchesCountArray[p] = liveMatchesCount;
                preMatchesCount = getPreMatchMatchesCount();
                preMatchMatchesCountArray[p] = preMatchesCount;
//                System.out.println("liveMatchesCount: " + liveMatchesCount + "  preMatchesCount: "+ preMatchesCount );

//                chart.addSeries("Live",upcomingMatchesCountArray);
//                double[][] dataUpcoming = new double[][]{upcomingMatchesCountArrayXAxis ,upcomingMatchesCountArray};
//                double[][] dataLive = new double[][]{liveMatchesCountArrayXAxis ,localLiveMatchesCountArray};
//                chartUpcoming.updateXYSeries("Upcoming Matches", dataUpcoming[0], dataUpcoming[1], null);
//                chartLocalLive.updateXYSeries("Local Live Matches", dataLive[0], dataLive[1], null);

                chartUpcoming.updateXYSeries("Upcoming Matches", null, upcomingMatchesCountArray, null);
                chartLocalLive.updateXYSeries("Local Live Matches", null, localLiveMatchesCountArray, null);
                chartLive.updateXYSeries("Live Matches", null, liveMatchesCountArray, null);
                chartPreMatches.updateXYSeries("Pre Matches", null, preMatchMatchesCountArray, null);

                swUpcoming.repaintChart();
                swLocalLive.repaintChart();
                swLive.repaintChart();
                swPreMatch.repaintChart();


                if (upcomingMatchesCount < compareUpcomingMatchesCount) {
                    responseCod = 1000;
                    description = "Upcoming Matches count is less then: " + compareUpcomingMatchesCount;
                    alarmOn = true;
                    softAssert.fail("Upcoming Matches count is: " + upcomingMatchesCount);
                } else if (localLiveMatchesCount < compareLocalLifeMatchesCount) {
                    responseCod = 1001;
                    description = "Local Life Matches count is less then: " + compareLocalLifeMatchesCount;
                    alarmOn = true;
                    softAssert.fail("Local Life Matches count is: " + localLiveMatchesCount);
                } else {
                    responseCod = 0;
                    description = "null";
                    alarmOn = false;
                    softAssert.assertTrue(true);
                }


//                alarmMatchesCountJsonVariables.setUpcomingMatchesCount(upcomingMatchesCount);
//                alarmMatchesCountJsonVariables.setLifeMatchesCount(localLiveMatchesCount);
//
//                alarmMatchesCountJsonVariables.setResponseCode(responseCod);
//                alarmMatchesCountJsonVariables.setDescription(description);
//                alarmMatchesCountJsonVariables.setAlarmOn(alarmOn);
//
//                JSONObject jsonObjectMain = new JSONObject();
//                JSONObject jsonObjectResponseObject = new JSONObject();
//////        JSONArray jsonArray = new JSONArray();
//
//                JSONObject jsonResponseObject = new JSONObject();
//
//                jsonObjectResponseObject.put("UpcomingMatchesCount", alarmMatchesCountJsonVariables.getUpcomingMatchesCount());
//                jsonObjectResponseObject.put("LifeMatchesCount", alarmMatchesCountJsonVariables.getLifeMatchesCount());
//
//
//                jsonResponseObject.put("ResponseCod", alarmMatchesCountJsonVariables.getResponseCode());
//                jsonResponseObject.put("Description", alarmMatchesCountJsonVariables.getDescription());
//                jsonResponseObject.put("ResponseObject", jsonObjectResponseObject);
//
//////        jsonArray.put(jsonResponseObject);
//////        jsonObjectMain.put("UpcomingMatches",jsonArray);
//                jsonObjectMain.put("AlarmOn", alarmOn);
//                jsonObjectMain.put("DateTime", currentTime());
//                jsonObjectMain.put("MatchesCount", jsonResponseObject);
//                logger.info(jsonObjectMain);

                if (alarmOn) {
                    logger.fatal("UpcomingMatchesCount: " + upcomingMatchesCount + "  localLiveMatchesCount: " + localLiveMatchesCount + "  " + currentTime());
                    playSound();
                }
                TimeUnit.SECONDS.sleep(timeDelaySeconds);
            } catch (Exception e) {
                logger.info("Exception on while loop: " + e);
            }


            //for graph
            if (p < upcomingMatchesCountArray.length - 1) {
                p++;
            } else {
                for (int i = 1; i < upcomingMatchesCountArray.length; i++) {
                    upcomingMatchesCountArray[i - 1] = upcomingMatchesCountArray[i];
                    localLiveMatchesCountArray[i - 1] = localLiveMatchesCountArray[i];
                }
                upcomingMatchesCountArray[upcomingMatchesCountArray.length - 1] = upcomingMatchesCount;
                localLiveMatchesCountArray[localLiveMatchesCountArray.length - 1] = localLiveMatchesCount;
            }

        }


    }


}
