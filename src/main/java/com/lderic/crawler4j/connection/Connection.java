package com.lderic.crawler4j.connection;

import java.io.IOException;
import java.net.HttpCookie;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

@SuppressWarnings("ALL")
public interface Connection {
    <T> T open(Receivable<T> converter) throws IOException;

    byte[] open() throws IOException;

    List<Header> getResponseHeaders();

    interface Builder {
        Connection build();

        Builder url(URL url);

        Builder userAgent(String userAgent);

        Builder header(String name, String value);

        Builder cookie(String name, String value);

        Builder cookie(HttpCookie cookie);

        Builder referrer(String location);

        Builder contentType(String type);

        Builder connectTimeout(int milliseconds);

        Builder readTimeout(int milliseconds);

        Builder setMethod(Request.Method method);

        Builder setBody(byte[] bytes);

        <T> Builder setBody(T content, Sendable<T> converter);

        Builder setBody(String content);
    }

    public interface Request {
        URLConnection request() throws IOException;

        enum ConnectType {
            Json("application/json"),
            x_www_form_urlencoded("application/x-www-form-urlencoded");

            public final String string;

            ConnectType(String string) {
                this.string = string;
            }
        }

        enum Method {
            GET(false),
            HEAD(false),
            POST(true),
            PUT(true),
            DELETE(false),
            CONNECT(false),
            OPTIONS(false),
            TRACE(false),
            PATCH(false);

            public final boolean hasBody;

            Method(boolean hasBody) {
                this.hasBody = hasBody;
            }
        }
    }

    public interface Response {
        <T> T receive(Receivable<T> converter) throws IOException;

        byte[] receive() throws IOException;
    }
}