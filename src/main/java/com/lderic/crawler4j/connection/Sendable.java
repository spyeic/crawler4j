package com.lderic.crawler4j.connection;

/**
 * This is an interface that to convert type T to byte array
 * @implNote Any class that implement the interface can as a parameter in post method in Crawler
 * @see com.lderic.crawler4j.Crawler
 * @param <T>
 */
public interface Sendable<T> {
    byte[] toBytes(T original);
}
