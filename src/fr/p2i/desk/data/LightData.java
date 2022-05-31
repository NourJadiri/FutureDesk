package fr.p2i.desk.data;

import fr.p2i.desk.util.SensorData;

import java.util.ArrayList;
import java.util.ArrayList;

public class LightData extends SensorData {

    private int r;
    private int g;
    private int b;
    private int light;
    private long timestamp;

    public LightData(int r, int g, int b, int light) {
        this.type = "lights";
        timestamp = System.currentTimeMillis();
        this.r = r;
        this.g = g;
        this.b = b;
        this.light = light;
    }

    public LightData() {
        this.type = "lights";
    }

    public LightData(long timestamp, int[] t) {
        this();
        this.timestamp = timestamp;
        light = t[0];
        r = t[1];
        g = t[2];
        b = t[3];
    }

    public int getR() {
        return r;
    }

    public int getB() {
        return b;
    }

    public int getG() {
        return g;
    }

    public int getLight() {
        return light;
    }

    public int[] getLightData() {
        int[] result = new int[4];
        result[0] = r;
        result[0] = g;
        result[0] = b;
        result[0] = light;
        return result;
    }

    @Override
    public String toString() {
        return timestamp + ";" + r + ";" + g + ';' + b + ";" + light;
    }

    public static LightData moyenne(ArrayList<LightData> list) {
        int[] somme = { 0, 0, 0, 0 };
        for (int i = 0; i < list.size(); i++) {
            int[] temp = list.get(i).getLightData();
            for (int j = 0; j < 4; j++) {
                somme[j] += temp[j];
            }
        }

        for (int i = 0; i < 4; i++) {
            somme[i] /= list.size();
        }

        return new LightData(System.currentTimeMillis(), somme);
    }
}
