package com.lderic.crawler4j.converter.receiver;

import com.lderic.crawler4j.converter.sender.InputStreamSender;

import java.io.InputStream;

public class ByteArrayReceiver implements Receiver<byte[]>{
    @Override
    public byte[] toOriginal(InputStream content) {
        return new InputStreamSender().toBytes(content);
    }
}
