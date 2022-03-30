package com.lderic.crawler4j.converter.receiver;

import java.io.*;
import java.util.Objects;

public class FileReceiver implements Receiver<File> {
    private final File file;

    /**
     * @param file Target file, only used when receiving content.
     */
    public FileReceiver(File file) {
        this.file = file;
    }

    @Override
    public File toOriginal(InputStream content) {
        Objects.requireNonNull(file);
        try (
                BufferedInputStream bis = new BufferedInputStream(content);
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file))
        ) {
            int len;

            while ((len = bis.read()) != -1) {
                bos.write(len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
}
