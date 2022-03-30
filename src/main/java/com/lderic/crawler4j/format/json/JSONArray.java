package com.lderic.crawler4j.format.json;

import java.util.List;

public class JSONArray<T extends JSONElement> extends JSONElement {
    private final List<T> list;

    public JSONArray(List<T> value) {
        this.list = value;
    }

    public JSONElement get(int index) {
        return list.get(index);
    }

    public void add(T element) {
        element.setFather(this);
        list.add(element);
    }

    public JSONElement remove(int index) {
        JSONElement result = list.remove(index);
        result.setFather(null);
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        if (list.size() > 0) {
            sb.append(list.get(0).toString());
        }
        for (int i = 1; i < list.size(); i++) {
            sb.append(", ").append(list.get(i).toString());
        }
        sb.append("]");
        return sb.toString();
    }
}
