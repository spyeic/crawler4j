package com.lderic.crawler4j.format.json;

public class JSONString extends JSONValue<String> {
    private final String value;

    protected JSONString(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "\"" + value + "\"";
    }

    @Override
    public String getValue() {
        return value;
    }
}
