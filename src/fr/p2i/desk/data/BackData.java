package fr.p2i.desk.data;

import fr.p2i.desk.util.SensorData;
import java.util.ArrayList;

public class BackData extends SensorData {

    private int d1;
    private int d2;
    private int d3;
    private int g1;
    private int g2;
    private int g3;
    private long timestamp;

    public BackData() {
        timestamp = System.currentTimeMillis();
        this.type = "back";
    }

    public BackData(long timestamp, int[] t) {
        this();
        d1 = t[0];
        d2 = t[1];
        d3 = t[2];
        g1 = t[3];
        g2 = t[4];
        g3 = t[5];
    }

    public BackData(int d1, int d2, int d3, int g1, int g2, int g3) {
        this();
        this.d1 = d1;
        this.d2 = d2;
        this.d3 = d3;
        this.g1 = g1;
        this.g2 = g2;
        this.g3 = g3;
    }

    public int[] getBackData() {
        int[] result = new int[6];
        result[0] = d1;
        result[1] = d2;
        result[2] = d3;
        result[3] = g1;
        result[4] = g2;
        result[5] = g3;
        return result;
    }

    @Override
    public String toString() {
        return timestamp + ";" + d1 + ";" + d2 + ";" + d3 + ";" + g1 + ";" + g2 + ";" + g3;
    }

    public static BackData moyenne(ArrayList<BackData> list) {
        int[] somme = { 0, 0, 0, 0, 0, 0 };
        for (int i = 0; i < list.size(); i++) {
            int[] temp = list.get(i).getBackData();
            for (int j = 0; j < 6; j++) {
                somme[j] += temp[j];
            }
        }

        for (int i = 0; i < 6; i++) {
            somme[i] /= list.size();
        }

        return new BackData(System.currentTimeMillis(), somme);
    }
}
