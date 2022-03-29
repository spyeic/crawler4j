package com.lderic.crawler4j.format.json;

public class JSONBoolean extends JSONElement {
    boolean value;

    public JSONBoolean(boolean value) {
        this.value = value;
    }

    @Override
    public String buildString() {
        return value ? "true" : "false";
    }
}
