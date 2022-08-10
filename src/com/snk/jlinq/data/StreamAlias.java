package com.snk.jlinq.data;

import java.util.Objects;

public class StreamAlias {
    private final String name;
    private final Class<?> clazz;

    private StreamAlias(String name, Class<?> clazz) {
        this.name = name;
        this.clazz = clazz;
    }

    public static StreamAlias of(Class<?> clazz) {
        return new StreamAlias("", clazz);
    }

    public static StreamAlias of(Class<?> clazz, String name) {
        return new StreamAlias(name, clazz);
    }

    public String name() {
        return name;
    }

    public Class<?> clazz() {
        return clazz;
    }

    public boolean canMatch(StreamAlias other) {
        if (name == null || name.isBlank()) {
            return clazz.equals(other.clazz);
        } else {
            return name.equals(other.name) && clazz.equals(other.clazz);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StreamAlias that = (StreamAlias) o;

        if (!Objects.equals(name, that.name)) return false;
        return Objects.equals(clazz, that.clazz);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (clazz != null ? clazz.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "StreamAlias{" +
                "name='" + name + '\'' +
                ", clazz=" + clazz.getName() +
                '}';
    }
}