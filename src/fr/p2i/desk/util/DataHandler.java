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
