package com.lderic.crawler4j.connection;

public class Header {
    enum RequestHeaderKey {
        AIM("A-IM"),
        Accept("Accept"),
        AcceptCharset("Accept-Charset"),
        AcceptDatetime("Accept-Datetime"),
        AcceptEncoding("Accept-Encoding"),
        AcceptLanguage("Accept-Language"),
        Authorization("Authorization"),
        CacheControl("Cache-Control"),
        Connection("Connection"),
        ContentEncoding("Content-Encoding"),
        ContentLength("Content-Length"),
        ContentMD5("Content-MD5"),
        ContentType("Content-Type"),
        Cookie("Cookie"),
        Date("Date"),
        Expect("Expect"),
        Forwarded("Forwarded"),
        From("From"),
        Host("Host"),
        HTTP2Settings("HTTP2-Settings"),
        IfMatch("If-Match"),
        IfModifiedSince("If-Modified-Since"),
        IfNoneMatch("If-None-Match"),
        IfRange("If-Range"),
        IfUnmodifiedSince("If-Unmodified-Since"),
        MaxForwards("Max-Forwards"),
        Origin("Origin"),
        Pragma("Pragma"),
        Prefer("Prefer"),
        ProxyAuthorization("Proxy-Authorization"),
        Range("Range"),
        Referer("Referer"),
        TE("TE"),
        Trailer("Trailer"),
        TransferEncoding("Transfer-Encoding"),
        UserAgent("User-Agent"),
        Upgrade("Upgrade"),
        Via("Via"),
        Warning("Warning");

        public final String string;

        RequestHeaderKey(String string) {
            this.string = string;
        }
    }


    private final String key;
    private final String value;

    /**
     * @param key   Header Key
     * @param value Header Value
     * @see RequestHeaderKey
     */
    public Header(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return key + ": " + value;
    }

    public static Header userAgent(String userAgent) {
        return new Header(RequestHeaderKey.UserAgent.string, userAgent);
    }

    public static Header defaultUserAgent() {
        return new Header(RequestHeaderKey.UserAgent.string, "[Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/99.0.4844.74 Safari/537.36]");
    }

    public static Header referrer(String url) {
        return new Header(RequestHeaderKey.Referer.string, url);
    }

    public static Header contentType(String type) {
        return new Header(RequestHeaderKey.ContentType.string, type);
    }
}
