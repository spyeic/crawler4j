package com.lderic.crawler4j.connection;

/**
 * This is an interface to convert byte array to Type T.
 * @implNote Any class that implement the interface can as a parameter in get method in Crawler
 * @see com.lderic.crawler4j.Crawler
 * @param <T>
 */
public interface Receivable<T> {
    T toOriginal(byte[] content);
}
