package com.lderic.crawler4j.format.json;

/**
 * The root class of JSON elements.
 */
public abstract class JSONElement {
    private JSONContainer container;

    public abstract String toString();

    public JSONContainer getContainer() {
        return container;
    }

    protected void setFather(JSONContainer father) {
        this.container = father;
    }

    public JSONObject asObject() {
        return (JSONObject) this;
    }

    public JSONArray asArray() {
        return (JSONArray) this;
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
