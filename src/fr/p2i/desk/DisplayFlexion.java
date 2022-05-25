package fr.p2i.desk;
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

    public DisplayFlexion() {
        icon = new ImageIcon(new ImageIcon("back.jpg").getImage().getScaledInstance(Width, Height, Image.SCALE_DEFAULT));
        image= new JLabel();
        image.setIcon(icon);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("My Frame");
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
        System.out.println("R="+R);

        return R;
    }

    public void setColor(JPanel P, double val) {
        if (val>=45) {

            P.setBackground(new Color((int) this.mapR(val),0,0));
            System.out.println(P + "changed");
        } else {

            P.setBackground(new Color(0,(int) this.mapG(val),0));
        }
    }

}
