package com.lderic.crawler4j;

import com.lderic.crawler4j.connection.Connection;
import com.lderic.crawler4j.connection.CookieStorage;
import com.lderic.crawler4j.connection.DefaultCookieStorage;
import com.lderic.crawler4j.connection.HttpConnection;
import com.lderic.crawler4j.converter.receiver.ByteArrayReceiver;
import com.lderic.crawler4j.converter.receiver.Receiver;
import com.lderic.crawler4j.converter.sender.Sender;

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

    public CookieStorage getCookieStorage() {
        return storage;
    }

    public Connection.Response<byte[]> get(String url) throws IOException {
        return get(url, (Consumer<Connection.Builder>) null);
    }

    public Connection.Response<byte[]> get(String url, Consumer<Connection.Builder> consumer) throws IOException {
        return request(Connection.Request.Method.GET, url, null, new ByteArrayReceiver(), consumer);
    }

    public <T> Connection.Response<T> get(String url, Receiver<T> converter) throws IOException {
        return get(url, converter, null);
    }

    public <T> Connection.Response<T> get(String url, Receiver<T> converter, Consumer<Connection.Builder> consumer) throws IOException {
        return request(Connection.Request.Method.GET, url, null, converter, consumer);
    }

    public Connection.Response<byte[]> post(String url, byte[] body) throws IOException {
        return post(url, body, (Consumer<Connection.Builder>) null);
    }

    public Connection.Response<byte[]> post(String url, byte[] body, Consumer<Connection.Builder> consumer) throws IOException {
        return request(Connection.Request.Method.POST, url, body, new ByteArrayReceiver(), consumer);
    }

    public <T> Connection.Response<T> post(String url, byte[] body, Receiver<T> converter) throws IOException {
        return post(url, body, converter, null);
    }

    public <T> Connection.Response<T> post(String url, byte[] body, Receiver<T> converter, Consumer<Connection.Builder> consumer) throws IOException {
        return request(Connection.Request.Method.POST, url, body, converter, consumer);
    }

    public <T> Connection.Response<byte[]> post(String url, T body, Sender<T> converter) throws IOException {
        return post(url, body, converter, (Consumer<Connection.Builder>) null);
    }

    public <T> Connection.Response<byte[]> post(String url, T body, Sender<T> converter, Consumer<Connection.Builder> consumer) throws IOException {
        return request(Connection.Request.Method.POST, url, converter.toBytes(body), new ByteArrayReceiver(), consumer);
    }

    public <TInput, TOutput> Connection.Response<TInput> post(String url, TOutput body, Sender<TOutput> oConverter, Receiver<TInput> iConverter) throws IOException {
        return post(url, body, oConverter, iConverter, null);
    }

    public <TInput, TOutput> Connection.Response<TInput> post(String url, TOutput body, Sender<TOutput> oConverter, Receiver<TInput> iConverter, Consumer<Connection.Builder> consumer) throws IOException {
        return post(url, oConverter.toBytes(body), iConverter, consumer);
    }

    public <T> Connection.Response<T> request(Connection.Request.Method method, String url, byte[] body, Receiver<T> converter, Consumer<Connection.Builder> consumer) throws IOException {
        Connection.Builder builder = newBuilder();
        builder.setMethod(method).url(new URL(url)).setBody(body);
        if (consumer != null) {
            consumer.accept(builder);
        }
        return builder.build().open(converter);
    }
}
