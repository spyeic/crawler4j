package com.lderic.crawler4j.converter.sender;

public class ByteArraySender implements Sender<byte[]> {
    @Override
    public byte[] toBytes(byte[] original) {
        return original;
    }
}
