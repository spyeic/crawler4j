package com.lderic.crawler4j.format.json;

/**
 * Mark class.
 */
public abstract class JSONElement {
    public abstract String buildString();

    public JSONObject asObject(){
        return (JSONObject) this;
    }

    public JSONArray<JSONElement> asArray(){
        return (JSONArray<JSONElement>) this;
    }

    public JSONNumber asNumber() {
        return (JSONNumber) this;
    }

    public JSONBoolean asBoolean() {
        return (JSONBoolean) this;
    }

    public JSONString asString() {
        return (JSONString) this;
    }
}
