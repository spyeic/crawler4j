package com.lderic.crawler4j.format.json;

public abstract class JSONValue<T> extends JSONElement{
    public abstract T getValue() throws JSONConvertException;
}