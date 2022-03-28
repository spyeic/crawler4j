package com.lderic.crawler4j;

import com.lderic.crawler4j.connection.Connection;
import com.lderic.crawler4j.connection.CookieStorage;
import com.lderic.crawler4j.connection.DefaultCookieStorage;
import com.lderic.crawler4j.connection.HttpConnection;
import com.lderic.crawler4j.converter.ByteArrayConverter;
import com.lderic.crawler4j.converter.InputStreamConverter;
import com.lderic.crawler4j.converter.Receiver;
import com.lderic.crawler4j.converter.Sender;

import java.io.IOException;
import java.net.URL;
import java.util.function.Consumer;

public class Crawler {
    private final CookieStorage storage;

    public Crawler() {
        this.storage = new DefaultCookieStorage();
    }

    public Crawler(CookieStorage storage) {
        this.storage = storage;
    }

    public Connection.Builder newBuilder() {
        return HttpConnection.newBuilder(storage);
    }

    public byte[] get(String url) throws IOException {
        return get(url, (Consumer<Connection.Builder>) null);
    }

    public byte[] get(String url, Consumer<Connection.Builder> consumer) throws IOException {
        return request(Connection.Request.Method.GET, url, null, new ByteArrayConverter(), consumer);
    }

    public <T> T get(String url, Receiver<T> converter) throws IOException {
        return get(url, converter, null);
    }

    public <T> T get(String url, Receiver<T> converter, Consumer<Connection.Builder> consumer) throws IOException {
        return request(Connection.Request.Method.GET, url, null, converter, consumer);
    }

    public byte[] post(String url, byte[] body) throws IOException {
        return post(url, body, (Consumer<Connection.Builder>) null);
    }

    public byte[] post(String url, byte[] body, Consumer<Connection.Builder> consumer) throws IOException {
        return request(Connection.Request.Method.POST, url, body, new ByteArrayConverter(), consumer);
    }

    public <T> T post(String url, byte[] body, Receiver<T> converter) throws IOException {
        return post(url, body, converter, null);
    }

    public <T> T post(String url, byte[] body, Receiver<T> converter, Consumer<Connection.Builder> consumer) throws IOException {
        return request(Connection.Request.Method.POST, url, body, converter, consumer);
    }

    public <T> byte[] post(String url, T body, Sender<T> converter) throws IOException {
        return post(url, body, converter, (Consumer<Connection.Builder>) null);
    }

    public <T> byte[] post(String url, T body, Sender<T> converter, Consumer<Connection.Builder> consumer) throws IOException {
        return request(Connection.Request.Method.POST, url, converter.toBytes(body), new ByteArrayConverter(), consumer);
    }

    public <TInput, TOutput> TInput post(String url, TOutput body, Sender<TOutput> oConverter, Receiver<TInput> iConverter) throws IOException {
        return post(url, body, oConverter, iConverter, null);
    }

    public <TInput, TOutput> TInput post(String url, TOutput body, Sender<TOutput> oConverter, Receiver<TInput> iConverter, Consumer<Connection.Builder> consumer) throws IOException {
        return post(url, oConverter.toBytes(body), iConverter, consumer);
    }

    public <T> T request(Connection.Request.Method method, String url, byte[] body, Receiver<T> converter, Consumer<Connection.Builder> consumer) throws IOException {
        Connection.Builder builder = newBuilder();
        builder.setMethod(method).url(new URL(url)).setBody(body);
        if (consumer != null) {
            consumer.accept(builder);
        }
        return builder.build().open(converter);
    }
}
