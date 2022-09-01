package com.snk.jlinq.udt;

import java.util.Objects;

public class Tuple1<T1> extends Tuple {
    protected final T1 v1;

    public Tuple1(T1 v1) {
        this.v1 = v1;
    }

    public T1 v1() {
        return v1;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tuple1<?> tuple1 = (Tuple1<?>) o;

        return Objects.equals(v1, tuple1.v1);
    }

    @Override
    public int hashCode() {
        return v1 != null ? v1.hashCode() : 0;
    }
}
