package Graphs;

import org.apache.commons.lang3.StringUtils;
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

    static String logFilePath = System.getProperty("user.dir") + "\\log\\GraphLog.log.2023-02-07-08";

    static SwingWrapper<XYChart> sw ;
    //    static XYChart chart;
    static final int timeDelaySeconds = 60;

    public static Logger logger;
    static List<XYChart> charts = new ArrayList<>();
    static ArrayList<String> LogLines = new ArrayList<>();

    static ArrayList<Double> preMatchMatchesCountArrayList = new ArrayList<>();
    static ArrayList<Double> upcomingMatchesCountArrayList = new ArrayList<>();
    static ArrayList<Double> topLiveMatchesCountArrayList = new ArrayList<>();
    static ArrayList<Double> liveMatchesCountArrayList = new ArrayList<>();

    static XYChart chartPreMatch;
    static XYChart chartUpcoming;
    static XYChart chartLive;
    static XYChart chartTopLive;

    public static void main(String[] args) {
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

        getDataForGraph();

        double[] preMatchMatchesCountArray = new double[preMatchMatchesCountArrayList.size()];
        double[] upcomingMatchesCountArray  = new double[upcomingMatchesCountArrayList.size()];
        double[] topLiveMatchesCountArray = new double[topLiveMatchesCountArrayList.size()];
        double[] liveMatchesCountArray  = new double[liveMatchesCountArrayList.size()];
        for (int l=0;l<preMatchMatchesCountArrayList.size();l++){
            preMatchMatchesCountArray[l] = preMatchMatchesCountArrayList.get(l);
        }
        for (int l=0;l<upcomingMatchesCountArrayList.size();l++){
            upcomingMatchesCountArray[l] = upcomingMatchesCountArrayList.get(l);
        }
        for (int l=0;l<topLiveMatchesCountArrayList.size();l++){
            topLiveMatchesCountArray[l] = topLiveMatchesCountArrayList.get(l);
        }
        for (int l=0;l<liveMatchesCountArrayList.size();l++){
            liveMatchesCountArray[l] = liveMatchesCountArrayList.get(l);
        }


        chartPreMatch.updateXYSeries("PreMatch", null, preMatchMatchesCountArray, null);
        chartLive.updateXYSeries("Live", null, liveMatchesCountArray, null);
        chartUpcoming.updateXYSeries("Upcoming", null, upcomingMatchesCountArray, null);
        chartTopLive.updateXYSeries("TopLive", null, topLiveMatchesCountArray, null);
        for (int l = 0; l < charts.size(); l++) {
            sw.repaintChart(l);
        }
    }

    @Override
    public XYChart getChart() {
        float timeDelayMinutesVisualisation = (float) timeDelaySeconds / 60 ;
        DecimalFormat df = new DecimalFormat("#.#");
        // Series
        switch(chartsNum) {
            case 1:{
                // Create Chart
                chartPreMatch = new XYChartBuilder().width(500).height(250).xAxisTitle("X-Axis division: " + df.format(timeDelayMinutesVisualisation)+ " min").yAxisTitle("Y").build();

                // Customize Chart
                chartPreMatch.getStyler().setLegendPosition(Styler.LegendPosition.OutsideS);
                chartPreMatch.getStyler().setAxisTitlesVisible(false);
                chartPreMatch.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);
                chartPreMatch.getStyler().setSeriesColors(new Color[]{new Color(0, 110, 0)});
                chartPreMatch.getStyler().setToolTipsEnabled(true);
                chartPreMatch.getStyler().setZoomEnabled(true);
                chartPreMatch.getStyler().setChartBackgroundColor(new Color(230, 255, 255));
                chartPreMatch.getStyler().setLegendBackgroundColor(new Color(255, 255, 255));
                chartPreMatch.getStyler().setXAxisLabelRotation(90);
                chartPreMatch.getStyler().setMarkerSize(3);
                chartPreMatch.getStyler().setXAxisTitleVisible(true);
                chartPreMatch.addSeries("PreMatch", null, new double[]{0.0});
                chartsNum++;
                return chartPreMatch;

            }
            case 2:
            {
                chartLive = new XYChartBuilder().width(500).height(250).xAxisTitle("X-Axis division: " + df.format(timeDelayMinutesVisualisation)+ " min").yAxisTitle("Y").build();

                // Customize Chart
                chartLive.getStyler().setLegendPosition(Styler.LegendPosition.OutsideS);
                chartLive.getStyler().setAxisTitlesVisible(false);
                chartLive.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);
                chartLive.getStyler().setSeriesColors(new Color[]{new Color(0, 0, 255)});
                chartLive.getStyler().setToolTipsEnabled(true);
                chartLive.getStyler().setZoomEnabled(true);
                chartLive.getStyler().setChartBackgroundColor(new Color(230, 255, 255));
                chartLive.getStyler().setLegendBackgroundColor(new Color(255, 255, 255));
                chartLive.getStyler().setXAxisLabelRotation(90);
                chartLive.getStyler().setMarkerSize(3);
                chartLive.getStyler().setXAxisTitleVisible(true);
                chartLive.addSeries("Live", null,  new double[]{0.0});
                chartsNum++;
                return chartLive;

            }
            case 3:{
                chartUpcoming = new XYChartBuilder().width(500).height(250).xAxisTitle("X-Axis division: " + df.format(timeDelayMinutesVisualisation)+ " min").yAxisTitle("Y").build();

                // Customize Chart
                chartUpcoming.getStyler().setLegendPosition(Styler.LegendPosition.OutsideS);
                chartUpcoming.getStyler().setAxisTitlesVisible(false);
                chartUpcoming.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);
                chartUpcoming.getStyler().setSeriesColors(new Color[]{new Color(0, 185, 0)});
                chartUpcoming.getStyler().setToolTipsEnabled(true);
                chartUpcoming.getStyler().setZoomEnabled(true);
                chartUpcoming.getStyler().setChartBackgroundColor(new Color(230, 255, 255));
                chartUpcoming.getStyler().setLegendBackgroundColor(new Color(255, 255, 255));
                chartUpcoming.getStyler().setXAxisLabelRotation(90);
                chartUpcoming.getStyler().setMarkerSize(3);
                chartUpcoming.getStyler().setXAxisTitleVisible(true);
                chartUpcoming.addSeries("Upcoming", null,  new double[]{0.0});
                chartsNum++;
                return chartUpcoming;

            }
            case 4:
            {
                chartTopLive = new XYChartBuilder().width(500).height(250).xAxisTitle("X-Axis division: " + df.format(timeDelayMinutesVisualisation)+ " min").yAxisTitle("Y").build();

                // Customize Chart
                chartTopLive.getStyler().setLegendPosition(Styler.LegendPosition.OutsideS);
                chartTopLive.getStyler().setAxisTitlesVisible(false);
                chartTopLive.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);
                chartTopLive.getStyler().setSeriesColors(new Color[]{new Color(0, 150, 255)});
                chartTopLive.getStyler().setMarkerSize(3);
                chartTopLive.getStyler().setToolTipsEnabled(true);
                chartTopLive.getStyler().setZoomEnabled(true);
                chartTopLive.getStyler().setChartBackgroundColor(new Color(230, 255, 255));
                chartTopLive.getStyler().setLegendBackgroundColor(new Color(255, 255, 255));
                chartTopLive.getStyler().setXAxisLabelRotation(90);
                chartTopLive.getStyler().setXAxisTitleVisible(true);
                chartTopLive.addSeries("TopLive", null,  new double[]{0.0});
                chartsNum++;
                return chartTopLive;
            }
            default:{
                return null;
            }
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




    public static void getDataForGraph() {
        try {
            File file = new File(logFilePath);    //creates a new file instance
            FileReader fr = new FileReader(file);   //reads the file
            BufferedReader br = new BufferedReader(fr);  //creates a buffering character input stream
            StringBuffer sb = new StringBuffer();    //constructs a string buffer with no characters
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);      //appends line to string buffer
                LogLines.add(line);
                sb.append("\n");     //line feed
            }
            fr.close();    //closes the stream and release the resources
//            System.out.println("Contents of File: ");

            for (int k = 0 ; k<LogLines.size();k++){
                try{
                    String oneLine = LogLines.get(k);

                    try{
                        String preMatch = StringUtils.substringBetween(oneLine,"preMatchesCount: "," ");
                        preMatchMatchesCountArrayList.add(Double.parseDouble(preMatch));
                    }
                    catch (Exception ignored){}
                    try{
                        String upcoming  = StringUtils.substringBetween(oneLine,"UpcomingMatchesCount: ","  topLiveMatchesCount:");
                        upcomingMatchesCountArrayList.add(Double.parseDouble(upcoming));
                    }
                    catch (Exception ignored){}
                    try{
                        String live = StringUtils.substringBetween(oneLine,"liveMatchesCount: ","    preMatchesCount:");
                        liveMatchesCountArrayList.add(Double.parseDouble(live));
                    }
                    catch (Exception ignored){}
                    try{
                        String topLive = StringUtils.substringBetween(oneLine,"topLiveMatchesCount: ","    liveMatchesCount: ");
                        topLiveMatchesCountArrayList.add(Double.parseDouble(topLive));
                    }
                    catch (Exception ignored){}






//                    System.out.println(" preMatch >>>> " + preMatchMatchesCountArray[k] );
//                    System.out.println(" upcoming >>>> " + upcomingMatchesCountArray[k] );
//                    System.out.println(" live >>>> " + liveMatchesCountArray[k] );
//                    System.out.println(" topLive >>>> " + topLiveMatchesCountArray[k] );
//                    System.out.println();
                }
                catch (Exception e){
                }
            }


//            System.out.println(sb.toString());   //returns a string that textually represents the object
        } catch (Exception e) {
            e.printStackTrace();
        }
    }





    @Override
    public String getExampleChartName() {
        return null;
    }
}
