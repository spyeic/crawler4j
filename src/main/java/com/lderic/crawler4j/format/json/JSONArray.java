package com.lderic.crawler4j.format.json;

import java.util.*;

public class JSONArray extends JSONContainer implements List<JSONElement> {
    private final List<JSONElement> list;

    public JSONArray(List<JSONElement> value) {
        this.list = value;
    }

    public JSONArray() {
        this.list = new ArrayList<>();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        if (list.size() > 0) {
            sb.append(list.get(0).toString());
        }
        for (int i = 1; i < list.size(); i++) {
            sb.append(", ").append(list.get(i).toString());
        }
        sb.append("]");
        return sb.toString();
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return list.contains(o);
    }

    @Override
    public Iterator<JSONElement> iterator() {
        return list.iterator();
    }

    @Override
    public Object[] toArray() {
        return list.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return list.toArray(a);
    }

    @Override
    public boolean add(JSONElement jsonElement) {
        if (jsonElement != null) {
            jsonElement.setFather(this);
        }
        return list.add(jsonElement);
    }

    @Override
    public boolean remove(Object o) {
        if (list.remove(o)) {
            ((JSONElement) o).setFather(this);
            return true;
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return list.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends JSONElement> c) {
        if (list.addAll(c)) {
            c.forEach(jsonElement -> jsonElement.setFather(this));
            return true;
        }
        return false;
    }

    @Override
    public boolean addAll(int index, Collection<? extends JSONElement> c) {
        if (list.addAll(index, c)) {
            c.forEach(jsonElement -> jsonElement.setFather(this));
            return true;
        }
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        if (list.removeAll(c)) {
            c.forEach(o -> ((JSONElement) o).setFather(null));
            return true;
        }
        return false;
    }

    /**
     * Unavailable now
     */
    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public JSONElement get(int index) {
        return list.get(index);
    }

    @Override
    public JSONElement set(int index, JSONElement element) {
        element.setFather(this);
        JSONElement result = list.set(index, element);
        if (result != null) {
            result.setFather(null);
        }
        return result;
    }

    @Override
    public void add(int index, JSONElement element) {
        Objects.requireNonNull(element);
        element.setFather(this);
        list.add(index, element);
    }

    @Override
    public JSONElement remove(int index) {
        JSONElement result = list.remove(index);
        if (result != null) {
            result.setFather(null);
        }
        return result;
    }

    @Override
    public int indexOf(Object o) {
        return list.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return list.lastIndexOf(o);
    }

    @Override
    public ListIterator<JSONElement> listIterator() {
        return list.listIterator();
    }

    @Override
    public ListIterator<JSONElement> listIterator(int index) {
        return list.listIterator(index);
    }

    @Override
    public List<JSONElement> subList(int fromIndex, int toIndex) {
        return list.subList(fromIndex, toIndex);
    }
}
