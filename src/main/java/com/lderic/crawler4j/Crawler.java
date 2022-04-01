package com.lderic.crawler4j;

import com.lderic.crawler4j.connection.*;
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
        return HttpConnection.getBuilder(storage);
    }

    public CookieStorage getCookieStorage() {
        return storage;
    }

    public Response<byte[]> get(String url) throws IOException {
        return get(url, (Consumer<Connection.Builder>) null);
    }

    public Response<byte[]> get(String url, Consumer<Connection.Builder> consumer) throws IOException {
        return request(Request.Method.GET, url, null, new ByteArrayReceiver(), consumer);
    }

    public <T> Response<T> get(String url, Receiver<T> receiver) throws IOException {
        return get(url, receiver, null);
    }

    public <T> Response<T> get(String url, Receiver<T> receiver, Consumer<Connection.Builder> consumer) throws IOException {
        return request(Request.Method.GET, url, null, receiver, consumer);
    }

    public Response<byte[]> post(String url, byte[] body) throws IOException {
        return post(url, body, (Consumer<Connection.Builder>) null);
    }

    public Response<byte[]> post(String url, byte[] body, Consumer<Connection.Builder> consumer) throws IOException {
        return request(Request.Method.POST, url, body, new ByteArrayReceiver(), consumer);
    }

    public <T> Response<T> post(String url, byte[] body, Receiver<T> converter) throws IOException {
        return post(url, body, converter, null);
    }

    public <T> Response<T> post(String url, byte[] body, Receiver<T> receiver, Consumer<Connection.Builder> consumer) throws IOException {
        return request(Request.Method.POST, url, body, receiver, consumer);
    }

    public <T> Response<byte[]> post(String url, T body, Sender<T> sender) throws IOException {
        return post(url, body, sender, (Consumer<Connection.Builder>) null);
    }

    public <T> Response<byte[]> post(String url, T body, Sender<T> sender, Consumer<Connection.Builder> consumer) throws IOException {
        return request(Request.Method.POST, url, sender.toBytes(body), new ByteArrayReceiver(), consumer);
    }

    public <TInput, TOutput> Response<TInput> post(String url, TOutput body, Sender<TOutput> sender, Receiver<TInput> receiver) throws IOException {
        return post(url, body, sender, receiver, null);
    }

    public <TInput, TOutput> Response<TInput> post(String url, TOutput body, Sender<TOutput> sender, Receiver<TInput> receiver, Consumer<Connection.Builder> consumer) throws IOException {
        return post(url, sender.toBytes(body), receiver, consumer);
    }

    public <T> Response<T> request(Request.Method method, String url, byte[] body, Receiver<T> receiver, Consumer<Connection.Builder> consumer) throws IOException {
        Connection.Builder builder = newBuilder();
        builder.setMethod(method).url(new URL(url)).setBody(body);
        if (consumer != null) {
            consumer.accept(builder);
        }
        return builder.build().open(receiver);
    }
}
