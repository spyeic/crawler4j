package com.lderic.crawler4j.converter.sender;

import java.nio.charset.StandardCharsets;

public class StringSender implements Sender<String>{
    @Override
    public byte[] toBytes(String original) {
        return original.getBytes(StandardCharsets.UTF_8);
    }
}
