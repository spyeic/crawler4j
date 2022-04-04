package com.lderic.crawler4j.format.json;

/**
 * A mark class for JSONObject and JSONArray
 */
public abstract class JSONContainer extends JSONElement{
    protected abstract String beautify(int indentation);
}
