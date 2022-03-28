package com.lderic.crawler4j;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 目前没用
 */
public class Site {
    private final URL url;

    public Site(String url) throws MalformedURLException {
        this(new URL(url));
    }

    public Site(URL url){
        this.url = url;
    }

    public void crawl() throws IOException {
        url.openConnection();
    }
}
