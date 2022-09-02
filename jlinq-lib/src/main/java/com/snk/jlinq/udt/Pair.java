package com.snk.jlinq.udt;

import java.util.Map;

public final class Pair<T1, T2> {
    private final T1 left;
    private final T2 right;

    public Pair(T1 left, T2 right) {
        this.left = left;
        this.right = right;
    }

    public static <T1, T2> Pair<T1, T2> of(T1 left, T2 right) {
        return new Pair<>(left, right);
    }

    public static <T1, T2> Pair<T1, T2> of(Map.Entry<T1, T2> kv) {
        return new Pair<>(kv.getKey(), kv.getValue());
    }

    public T1 left() {
        return left;
    }

    public T2 right() {
        return right;
    }
}
