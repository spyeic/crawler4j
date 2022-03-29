package com.lderic.crawler4j.format.json;

public class JSONNull extends JSONElement {
    @Override
    public String toString() {
        return "null";
    }

    @Override
    public Object getValue() {
        return null;
    }
}
