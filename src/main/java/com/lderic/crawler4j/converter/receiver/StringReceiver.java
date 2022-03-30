package com.lderic.crawler4j.converter.receiver;

import java.io.InputStream;

public class StringReceiver implements Receiver<String>{
    @Override
    public String toOriginal(InputStream content) {
        return new String(new ByteArrayReceiver().toOriginal(content));
    }
}
