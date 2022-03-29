package com.lderic.crawler4j.format.json;

public class JSONString extends JSONElement {
    private final String value;

    public JSONString(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "\"" + value + "\"";
    }

    @Override
    public Object getValue() {
        return value;
    }
}
