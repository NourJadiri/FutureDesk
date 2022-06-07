package fr.p2i.desk;
import fr.p2i.desk.data.BackData;
import fr.p2i.desk.data.BottomData;

import javax.swing.*;
import java.awt.*;

public class DisplayFlexion extends JFrame {
    public int Height=400;
    public int Width=500;
    public double max=90; //max value of angle of inclination
    public double min=0; // min value of angle of inclination
    public ImageIcon icon;
    public JLabel image;
    public JPanel F1;
    public JPanel F2;
    public JPanel F3;
    public JPanel F4;
    public JPanel F5;
    public JPanel F6;

    public static void main(String[] args) {
        DisplayFlexion df = new DisplayFlexion();
    }

    public DisplayFlexion() {
        icon = new ImageIcon(new ImageIcon("/Users/massin/Documents/BDE/SS/dos.png").getImage().getScaledInstance(Width, Height, Image.SCALE_DEFAULT));
        image= new JLabel();
        image.setIcon(icon);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Flexion Frame");
        setSize(Width, Height);

        F1= new JPanel();
        F1.setBounds(222,70,25,40);
        F1.setBackground(Color.RED);
        this.add(F1);
        this.add(image);

        F2= new JPanel();
        F2.setBounds(262,70,25,40);
        F2.setBackground(Color.RED);
        this.add(F2);
        this.add(image);

        F3= new JPanel();
        F3.setBounds(145,130,85,40);
        F3.setBackground(Color.RED);
        this.add(F3);
        this.add(image);

        F4= new JPanel();
        F4.setBounds(280,130,85,40);
        F4.setBackground(Color.RED);
        this.add(F4);
        this.add(image);

        F5= new JPanel();
        F5.setBounds(238,130,30,100);
        F5.setBackground(Color.RED);
        this.add(F5);
        this.add(image);

        F6= new JPanel();
        F6.setBounds(238,250,30,100);
        F6.setBackground(Color.RED);
        this.add(F6);
        this.add(image);

        setVisible(true);
    }
    public double map(double val, double Max, double Min){
        double a = (val - Min)/(Max-Min);
        double R = (a * 164.0D)+90.0D;
        return R;
    }

    public void setBD(BackData bd){
        for(int a : bd.getBackData()){
            System.out.println(a);
        }
        setColor(F2,bd.getBackData()[0], 255,80,180);
        setColor(F1,bd.getBackData()[2], 255,80,180);
        setColor(F4,bd.getBackData()[1], 255,80,200);
        setColor(F3,bd.getBackData()[3], 255,80,200);
        setColor(F5,bd.getBackData()[4], 255,80,200);
        setColor(F6,bd.getBackData()[5], 255,80,200);
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
