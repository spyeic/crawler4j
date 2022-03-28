package com.lderic.crawler4j;

import com.lderic.crawler4j.connection.Connection;
import com.lderic.crawler4j.connection.converter.FileConverter;
import com.lderic.crawler4j.connection.converter.StringConverter;

import java.io.File;
import java.io.IOException;
import java.net.URL;

@SuppressWarnings("CodeBlock2Expr")
public class Example {
    public static void main(String[] args) throws IOException {
        // 新建实例
        Crawler crawler = new Crawler();

        // 例子:
        // 返回百度页面的字节数组
        crawler.get("https://www.baidu.com");

        // 返回百度页面的字符串
        crawler.get("https://www.baidu.com", new StringConverter());

        // 和上面那个效果相同
        crawler.newBuilder()
                .setMethod(Connection.Request.Method.GET)
                .url(new URL("https://www.baidu.com"))
                .build()
                .open(new StringConverter());

        // 效果还是一样
        crawler.request("https://www.baidu.com", builder -> {
            builder.setMethod(Connection.Request.Method.GET);
        });

        // 这个是访问P站的 大同小异
        crawler.get(
                // 图片地址
                "https://i.pximg.net/img-master/img/2020/09/22/00/00/04/84526667_p0_master1200.jpg",
                // 保存的位置
                new FileConverter(new File("src/main/resources/img.jpg")),
                builder -> {
                    // 反反爬虫
                    builder.referrer("https://www.pixiv.net");
                }
        );
    }
}
