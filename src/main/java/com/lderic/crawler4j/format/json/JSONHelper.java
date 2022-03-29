package com.lderic.crawler4j.format.json;

import java.util.ArrayList;
import java.util.List;

public class JSONHelper {
    public static String toKeyValueSet(String key, JSONElement value) {
        return "\"" + key + "\": " + value.buildString();
    }

    public static JSONArray<JSONNumber> jsonArrayOf(Number... nums) {
        List<JSONNumber> list = new ArrayList<>();
        for (Number num : nums) {
            list.add(new JSONNumber(num));
        }
        return new JSONArray<>(list);
    }

    public static List<JSONNumber> jsonNumberListOf(Number... nums){
        List<JSONNumber> list = new ArrayList<>();
        for (Number num : nums) {
            list.add(new JSONNumber(num));
        }
        return list;
    }
}
