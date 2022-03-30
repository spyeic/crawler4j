package com.lderic.crawler4j.converter.sender;

import com.lderic.crawler4j.format.json.JSONObject;

import java.nio.charset.StandardCharsets;

public class JsonSender implements Sender<JSONObject> {
    @Override
    public byte[] toBytes(JSONObject original) {
        return original.toString().getBytes(StandardCharsets.UTF_8);
    }
}
