package com.lderic.crawler4j.converter;

import java.io.*;

public class InputStreamConverter implements Receiver<InputStream>, Sender<InputStream> {

    @Override
    public InputStream toOriginal(InputStream content) {
        // clone the InputStream
        InputStream result = null;
        try (
                BufferedInputStream bis = new BufferedInputStream(content);
                ByteArrayOutputStream baos = new ByteArrayOutputStream(8192)
        ) {
            int len;

            while ((len = bis.read()) != -1) {
                baos.write(len);
            }
            result = new ByteArrayInputStream(baos.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

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
