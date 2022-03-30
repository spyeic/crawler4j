package com.lderic.crawler4j.converter.sender;

import java.io.*;

public class FileSender implements Sender<File> {
    @Override
    public byte[] toBytes(File original) {
        try (
                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(original));
                ByteArrayOutputStream baos = new ByteArrayOutputStream(8192)
        ) {
            int len;

            while ((len = bis.read()) != -1) {
                baos.write(len);
            }
            return baos.toByteArray();
        } catch (IOException e) {
            throw new Error("FileConverter Error");
        }
    }
}
