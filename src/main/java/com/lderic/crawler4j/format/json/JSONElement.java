package com.lderic.crawler4j.format.json;

/**
 * Mark class.
 */
public abstract class JSONElement {
    private JSONElement father;

    public abstract String toString();

    public JSONElement getFather(){
        return father;
    }

    protected void setFather(JSONElement father){
        this.father = father;
    }

    public abstract Object getValue() throws JSONConvertException;

    public JSONObject asObject(){
        return (JSONObject) this;
    }

    public JSONArray<JSONElement> asArray(){
        return (JSONArray<JSONElement>) this;
    }
}
