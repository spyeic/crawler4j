package com.lderic.crawler4j.converter.sender;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class InputStreamSender implements Sender<InputStream> {
    @Override
    public byte[] toBytes(InputStream original) {
        byte[] result = new byte[0];
        try (
                BufferedInputStream bis = new BufferedInputStream(original);
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
}
