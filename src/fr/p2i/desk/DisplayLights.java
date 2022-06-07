package fr.p2i.desk;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;

public class DisplayLights extends JFrame {
    private static final long serialVersionUID = 1L;
    private static DefaultCategoryDataset dataset;
    public DisplayLights() {
        super("Light Sensor");

        // Create Dataset
        CategoryDataset dataset = createDataset();

        //Create chart
        JFreeChart chart= ChartFactory.createBarChart(
                "Sensor Light", //Chart Title
                "Color", // Category axis
                "Color", // Value axis
                dataset,
                PlotOrientation.VERTICAL,
                true,true,false
        );

        ChartPanel panel=new ChartPanel(chart);
        setContentPane(panel);
        this.setSize(800, 400);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    private CategoryDataset createDataset() {
        dataset = new DefaultCategoryDataset();

        // Population in 2005






        dataset.addValue(30,"RED","RED");
        dataset.addValue(30,"BLUE","BLUE");
        dataset.addValue(30,"GREEN","GREEN");
        dataset.addValue(30,"INTENSITY","INTENSITY");
        return dataset;
    }

    public void setValueColor(int value,String color){
        dataset.setValue(value,color,color);
    }


}
