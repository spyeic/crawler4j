package com.lderic.crawler4j.converter.receiver;

import java.io.*;

public class InputStreamReceiver implements Receiver<InputStream>{
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
}
