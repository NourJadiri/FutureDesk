package fr.p2i.desk.data;

import fr.p2i.desk.util.SensorData;

public class LightData extends SensorData {

    private int r;
    private int g;
    private int b;
    private int light;
    private long timestamp;
    public LightData(int r,int g,int b,int light){
        this.type = "lights";
        timestamp=System.currentTimeMillis();
        this.r=r;
        this.g=g;
        this.b=b;
        this.light=light;
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

    @Override
    public String toString() {
        return timestamp+";"+r+";"+g+';'+b+";"+light;
    }
}
