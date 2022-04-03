package com.lderic.crawler4j.io;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.CharBuffer;

public abstract class UnicodeReader extends Reader {
    protected final Reader in;
    protected final char[] buf;

    public UnicodeReader(Reader in, int sz) {
        this.in = in;
        this.buf = new char[sz];
    }

    /**
     * Notes that you can only convert one character once, so it doesn't support other read method.
     * @return next character
     */
    @Override
    public abstract int read() throws IOException;

    @Override
    public long transferTo(Writer out) throws IOException {
        throw new IOException("Not supported");
    }

    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
        throw new IOException("Not supported");
    }

    @Override
    public int read(CharBuffer target) throws IOException {
        throw new IOException("Not supported");
    }

    @Override
    public int read(char[] cbuf) throws IOException {
        throw new IOException("Not supported");
    }

    @Override
    public long skip(long n) throws IOException {
        throw new IOException("Not supported");
    }

    @Override
    public boolean ready() throws IOException {
        return in.ready();
    }

    @Override
    public void close() throws IOException {
        in.close();
    }
}
