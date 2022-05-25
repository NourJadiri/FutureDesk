package fr.p2i.desk;

import javax.swing.*;
import java.awt.*;

public class DisplayPressure extends JFrame {
    public JPanel P1;
    public JPanel P2;
    public JPanel P3;
    public JPanel P4;
    public double max= 2000;
    public double min=100;

    public DisplayPressure() {
        this.setBounds(300,300,300, 300);
        this.setUndecorated(true);
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
        P2.setBackground(Color.RED);
        JLabel J2= new JLabel("P2");
        P2.add(J2);
        this.add(P2);


        P3 = new JPanel();
        P3.setBounds(0, 150, 150, 150);
        P3.setBackground(Color.BLUE);
        JLabel J3= new JLabel("P3");
        P3.add(J3);
        this.add(P3);


        P4 = new JPanel();
        P4.setBounds(150, 150, 150, 150);
        P4.setBackground(Color.YELLOW);
        JLabel J4= new JLabel("P4");
        P4.add(J4);
        this.add(P4);

        this.setVisible(true);


    }
    public double mapG(double val){
        double x=-188/(this.max-this.min);
        double b=255-(this.min*x);
        double G=val*x+b;

        return G;
    }

    public double mapR(double val){
        double x=-135/(this.max-this.min);
        double b=255-this.min*x;
        double R=val*x+b;

        return R;
    }

    public void setColor(JPanel P, double val) {
        if (val<=900) {
            P.setBackground(new Color((int) this.mapR(val),0, 0));
            System.out.println(P + "changed");
        } else {
            P.setBackground(new Color(0,(int) this.mapG(val),0));
        }
    }
}
