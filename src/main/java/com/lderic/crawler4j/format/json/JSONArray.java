package com.lderic.crawler4j.format.json;

import java.util.List;

public class JSONArray<T extends JSONElement> extends JSONElement {
    List<T> list;

    public JSONArray(List<T> value) {
        this.list = value;
    }

    public JSONElement get(int index) {
        return list.get(index);
    }

    public void add(T element) {
        list.add(element);
    }

    @Override
    public String buildString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        if (list.size() > 0) {
            sb.append(list.get(0).buildString());
        }
        for (int i = 1; i < list.size(); i++) {
            sb.append(", ").append(list.get(i).buildString());
        }
        sb.append("]");
        return sb.toString();
    }
}
