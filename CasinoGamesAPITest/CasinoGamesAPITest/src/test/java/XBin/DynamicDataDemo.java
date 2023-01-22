package XBin;


import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

import java.util.Arrays;
import java.util.List;

public class DynamicDataDemo  {


    public static void main(String[] args) throws Exception {

        double[] xData = new double[] {0.0, 1.0, 2.0};
        double[] yData = new double[] {2.0, 1.0, 0.0};

        // Create Chart
        XYChart chart1 = QuickChart.getChart("Sample Chart", "X", "Y", "y(x)", xData, yData);
        XYChart chart2 = QuickChart.getChart("Sample Chart", "X", "Y", "y(x)", yData, xData);

//        List<XYChart>  list= Arrays.asList(chart1, chart1);
        // Show it
        new SwingWrapper(chart2).displayChart();
        new SwingWrapper(chart1).displayChart();
    }

}



