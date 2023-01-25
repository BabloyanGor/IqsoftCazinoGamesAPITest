package Graphs;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.json.JSONArray;
import org.json.JSONObject;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AlarmMatchesCountJson {
    public static Logger logger;
    public static int delayTime = 5;

    static boolean firstCall = true;

    AlarmMatchesCountJson() {
        logger = Logger.getLogger("craftBetWorld");
        PropertyConfigurator.configure("Log4j.properties");
    }

    public static void main(String[] args) {
        AlarmMatchesCountJson alarmMatchesCountJson = new AlarmMatchesCountJson();

//        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
//        service.schedule(AlarmMatchesCountJson::getMatchesCount,delayTime ,TimeUnit.SECONDS);
        getMatchesCount();

    }


    public static void getMatchesCount() {
        int responseCod = 0;
        String description = null;
        boolean alarmOn = false;

        int upcomingMatchesCount;
        int localLiveMatchesCount;
        int liveMatchesCount;
        int preMatchesCount;

        ArrayList<String> upcomingSportsLocal;
        ArrayList<String> liveSportsTop;

        final int xAxisLength = 300;
        final int timeDelaySeconds = 1;
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
        XYChart chartTopLive = QuickChart.getChart("Top Live matches count", "X: " + df.format(timeDelayMinutesVisualisation) + " min", "Value", "Top Live Matches", initDataTopLive[0], initDataTopLive[1]);
        XYChart chartLive = QuickChart.getChart("Live matches count", "X: " + df.format(timeDelayMinutesVisualisation) + " min", "Value", "Live Matches", initDataLive[0], initDataLive[1]);
        XYChart chartPreMatches = QuickChart.getChart("Pre matches count", "X: " + df.format(timeDelayMinutesVisualisation) + " min", "Value", "Pre Matches", initDataPreMatch[0], initDataPreMatch[1]);

        charts.add(chartUpcoming);
        charts.add(chartTopLive);
        charts.add(chartLive);
        charts.add(chartPreMatches);

        // Show it
        SwingWrapper<XYChart> sw = new SwingWrapper<>(charts);
        sw.setTitle("IQ-Soft Matches Count Graph    StartTime: " + currentTime());
        sw.displayChartMatrix();


        while (true) {
            upcomingMatchesCount = 0;
            localLiveMatchesCount = 0;
            liveMatchesCount = 0;
            preMatchesCount = 0;

            try {

                upcomingSportsLocal = getUpcomingSportsIDs();
                for (String id : upcomingSportsLocal) {
                    upcomingMatchesCount += getUpcomingMatchesCount(id);
                }

                liveSportsTop = getLiveSportsIDs();
                for (String id : liveSportsTop) {
                    localLiveMatchesCount += getLocalLifeMatchesCount(id);
                }

                liveMatchesCount = getLifeMatchesCount();
                preMatchesCount = getPreMatchMatchesCount();

                if (p < upcomingMatchesCountArray.length) {
                    upcomingMatchesCountArray[p] = upcomingMatchesCount;
                    topLiveMatchesCountArray[p] = localLiveMatchesCount;
                    liveMatchesCountArray[p] = liveMatchesCount;
                    preMatchMatchesCountArray[p] = preMatchesCount;
                    p++;
                } else {
                    int lastArrayItem = p - 1;

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
                } else if (liveMatchesCount < compareLifeMatchesCount) {
                    responseCod = 1002;
                    description = "Life Matches count is less then: " + compareLifeMatchesCount;
                    alarmOn = true;
//                    logger.error("Life Matches count is: " + liveMatchesCount);
                } else if (preMatchesCount < comparePreMatchesCount) {
                    responseCod = 1003;
                    description = "Pre Matches count is less then: " + comparePreMatchesCount;
                    alarmOn = true;
//                    logger.error("Pre Matches count is: " + preMatchesCount);
                } else {
                    responseCod = 0;
                    description = "null";
                    alarmOn = false;
                }
                if (alarmOn) {
                    logger.error("UpcomingMatchesCount: " + upcomingMatchesCount + "  localLiveMatchesCount: " + localLiveMatchesCount + "  "
                            + "  liveMatchesCount: " + liveMatchesCount + "  " + "  preMatchesCount: " + preMatchesCount + "  " + currentTime());
                    playSound();
                } else {
                    logger.info("UpcomingMatchesCount: " + upcomingMatchesCount + "  localLiveMatchesCount: " + localLiveMatchesCount + "  "
                            + "  liveMatchesCount: " + liveMatchesCount + "  " + "  preMatchesCount: " + preMatchesCount + "  " + currentTime());
                }

                TimeUnit.SECONDS.sleep(timeDelaySeconds);

            } catch (Exception e) {
                logger.fatal("Exception on while loop: " + e);
            }
//            System.out.println();
        }


    }


    public static void playSound() {
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

    public static String currentTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime dateTimeNow = LocalDateTime.now();
        return dtf.format(dateTimeNow);
    }


    public static ArrayList<String> getUpcomingSportsIDs() {
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
//                String name = String.valueOf(jsonObjectGame.get("Name"));
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

    public static int getUpcomingMatchesCount(String sportID) {

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

    public static ArrayList<String> getLiveSportsIDs() {
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

    public static int getLocalLifeMatchesCount(String sportID) {
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

    public static int getLifeMatchesCount() {
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

    public static int getPreMatchMatchesCount() {
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


}
