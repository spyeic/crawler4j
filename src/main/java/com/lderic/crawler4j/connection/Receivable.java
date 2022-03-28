package com.lderic.crawler4j.connection;

import java.io.IOException;
import java.io.InputStream;

/**
 * This is an interface to convert byte array to Type T.
 * @implNote Any class that implement the interface can as a parameter in get method in Crawler
 * @see com.lderic.crawler4j.Crawler
 * @param <T>
 */
public interface Receivable<T> {
    T toOriginal(InputStream content);
}
