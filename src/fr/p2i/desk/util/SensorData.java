package fr.p2i.desk.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SensorData {

    public String type;
    Map<String, List<Integer>> mp = new HashMap<>();

    public Map<String, Integer> mean() {
        Map<String, Integer> map = new HashMap<>();
        for (String s : mp.keySet()) {
            Integer b = 0;
            for (Integer a : mp.get(s)) {
                b = b + a;
            }
            b = b / mp.get(s).size();
            map.put(s, b);
        }
        return map;
    }

    public SensorData() {
    }

    public SensorData(int t, int[] a) {
    }
}
