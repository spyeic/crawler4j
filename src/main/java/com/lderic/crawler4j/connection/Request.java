package com.lderic.crawler4j.connection;

import java.io.IOException;
import java.net.URLConnection;

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