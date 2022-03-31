package com.lderic.crawler4j.connection;

import com.lderic.crawler4j.converter.receiver.Receiver;
import com.lderic.crawler4j.converter.sender.Sender;

import java.io.IOException;
import java.net.HttpCookie;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

@SuppressWarnings("ALL")
public interface Connection {
    <T> Response<T> open(Receiver<T> converter) throws IOException;

    interface Builder {
        Connection build();

        Builder url(URL url);

        Builder userAgent(String userAgent);

        Builder header(Header header);

        Builder header(String name, String value);

        Builder cookie(String name, String value);

        Builder cookie(HttpCookie cookie);

        Builder referrer(String location);

        Builder contentType(String type);

        Builder connectTimeout(int milliseconds);

        Builder readTimeout(int milliseconds);

        Builder setMethod(Request.Method method);

        Builder setBody(byte[] bytes);

        <T> Builder setBody(T content, Sender<T> converter);

        Builder setBody(String content);
    }
}
