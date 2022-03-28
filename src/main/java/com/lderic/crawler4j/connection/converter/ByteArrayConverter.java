package com.lderic.crawler4j.connection.converter;

import com.lderic.crawler4j.connection.Receivable;
import com.lderic.crawler4j.connection.Sendable;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ByteArrayConverter implements Receivable<byte[]>, Sendable<byte[]> {

    @Override
    public byte[] toOriginal(InputStream content) {
        byte[] result = new byte[0];
        try (
                BufferedInputStream bis = new BufferedInputStream(content);
                ByteArrayOutputStream baos = new ByteArrayOutputStream(8192)
        ) {
            int len;

            while ((len = bis.read()) != -1) {
                baos.write(len);
            }
            result = baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public byte[] toBytes(byte[] original) {
        return original;
    }
}
