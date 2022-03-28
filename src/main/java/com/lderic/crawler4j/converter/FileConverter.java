package com.lderic.crawler4j.converter;

import java.io.*;
import java.util.Objects;

public class FileConverter implements Receiver<File>, Sender<File> {
    private File file;

    /**
     * @param file Target file, only used when receiving content.
     */
    public FileConverter(File file) {
        this.file = file;
    }

    public FileConverter() {}

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
