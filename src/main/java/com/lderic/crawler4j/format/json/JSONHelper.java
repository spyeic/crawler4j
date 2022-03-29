package com.lderic.crawler4j.format.json;

import java.util.ArrayList;
import java.util.List;

public class JSONHelper {
    public static String toKeyValueSet(String key, JSONElement value) {
        return "\"" + key + "\": " + value.toString();
    }

    public static JSONArray<JSONNumber> jsonArrayOf(Number... nums) {
        return new JSONArray<>(numberListOf(nums));
    }

    public static JSONArray<JSONString> jsonArrayOf(String... nums) {
        return new JSONArray<>(stringListOf(nums));
    }

    public static List<JSONNumber> numberListOf(Number... nums) {
        List<JSONNumber> list = new ArrayList<>();
        for (Number num : nums) {
            list.add(new JSONNumber(num));
        }
        return list;
    }

    public static List<JSONString> stringListOf(String... strings) {
        List<JSONString> list = new ArrayList<>();
        for (String str : strings) {
            list.add(new JSONString(str));
        }
        return list;
    }
}
