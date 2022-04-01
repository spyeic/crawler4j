package com.lderic.crawler4j.converter.receiver;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * This is an interface to convert byte array to Type T.
 *
 * @param <T>
 * @implNote Any class that implement the interface can as a parameter in get method in Crawler
 * @see com.lderic.crawler4j.Crawler
 */
public interface Receiver<T> {
    T toOriginal(InputStream content);

    default T fromBytes(byte[] bytes) {
        return toOriginal(new ByteArrayInputStream(bytes));
    }
}
