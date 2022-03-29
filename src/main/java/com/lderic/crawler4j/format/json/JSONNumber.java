package com.lderic.crawler4j.format.json;

public class JSONNumber extends JSONElement{
    Number value;

    public JSONNumber(Number num){
        this.value = num;
    }

    @Override
    public String buildString() {
        return value.toString();
    }
}
