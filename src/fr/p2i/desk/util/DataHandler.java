package fr.p2i.desk.util;

import fr.p2i.desk.Main;

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
