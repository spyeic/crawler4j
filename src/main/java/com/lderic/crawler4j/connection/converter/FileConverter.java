package com.lderic.crawler4j.connection.converter;

import com.lderic.crawler4j.connection.Inputtable;
import com.lderic.crawler4j.connection.Outputtable;

import java.io.*;

public class FileConverter implements Inputtable<File>, Outputtable<File> {
    private final File file;

    public FileConverter(File file) {
        this.file = file;
    }

    @Override
    public File transfer(byte[] content) {
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(content);
        }catch (IOException e){
            e.printStackTrace();
        }
        return file;
    }

    @Override
    public byte[] body(File original) {
        try (
                BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
                ByteArrayOutputStream baos = new ByteArrayOutputStream(8192)
        ) {
            int len;

            while ((len = bis.read()) != -1){
                baos.write(len);
            }
            return baos.toByteArray();
        }catch (IOException e){
            throw new Error("FileConverter Error");
        }
    }
}
