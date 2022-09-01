package com.snk.jlinq.udt;

import java.util.Objects;

public class Tuple2<T1, T2> extends Tuple1<T1> {
    protected final T2 v2;

    public Tuple2(T1 v1, T2 v2) {
        super(v1);
        this.v2 = v2;
    }

    public T2 v2() {
        return v2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Tuple2<?, ?> tuple2 = (Tuple2<?, ?>) o;

        return Objects.equals(v2, tuple2.v2);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (v2 != null ? v2.hashCode() : 0);
        return result;
    }
}
