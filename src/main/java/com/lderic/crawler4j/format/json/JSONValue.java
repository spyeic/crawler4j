package com.lderic.crawler4j.format.json;

/**
 * Mark class.
 * @param <T> Type of value.
 */
public abstract class JSONValue<T> extends JSONElement{
    public abstract T getValue() throws JSONConvertException;
}