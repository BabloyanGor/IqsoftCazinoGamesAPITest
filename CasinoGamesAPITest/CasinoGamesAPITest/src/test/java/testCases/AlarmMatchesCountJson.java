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
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;


import java.io.File;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
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
//        ArrayList<String> upcomingSportsNames = new ArrayList<>();
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
//                upcomingSportsNames.add(name);
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
                logger.fatal("Upcoming Sports Exception  " + e);
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
                logger.fatal("Count Exception  " + e);
                return count;
            }
        }
    }

    public ArrayList<String> getLiveSportsIDs() {
        ArrayList<String> liveSportsIDs = new ArrayList<>();
//        ArrayList<String> liveSportsNames = new ArrayList<>();
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
//                liveSportsNames.add(name);


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
                logger.fatal("Live Sports Exception  " + e);
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
                logger.fatal("Count Exception  " + e);
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
                logger.fatal("Count Exception  " + e);
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
            for (int i = 0; i < jsonArrayGames.length(); i++) {
                JSONObject sportJson = new JSONObject(jsonArrayGames.get(i).toString());
                JSONArray sportArray = sportJson.getJSONArray("Rs");
                for (int j = 0; j < sportArray.length(); j++) {
                    JSONObject competitionJson = new JSONObject(sportArray.get(j).toString());
                    JSONArray competitionArray = competitionJson.getJSONArray("Cs");
                    for (int k = 0; k < competitionArray.length(); k++) {
                        JSONObject matchJson = new JSONObject(competitionArray.get(k).toString());
                        JSONArray matchArray = matchJson.getJSONArray("Ms");
                        count += matchArray.length();
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
                for (int i = 0; i < jsonArrayGames.length(); i++) {
                    JSONObject sportJson = new JSONObject(jsonArrayGames.get(i));
                    JSONArray sportArray = sportJson.getJSONArray("Rs");
                    count += sportArray.length();
                }
                return count;
            } catch (Exception k) {
                logger.fatal("Count Exception  " + e);
                return count;
            }
        }
    }

    public void playSound() {
        try {
            String path = System.getProperty("user.dir") + "\\src\\test\\java\\mp3\\1.wav";
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
            logger.fatal("Exception with playing sound: " + ex);

        }
    }

    public String currentTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime dateTimeNow = LocalDateTime.now();
        return dtf.format(dateTimeNow);
    }


    @Test(description = "Show Graph count of Upcoming, LocalLive, Live, Pre Matches")
    @Severity(SeverityLevel.BLOCKER)
    public void getMatchesCount() {

        // New futures need to be changed

        // 1. Logs for all calls (calls every minute but show graph for 5 cals 1 point (if in 5 calls there is 0 show 0 ))
        // 2. Different sounds for every call for live (One tik)(If possible error message)
        // 3. X-axis should be 300 (every 5 minutes * 300 = 1500minutes 25 hours)
        // 4. Handle network errors (Sound tik + log + error message pop up)

        int upcomingMatchesCount;
        int localLiveMatchesCount;
        int liveMatchesCount;
        int preMatchesCount;

        final int xAxisLength = 300;
        final int timeDelaySeconds = 300;
        float timeDelayMinutesVisualisation = (float) timeDelaySeconds / 60;
        DecimalFormat df = new DecimalFormat("#.#");

        final int compareUpcomingMatchesCount = 5;
        final int compareTopLifeMatchesCount = 1;
        final int compareLifeMatchesCount = 0;
        final int comparePreMatchesCount = 0;

        int p = 0;

        double[] matchesCountArrayXAxis = new double[xAxisLength];

        double[] upcomingMatchesCountArray = new double[xAxisLength];
        double[][] initDataUpcoming = new double[][]{matchesCountArrayXAxis, upcomingMatchesCountArray};

        double[] topLiveMatchesCountArray = new double[xAxisLength];
        double[][] initDataTopLive = new double[][]{matchesCountArrayXAxis, topLiveMatchesCountArray};

        double[] liveMatchesCountArray = new double[xAxisLength];
        double[][] initDataLive = new double[][]{matchesCountArrayXAxis, liveMatchesCountArray};

        double[] preMatchMatchesCountArray = new double[xAxisLength];
        double[][] initDataPreMatch = new double[][]{matchesCountArrayXAxis, preMatchMatchesCountArray};

        // Create Charts
        List<XYChart> charts = new ArrayList<>();

        XYChart chartUpcoming = QuickChart.getChart("Upcoming matches count", "X: " + df.format(timeDelayMinutesVisualisation) + " min", "Value", "Upcoming Matches", initDataUpcoming[0], initDataUpcoming[1]);
        XYChart chartTopLive = QuickChart.getChart("Top Live matches count" , "X: " + df.format(timeDelayMinutesVisualisation) + " min", "Value", "Top Live Matches", initDataTopLive[0], initDataTopLive[1]);
        XYChart chartLive = QuickChart.getChart("Live matches count" , "X: " + df.format(timeDelayMinutesVisualisation) + " min", "Value", "Live Matches", initDataLive[0], initDataLive[1]);
        XYChart chartPreMatches = QuickChart.getChart("Pre matches count", "X: " + df.format(timeDelayMinutesVisualisation) + " min", "Value", "Pre Matches", initDataPreMatch[0], initDataPreMatch[1]);

        charts.add(chartUpcoming);
        charts.add(chartTopLive);
        charts.add(chartLive);
        charts.add(chartPreMatches);

        // Show it
        SwingWrapper<XYChart> sw = new SwingWrapper<>(charts);
        sw.setTitle("IQ-Soft Matches Count Graph    StartTime: " + currentTime());
        sw.displayChartMatrix();

//        SwingWrapper<XYChart> swUpcoming = new SwingWrapper<XYChart>(chartUpcoming);
//        SwingWrapper<XYChart> swLocalLive = new SwingWrapper<XYChart>(chartTopLive);
//        SwingWrapper<XYChart> swLive = new SwingWrapper<XYChart>(chartLive);
//        SwingWrapper<XYChart> swPreMatch = new SwingWrapper<XYChart>(chartPreMatches);

//
//        swUpcoming.displayChart();
//        swLocalLive.displayChart();
//        swLive.displayChart();
//        swPreMatch.displayChart();

        while (true) {
            upcomingMatchesCount = 0;
            localLiveMatchesCount = 0;
            liveMatchesCount = 0;
            preMatchesCount = 0;
//            int tempUpcomingMatchesCount = 0;
//            int tempLocalLiveMatchesCount = 0;
//            int tempLiveMatchesCount = 0;
//            int tempPreMatchesCount = 0;

//            int collectForGraph = 5;
            try {

//                for (int c=0; c <=collectForGraph; c++){
                    ArrayList<String> upcomingSportsLocal = getUpcomingSportsIDs();
                    for (String id : upcomingSportsLocal) {
                        upcomingMatchesCount += getUpcomingMatchesCount(id);
                    }

                    ArrayList<String> liveSportsTop = getLiveSportsIDs();
                    for (String id : liveSportsTop) {
                        localLiveMatchesCount += getLocalLifeMatchesCount(id);
                    }

                    liveMatchesCount = getLifeMatchesCount();
                    preMatchesCount = getPreMatchMatchesCount();


//                    TimeUnit.SECONDS.sleep(timeDelaySeconds);
//                }



//                System.out.println("liveMatchesCount: " + liveMatchesCount + "  preMatchesCount: "+ preMatchesCount );

//                chart.addSeries("Live",upcomingMatchesCountArray);
//                double[][] dataUpcoming = new double[][]{upcomingMatchesCountArrayXAxis ,upcomingMatchesCountArray};
//                double[][] dataLive = new double[][]{liveMatchesCountArrayXAxis ,topLiveMatchesCountArray};
//                chartUpcoming.updateXYSeries("Upcoming Matches", dataUpcoming[0], dataUpcoming[1], null);
//                chartTopLive.updateXYSeries("Local Live Matches", dataLive[0], dataLive[1], null);
//                swUpcoming.repaintChart();
//                swLocalLive.repaintChart();
//                swLive.repaintChart();
//                swPreMatch.repaintChart();



            //for graph
            if (p < upcomingMatchesCountArray.length) {
                upcomingMatchesCountArray[p] = upcomingMatchesCount;
                topLiveMatchesCountArray[p] = localLiveMatchesCount;
                liveMatchesCountArray[p] = liveMatchesCount;
                preMatchMatchesCountArray[p] = preMatchesCount;
                p++;
            } else {
                int lastArrayItem = p-1;

                for (int i = 1; i < upcomingMatchesCountArray.length; i++) {
                    upcomingMatchesCountArray[i - 1] = upcomingMatchesCountArray[i];
                    topLiveMatchesCountArray[i - 1] = topLiveMatchesCountArray[i];
                    liveMatchesCountArray[i - 1] = liveMatchesCountArray[i];
                    preMatchMatchesCountArray[i - 1] = preMatchMatchesCountArray[i];
                }
                upcomingMatchesCountArray[lastArrayItem] = upcomingMatchesCount;
                topLiveMatchesCountArray[lastArrayItem] = localLiveMatchesCount;
                liveMatchesCountArray[lastArrayItem] = liveMatchesCount;
                preMatchMatchesCountArray[lastArrayItem] = preMatchesCount;
            }


                chartUpcoming.updateXYSeries("Upcoming Matches", null, upcomingMatchesCountArray, null);
                chartTopLive.updateXYSeries("Top Live Matches", null, topLiveMatchesCountArray, null);
                chartLive.updateXYSeries("Live Matches", null, liveMatchesCountArray, null);
                chartPreMatches.updateXYSeries("Pre Matches", null, preMatchMatchesCountArray, null);
                for (int l = 0; l < charts.size(); l++) {
                    sw.repaintChart(l);
                }


                if (upcomingMatchesCount < compareUpcomingMatchesCount) {
                    responseCod = 1000;
                    description = "Upcoming Matches count is less then: " + compareUpcomingMatchesCount;
                    alarmOn = true;
//                    logger.error("Upcoming Matches count is: " + upcomingMatchesCount);
                } else if (localLiveMatchesCount < compareTopLifeMatchesCount) {
                    responseCod = 1001;
                    description = "Local Life Matches count is less then: " + compareTopLifeMatchesCount;
                    alarmOn = true;
//                    logger.error("Local Life Matches count is: " + localLiveMatchesCount);
                }
                else if (liveMatchesCount < compareLifeMatchesCount) {
                    responseCod = 1002;
                    description = "Life Matches count is less then: " + compareLifeMatchesCount;
                    alarmOn = false;
//                    logger.error("Life Matches count is: " + liveMatchesCount);
                }
                else if (preMatchesCount < comparePreMatchesCount) {
                    responseCod = 1003;
                    description = "Pre Matches count is less then: " + comparePreMatchesCount;
                    alarmOn = false;
//                    logger.error("Pre Matches count is: " + preMatchesCount);
                }else {
                    responseCod = 0;
                    description = "null";
                    alarmOn = false;
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
                    logger.error("UpcomingMatchesCount: " + upcomingMatchesCount + "  localLiveMatchesCount: " + localLiveMatchesCount + "  "
                            + "  liveMatchesCount: " + liveMatchesCount + "  "+ "  preMatchesCount: " + preMatchesCount + "  "+ currentTime());
                    playSound();
                }
                else{
                    logger.info("UpcomingMatchesCount: " + upcomingMatchesCount + "  localLiveMatchesCount: " + localLiveMatchesCount + "  "
                            + "  liveMatchesCount: " + liveMatchesCount + "  "+ "  preMatchesCount: " + preMatchesCount + "  "+ currentTime());
                }

                TimeUnit.SECONDS.sleep(timeDelaySeconds);

            } catch (Exception e) {
                logger.fatal("Exception on while loop: " + e);
            }
//            System.out.println();
        }


    }


}
