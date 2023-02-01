package Graphs;

import org.apache.log4j.Logger;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.demo.charts.ExampleChart;
import org.knowm.xchart.style.Styler;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class CreateChartFromLog implements ExampleChart<XYChart> {

    static String logFilePath = System.getProperty("user.dir") + "\\log\\GraphLog.log.2023-02-01-16-02";

    static SwingWrapper<XYChart> sw ;
    //    static XYChart chart;
    static final int averageNum = 1;
    static final int xAxisLength = 288;
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

    public static void main(String[] args) {
        showLogGraph();
//        createDataForGraph();
    }



    public static void showLogGraph(){
        initChart();
    }


    static int chartsNum = 1;
    public static void initChart(){
        ExampleChart<XYChart> preMatchesCount = new CreateChartFromLog();
        ExampleChart<XYChart> upcomingMatchesCount = new CreateChartFromLog();
        ExampleChart<XYChart> liveMatchesCount = new CreateChartFromLog();
        ExampleChart<XYChart> topLiveMatchesCount = new CreateChartFromLog();

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

    @Override
    public XYChart getChart() {
        float timeDelayMinutesVisualisation = (float) timeDelaySeconds / 60 * averageNum;
        DecimalFormat df = new DecimalFormat("#.#");
        // Series
        switch(chartsNum) {
            case 1:{
                // Create Chart
                chartPreMatch = new XYChartBuilder().width(500).height(250).xAxisTitle("X-Axis division: " + df.format(timeDelayMinutesVisualisation)+ " min").yAxisTitle("Y").build();
                for (int m=1; m<=xAxisLength;m++){
                    matchesCountArrayXAxis[m-1] = m;
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
            case 2:
            {
                chartLive = new XYChartBuilder().width(500).height(250).xAxisTitle("X-Axis division: " + df.format(timeDelayMinutesVisualisation)+ " min").yAxisTitle("Y").build();
                for (int m=1; m<=xAxisLength;m++){
                    matchesCountArrayXAxis[m-1] = m;
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
            case 3:{
                chartUpcoming = new XYChartBuilder().width(500).height(250).xAxisTitle("X-Axis division: " + df.format(timeDelayMinutesVisualisation)+ " min").yAxisTitle("Y").build();
                for (int m=1; m<=xAxisLength;m++){
                    matchesCountArrayXAxis[m-1] = m;
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
            case 4:
            {
                chartTopLive = new XYChartBuilder().width(500).height(250).xAxisTitle("X-Axis division: " + df.format(timeDelayMinutesVisualisation)+ " min").yAxisTitle("Y").build();
                for (int m=1; m<=xAxisLength;m++){
                    matchesCountArrayXAxis[m-1] = m;
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
            default:{
                return null;
            }
        }
            }

    @Override
    public String getExampleChartName() {
        return null;
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



    public static void createDataForGraph() {
        try {
            File file = new File(logFilePath);    //creates a new file instance
            FileReader fr = new FileReader(file);   //reads the file
            BufferedReader br = new BufferedReader(fr);  //creates a buffering character input stream
            StringBuffer sb = new StringBuffer();    //constructs a string buffer with no characters
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);      //appends line to string buffer
                sb.append("\n");     //line feed
            }
            fr.close();    //closes the stream and release the resources
            System.out.println("Contents of File: ");
            System.out.println(sb.toString());   //returns a string that textually represents the object
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
