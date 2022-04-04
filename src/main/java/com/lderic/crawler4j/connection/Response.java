package com.lderic.crawler4j.connection;

import com.lderic.crawler4j.converter.receiver.Receiver;

import java.io.IOException;
import java.util.List;

public interface Response<T> {
    T getContent();

    String getURL();

    int getCode();

    String getMessage();

    List<Header> getHeaders();

    Header getHeader(String name);

    T receive(Receiver<T> converter) throws IOException;
}