package com.lderic.crawler4j.connection;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class HttpConnection implements Connection {
    private final HttpRequest req;
    private final CookieStorage cookieStorage;
    private HttpResponse res;

    private HttpConnection(HttpRequest req) {
        this.req = req;
        this.cookieStorage = req.cookieStorage;
    }

    public static Builder newBuilder(CookieStorage cookieStorage) {
        return new HttpConnectionBuilder(cookieStorage);
    }

    @Override
    public <T> T open(Inputtable<T> converter) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) req.request();
        res = new HttpResponse(conn, cookieStorage);
        return res.receive(converter);
    }

    @Override
    public byte[] open() throws IOException {
        HttpURLConnection conn = (HttpURLConnection) req.request();
        res = new HttpResponse(conn, cookieStorage);
        return res.receive();
    }

    @Override
    public List<Header> getResponseHeaders() {
        if (res.headers == null) {
            return null;
        }
        return res.headers;
    }

    protected static class HttpConnectionBuilder implements Builder {
        private Request.Method requestMethod;
        private URL url;
        private int connectTimeout;
        private int readTimeout;
        private final List<Header> headers = new ArrayList<>();
        private final CookieStorage cookieStorage;
        private byte[] body;

        private final List<HttpCookie> tempCookies = new ArrayList<>();

        public HttpConnectionBuilder(CookieStorage cookieStorage) {
            this.cookieStorage = cookieStorage;
            this.headers.add(Header.defaultUserAgent());
        }

        @Override
        public Connection build() {
            Objects.requireNonNull(url);

            tempCookies.forEach(cookie -> cookieStorage.add(url.toString(), cookie));

            return new HttpConnection(new HttpRequest(requestMethod, url, headers, cookieStorage, body, connectTimeout, readTimeout));
        }

        @Override
        public Builder url(URL url) {
            this.url = url;
            return this;
        }

        @Override
        public Builder userAgent(String userAgent) {
            this.headers.add(Header.userAgent(userAgent));
            return this;
        }

        @Override
        public Builder header(String name, String value) {
            this.headers.add(new Header(name, value));
            return this;
        }

        @Override
        public Builder cookie(String name, String value) {
            tempCookies.add(new HttpCookie(name, value));
            return this;
        }

        @Override
        public Builder cookie(HttpCookie cookie) {
            tempCookies.add(cookie);
            return this;
        }

        @Override
        public Builder referrer(String url) {
            this.headers.add(Header.referrer(url));
            return this;
        }

        @Override
        public Builder contentType(String type) {
            this.headers.add(Header.contentType(type));
            return this;
        }

        @Override
        public Builder connectTimeout(int milliseconds) {
            this.connectTimeout = milliseconds;
            return this;
        }

        @Override
        public Builder readTimeout(int milliseconds) {
            this.readTimeout = milliseconds;
            return this;
        }

        @Override
        public Builder setMethod(Request.Method method) {
            requestMethod = method;
            return this;
        }

        @Override
        public Builder setBody(byte[] content) {
            this.body = content;
            return this;
        }

        @Override
        public <T> Builder setBody(T content, Outputtable<T> converter) {
            setBody(converter.body(content));
            return this;
        }

        @Override
        public Builder setBody(String content) {
            setBody(content.getBytes(StandardCharsets.UTF_8));
            return this;
        }
    }

    public static class HttpRequest implements Request {
        private final Method requestMethod;
        private final URL url;
        private final List<Header> headers;
        private final CookieStorage cookieStorage;
        private final byte[] body;
        private final Integer connectTimeout;
        private final Integer readTimeout;

        private HttpRequest(Method method, URL url, List<Header> headers, CookieStorage cookieStorage, byte[] body, Integer connectTimeout, Integer readTimeout) {
            this.requestMethod = method;
            this.url = url;
            this.headers = headers;
            this.cookieStorage = cookieStorage;
            this.body = body;
            this.connectTimeout = connectTimeout;
            this.readTimeout = readTimeout;
        }

        @Override
        public URLConnection request() throws IOException {
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod(requestMethod.name());
            if (requestMethod.hasBody) {
                conn.setDoOutput(true);
                conn.setDoInput(true);
            }
            if (connectTimeout != null) {
                conn.setConnectTimeout(connectTimeout);
            }
            if (readTimeout != null) {
                conn.setReadTimeout(readTimeout);
            }
            headers.forEach(header -> conn.setRequestProperty(header.getKey(), header.getValue()));
            StringBuilder sb = new StringBuilder();
            List<HttpCookie> cookies = cookieStorage.getAll();
            if (cookies.size() != 0) {
                sb.append(cookies.get(0).toString());
                for (int i = 1; i < cookies.size(); i++) {
                    sb.append(";").append(cookies.get(i).toString());
                }
                conn.setRequestProperty(Header.RequestHeaderKey.Cookie.string, sb.toString());
            }

            // Start connection
            conn.connect();

            if (requestMethod.hasBody) {
                if (body != null) {
                    conn.getOutputStream().write(body);
                }
            }
            return conn;
        }
    }

    public static class HttpResponse implements Response {
        private final HttpURLConnection conn;
        private final CookieStorage cookieStorage;
        @SuppressWarnings("FieldCanBeLocal")
        private int code;
        @SuppressWarnings("FieldCanBeLocal")
        private String message;
        private List<Header> headers;

        private HttpResponse(HttpURLConnection conn, CookieStorage cookieStorage) {
            this.conn = conn;
            this.cookieStorage = cookieStorage;
        }

        @Override
        public <T> T receive(Inputtable<T> converter) throws IOException {
            return converter.transfer(receive());
        }

        @Override
        public byte[] receive() throws IOException {
            byte[] result;
            code = conn.getResponseCode();
            message = conn.getResponseMessage();
            try (BufferedInputStream bis = new BufferedInputStream(conn.getInputStream()); ByteArrayOutputStream baos = new ByteArrayOutputStream(8192)) {
                int len;
                while ((len = bis.read()) != -1) {
                    baos.write(len);
                }
                result = baos.toByteArray();
            }
            Map<String, List<String>> headerFields = conn.getHeaderFields();
            headers = new ArrayList<>();
            headerFields.forEach((key, values) -> {
                if (key != null) {
                    if ("Set-Cookie".equals(key)) {
                        values.forEach(str -> HttpCookie.parse(str).forEach(cookie -> {
                            System.out.println(cookie);
                            cookieStorage.add(conn.getURL().toString(), cookie);
                        }));
                    } else {
                        StringBuilder sb = new StringBuilder();
                        sb.append(values.get(0));

                        for (int i = 1; i < values.size(); i++) {
                            sb.append(";").append(i);
                        }
                        headers.add(new Header(key, sb.toString()));
                    }
                }
            });
            conn.disconnect();
            return result;
        }
    }
}
