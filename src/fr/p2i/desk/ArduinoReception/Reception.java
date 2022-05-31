package fr.p2i.desk.ArduinoReception;

import java.util.Arrays;
import fr.p2i.desk.data.BackData;
import fr.p2i.desk.data.BottomData;
import fr.p2i.desk.data.LightData;
import fr.p2i.desk.util.ArduinoManager;
import fr.p2i.desk.util.Console;
import fr.p2i.desk.util.Database;
import fr.p2i.desk.Main;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;

enum typeDonne {
    TORSI,
    LUMI,
    FLEXI
};

public class Reception {
    public static ArrayList<BackData> backTemp;
    public static ArrayList<BottomData> bottomTemp;
    public static ArrayList<LightData> lightTemp;

    public static void launch(String[] args) {

        // Objet matérialisant la console d'exécution (Affichage Écran / Lecture
        // Clavier)
        final Console console = new Console();

        // Affichage sur la console
        console.log("DÉBUT du programme TestArduino");

        console.log("TOUS les Ports COM Virtuels:");
        for (String port : ArduinoManager.listVirtualComPorts()) {
            console.log(" - " + port);
        }
        console.log("----");

        // Recherche d'un port disponible (avec une liste d'exceptions si besoin)
        String myPort = ArduinoManager.searchVirtualComPort("COM0", "/dev/tty.usbserial-FTUS8LMO", "<autre-exception>");

        console.log("CONNEXION au port " + myPort);

        String[] typesCapteur = { "torsi", "lumi", "flexi" };

        ArduinoManager arduino = new ArduinoManager(myPort) {
            @Override
            protected void onData(String line) {

                // Cette méthode est appelée AUTOMATIQUEMENT lorsque l'Arduino envoie des
                // données
                // Affichage sur la Console de la ligne transmise par l'Arduino
                console.println("ARDUINO >> " + line);

                String type = line.split(":")[0];
                String data = line.split(":")[1];
                if (type == "torsi") {
                    ArrayList<BackData> temp = Reception.backDataFormating(data);
                    for (BackData x : temp) {
                        backTemp.add(x);
                    }
                } else if (type == "lumi") {
                    ArrayList<LightData> temp = Reception.lightDataFormating(data);
                    for (LightData x : temp) {
                        lightTemp.add(x);
                    }
                } else if (type == "flexi") {
                    ArrayList<BottomData> temp = Reception.bottomDataFormating(data);
                    for (BottomData x : temp) {
                        bottomTemp.add(x);
                    }
                }
            }
        };

        try

        {

            console.log("DÉMARRAGE de la connexion");
            // Connexion à l'Arduino
            arduino.start();

            console.log("BOUCLE infinie en attente du Clavier");
            // Boucle d'ecriture sur l'arduino (execution concurrente au thread)
            boolean exit = false;

            while (!exit) {

                // Lecture Clavier de la ligne saisie par l'Utilisateur
                String line = console.readLine("Envoyer une ligne (ou 'stop') > ");

                if (line.length() != 0) {

                    // Affichage sur l'écran
                    console.log("CLAVIER >> " + line);

                    // Test de sortie de boucle
                    exit = line.equalsIgnoreCase("stop");

                    if (!exit) {
                        // Envoi sur l'Arduino du texte saisi au Clavier
                        arduino.write(line);
                    }
                }

                // Addicione a la base de données
                if (Reception.bottomTemp.size() >= 30 * 8) {
                    BottomData moyen = BottomData.moyenne(Reception.bottomTemp);
                    try {
                        Main.db.insert(moyen);
                    } catch (Exception e) {
                        System.out.println("Erreur ajout BottomData");
                        e.printStackTrace();
                    }
                }

                if (Reception.backTemp.size() >= 30 * 5) {
                    BackData moyen = BackData.moyenne(Reception.backTemp);
                    try {
                        Main.db.insert(moyen);
                    } catch (Exception e) {
                        System.out.println("Erreur ajout BackData");
                        e.printStackTrace();
                    }
                }

                if (Reception.backTemp.size() >= 30 * 8) {
                    LightData moyen = LightData.moyenne(Reception.lightTemp);
                    try {
                        Main.db.insert(moyen);
                    } catch (Exception e) {
                        System.out.println("Erreur ajout LightsData");
                        e.printStackTrace();
                    }
                }
            }

            console.log("ARRÊT de la connexion");
            // Fin de la connexion à l'Arduino
            arduino.stop();

        } catch (IOException ex) {
            // Si un problème a eu lieu...
            console.log(ex);
        }

    }

    public static ArrayList<BackData> backDataFormating(String line) {
        ArrayList<BackData> backDatas = new ArrayList<>();

        String[] temp = line.split(";");
        int[] torsionData = new int[30];

        for (int i = 0; i < torsionData.length; i++) {
            torsionData[i] = Integer.parseInt(temp[i]);
        }

        int[][] torsionData_Formated = new int[5][6];

        for (int i = 0; i < torsionData.length / 6; i++) {
            torsionData_Formated[i] = getSliceOfArray(torsionData, 6 * i, 6 * (i + 1) - 1);
        }

        for (int i = 0; i < torsionData_Formated.length; i++) {
            backDatas.add(new BackData(System.currentTimeMillis(), torsionData_Formated[i]));
        }

        return backDatas;
    }

    public static ArrayList<BottomData> bottomDataFormating(String line) {
        ArrayList<BottomData> bottomDatas = new ArrayList<>();

        String[] temp = line.split(";");
        int[] flexiforceData = new int[32];

        for (int i = 0; i < flexiforceData.length; i++) {
            flexiforceData[i] = Integer.parseInt(temp[i]);
        }

        int[][] flexiforceData_Formated = new int[8][4];

        for (int i = 0; i < flexiforceData.length / 6; i++) {
            flexiforceData_Formated[i] = getSliceOfArray(flexiforceData, 4 * i, 4 * (i + 1) - 1);
        }

        for (int i = 0; i < flexiforceData_Formated.length; i++) {
            bottomDatas.add(new BottomData(System.currentTimeMillis(), flexiforceData_Formated[i]));
        }

        return bottomDatas;
    }

    public static ArrayList<LightData> lightDataFormating(String line) {
        ArrayList<LightData> lightDatas = new ArrayList<>();

        String[] temp = line.split(";");
        int[] lightData = new int[32];

        for (int i = 0; i < lightData.length; i++) {
            lightData[i] = Integer.parseInt(temp[i]);
        }

        int[][] lightData_Formated = new int[8][4];

        for (int i = 0; i < lightData.length / 6; i++) {
            lightData_Formated[i] = getSliceOfArray(lightData, 4 * i, 4 * (i + 1) - 1);
        }

        for (int i = 0; i < lightData_Formated.length; i++) {
            lightDatas.add(new LightData(System.currentTimeMillis(), lightData_Formated[i]));
        }

        return lightDatas;
    }

    public static int[] getSliceOfArray(int[] arr, int startIndex, int endIndex) {

        // Get the slice of the Array
        int[] slice = Arrays.copyOfRange(arr, startIndex, endIndex);

        // return the slice
        return slice;
    }
}