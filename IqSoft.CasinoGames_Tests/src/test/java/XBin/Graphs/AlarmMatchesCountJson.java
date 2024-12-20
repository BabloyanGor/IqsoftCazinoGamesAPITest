package XBin.Graphs;


import java.awt.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.TimeUnit;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.json.JSONArray;
import kong.unirest.json.JSONObject;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.style.Styler;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.ArrayUtils.contains;

public class AlarmMatchesCountJson  {

    static final int averageNum = 5;
    static final int xAxisLength = 288;
    static final int timeDelaySeconds = 60;
    static final int compareUpcomingMatchesCount = 5;
    static final int compareTopLifeMatchesCount = 1;
    static final int compareLifeMatchesCount = 1;
    static final int comparePreMatchesCount = 1;

    public static Logger logger;
    static int xAxisValue = xAxisLength;

    public static void loggerSetUp() {
        logger = Logger.getLogger("craftBetWorld");
        PropertyConfigurator.configure("log4j.properties");
    }

    public static void main(String[] args) {
        loggerSetUp();
        getMatchesCount();
    }


    public static void getMatchesCount() {
//        int responseCod = 0;
//        String description = null;
        boolean alarmOnUpcoming = false;
        boolean alarmOnPreMatch = false;
        boolean alarmOnTopLive = false;
        boolean alarmOnLive = false;

        int upcomingMatchesCount;
        int topLiveMatchesCount;
        int liveMatchesCount;
        int preMatchesCount;

        int[] tempUpcomingMatchesLocal = new int[averageNum];
        int[] tempTopLiveMatchesCount = new int[averageNum];
        int[] tempLiveMatchesCount = new int[averageNum];
        int[] tempPreMatchesCount = new int[averageNum];

        ArrayList<String> upcomingSportsLocal;
        ArrayList<String> liveSportsTop;


        float timeDelayMinutesVisualisation = (float) timeDelaySeconds / 60 * averageNum;
        DecimalFormat df = new DecimalFormat("#.#");
        int p = 0;


        double[] matchesCountArrayXAxis = new double[xAxisLength];
        for (int m=1; m<=xAxisLength;m++){
            matchesCountArrayXAxis[m-1] = m;
        }


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


        XYChart chartPreMatches = QuickChart.getChart("Pre matches count", "X-Axis division: " + df.format(timeDelayMinutesVisualisation) + " min", "Value", "Pre Matches", initDataPreMatch[0], initDataPreMatch[1]);
        XYChart chartLive = QuickChart.getChart("Live matches count", "X-Axis division: " + df.format(timeDelayMinutesVisualisation) + " min", "Value", "Live Matches", initDataLive[0], initDataLive[1]);
        XYChart chartUpcoming = QuickChart.getChart("Upcoming matches count", "X-Axis division: " + df.format(timeDelayMinutesVisualisation) + " min", "Value", "Upcoming Matches", initDataUpcoming[0], initDataUpcoming[1]);
        XYChart chartTopLive = QuickChart.getChart("Top Live matches count", "X-Axis division: " + df.format(timeDelayMinutesVisualisation) + " min", "Value", "Top Live Matches", initDataTopLive[0], initDataTopLive[1]);

        charts.add(chartPreMatches);
        charts.add(chartLive);
        charts.add(chartUpcoming);
        charts.add(chartTopLive);



        //Styles for Chart
//        chartUpcoming.getStyler().setCursorColor(Color.red);
        chartUpcoming.getStyler().setPlotGridVerticalLinesVisible(true);
        chartUpcoming.getStyler().setLegendPosition(Styler.LegendPosition.OutsideS);
        chartUpcoming.getStyler().setXAxisMax((double) xAxisLength);
        chartUpcoming.getStyler().setXAxisMin(1.0);
        chartUpcoming.getStyler().setToolTipsEnabled(true);
        chartUpcoming.getStyler().setZoomEnabled(true);
//        chartUpcoming.getStyler().setZoomResetByButton(true);
        chartUpcoming.getStyler().setZoomResetByDoubleClick(true);
        chartUpcoming.getStyler().setChartBackgroundColor(new Color(210, 210, 210));
        chartUpcoming.getStyler().setLegendBackgroundColor(new Color(237, 236, 255));
//        chartUpcoming.getStyler().setCursorEnabled(true);
//        chartUpcoming.getStyler().setCursorLineWidth(1.0f);
        chartUpcoming.getStyler().setXAxisLabelRotation(90);

        chartTopLive.getStyler().setPlotGridVerticalLinesVisible(true);
        chartTopLive.getStyler().setLegendPosition(Styler.LegendPosition.OutsideS);
        chartTopLive.getStyler().setXAxisMax((double) xAxisLength);
        chartTopLive.getStyler().setXAxisMin(1.0);
        chartTopLive.getStyler().setToolTipsEnabled(true);
        chartTopLive.getStyler().setZoomEnabled(true);
//        chartTopLive.getStyler().setZoomResetByButton(true);
        chartTopLive.getStyler().setZoomResetByDoubleClick(true);
        chartTopLive.getStyler().setChartBackgroundColor(new Color(210, 210, 210));
        chartTopLive.getStyler().setLegendBackgroundColor(new Color(237, 236, 255));
//        chartTopLive.getStyler().setCursorEnabled(true);
//        chartTopLive.getStyler().setCursorLineWidth(1.0f);
        chartTopLive.getStyler().setXAxisLabelRotation(90);

        chartLive.getStyler().setPlotGridVerticalLinesVisible(true);
        chartLive.getStyler().setLegendPosition(Styler.LegendPosition.OutsideS);
        chartLive.getStyler().setXAxisMax((double) xAxisLength);
        chartLive.getStyler().setXAxisMin(1.0);
        chartLive.getStyler().setToolTipsEnabled(true);
        chartLive.getStyler().setZoomEnabled(true);
//        chartLive.getStyler().setZoomResetByButton(true);
        chartLive.getStyler().setZoomResetByDoubleClick(true);
        chartLive.getStyler().setChartBackgroundColor(new Color(210, 210, 210));
        chartLive.getStyler().setLegendBackgroundColor(new Color(237, 236, 255));
//        chartLive.getStyler().setCursorEnabled(true);
//        chartLive.getStyler().setCursorLineWidth(1.0f);
        chartLive.getStyler().setXAxisLabelRotation(90);

        chartPreMatches.getStyler().setPlotGridVerticalLinesVisible(true);
        chartPreMatches.getStyler().setLegendPosition(Styler.LegendPosition.OutsideS);
        chartPreMatches.getStyler().setXAxisMax((double) xAxisLength);
        chartPreMatches.getStyler().setXAxisMin(1.0);
        chartPreMatches.getStyler().setToolTipsEnabled(true);
        chartPreMatches.getStyler().setZoomEnabled(true);
//        chartPreMatches.getStyler().setZoomResetByButton(true);
        chartPreMatches.getStyler().setZoomResetByDoubleClick(true);
        chartPreMatches.getStyler().setChartBackgroundColor(new Color(210, 210, 210));
        chartPreMatches.getStyler().setLegendBackgroundColor(new Color(237, 236, 255));
//        chartPreMatches.getStyler().setCursorEnabled(true);
//        chartPreMatches.getStyler().setCursorLineWidth(1.0f);
        chartPreMatches.getStyler().setXAxisLabelRotation(90);

        // Show it
        SwingWrapper<XYChart> sw = new SwingWrapper<>(charts);
        sw.setTitle("IQ-Soft Matches Count Graph    StartTime: " + currentTime());

        sw.displayChartMatrix();


        int k;

        while (true) {

            try {
                upcomingMatchesCount = 100;
                topLiveMatchesCount = 100;
                liveMatchesCount = 100;
                preMatchesCount = 100;

                //get matches count (graph will show average of calls averageNum times )
                for (k = 0; k < averageNum; k++) {

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


//                    upcomingMatchesCount += tempUpcomingMatchesLocal;
//                    topLiveMatchesCount += tempTopLiveMatchesCount;
//                    liveMatchesCount += tempLiveMatchesCount;
//                    preMatchesCount += tempPreMatchesCount;


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

//                        matchesCountArrayXAxis[i - 1] = matchesCountArrayXAxis[i];
//                        System.out.println();

                    }
                    xAxisValue++;
//                    matchesCountArrayXAxis[lastArrayItem] = xAxisValue;
                    upcomingMatchesCountArray[lastArrayItem] = upcomingMatchesCount;
                    topLiveMatchesCountArray[lastArrayItem] = topLiveMatchesCount;
                    liveMatchesCountArray[lastArrayItem] = liveMatchesCount;
                    preMatchMatchesCountArray[lastArrayItem] = preMatchesCount;

//                    System.out.println();
                }


                // Update charts
                chartPreMatches.updateXYSeries("Pre Matches", null, preMatchMatchesCountArray, null);
                chartLive.updateXYSeries("Live Matches", null, liveMatchesCountArray, null);
                chartUpcoming.updateXYSeries("Upcoming Matches", null, upcomingMatchesCountArray, null);
                chartTopLive.updateXYSeries("Top Live Matches", null, topLiveMatchesCountArray, null);
                for (int l = 0; l < charts.size(); l++) {
                    sw.repaintChart(l);
                }


                //Compare Count matches and turn on alarm  if needed
                if (preMatchesCount < comparePreMatchesCount) {
//                    responseCod = 1003;
//                    description = "Pre Matches count is less then: " + comparePreMatchesCount;
                    chartPreMatches.getStyler().setChartBackgroundColor(new Color(255, 68, 68));
                    alarmOnPreMatch = true;
                    logger.error("UpcomingMatchesCount: " + upcomingMatchesCount + "  topLiveMatchesCount: " + topLiveMatchesCount + "  "
                            + "  liveMatchesCount: " + liveMatchesCount + "  " + "  preMatchesCount: " + preMatchesCount);
//                    logger.error("Pre Matches count is: " + preMatchesCount);
                }
                if (upcomingMatchesCount < compareUpcomingMatchesCount) {
//                    responseCod = 1000;
//                    description = "Upcoming Matches count is less then: " + compareUpcomingMatchesCount;
                    chartUpcoming.getStyler().setChartBackgroundColor(new Color(255, 68, 68));
                    alarmOnUpcoming = true;
                    logger.error("UpcomingMatchesCount: " + upcomingMatchesCount + "  topLiveMatchesCount: " + topLiveMatchesCount + "  "
                            + "  liveMatchesCount: " + liveMatchesCount + "  " + "  preMatchesCount: " + preMatchesCount);
//                    logger.error("Upcoming Matches count is: " + upcomingMatchesCount);
                }
                if (liveMatchesCount < compareLifeMatchesCount) {
//                    responseCod = 1002;
//                    description = "Life Matches count is less then: " + compareLifeMatchesCount;
                    chartLive.getStyler().setChartBackgroundColor(new Color(255, 68, 68));
                    alarmOnLive = true;
                    logger.error("UpcomingMatchesCount: " + upcomingMatchesCount + "  topLiveMatchesCount: " + topLiveMatchesCount + "  "
                            + "  liveMatchesCount: " + liveMatchesCount + "  " + "  preMatchesCount: " + preMatchesCount);
//                    logger.error("Life Matches count is: " + liveMatchesCount);
                }
                if (topLiveMatchesCount < compareTopLifeMatchesCount) {
//                    responseCod = 1001;
//                    description = "Local Life Matches count is less then: " + compareTopLifeMatchesCount;
                    chartTopLive.getStyler().setChartBackgroundColor(new Color(255, 68, 68));
                    alarmOnTopLive = true;
                    logger.error("UpcomingMatchesCount: " + upcomingMatchesCount + "  topLiveMatchesCount: " + topLiveMatchesCount + "  "
                            + "  liveMatchesCount: " + liveMatchesCount + "  " + "  preMatchesCount: " + preMatchesCount);
//                    logger.error("Local Life Matches count is: " + topLiveMatchesCount);
                }
                if (upcomingMatchesCount >= compareUpcomingMatchesCount && topLiveMatchesCount >= compareTopLifeMatchesCount
                        && liveMatchesCount >= compareLifeMatchesCount && preMatchesCount >= comparePreMatchesCount) {
//                    responseCod = 0;
//                    description = "null";
                    chartUpcoming.getStyler().setChartBackgroundColor(new Color(210, 210, 210));
                    chartLive.getStyler().setChartBackgroundColor(new Color(210, 210, 210));
                    chartTopLive.getStyler().setChartBackgroundColor(new Color(210, 210, 210));
                    chartPreMatches.getStyler().setChartBackgroundColor(new Color(210, 210, 210));

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
//            System.out.println();
        }
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
            Unirest.config().reset();
            Unirest.config().connectTimeout(60000);
            Unirest.config().socketTimeout(60000);
            HttpResponse<String> response = Unirest.get("https://sportsbookwebsitewebapi.craftbet.com/website/getupcomingsports?LanguageId=en&TimeZone=4&Id=11955")
                    .header("content-type", "application/json; charset=utf-8")
                    .header("origin", "https://sportsbookwebsite.craftbet.com")
                    .asString();

            JSONObject jsonObjectBody = new JSONObject(response.getBody());
            Unirest.shutDown(true);


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
                Unirest.config().reset();
                Unirest.config().connectTimeout(60000);
                Unirest.config().socketTimeout(60000);
                HttpResponse<String> response = Unirest.get("https://sportsbookwebsitewebapi.craftbet.com/website/getupcomingsports?LanguageId=en&TimeZone=4&Id=11955")
                        .header("content-type", "application/json; charset=utf-8")
                        .header("origin", "https://sportsbookwebsite.craftbet.com")
                        .asString();

                JSONObject jsonObjectBody = new JSONObject(response.getBody());
                Unirest.shutDown(true);

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
            Unirest.config().reset();
            Unirest.config().connectTimeout(60000);
            Unirest.config().socketTimeout(60000);            HttpResponse<String> response = Unirest.get("https://sportsbookwebsitewebapi.craftbet.com/website/getupcomingmatches?LanguageId=en&TimeZone=4&SportId=" + sportID)
                    .header("content-type", "application/json; charset=utf-8")
                    .header("origin", "https://sportsbookwebsite.craftbet.com")
                    .asString();
            JSONObject jsonObjectBody = new JSONObject(response.getBody());
            Unirest.shutDown(true);



            JSONObject jsonObjectResponseObject = new JSONObject(jsonObjectBody.toString());
            JSONArray jsonArrayGames = jsonObjectResponseObject.getJSONArray("Ms");
            count = jsonArrayGames.length();
            return count;
        } catch (Exception e) {
            try {
                Unirest.config().reset();
                Unirest.config().connectTimeout(60000);
                Unirest.config().socketTimeout(60000);
                HttpResponse<String> response = Unirest.get("https://sportsbookwebsitewebapi.craftbet.com/website/getupcomingmatches?LanguageId=en&TimeZone=4&SportId=" + sportID)
                        .header("content-type", "application/json; charset=utf-8")
                        .header("origin", "https://sportsbookwebsite.craftbet.com")
                        .asString();
                JSONObject jsonObjectBody = new JSONObject(response.getBody());
                Unirest.shutDown(true);


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
            Unirest.config().reset();
            Unirest.config().connectTimeout(60000);
            Unirest.config().socketTimeout(60000);
            HttpResponse<String> response = Unirest.get("https://sportsbookwebsitewebapi.craftbet.com/website/getlivesports?LanguageId=en&TimeZone=4")
                    .header("content-type", "application/json; charset=utf-8")
                    .header("origin", "https://sportsbookwebsite.craftbet.com")
                    .asString();

            JSONObject jsonObjectBody = new JSONObject(response.getBody());
            Unirest.shutDown(true);



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
                Unirest.config().reset();
                Unirest.config().connectTimeout(60000);
                Unirest.config().socketTimeout(60000);
                HttpResponse<String> response = Unirest.get("https://sportsbookwebsitewebapi.craftbet.com/website/getlivesports?LanguageId=en&TimeZone=4")
                        .header("content-type", "application/json; charset=utf-8")
                        .header("origin", "https://sportsbookwebsite.craftbet.com")
                        .asString();

                JSONObject jsonObjectBody = new JSONObject(response.getBody());
                Unirest.shutDown(true);


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
            Unirest.config().reset();
            Unirest.config().connectTimeout(60000);
            Unirest.config().socketTimeout(60000);
            HttpResponse<String> response = Unirest.get("https://sportsbookwebsitewebapi.craftbet.com/website/gettoplivematches?LanguageId=en&TimeZone=4&SportId=" + sportID)
                    .header("origin", "https://sportsbookwebsite.craftbet.com")
                    .header("content-type", "application/json")
                    .asString();
            JSONObject jsonObjectBody = new JSONObject(response.getBody());
            Unirest.shutDown(true);


            JSONObject jsonObjectResponseObject = new JSONObject(jsonObjectBody.toString());
            JSONArray jsonArrayGames = jsonObjectResponseObject.getJSONArray("Ms");
            count = jsonArrayGames.length();
            return count;
        } catch (Exception e) {
            try {
                Unirest.config().reset();
                Unirest.config().connectTimeout(60000);
                Unirest.config().socketTimeout(60000);
                HttpResponse<String> response = Unirest.get("https://sportsbookwebsitewebapi.craftbet.com/website/gettoplivematches?LanguageId=en&TimeZone=4&SportId=" + sportID)
                        .header("origin", "https://sportsbookwebsite.craftbet.com")
                        .header("content-type", "application/json")
                        .asString();
                JSONObject jsonObjectBody = new JSONObject(response.getBody());
                Unirest.shutDown(true);


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
            Unirest.config().reset();
            Unirest.config().connectTimeout(60000);
            Unirest.config().socketTimeout(60000);
            HttpResponse<String> response = Unirest.get("https://sportsbookwebsitewebapi.craftbet.com/website/getlivematchescount?LanguageId=en&TimeZone=4")
                    .header("content-type", "application/json")
                    .header("origin", "https://sportsbookwebsite.craftbet.com")
                    .asString();

            JSONObject jsonObjectBody = new JSONObject(response.getBody());
            Unirest.shutDown(true);


            JSONObject jsonObjectResponseObject = new JSONObject(jsonObjectBody.toString());
            count = jsonObjectResponseObject.getInt("TotalCount");
            return count;
        } catch (Exception e) {
            try {
                Unirest.config().reset();
                Unirest.config().connectTimeout(60000);
                Unirest.config().socketTimeout(60000);
                HttpResponse<String> response = Unirest.get("https://sportsbookwebsitewebapi.craftbet.com/website/getlivematchescount?LanguageId=en&TimeZone=4")
                        .header("content-type", "application/json")
                        .header("origin", "https://sportsbookwebsite.craftbet.com")
                        .asString();

                JSONObject jsonObjectBody = new JSONObject(response.getBody());
                Unirest.shutDown(true);


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
            Unirest.config().reset();
            Unirest.config().connectTimeout(60000);
            Unirest.config().socketTimeout(60000);
            HttpResponse<String> response = Unirest.get("https://sportsbookwebsitewebapi.craftbet.com/website/getprematchtree?LanguageId=en&TimeZone=4")
                    .header("origin", "https://sportsbookwebsite.craftbet.com")
                    .header("content-type", "application/json")
                    .asString();
            JSONObject jsonObjectBody = new JSONObject(response.getBody());
            Unirest.shutDown(true);


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
                Unirest.config().reset();
                Unirest.config().connectTimeout(60000);
                Unirest.config().socketTimeout(60000);
                HttpResponse<String> response = Unirest.get("https://sportsbookwebsitewebapi.craftbet.com/website/getprematchtree?LanguageId=en&TimeZone=4")
                        .header("origin", "https://sportsbookwebsite.craftbet.com")
                        .header("content-type", "application/json")
                        .asString();
                JSONObject jsonObjectBody = new JSONObject(response.getBody());
                Unirest.shutDown(true);

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
