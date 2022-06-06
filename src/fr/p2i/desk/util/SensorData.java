package fr.p2i.desk.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SensorData {

    public String type;
    Map<String, List<Integer>> mp = new HashMap<>();



    public SensorData() {
    }

    public SensorData(int t, int[] a) {
    }
}
