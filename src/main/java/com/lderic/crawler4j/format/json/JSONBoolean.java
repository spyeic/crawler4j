package com.lderic.crawler4j.format.json;

public class JSONBoolean extends JSONElement {
    private final boolean value;

    public JSONBoolean(boolean value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value ? "true" : "false";
    }

    @Override
    public Object getValue() {
        return value;
    }
}
