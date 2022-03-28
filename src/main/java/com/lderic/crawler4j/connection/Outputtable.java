package com.lderic.crawler4j.connection;

public interface Outputtable<T> {
    byte[] body(T original);
}
