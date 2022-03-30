package com.lderic.crawler4j.format.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;

public class JSONParser {

    final BufferedReader reader;
    private boolean canIgnore = true;

    public JSONParser(Reader reader) {
        if (reader instanceof BufferedReader) {
            this.reader = (BufferedReader) reader;
        } else {
            this.reader = new BufferedReader(reader);
        }
    }

    public static void main(String[] args) throws IOException, JSONConvertException {
        JSONObject object = parse(new StringReader("{}"));
    }

    public static JSONObject parse(Reader reader) throws IOException, JSONConvertException {
        return new JSONParser(reader).parse();
    }

    public JSONObject parse() throws IOException, JSONConvertException {
        int len;
        while ((len = reader.read()) != -1) {
            char c = (char) len;
            if (c == '{') {
                return new ObjectBuilder().parseObject();
            }
        }
        throw new JSONConvertException("Fail to convert, please check the JSON String.");
    }

    private class ObjectBuilder {
        private final HashMap<String, JSONElement> map = new HashMap<>();
        private boolean canSkip = false;

        private void parseEntryToMap(String entry) {
            String[] arr = entry.split(":");
            String key = arr[0].substring(1, arr[0].length() - 1);
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

        private JSONObject parseObject() throws IOException {
            int len;
            StringBuilder sb = new StringBuilder();
            JSONObject result = new JSONObject();

            while ((len = reader.read()) != -1) {
                char c = (char) len;
                if ((c == ' ' || c == '\n') && canIgnore) {
                    continue;
                }
                if (c == ',') {
                    if (canSkip) {
                        canSkip = false;
                    } else {
                        parseEntryToMap(sb.toString());
                    }
                    sb = new StringBuilder();
                    continue;
                }
                if (c == '"') {
                    canIgnore = !canIgnore;
                }
                if (c == '{') {
                    ObjectBuilder builder = new ObjectBuilder();
                    JSONObject obj = builder.parseObject();
                    map.put(sb.substring(1, sb.length() - 2), obj);
                    canSkip = true;
                    continue;
                }
                if (c == '}') {
                    if (canSkip) {
                        canSkip = false;
                    } else {
                        parseEntryToMap(sb.toString());
                    }
                    map.forEach(result::setEntry);
                    return result;
                }
                sb.append(c);
            }
            map.forEach(result::setEntry);
            return result;
        }
    }

    private class ArrayBuilder {
        private final ArrayList<JSONElement> list = new ArrayList<>();

    }
}
