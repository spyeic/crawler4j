package com.lderic.crawler4j.converter;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class StringConverter implements Receiver<String>, Sender<String> {
    @Override
    public String toOriginal(InputStream content) {
        return new String(new ByteArrayConverter().toOriginal(content));
    }

    @Override
    public byte[] toBytes(String original) {
        return original.getBytes(StandardCharsets.UTF_8);
    }
}
