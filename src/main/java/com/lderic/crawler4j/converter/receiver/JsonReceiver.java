package com.lderic.crawler4j.converter.receiver;

import com.lderic.crawler4j.format.json.JSONConvertException;
import com.lderic.crawler4j.format.json.JSONObject;
import com.lderic.crawler4j.format.json.JSONParser;

import java.io.InputStream;

public class JsonReceiver implements Receiver<JSONObject> {
    @Override
    public JSONObject toOriginal(InputStream content) {
        try {
            return JSONParser.parse(content);
        } catch (JSONConvertException e) {
            return null;
        }
    }
}
