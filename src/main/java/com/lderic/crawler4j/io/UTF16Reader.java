package com.lderic.crawler4j.io;

import java.io.IOException;
import java.io.Reader;

public class UTF16Reader extends UnicodeReader {
    public UTF16Reader(Reader in) {
        this(in, 8192);
    }

    public UTF16Reader(Reader in, int sz) {
        super(in, sz);
    }

    @Override
    public int read() throws IOException {
        //TODO
        return -1;
    }
}
