package com.lderic.crawler4j.format.json;

import java.util.Objects;

public class JSONString extends JSONElement {
    String value;

    public JSONString(String value) {
        Objects.requireNonNull(value);
        this.value = value;
    }

    @Override
    public String buildString() {
        return "\"" + value + "\"";
    }
}
