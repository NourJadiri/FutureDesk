package fr.p2i.desk.ArduinoReception;

import java.util.Arrays;
import fr.p2i.desk.data.BackData;
import fr.p2i.desk.util.ArduinoManager;
import fr.p2i.desk.util.Console;

import java.io.IOException;
import java.util.ArrayList;

public class Reception {

    public static void launch(String[] args) {

        // Objet matérialisant la console d'exécution (Affichage Écran / Lecture Clavier)
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

        ArduinoManager arduino = new ArduinoManager(myPort) {
            @Override
            protected void onData(String line) {

                // Cette méthode est appelée AUTOMATIQUEMENT lorsque l'Arduino envoie des données
                // Affichage sur la Console de la ligne transmise par l'Arduino
                console.println("ARDUINO >> " + line);

            }
        };

        try {

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
        int[] flexiforceData = new int[30];

        for (int i = 0; i < flexiforceData.length; i++) {
            flexiforceData[i] = Integer.parseInt(temp[i]);
        }

        int[][] flexiforceData_Formated = new int[5][6];

        for (int i = 0; i < flexiforceData.length / 6; i++) {
            flexiforceData_Formated[i] = getSliceOfArray(flexiforceData, 6 * i, 6 * (i + 1) - 1);
        }

        for (int i = 0; i < flexiforceData_Formated.length; i++) {
            backDatas.add(new BackData(System.currentTimeMillis(),flexiforceData_Formated[i]));
        }


        return backDatas;
    }

    public static int[] getSliceOfArray(int[] arr, int startIndex, int endIndex) {

        // Get the slice of the Array
        int[] slice = Arrays.copyOfRange(arr, startIndex, endIndex);

        // return the slice
        return slice;
    }
}