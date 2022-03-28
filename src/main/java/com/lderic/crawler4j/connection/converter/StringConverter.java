package com.lderic.crawler4j.connection.converter;

import com.lderic.crawler4j.connection.Receivable;
import com.lderic.crawler4j.connection.Sendable;

import java.nio.charset.StandardCharsets;

public class StringConverter implements Receivable<String>, Sendable<String> {
    @Override
    public String toOriginal(byte[] content) {
        return new String(content);
    }

    @Override
    public byte[] toBytes(String original) {
        return original.getBytes(StandardCharsets.UTF_8);
    }
}
