package com.lderic.crawler4j.converter;

import java.io.InputStream;

public class ByteArrayConverter implements Receiver<byte[]>, Sender<byte[]> {

    @Override
    public byte[] toOriginal(InputStream content) {
        return new InputStreamConverter().toBytes(content);
    }

    @Override
    public byte[] toBytes(byte[] original) {
        return original;
    }
}
