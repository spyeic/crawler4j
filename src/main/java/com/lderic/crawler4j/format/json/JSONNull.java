package com.lderic.crawler4j.format.json;

public class JSONNull extends JSONElement {
    @Override
    public String buildString() {
        return "null";
    }
}
