package com.lderic.crawler4j.format.json;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class JSONObject extends JSONElement {
    private final HashMap<String, JSONElement> map = new HashMap<>();

    public JSONObject setEntry(String key, Number value) {
        return setEntry(key, new JSONNumber(value));
    }

    public JSONObject setEntry(String key, String value) {
        return setEntry(key, new JSONString(value));
    }

    public JSONObject setEntry(String key, boolean value) {
        return setEntry(key, new JSONBoolean(value));
    }

    public <T extends JSONElement> JSONObject setEntry(String key, List<T> value) {
        return setEntry(key, new JSONArray<>(value));
    }

    public JSONObject setEntry(String key, String... values) {
        return setEntry(key, JSONHelper.jsonArrayOf(values));
    }

    public JSONObject setEntry(String key, Number... values) {
        return setEntry(key, JSONHelper.jsonArrayOf(values));
    }

    public JSONObject setEntry(String key, JSONObject value) {
        return setEntry(key, (JSONElement) value);
    }

    public JSONObject setNullEntry(String key) {
        return setEntry(key, new JSONNull());
    }

    public JSONObject setEntry(String key, JSONElement value) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(value);
        value.setFather(this);
        map.put(key, value);
        return this;
    }

    public JSONElement remove(String key) {
        JSONElement result = map.remove(key);
        result.setFather(null);
        return result;
    }

    public JSONObject newChildObject(String key) {
        JSONObject child = new JSONObject();
        child.setFather(this);
        this.setEntry(key, child);
        return child;
    }

    public JSONElement get(String key) throws JSONConvertException {
        JSONElement result = map.get(key);
        if (result == null) {
            throw new JSONConvertException("This JSONObject doesn't have key " + key);
        }
        return result;
    }

    @Override
    public String toString() {
        List<Map.Entry<String, JSONElement>> list = map.entrySet().stream().toList();
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (map.size() > 0) {
            sb.append(JSONHelper.toKeyValueSet(list.get(0).getKey(), list.get(0).getValue()));
        }
        for (int i = 1; i < list.size(); i++) {
            sb.append(", ").append(JSONHelper.toKeyValueSet(list.get(i).getKey(), list.get(i).getValue()));
        }
        sb.append("}");
        return sb.toString();
    }

    @Override
    public Object getValue() throws JSONConvertException {
        throw new JSONConvertException("JSONObject doesn't have any value");
    }
}
