package fr.p2i.desk;

import fr.p2i.desk.data.BackData;
import fr.p2i.desk.data.BottomData;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Day;
import org.jfree.data.time.FixedMillisecond;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Random;

public class DisplayPressure extends JFrame {
    public JPanel P1;
    public JPanel P2;
    public JPanel P3;
    public JPanel P4;
    private static final int N = 16;
    private static final Random random = new Random();
    public double max= 2000;
    public static TimeSeries ts;
    public double min=100;

    public static void main(String[] args) {
        DisplayPressure dp = new DisplayPressure();
        dp.setBD(new BottomData(System.currentTimeMillis(),new int[]{100,100,100,105}));
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        dp.setBD(new BottomData(System.currentTimeMillis(),new int[]{166,120,100,240}));

    }

    private static XYDataset createDataset() {
        TimeSeriesCollection tsc = new TimeSeriesCollection();
            ts = new TimeSeries("BottomData");

            tsc.addSeries(ts);
        return tsc;
    }

    private static JFreeChart createChart(final XYDataset dataset) {
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "Score de posture", "Temps réel", "Score", dataset, false, false, false);
        return chart;
    }

    public DisplayPressure() {
        this.setBounds(300,300,300, 630);
        this.setTitle("Chair sensors");
        this.setUndecorated(false);
        this.setBackground(Color.BLACK);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setLayout(null);

        P1 = new JPanel();
        P1.setBounds(0, 0, 150, 150);
        P1.setBackground(Color.GREEN);
        JLabel J1= new JLabel("P1");
        P1.add(J1);
        this.add(P1);


        P2 = new JPanel();
        P2.setBounds(150, 0, 150, 150);
        P2.setBackground(Color.GREEN);
        JLabel J2= new JLabel("P2");
        P2.add(J2);
        this.add(P2);


        P3 = new JPanel();
        P3.setBounds(0, 150, 150, 150);
        P3.setBackground(Color.GREEN);
        JLabel J3= new JLabel("P3");
        P3.add(J3);
        this.add(P3);


        P4 = new JPanel();
        P4.setBounds(150, 150, 150, 150);
        P4.setBackground(Color.GREEN);
        JLabel J4= new JLabel("P4");
        P4.add(J4);
        this.add(P4);

        XYDataset dataset = createDataset();
        JFreeChart chart = createChart(dataset);
        final XYPlot plot = chart.getXYPlot();
        plot.setDataset(dataset);
        ChartPanel chartPanel = new ChartPanel(chart) {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(300, 300);
            }
        };
        chartPanel.setDomainZoomable(true);


        JPanel jPanel4 = new JPanel();
        jPanel4.setBounds(0,300,300,300);
        jPanel4.add(chartPanel, BorderLayout.SOUTH);
        JPanel jPanel = new JPanel();
        jPanel.add(new JTextArea(""));
        jPanel.setBounds(300,600,100,30);

        this.add(new JButton(new AbstractAction("Show graph") {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(jPanel4.isVisible()){
                    jPanel4.setVisible(false);

                }
            }

        }));
        this.add(jPanel4);
        this.setLocationRelativeTo(null);
        this.setVisible(true);


    }




    public double map(double val, double Max, double Min){
        double a = (val - Min)/(Max-Min);
        double R = (a * 164.0D)+90.0D;
        return R;
    }

    public void setBD(BottomData bd){
        for(int a : bd.getBottomData()){
            System.out.println(a);
        }
        setColor(P2,bd.getBottomData()[0], 255,80,180);
        setColor(P1,bd.getBottomData()[2], 255,80,180);
        setColor(P4,bd.getBottomData()[1], 255,80,200);
        setColor(P3,bd.getBottomData()[3], 255,80,200);
    }

    public void setColor(JPanel P, double val,double Max, double Min, double critical) {
        if (val<=critical) {
            P.setBackground(new Color(0,(int) this.map(val,Max,Min),0));
          //  System.out.println(P + "changed");
        } else {
            P.setBackground(new Color((int) this.map(val,Max,Min),0,0));
        }
    }
}
