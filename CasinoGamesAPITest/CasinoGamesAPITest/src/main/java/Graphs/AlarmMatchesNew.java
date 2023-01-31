package Graphs;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.json.JSONArray;
import org.json.JSONObject;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.demo.charts.ExampleChart;
import org.knowm.xchart.style.Styler;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.*;
import java.io.File;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.apache.commons.lang3.ArrayUtils.contains;

public class AlarmMatchesNew  implements ExampleChart<XYChart> {

    static SwingWrapper<XYChart> sw ;
    static XYChart chart;
    static final int averageNum = 2;
    static final int xAxisLength = 10;
    static final int timeDelaySeconds = 1;
    static final int compareUpcomingMatchesCount = 5;
    static final int compareTopLifeMatchesCount = 1;
    static final int compareLifeMatchesCount = 1;
    static final int comparePreMatchesCount = 1;
    public static Logger logger;
    static List<XYChart> charts = new ArrayList<>();
    static double[] matchesCountArrayXAxis = new double[xAxisLength];
    static double[] preMatchMatchesCountArray = new double[xAxisLength];
    static double[] upcomingMatchesCountArray = new double[xAxisLength];
    static double[] topLiveMatchesCountArray = new double[xAxisLength];
    static double[] liveMatchesCountArray = new double[xAxisLength];



    static boolean alarmOnUpcoming = false;
    static boolean alarmOnPreMatch = false;
    static boolean alarmOnTopLive = false;
    static boolean alarmOnLive = false;

    static int upcomingMatchesCount;
    static int topLiveMatchesCount;
    static int liveMatchesCount;
    static int preMatchesCount;

    static int[] tempUpcomingMatchesLocal = new int[averageNum];
    static int[] tempTopLiveMatchesCount = new int[averageNum];
    static int[] tempLiveMatchesCount = new int[averageNum];
    static int[] tempPreMatchesCount = new int[averageNum];

    static ArrayList<String> upcomingSportsLocal;
    static ArrayList<String> liveSportsTop;

    static int p = 0;
    static int xAxisValue = xAxisLength;

    static XYChart chartPreMatch;
    static XYChart chartUpcoming;
    static XYChart chartLive;
    static XYChart chartTopLive;
    public static void main(String[] args) throws InterruptedException {
        loggerSetUp();
        initChart();
        TimeUnit.SECONDS.sleep(timeDelaySeconds);
        updateChart();

    }

    public static void initChart(){
        ExampleChart<XYChart> preMatchesCount = new AlarmMatchesNew();
        ExampleChart<XYChart> upcomingMatchesCount = new AlarmMatchesNew();
        ExampleChart<XYChart> liveMatchesCount = new AlarmMatchesNew();
        ExampleChart<XYChart> topLiveMatchesCount = new AlarmMatchesNew();

         chartPreMatch = preMatchesCount.getChart();
         chartUpcoming = upcomingMatchesCount.getChart();
         chartLive = liveMatchesCount.getChart();
         chartTopLive = topLiveMatchesCount.getChart();

        charts.add(chartPreMatch);
        charts.add(chartUpcoming);
        charts.add(chartLive);
        charts.add(chartTopLive);

        sw = new SwingWrapper<>(charts);
//        sw = new SwingWrapper<XYChart>(chartPreMatch);
        sw.setTitle("Matches Count " + currentTime());
        sw.displayChartMatrix();
    }





    public static void updateChart() throws InterruptedException {
        while (true){
            try{
                upcomingMatchesCount = 1000;
                topLiveMatchesCount = 1000;
                liveMatchesCount = 1000;
                preMatchesCount = 1000;
                //get matches count (graph will show average of calls averageNum times )
                for (int k = 0; k < averageNum; k++) {

                    upcomingSportsLocal = getUpcomingSportsIDs();
                    tempUpcomingMatchesLocal[k] = 0;
                    if (upcomingSportsLocal != null) {
                        for (String id : upcomingSportsLocal) {
                            tempUpcomingMatchesLocal[k] += getUpcomingMatchesCount(id);
                        }
                    }


                    liveSportsTop = getLiveSportsIDs();
                    tempTopLiveMatchesCount[k] = 0;
                    if (liveSportsTop != null) {
                        for (String id : liveSportsTop) {
                            tempTopLiveMatchesCount[k] += getLocalLifeMatchesCount(id);
                        }
                    }


                    tempLiveMatchesCount[k] = getLifeMatchesCount();
                    tempPreMatchesCount[k] = getPreMatchMatchesCount();

                    logger.info("UpcomingMatchesCount: " + tempUpcomingMatchesLocal[k] + "  topLiveMatchesCount: " + tempTopLiveMatchesCount[k] + "  "
                            + "  liveMatchesCount: " + tempLiveMatchesCount[k] + "  " + "  preMatchesCount: " + tempPreMatchesCount[k]);

                    TimeUnit.SECONDS.sleep(timeDelaySeconds);
                }

                boolean containsZeroUpcoming = contains(tempUpcomingMatchesLocal, 0);
                upcomingMatchesCount = 0;
                if (!containsZeroUpcoming) {
                    for (int m = 0; m < averageNum; m++) {
                        upcomingMatchesCount += tempUpcomingMatchesLocal[m];
                    }
                }
                boolean containsZeroPreMatch = contains(tempPreMatchesCount, 0);
                preMatchesCount = 0;
                if (!containsZeroPreMatch) {
                    for (int m = 0; m < averageNum; m++) {
                        preMatchesCount += tempPreMatchesCount[m];
                    }
                }
                boolean containsZeroTopLive = contains(tempTopLiveMatchesCount, 0);
                topLiveMatchesCount = 0;
                if (!containsZeroTopLive) {
                    for (int m = 0; m < averageNum; m++) {
                        topLiveMatchesCount += tempTopLiveMatchesCount[m];
                    }
                }
                boolean containsZeroLive = contains(tempLiveMatchesCount, 0);
                liveMatchesCount = 0;
                if (!containsZeroLive) {
                    for (int m = 0; m < averageNum; m++) {
                        liveMatchesCount += tempLiveMatchesCount[m];
                    }
                }
                upcomingMatchesCount = upcomingMatchesCount / averageNum;
                topLiveMatchesCount = topLiveMatchesCount / averageNum;
                liveMatchesCount = liveMatchesCount / averageNum;
                preMatchesCount = preMatchesCount / averageNum;

                //Creating arrays that contain last Matches Count Values

                if (p < upcomingMatchesCountArray.length) {
                    upcomingMatchesCountArray[p] = upcomingMatchesCount;
                    topLiveMatchesCountArray[p] = topLiveMatchesCount;
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

                        matchesCountArrayXAxis[i - 1] = matchesCountArrayXAxis[i];
//                        System.out.println();

                    }
                    xAxisValue++;
                    matchesCountArrayXAxis[lastArrayItem] = xAxisValue;
                    upcomingMatchesCountArray[lastArrayItem] = upcomingMatchesCount;
                    topLiveMatchesCountArray[lastArrayItem] = topLiveMatchesCount;
                    liveMatchesCountArray[lastArrayItem] = liveMatchesCount;
                    preMatchMatchesCountArray[lastArrayItem] = preMatchesCount;

//                    System.out.println();
                }




                chartPreMatch.updateXYSeries("preMatch",matchesCountArrayXAxis, preMatchMatchesCountArray,null);
                chartUpcoming.updateXYSeries("upcoming",matchesCountArrayXAxis, upcomingMatchesCountArray,null);
                chartLive.updateXYSeries("Live", matchesCountArrayXAxis, liveMatchesCountArray,null);
                chartTopLive.updateXYSeries("topLive", matchesCountArrayXAxis, topLiveMatchesCountArray,null);
                for (int l = 0; l < charts.size(); l++) {
                    sw.repaintChart(l);
                }






                //Compare Count matches and turn on alarm  if needed
                if (preMatchesCount < comparePreMatchesCount) {
                    chart.getStyler().setChartBackgroundColor(new Color(255, 68, 68));
                    alarmOnPreMatch = true;
                    logger.error("UpcomingMatchesCount: " + upcomingMatchesCount + "  topLiveMatchesCount: " + topLiveMatchesCount + "  "
                            + "  liveMatchesCount: " + liveMatchesCount + "  " + "  preMatchesCount: " + preMatchesCount);
                }
                if (upcomingMatchesCount < compareUpcomingMatchesCount) {
                    chart.getStyler().setChartBackgroundColor(new Color(255, 68, 68));
                    alarmOnUpcoming = true;
                    logger.error("UpcomingMatchesCount: " + upcomingMatchesCount + "  topLiveMatchesCount: " + topLiveMatchesCount + "  "
                            + "  liveMatchesCount: " + liveMatchesCount + "  " + "  preMatchesCount: " + preMatchesCount);
                }
                if (liveMatchesCount < compareLifeMatchesCount) {
                    chart.getStyler().setChartBackgroundColor(new Color(255, 68, 68));
                    alarmOnLive = true;
                    logger.error("UpcomingMatchesCount: " + upcomingMatchesCount + "  topLiveMatchesCount: " + topLiveMatchesCount + "  "
                            + "  liveMatchesCount: " + liveMatchesCount + "  " + "  preMatchesCount: " + preMatchesCount);
                }
                if (topLiveMatchesCount < compareTopLifeMatchesCount) {
                    chart.getStyler().setChartBackgroundColor(new Color(255, 68, 68));
                    alarmOnTopLive = true;
                    logger.error("UpcomingMatchesCount: " + upcomingMatchesCount + "  topLiveMatchesCount: " + topLiveMatchesCount + "  "
                            + "  liveMatchesCount: " + liveMatchesCount + "  " + "  preMatchesCount: " + preMatchesCount);
                }
                if (upcomingMatchesCount >= compareUpcomingMatchesCount && topLiveMatchesCount >= compareTopLifeMatchesCount
                        && liveMatchesCount >= compareLifeMatchesCount && preMatchesCount >= comparePreMatchesCount) {
//
                    chart.getStyler().setChartBackgroundColor(new Color(210, 210, 210));


                    alarmOnUpcoming = false;
                    alarmOnTopLive = false;
                    alarmOnLive = false;
                    alarmOnPreMatch = false;
                }

                // When alarm is on play specific sound

                if (alarmOnPreMatch) {
                    playSound(System.getProperty("user.dir") + "\\src\\test\\java\\mp3\\prematchMain.wav", 10);
                } else if (alarmOnUpcoming) {
                    playSound(System.getProperty("user.dir") + "\\src\\test\\java\\mp3\\upcoming.wav", 8);
                } else if (alarmOnLive) {
                    playSound(System.getProperty("user.dir") + "\\src\\test\\java\\mp3\\live.wav", 4);
                } else if (alarmOnTopLive) {
                    playSound(System.getProperty("user.dir") + "\\src\\test\\java\\mp3\\topLive.wav", 4);
                }






            }
            catch(Exception e){
                logger.fatal("Exception on Main While loop: " + e);
            }

        }






    }



    @Override
    public XYChart getChart() { // String name
        // Create Chart
        chart = new XYChartBuilder().width(800).height(600).title("Matches Count  " + currentTime()).xAxisTitle("X").yAxisTitle("Y").build();
        for (int m=1; m<=xAxisLength;m++){
            matchesCountArrayXAxis[m-1] = m;
        }
        // Customize Chart
        chart.getStyler().setLegendPosition(Styler.LegendPosition.OutsideS);
        chart.getStyler().setAxisTitlesVisible(false);
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Area);
        chart.getStyler().setToolTipsEnabled(true);
        chart.getStyler().setZoomEnabled(true);
        chart.getStyler().setChartBackgroundColor(new Color(210, 210, 210));
        chart.getStyler().setLegendBackgroundColor(new Color(237, 236, 255));
        chart.getStyler().setXAxisLabelRotation(90);
        // Series
//        switch()
//            case 1
        chart.addSeries("preMatch", matchesCountArrayXAxis, preMatchMatchesCountArray);
//        chart.addSeries("upcoming", matchesCountArrayXAxis, upcomingMatchesCountArray);
//        chart.addSeries("Live", matchesCountArrayXAxis, liveMatchesCountArray);
//        chart.addSeries("topLive", matchesCountArrayXAxis, topLiveMatchesCountArray);
        return chart;
    }




    @Override
    public String getExampleChartName() {
        return null;
    }

















    public static void loggerSetUp() {
        logger = Logger.getLogger("craftBetWorld");
        PropertyConfigurator.configure("log4j.properties");
    }

    public static void playSound(String path, int timeDelay) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(path).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
            TimeUnit.SECONDS.sleep(timeDelay);
            clip.close();

        } catch (Exception ex) {
            logger.fatal("Error with playing sound." + path + "  Exception: " + ex);
        }
    }

    public static String currentTime() {
        try {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime dateTimeNow = LocalDateTime.now();
            return dtf.format(dateTimeNow);
        } catch (Exception e) {
            return "currentTime() has an Exception" + e;
        }

    }

    private static boolean netIsAvailable() {
        try {
            final URL url = new URL("http://www.google.com");
            final URLConnection conn = url.openConnection();
            conn.connect();
            conn.getInputStream().close();
            return true;
        } catch (Exception k) {
            return false;
        }
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
                liveSportsIDs.add(id);
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
