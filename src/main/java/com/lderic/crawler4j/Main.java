package com.lderic.crawler4j;

import com.lderic.crawler4j.format.json.JSONConvertException;
import com.lderic.crawler4j.format.json.JSONParser;

public class Main {
    public static void main(String[] args) throws JSONConvertException {
        System.out.println(JSONParser.parse("{\"1\":{\"2\":{\"3\":{\"4\":[5,{\"6\":7}]}}}}"));
    }
}
