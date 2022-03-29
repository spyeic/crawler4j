package com.lderic.crawler4j.format.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;

public class JSONParser {

    public static void main(String[] args) throws IOException {
        JSONObject object = parse(new StringReader("{\"name\": \"eric\", \"age\": 16, \"details\":   {\"hair\": \"black\", \"color\": \"yellow\"}}"));
        System.out.println(object == null);
    }

    final BufferedReader reader;
    HashMap<String, JSONElement> map = new HashMap<>();

    public JSONParser(Reader reader) {
        if (reader instanceof BufferedReader) {
            this.reader = (BufferedReader) reader;
        } else {
            this.reader = new BufferedReader(reader);
        }
    }

    static boolean canIgnore = true;

    public void parseEntryToMap(String entry) {
        String[] arr = entry.split(":");
        String key = arr[0].substring(1, arr.length - 1);
        String value = arr[1];
        if ("null".equalsIgnoreCase(value)) {
            map.put(key, new JSONNull());
            return;
        }
        if ("true".equalsIgnoreCase(arr[1]) || "false".equalsIgnoreCase(arr[1])) {
            map.put(key, new JSONBoolean(Boolean.parseBoolean(value)));
            return;
        }

        Number num = null;
        try {
            num = Long.parseLong(value);
        } catch (Exception ignored) {
            try {
                num = Double.parseDouble(value);
            } catch (Exception ignored2) {
            }
        }

        if (num != null) {
            map.put(key, new JSONNumber(num));
        } else {
            if (value.startsWith("\"")) {
                value = value.substring(1);
            }
            if (value.endsWith("\"")) {
                value = value.substring(0, value.length() - 1);
            }
            map.put(key, new JSONString(value));
        }
    }

    public JSONObject parse() throws IOException {
        int len;
        StringBuilder sb = new StringBuilder();

        while ((len = reader.read()) != -1) {
            char c = (char) len;
            if (c == ' ' && canIgnore) {
                continue;
            }
            if (c == ',') {
//                sb.toString(): Key Value Pair
                parseEntryToMap(sb.toString());
                System.out.println(sb);
                sb = new StringBuilder();
                continue;
            }


            if (c == '"') {
                canIgnore = !canIgnore;
            }
            if (c == '{') {
                System.out.println();
                JSONParser parser = new JSONParser(reader);
                System.out.println(sb);
                map.put(sb.toString(), parser.parse());
            }
            if (c == '}') {
                JSONObject obj = new JSONObject();
                map.forEach(obj::setEntry);
                return obj;
            }

            sb.append(c);

        }
        return null;
    }

    public static JSONObject parse(Reader reader) throws IOException {
        return new JSONParser(reader).parse();
    }
}
