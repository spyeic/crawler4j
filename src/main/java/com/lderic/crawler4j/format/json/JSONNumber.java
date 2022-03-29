package com.lderic.crawler4j.format.json;

public class JSONNumber extends JSONElement{
    private final Number value;

    public JSONNumber(Number num){
        this.value = num;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public JSONElement getFather() {
        return null;
    }

    @Override
    public Object getValue() {
        return value;
    }
}
