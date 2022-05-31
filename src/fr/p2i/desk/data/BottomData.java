package fr.p2i.desk.data;

import fr.p2i.desk.util.SensorData;
import java.util.ArrayList;

public class BottomData extends SensorData {

    private int d1;
    private int d2;
    private int g1;
    private int g2;
    private long timestamp;

    public BottomData(int d1, int d2, int g1, int g2) {
        this.type = "bottom";
        timestamp = System.currentTimeMillis();
        this.d1 = d1;
        this.d2 = d2;
        this.g1 = g1;
        this.g2 = g2;
    }

    public BottomData() {
        this.type = "bottom";
    }

    public BottomData(long timestamp, int[] t) {
        this();
        this.timestamp = timestamp;
        d1 = t[0];
        d2 = t[1];
        g1 = t[2];
        g2 = t[3];
    }

    public int[] getBottomData() {
        int[] result = new int[6];
        result[0] = d1;
        result[0] = d2;
        result[0] = g1;
        result[0] = g2;
        return result;
    }

    @Override
    public String toString() {
        return timestamp + ";" + d1 + ";" + d2 + ";" + g1 + ";" + g2;
    }

    public static BottomData moyenne(ArrayList<BottomData> list) {
        int[] somme = { 0, 0, 0, 0 };
        for (int i = 0; i < list.size(); i++) {
            int[] temp = list.get(i).getBottomData();
            for (int j = 0; j < 4; j++) {
                somme[j] += temp[j];
            }
        }

        for (int i = 0; i < 4; i++) {
            somme[i] /= list.size();
        }

        return new BottomData(System.currentTimeMillis(), somme);
    }
}
