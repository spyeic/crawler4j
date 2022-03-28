package com.lderic.crawler4j.connection;

public interface Inputtable<T> {
    T transfer(byte[] content);
}
