package com.lderic.crawler4j.format.json;

import java.util.ArrayList;
import java.util.List;

public class JSONHelper {
    public static String toKeyValueSet(String key, JSONElement value) {
        return "\"" + key + "\": " + value.toString();
    }

    public static JSONArray jsonArrayOf(Number... nums) {
        return new JSONArray(numberListOf(nums));
    }

    public static JSONArray jsonArrayOf(String... nums) {
        return new JSONArray(stringListOf(nums));
    }

    public static List<JSONElement> numberListOf(Number... nums) {
        List<JSONElement> list = new ArrayList<>();
        for (Number num : nums) {
            list.add(new JSONNumber(num));
        }
        return list;
    }

    public static List<JSONElement> stringListOf(String... strings) {
        List<JSONElement> list = new ArrayList<>();
        for (String str : strings) {
            list.add(new JSONString(str));
        }
        return list;
    }
}
