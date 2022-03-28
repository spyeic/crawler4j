package com.lderic.crawler4j;

import com.lderic.crawler4j.connection.*;

import java.io.IOException;
import java.net.URL;
import java.util.function.Consumer;

public class Crawler {
    public static void main(String[] args) throws IOException {
        Crawler crawler = new Crawler();
    }

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
        return request(Connection.Request.Method.GET, url, null, consumer);
    }

    public <T> T get(String url, Inputtable<T> converter) throws IOException {
        return get(url, converter, null);
    }

    public <T> T get(String url, Inputtable<T> converter, Consumer<Connection.Builder> consumer) throws IOException {
        return request(Connection.Request.Method.GET, url, null, converter, consumer);
    }

    public byte[] post(String url, byte[] body) throws IOException {
        return post(url, body, (Consumer<Connection.Builder>) null);
    }

    public byte[] post(String url, byte[] body, Consumer<Connection.Builder> consumer) throws IOException {
        return request(Connection.Request.Method.POST, url, body, consumer);
    }

    public <T> T post(String url, byte[] body, Inputtable<T> converter) throws IOException {
        return post(url, body, converter, null);
    }

    public <T> T post(String url, byte[] body, Inputtable<T> converter, Consumer<Connection.Builder> consumer) throws IOException {
        return request(Connection.Request.Method.POST, url, body, converter, consumer);
    }

    public <TInput, TOutput> TInput post(String url, TOutput body, Outputtable<TOutput> oConverter, Inputtable<TInput> iConverter) throws IOException {
        return post(url, body, oConverter, iConverter, null);
    }

    public <TInput, TOutput> TInput post(String url, TOutput body, Outputtable<TOutput> oConverter, Inputtable<TInput> iConverter, Consumer<Connection.Builder> consumer) throws IOException {
        return post(url, oConverter.body(body), iConverter, consumer);
    }

    public byte[] request(String url, Consumer<Connection.Builder> consumer) throws IOException {
        Connection.Builder builder = newBuilder();
        builder.url(new URL(url));
        if (consumer != null) {
            consumer.accept(builder);
        }
        return builder.build().open();
    }

    public <T> T request(String url, Inputtable<T> converter, Consumer<Connection.Builder> consumer) throws IOException {
        Connection.Builder builder = newBuilder();
        builder.url(new URL(url));
        if (consumer != null) {
            consumer.accept(builder);
        }
        return builder.build().open(converter);
    }

    public byte[] request(Connection.Request.Method method, String url, byte[] body, Consumer<Connection.Builder> consumer) throws IOException {
        Connection.Builder builder = newBuilder();
        builder.setMethod(method).url(new URL(url)).setBody(body);
        if (consumer != null) {
            consumer.accept(builder);
        }
        return builder.build().open();
    }

    public <T> T request(Connection.Request.Method method, String url, byte[] body, Inputtable<T> converter, Consumer<Connection.Builder> consumer) throws IOException {
        Connection.Builder builder = newBuilder();
        builder.setMethod(method).url(new URL(url)).setBody(body);
        if (consumer != null) {
            consumer.accept(builder);
        }
        return builder.build().open(converter);
    }
}
