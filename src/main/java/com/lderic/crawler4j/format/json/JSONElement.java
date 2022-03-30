package com.lderic.crawler4j.format.json;

/**
 * Mark class.
 */
public abstract class JSONElement {
    private JSONElement father;

    public abstract String toString();

    public JSONElement getFather() {
        return father;
    }

    protected void setFather(JSONElement father) {
        this.father = father;
    }

    public JSONObject asObject() {
        return (JSONObject) this;
    }

    @SuppressWarnings("unchecked")
    public JSONArray<JSONElement> asArray() {
        return (JSONArray<JSONElement>) this;
    }

    @SuppressWarnings("unchecked")
    public JSONValue<Object> asValue() {
        return (JSONValue<Object>) this;
    }

    public String asString() throws JSONConvertException {
        return as(String.class);
    }

    public int asInt() throws JSONConvertException {
        return as(int.class);
    }

    public long asLong() throws JSONConvertException {
        return as(long.class);
    }

    public double asDouble() throws JSONConvertException {
        return as(double.class);
    }

    @SuppressWarnings("unchecked")
    public <T> T as(Class<T> clazz) throws JSONConvertException {
        try {
            return (T) this.asValue().getValue();
        } catch (Exception ignored) {
            throw new JSONConvertException("Can't convert " + this.asValue().getValue().getClass().getName() + " to " + clazz.getName());
        }
    }
}
