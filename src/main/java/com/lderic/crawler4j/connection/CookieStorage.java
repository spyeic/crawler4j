package com.lderic.crawler4j.connection;

import java.net.HttpCookie;
import java.util.List;

public interface CookieStorage {
    void add(String url, HttpCookie cookie);

    List<HttpCookie> get(String url);

    List<HttpCookie> getAll();

    void delete(String url);

    void debug();
}
