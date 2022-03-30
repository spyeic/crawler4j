package com.lderic.crawler4j.converter;

import com.lderic.crawler4j.format.json.JSONConvertException;
import com.lderic.crawler4j.format.json.JSONObject;
import com.lderic.crawler4j.format.json.JSONParser;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class JsonConverter implements Receiver<JSONObject>, Sender<JSONObject> {
    @Override
    public JSONObject toOriginal(InputStream content) {
        try {
            return JSONParser.parse(content);
        } catch (JSONConvertException e) {
            return null;
        }
    }

    @Override
    public byte[] toBytes(JSONObject original) {
        return original.toString().getBytes(StandardCharsets.UTF_8);
    }
}
