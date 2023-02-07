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
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.apache.commons.lang3.ArrayUtils.contains;

public class AlarmMatchesNew implements ExampleChart<XYChart> {

    static SwingWrapper<XYChart> sw;
    //    static XYChart chart;
    static final int averageNum = 5;
    static final int xAxisLength = 288;
    static final int timeDelaySeconds = 60;
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
        updateChart();
    }

    public static void initChart() {
        ExampleChart<XYChart> preMatchesCount = new AlarmMatchesNew();
        ExampleChart<XYChart> upcomingMatchesCount = new AlarmMatchesNew();
        ExampleChart<XYChart> liveMatchesCount = new AlarmMatchesNew();
        ExampleChart<XYChart> topLiveMatchesCount = new AlarmMatchesNew();

        chartPreMatch = preMatchesCount.getChart();
        chartLive = liveMatchesCount.getChart();
        chartUpcoming = upcomingMatchesCount.getChart();
        chartTopLive = topLiveMatchesCount.getChart();

        charts.add(chartPreMatch);
        charts.add(chartLive);
        charts.add(chartUpcoming);
        charts.add(chartTopLive);

        sw = new SwingWrapper<>(charts);
//        sw = new SwingWrapper<XYChart>(chartPreMatch);
        sw.setTitle("Matches Count " + currentTime());
        sw.displayChartMatrix();
    }

    static int chartsNum = 1;

    public XYChart getChart() {
        float timeDelayMinutesVisualisation = (float) timeDelaySeconds / 60 * averageNum;
        DecimalFormat df = new DecimalFormat("#.#");
        // Series
        switch (chartsNum) {
            case 1: {
                // Create Chart
                chartPreMatch = new XYChartBuilder().width(500).height(250).xAxisTitle("X-Axis division: " + df.format(timeDelayMinutesVisualisation) + " min").yAxisTitle("Y").build();
                for (int m = 1; m <= xAxisLength; m++) {
                    matchesCountArrayXAxis[m - 1] = m;
                }
                // Customize Chart
                chartPreMatch.getStyler().setLegendPosition(Styler.LegendPosition.OutsideS);
                chartPreMatch.getStyler().setAxisTitlesVisible(false);
                chartPreMatch.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Step);
                chartPreMatch.getStyler().setSeriesColors(new Color[]{new Color(0, 110, 0)});
                chartPreMatch.getStyler().setToolTipsEnabled(true);
                chartPreMatch.getStyler().setZoomEnabled(true);
                chartPreMatch.getStyler().setChartBackgroundColor(new Color(230, 255, 255));
                chartPreMatch.getStyler().setLegendBackgroundColor(new Color(255, 255, 255));
                chartPreMatch.getStyler().setXAxisLabelRotation(90);
                chartPreMatch.getStyler().setMarkerSize(3);
                chartPreMatch.getStyler().setXAxisTitleVisible(true);
                chartPreMatch.addSeries("PreMatch", matchesCountArrayXAxis, preMatchMatchesCountArray);
                chartsNum++;
                return chartPreMatch;

            }
            case 2: {
                chartLive = new XYChartBuilder().width(500).height(250).xAxisTitle("X-Axis division: " + df.format(timeDelayMinutesVisualisation) + " min").yAxisTitle("Y").build();
                for (int m = 1; m <= xAxisLength; m++) {
                    matchesCountArrayXAxis[m - 1] = m;
                }
                // Customize Chart
                chartLive.getStyler().setLegendPosition(Styler.LegendPosition.OutsideS);
                chartLive.getStyler().setAxisTitlesVisible(false);
                chartLive.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Step);
                chartLive.getStyler().setSeriesColors(new Color[]{new Color(0, 0, 255)});
                chartLive.getStyler().setToolTipsEnabled(true);
                chartLive.getStyler().setZoomEnabled(true);
                chartLive.getStyler().setChartBackgroundColor(new Color(230, 255, 255));
                chartLive.getStyler().setLegendBackgroundColor(new Color(255, 255, 255));
                chartLive.getStyler().setXAxisLabelRotation(90);
                chartLive.getStyler().setMarkerSize(3);
                chartLive.getStyler().setXAxisTitleVisible(true);
                chartLive.addSeries("Live", matchesCountArrayXAxis, upcomingMatchesCountArray);
                chartsNum++;
                return chartLive;

            }
            case 3: {
                chartUpcoming = new XYChartBuilder().width(500).height(250).xAxisTitle("X-Axis division: " + df.format(timeDelayMinutesVisualisation) + " min").yAxisTitle("Y").build();
                for (int m = 1; m <= xAxisLength; m++) {
                    matchesCountArrayXAxis[m - 1] = m;
                }
                // Customize Chart
                chartUpcoming.getStyler().setLegendPosition(Styler.LegendPosition.OutsideS);
                chartUpcoming.getStyler().setAxisTitlesVisible(false);
                chartUpcoming.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Step);
                chartUpcoming.getStyler().setSeriesColors(new Color[]{new Color(0, 185, 0)});
                chartUpcoming.getStyler().setToolTipsEnabled(true);
                chartUpcoming.getStyler().setZoomEnabled(true);
                chartUpcoming.getStyler().setChartBackgroundColor(new Color(230, 255, 255));
                chartUpcoming.getStyler().setLegendBackgroundColor(new Color(255, 255, 255));
                chartUpcoming.getStyler().setXAxisLabelRotation(90);
                chartUpcoming.getStyler().setMarkerSize(3);
                chartUpcoming.getStyler().setXAxisTitleVisible(true);
                chartUpcoming.addSeries("Upcoming", matchesCountArrayXAxis, liveMatchesCountArray);
                chartsNum++;
                return chartUpcoming;

            }
            case 4: {
                chartTopLive = new XYChartBuilder().width(500).height(250).xAxisTitle("X-Axis division: " + df.format(timeDelayMinutesVisualisation) + " min").yAxisTitle("Y").build();
                for (int m = 1; m <= xAxisLength; m++) {
                    matchesCountArrayXAxis[m - 1] = m;
                }
                // Customize Chart
                chartTopLive.getStyler().setLegendPosition(Styler.LegendPosition.OutsideS);
                chartTopLive.getStyler().setAxisTitlesVisible(false);
                chartTopLive.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Step);
                chartTopLive.getStyler().setSeriesColors(new Color[]{new Color(0, 150, 255)});
                chartTopLive.getStyler().setMarkerSize(3);
                chartTopLive.getStyler().setToolTipsEnabled(true);
                chartTopLive.getStyler().setZoomEnabled(true);
                chartTopLive.getStyler().setChartBackgroundColor(new Color(230, 255, 255));
                chartTopLive.getStyler().setLegendBackgroundColor(new Color(255, 255, 255));
                chartTopLive.getStyler().setXAxisLabelRotation(90);
                chartTopLive.getStyler().setXAxisTitleVisible(true);
                chartTopLive.addSeries("TopLive", matchesCountArrayXAxis, topLiveMatchesCountArray);
                chartsNum++;
                return chartTopLive;
            }
            default: {
                return null;
            }
        }


    }

    public static void updateChart()  {
        while (true) {
            try {
                upcomingMatchesCount = 0;
                topLiveMatchesCount= 0;
                liveMatchesCount= 0;
                preMatchesCount= 0;

                //get matches count (graph will show average of calls averageNum times )
                for (int k = 0; k < averageNum; k++) {

                    upcomingSportsLocal = getUpcomingSportsIDs();

                    if (upcomingSportsLocal != null) {
                        tempUpcomingMatchesLocal[k] = 0;
                        for (String id : upcomingSportsLocal) {
                            tempUpcomingMatchesLocal[k] += getUpcomingMatchesCount(id);
                        }
                    } else {
                        tempUpcomingMatchesLocal[k] = 555;
                    }

                    liveSportsTop = getLiveSportsIDs();

                    if (liveSportsTop != null) {
                        tempTopLiveMatchesCount[k] = 0;
                        for (String id : liveSportsTop) {
                            tempTopLiveMatchesCount[k] += getLocalLifeMatchesCount(id);
                        }
                    } else {
                        tempTopLiveMatchesCount[k] = 555;
                    }


                    tempLiveMatchesCount[k] = getLifeMatchesCount();
                    tempPreMatchesCount[k] = getPreMatchMatchesCount();

                    logger.info("UpcomingMatchesCount: " + tempUpcomingMatchesLocal[k] + "  topLiveMatchesCount: " + tempTopLiveMatchesCount[k] + "  "
                            + "  liveMatchesCount: " + tempLiveMatchesCount[k] + "  " + "  preMatchesCount: " + tempPreMatchesCount[k] + " ");

                    TimeUnit.SECONDS.sleep(timeDelaySeconds);
                }


                boolean containsZeroUpcoming = contains(tempUpcomingMatchesLocal, 0);
                boolean containsExceptionUpcoming = contains(tempUpcomingMatchesLocal, 555);
                if (!containsZeroUpcoming && !containsExceptionUpcoming) {
                    for (int m = 0; m < averageNum; m++) {
                        upcomingMatchesCount += tempUpcomingMatchesLocal[m];
                    }
                    upcomingMatchesCount = upcomingMatchesCount / averageNum;
                } else {
                    if (containsExceptionUpcoming) {
                        upcomingMatchesCount = 555;
                    } else {
                        upcomingMatchesCount = 0;
                    }
                }


                boolean containsZeroPreMatch = contains(tempPreMatchesCount, 0);
                boolean containsExceptionPreMatch = contains(tempPreMatchesCount, 555);
                if (!containsZeroPreMatch && !containsExceptionPreMatch) {
                    for (int m = 0; m < averageNum; m++) {
                        preMatchesCount += tempPreMatchesCount[m];
                    }
                    preMatchesCount = preMatchesCount / averageNum;
                } else {
                    if (containsExceptionPreMatch) {
                        preMatchesCount = 555;
                    } else {
                        preMatchesCount = 0;
                    }
                }


                boolean containsZeroTopLive = contains(tempTopLiveMatchesCount, 0);
                boolean containsExceptionTopLive = contains(tempTopLiveMatchesCount, 555);
                if (!containsZeroTopLive && !containsExceptionTopLive) {
                    for (int m = 0; m < averageNum; m++) {
                        topLiveMatchesCount += tempTopLiveMatchesCount[m];
                    }
                    topLiveMatchesCount = topLiveMatchesCount / averageNum;
                } else {
                    if (containsExceptionTopLive) {
                        topLiveMatchesCount = 555;
                    } else {
                        topLiveMatchesCount = 0;
                    }
                }


                boolean containsZeroLive = contains(tempLiveMatchesCount, 0);
                boolean containsExceptionLive = contains(tempLiveMatchesCount, 555);
                if (!containsZeroLive && !containsExceptionLive) {
                    for (int m = 0; m < averageNum; m++) {
                        liveMatchesCount += tempLiveMatchesCount[m];
                    }
                    liveMatchesCount = liveMatchesCount / averageNum;
                } else {
                    if (containsExceptionLive) {
                        liveMatchesCount = 555;
                    } else {
                        liveMatchesCount = 0;
                    }
                }







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

                chartPreMatch.updateXYSeries("PreMatch", matchesCountArrayXAxis, preMatchMatchesCountArray, null);
                chartLive.updateXYSeries("Live", matchesCountArrayXAxis, liveMatchesCountArray, null);
                chartUpcoming.updateXYSeries("Upcoming", matchesCountArrayXAxis, upcomingMatchesCountArray, null);
                chartTopLive.updateXYSeries("TopLive", matchesCountArrayXAxis, topLiveMatchesCountArray, null);
                for (int l = 0; l < charts.size(); l++) {
                    sw.repaintChart(l);
                }

                //Compare Count matches and turn on alarm  if needed
                if (preMatchesCount < comparePreMatchesCount) {
                    chartPreMatch.getStyler().setChartBackgroundColor(new Color(255, 0, 0));
                    alarmOnPreMatch = true;
//                    logger.error("Error PreMatches >>>>>>>>>>>>  "   + preMatchesCount);
                }
                if (upcomingMatchesCount < compareUpcomingMatchesCount) {
                    chartUpcoming.getStyler().setChartBackgroundColor(new Color(255, 0, 0));
                    alarmOnUpcoming = true;
//                    logger.error("Error UpcomingMatches >>>>>>>>>>>>  "   + upcomingMatchesCount);
                }
                if (liveMatchesCount < compareLifeMatchesCount) {
                    chartLive.getStyler().setChartBackgroundColor(new Color(255, 0, 0));
                    alarmOnLive = true;
//                    logger.error("Error LiveMatches >>>>>>>>>>>>  "   + liveMatchesCount);
                }
                if (topLiveMatchesCount < compareTopLifeMatchesCount) {
                    chartTopLive.getStyler().setChartBackgroundColor(new Color(255, 0, 0));
                    alarmOnTopLive = true;
//                    logger.error("Error topLiveMatches >>>>>>>>>>>>  "   + topLiveMatchesCount);
                }
                if (upcomingMatchesCount >= compareUpcomingMatchesCount && topLiveMatchesCount >= compareTopLifeMatchesCount
                        && liveMatchesCount >= compareLifeMatchesCount && preMatchesCount >= comparePreMatchesCount) {
//
                    chartPreMatch.getStyler().setChartBackgroundColor(new Color(230, 255, 255));
                    chartUpcoming.getStyler().setChartBackgroundColor(new Color(230, 255, 255));
                    chartLive.getStyler().setChartBackgroundColor(new Color(230, 255, 255));
                    chartTopLive.getStyler().setChartBackgroundColor(new Color(230, 255, 255));


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

            } catch (Exception e) {
                logger.fatal("Exception on Main While loop: " + e);
            }

        }
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

        int count = 555;
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
        int count = 555;
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
        int count = 555;
        try {
            Unirest.setTimeouts(0, 0);
            HttpResponse<String> response = Unirest.get("https://sportsbookwebsitewebapi.craftbet.com/website/getlivematchescount?LanguageId=en&TimeZone=4")
                    .header("content-type", "application/json")
                    .header("origin", "https://sportsbookwebsite.craftbet.com")
                    .asString();

            JSONObject jsonObjectBody = new JSONObject(response.getBody());
            Unirest.shutdown();
            count=0;
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
                count=0;
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
        int count = 555;
        try {
            Unirest.setTimeouts(0, 0);
            HttpResponse<String> response = Unirest.get("https://sportsbookwebsitewebapi.craftbet.com/website/getprematchtree?LanguageId=en&TimeZone=4")
                    .header("origin", "https://sportsbookwebsite.craftbet.com")
                    .header("content-type", "application/json")
                    .asString();
            JSONObject jsonObjectBody = new JSONObject(response.getBody());
            Unirest.shutdown();
            count=0;
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
                count=0;
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
