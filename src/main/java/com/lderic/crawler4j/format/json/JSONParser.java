package com.lderic.crawler4j.format.json;

import com.lderic.crawler4j.io.UTF8Reader;
import com.lderic.crawler4j.io.UnicodeReader;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class JSONParser {

    final UnicodeReader reader;
    private boolean canIgnore = true;

    /**
     * A parser to parse JSON
     */
    private JSONParser(Reader reader) {
        this.reader = new UTF8Reader(reader);
    }

    /**
     * Parse Reader to JSONObject
     *
     * @param reader JSONParser can close Reader Stream automatically
     * @return JSONObject
     * @throws JSONConvertException throws when can't parse JSON
     * @see JSONObject
     */
    public static JSONObject parse(Reader reader) throws JSONConvertException {
        return new JSONParser(reader).parse();
    }

    /**
     * Parse JSON String to JSONObject
     *
     * @param json JSON String
     * @return JSONObject
     * @throws JSONConvertException throws when can't parse JSON
     * @see JSONObject
     */
    public static JSONObject parse(String json) throws JSONConvertException {
        return parse(new StringReader(json));
    }

    public static JSONObject parse(InputStream inputStream) throws JSONConvertException {
        return parse(new InputStreamReader(inputStream));
    }

    public static JSONObject parse(File file) throws JSONConvertException {
        try {
            return parse(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public JSONObject parse() throws JSONConvertException {
        try (reader) {
            int len;
            while ((len = reader.read()) != -1) {
                char c = (char) len;
                if (c == '{') {
                    return new ObjectBuilder().parseObject();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        throw new JSONConvertException("Failed to convert, missing '{'");
    }

    private JSONElement parseToElement(String value) {
        if ("null".equalsIgnoreCase(value)) {
            return new JSONNull();
        }
        if ("true".equalsIgnoreCase(value) || "false".equalsIgnoreCase(value)) {
            return new JSONBoolean(Boolean.parseBoolean(value));
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
            return new JSONNumber(num);
        } else {
            if (value.startsWith("\"")) {
                value = value.substring(1);
            }
            if (value.endsWith("\"")) {
                value = value.substring(0, value.length() - 1);
            }
            return new JSONString(value);
        }
    }

    private class ObjectBuilder {
        private final LinkedHashMap<String, JSONElement> map = new LinkedHashMap<>();
        private boolean canSkip = false;

        private void addToMap(String entry) {
            String[] arr = entry.split(":");
            if (arr.length != 2) {
                return;
            }
            String key = arr[0].substring(1, arr[0].length() - 1);
            String value = arr[1];
            map.put(key, parseToElement(value));
        }

        private JSONObject parseObject() throws IOException, JSONConvertException {
            int len;
            StringBuilder sb = new StringBuilder();

            while ((len = reader.read()) != -1) {
                char c = (char) len;
                if (canIgnore && (c == ' ' || c == '\n')) {
                    continue;
                }
                switch (c) {
                    case '"' -> canIgnore = !canIgnore;
                    case ',' -> {
                        if (canSkip) {
                            canSkip = false;
                        } else {
                            addToMap(sb.toString());
                        }
                        sb = new StringBuilder();
                        continue;
                    }
                    case '{' -> {
                        ObjectBuilder builder = new ObjectBuilder();
                        JSONObject obj = builder.parseObject();
                        map.put(sb.substring(1, sb.length() - 2), obj);
                        canSkip = true;
                        continue;
                    }
                    case '}' -> {
                        if (canSkip) {
                            canSkip = false;
                        } else {
                            addToMap(sb.toString());
                        }
                        JSONObject result = new JSONObject();
                        map.forEach(result::setEntry);
                        return result;
                    }
                    case '[' -> {
                        ArrayBuilder builder = new ArrayBuilder();
                        JSONArray obj = builder.parseArray();
                        map.put(sb.substring(1, sb.length() - 2), obj);
                        canSkip = true;
                        continue;
                    }
                }
                sb.append(c);
            }
            throw new JSONConvertException("Failed to convert, please check the JSON String");
        }
    }

    private class ArrayBuilder {
        private final ArrayList<JSONElement> list = new ArrayList<>();
        private boolean canSkip = false;

        private void addToList(String value) {
            list.add(parseToElement(value));
        }

        private JSONArray parseArray() throws IOException, JSONConvertException {
            int len;
            StringBuilder sb = new StringBuilder();

            while ((len = reader.read()) != -1) {
                char c = (char) len;
                if (canIgnore && (c == ' ' || c == '\n')) {
                    continue;
                }
                switch (c) {
                    case '"' -> canIgnore = !canIgnore;
                    case ',' -> {
                        if (canSkip) {
                            canSkip = false;
                        } else {
                            addToList(sb.toString());
                        }
                        sb = new StringBuilder();
                        continue;
                    }
                    case '{' -> {
                        ObjectBuilder builder = new ObjectBuilder();
                        JSONObject obj = builder.parseObject();
                        list.add(obj);
                        canSkip = true;
                        continue;
                    }
                    case '[' -> {
                        ArrayBuilder builder = new ArrayBuilder();
                        JSONArray obj = builder.parseArray();
                        list.add(obj);
                        canSkip = true;
                        continue;
                    }
                    case ']' -> {
                        if (canSkip) {
                            canSkip = false;
                        } else {
                            addToList(sb.toString());
                        }
                        return new JSONArray(list);
                    }
                }
                sb.append(c);
            }
            throw new JSONConvertException("Failed to convert, please check the JSON String");
        }
    }
}
