package com.lderic.crawler4j.connection.converter;

import com.lderic.crawler4j.connection.Inputtable;
import com.lderic.crawler4j.connection.Outputtable;

import java.nio.charset.StandardCharsets;

public class StringConverter implements Inputtable<String>, Outputtable<String> {
    @Override
    public String transfer(byte[] content) {
        return new String(content);
    }

    @Override
    public byte[] body(String original) {
        return original.getBytes(StandardCharsets.UTF_8);
    }
}
