package com.lderic.crawler4j.io;

import java.io.IOException;
import java.io.Reader;

public class UTF8Reader extends UnicodeReader {
    private final char[] tempBuf;
    private int nextIndex, nChars, tempLength, tempIndex;

    public UTF8Reader(Reader in) {
        this(in, 8192);
    }

    public UTF8Reader(Reader in, int sz) {
        super(in, sz);
        this.tempBuf = new char[5];
    }

    private void fill() throws IOException {
        int sz;
        do {
            sz = in.read(buf, 0, buf.length);
        } while (sz == 0);
        nChars = sz;
        nextIndex = 0;
    }

    @Override
    public int read() throws IOException {
        synchronized (lock) {
            if (nextIndex >= nChars) {
                fill();
                if (nChars < 0) {
                    return -1;
                }
            }
            return read0();
        }
    }

    private int read0() throws IOException {
        if (tempLength > 0) {
            char temp = tempBuf[tempIndex++];
            if (temp == '\\') {
                return slash();
            } else {
                return temp;
            }
        }
        tempIndex = 0;
        int i = buf[nextIndex++];
        if ((char) i == '\\') {
            return slash();
        }
        return i;
    }

    private int slash() throws IOException {
        char next = buf[nextIndex++];
        //final char[] pattern = {'b', 'f', 'n', 'r', 't', 'v', '/', '"', '\'};
        switch (next) {
            case 'u' -> {
                tempBuf[0] = next;
                tempLength = 1;
                for (int j = 1; j < 5; j++) {
                    tempBuf[j] = buf[nextIndex++];
//                        sb.append((char) super.read());
                }
                try {
                    int uc = Integer.parseInt("" + tempBuf[1] + tempBuf[2] + tempBuf[3] + tempBuf[4], 16);
                    tempLength = 0;
                    return uc;
                } catch (Throwable t) {
                    tempLength += 4;
                    return buf[tempIndex++];
                }
            }
            case 'b' -> {
                return '\b';
            }
            case 'f' -> {
                return '\f';
            }
            case 'n' -> {
                return '\n';
            }
            case 'r' -> {
                return '\r';
            }
            case 't' -> {
                return '\t';
            }
            case '\\' -> throw new IOException("Unexpected token");
        }
        return next;
    }
}
