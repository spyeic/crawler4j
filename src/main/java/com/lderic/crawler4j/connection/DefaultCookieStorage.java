package com.lderic.crawler4j.connection;

import java.net.HttpCookie;
import java.util.*;

public class DefaultCookieStorage implements CookieStorage {
    private final Map<String, List<HttpCookie>> cookieStore = Collections.synchronizedMap(new HashMap<>());

    @Override
    public void add(String url, HttpCookie cookie) {
        Objects.requireNonNull(cookie);
        List<HttpCookie> list = getOrCreate(url);
        for (int i = 0; i < list.size(); i++) {
            //noinspection StringEquality
            if (list.get(i).getName() == cookie.getName()) {
                list.remove(i);
                break;
            }
        }
        list.add(cookie);
    }

    @Override
    public List<HttpCookie> get(String url) {
        return getOrCreate(url);
    }

    @Override
    public List<HttpCookie> getAll() {
        ArrayList<HttpCookie> list = new ArrayList<>();
        cookieStore.values().forEach(list::addAll);
        return list;
    }

    @Override
    public void delete(String url) {
        cookieStore.remove(url);
    }

    @Override
    public void debug() {

    }

    private List<HttpCookie> getOrCreate(String url) {
        List<HttpCookie> list;
        if (((list = cookieStore.get(url)) != null) || cookieStore.containsKey(url)) {
            return list;
        } else {
            list = Collections.synchronizedList(new ArrayList<>());
            cookieStore.put(url, list);
            return list;
        }
    }
}
