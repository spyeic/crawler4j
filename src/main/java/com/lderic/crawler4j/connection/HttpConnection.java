package com.lderic.crawler4j.connection;

import com.lderic.crawler4j.converter.receiver.Receiver;
import com.lderic.crawler4j.converter.sender.Sender;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class HttpConnection implements Connection {
    private final HttpRequest req;
    private final CookieStorage cookieStorage;
    private final URL url;

    private HttpURLConnection conn;

    private HttpConnection(CookieStorage cookieStorage, URL url, Request.Method requestMethod, List<Header> requestHeaders, byte[] body, Integer connectTimeout, Integer readTimeout) {
        this.cookieStorage = cookieStorage;
        this.url = url;
        this.req = new HttpRequest(requestMethod, requestHeaders, body, connectTimeout, readTimeout);
    }

    /**
     * Get a HttpConnection builder.
     *
     * @param cookieStorage a storage of
     * @see com.lderic.crawler4j.connection.Connection.Builder
     */
    public static Builder getBuilder(CookieStorage cookieStorage) {
        return new HttpConnectionBuilder(cookieStorage);
    }

    @Override
    public <T> Response<T> open(Receiver<T> receiver) throws IOException {
        req.request();
        HttpResponse<T> res = new HttpResponse<>();
        res.receive(receiver);
        conn.disconnect();
        return res;
    }

    protected static class HttpConnectionBuilder implements Builder {
        private final List<Header> headers = new ArrayList<>();
        private final CookieStorage cookieStorage;
        private final List<HttpCookie> tempCookies = new ArrayList<>();
        private Request.Method requestMethod;
        private URL url;
        private Integer connectTimeout;
        private Integer readTimeout;
        private byte[] body;

        public HttpConnectionBuilder(CookieStorage cookieStorage) {
            this.cookieStorage = cookieStorage;
            this.headers.add(Header.defaultUserAgent());
        }

        @Override
        public Connection build() {
            Objects.requireNonNull(url);

            tempCookies.forEach(cookie -> cookieStorage.add(url.toString(), cookie));

            return new HttpConnection(cookieStorage, url, requestMethod, headers, body, connectTimeout, readTimeout);
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
        public Builder header(Header header) {
            this.headers.add(header);
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
        public <T> Builder setBody(T content, Sender<T> converter) {
            setBody(converter.toBytes(content));
            return this;
        }

        @Override
        public Builder setBody(String content) {
            setBody(content.getBytes(StandardCharsets.UTF_8));
            return this;
        }
    }

    public class HttpResponse<T> implements Response<T> {
        private int code;
        private String message;
        private List<Header> headers;
        private T content;
        private boolean isReceived;


        private HttpResponse() {
        }

        @Override
        public T getContent() {
            return content;
        }

        @Override
        public int getCode() {
            return code;
        }

        @Override
        public String getMessage() {
            return message;
        }

        @Override
        public List<Header> getHeaders() {
            return headers;
        }

        @Override
        public Header getHeader(String name) {
            for (Header header : headers) {
                if (Objects.equals(header.getKey(), name)) {
                    return header;
                }
            }
            return null;
        }

        @Override
        public T receive(Receiver<T> receiver) throws IOException {
            if (isReceived) {
                throw new IOException("This request is already received");
            }
            content = receiver.toOriginal(receive());
            isReceived = true;
            return content;
        }

        private InputStream receive() throws IOException {
            code = conn.getResponseCode();
            message = conn.getResponseMessage();

            Map<String, List<String>> headerFields = conn.getHeaderFields();
            headers = new ArrayList<>();
            headerFields.forEach((key, values) -> {
                if (key != null) {
                    if ("Set-Cookie".equals(key)) {
                        values.forEach(str -> HttpCookie.parse(str).forEach(cookie -> {
//                            System.out.println(cookie);
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

            return conn.getInputStream();
        }
    }

    public class HttpRequest implements Request {
        private final Method requestMethod;
        private final List<Header> headers;
        private final byte[] body;
        private final Integer connectTimeout;
        private final Integer readTimeout;

        private HttpRequest(Method method, List<Header> headers, byte[] body, Integer connectTimeout, Integer readTimeout) {
            this.requestMethod = method;
            this.headers = headers;
            this.body = body;
            this.connectTimeout = connectTimeout;
            this.readTimeout = readTimeout;
        }

        @Override
        public void request() throws IOException {
            conn = (HttpURLConnection) url.openConnection();
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
        }
    }
}
