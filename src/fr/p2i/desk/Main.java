package fr.p2i.desk;

import fr.p2i.desk.ArduinoReception.Reception;
import fr.p2i.desk.data.BackData;
import fr.p2i.desk.data.BottomData;
import fr.p2i.desk.data.LightData;
import fr.p2i.desk.util.ArduinoManager;
import fr.p2i.desk.util.Database;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class Main extends JFrame {
    public static Database db;
    public static DisplayPressure dp;
    public Main(){
        db = new Database();
    }


    public static void main(String[] args) {
        Main a = new Main();
        //DisplayPressure dp = new DisplayPressure();
        //dp.setVisible(true);
        Thread b = new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(20000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                while(true){
                    System.out.println("z@ + "+Reception.bottomTemp.size()+" / "+Reception.backTemp.size() +" . "+Reception.lightTemp.size());
                    if (Reception.bottomTemp.size() >= 30 * 8) {
                        BottomData moyen = BottomData.moyenne(Reception.bottomTemp);
                        try {
                            Main.db.insert(moyen);
                        } catch (Exception e) {
                            System.out.println("Erreur ajout BottomData");
                            e.printStackTrace();
                        }
                        Reception.bottomTemp=new ArrayList<>();
                    }

                    if (Reception.backTemp.size() >= 30 * 5) {
                        BackData moyen = BackData.moyenne(Reception.backTemp);
                        try {
                            Main.db.insert(moyen);
                        } catch (Exception e) {
                            System.out.println("Erreur ajout BackData");
                            e.printStackTrace();
                        }
                        Reception.backTemp=new ArrayList<>();
                    }

                    if (Reception.lightTemp.size() >= 30 * 8) {
                        LightData moyen = LightData.moyenne(Reception.lightTemp);
                        try {
                            Main.db.insert(moyen);
                        } catch (Exception e) {
                            System.out.println("Erreur ajout LightsData");
                            e.printStackTrace();
                        }

                        Reception.lightTemp=new ArrayList<>();
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        };
        //b.start();
        dp = new DisplayPressure();
        Reception.launch(args);


    }
}
