package fr.p2i.desk;

import fr.p2i.desk.ArduinoReception.Reception;
import fr.p2i.desk.util.ArduinoManager;
import fr.p2i.desk.util.Database;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Main extends JFrame {
    public static Database db;
    public Main(){
        db = new Database();
        this.setSize(100,100);
        this.add(new JLabel("yo ma team"));
        this.setUndecorated(true);
        this.setBackground(new Color(1.0f,1.0f,1.0f, 0.0f));
        this.setVisible(true);

    }


    public static void main(String[] args) {
        Main a = new Main();
        DisplayPressure dp = new DisplayPressure();
        dp.setVisible(true);
        Reception.launch(args);


    }
}
