package com.lderic.crawler4j;

import com.lderic.crawler4j.connection.Connection;
import com.lderic.crawler4j.connection.Request;
import com.lderic.crawler4j.converter.receiver.FileReceiver;
import com.lderic.crawler4j.converter.receiver.StringReceiver;
import com.lderic.crawler4j.format.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class Example {
    public static void main(String[] args) {

        // 新建JSON
        JSONObject obj = new JSONObject().setEntry("key", "value")
                .setEntry("num", 120)
                .setEntry("array", (Number) List.of(21, 31219281, 13.219))
                .setEntry("obj", new JSONObject()
                        .setEntry("newObj", "new Value")
                        .setEntry("bool", true)
                        .setEntry("arr", 20, 50, 70.097)
                )
                .setNullEntry("last");
        System.out.println(obj);
    }

    public static void crawlerExample() throws IOException {
        // 新建实例
        Crawler crawler = new Crawler();

        // 例子:
        // 返回百度页面的字节数组
        crawler.get("https://www.baidu.com");

        // 返回百度页面的字符串
        crawler.get("https://www.baidu.com", new StringReceiver());

        // 和上面那个效果相同
        crawler.newBuilder()
                .setMethod(Request.Method.GET)
                .url(new URL("https://www.baidu.com"))
                .build()
                .open(new StringReceiver());

        // 效果还是一样
        crawler.request(Request.Method.GET, "https://www.baidu.com", null, null, null);

        // 这个是访问P站的 大同小异
        crawler.get(
                // 图片地址
                "https://i.pximg.net/img-master/img/2020/09/22/00/00/04/84526667_p0_master1200.jpg",
                // 保存的位置
                new FileReceiver(new File("src/main/resources/img.jpg")),
                builder -> {
                    // 反反爬虫
                    builder.referrer("https://www.pixiv.net");
                }
        );
    }
}
