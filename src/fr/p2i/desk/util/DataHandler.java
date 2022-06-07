package fr.p2i.desk.util;

import fr.p2i.desk.Main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.util.ArrayList;
import java.util.List;

public class DataHandler<E extends SensorData> {

    public static boolean work = false;
    private List<E> tempList = new ArrayList<E>();
    private List<E> list = new ArrayList<E>();
    public void add(E d){
        tempList.add(d);
    }

    public void clear(){
        for(E b : tempList) {
            list.add(b);
        }
        tempList.clear();
    }

    public List<E> getList() {
        return list;
    }

    public static double calculateSD(int numArray[])
    {
        double sum = 0.0, standardDeviation = 0.0;
        int length = numArray.length;

        for(double num : numArray) {
            sum += (double)num;
        }

        double mean = sum/length;

        for(double num: numArray) {
            standardDeviation += Math.pow(num - mean, 2);
        }

        return Math.sqrt(standardDeviation/length);
    }
    public static synchronized void playSound(final String url) {
        new Thread(new Runnable() {
            // The wrapper thread is unnecessary, unless it blocks on the
            // Clip finishing; see comments.
            public void run() {
                try {
                    Clip clip = AudioSystem.getClip();
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                            Main.class.getResourceAsStream("/path/to/sounds/" + url));
                    clip.open(inputStream);
                    clip.start();
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        }).start();
    }
    public void push(){
        for(E a : tempList) {
            try {
                Main.db.insert(a);
                list.add(a);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    tempList = new ArrayList<>();

    }
}
