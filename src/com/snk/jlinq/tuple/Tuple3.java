package com.snk.jlinq.tuple;

import java.util.Objects;

public class Tuple3<T1, T2, T3> extends Tuple2<T1, T2> {
    protected final T3 v3;

    public Tuple3(T1 v1, T2 v2, T3 v3) {
        super(v1, v2);
        this.v3 = v3;
    }

    public Tuple3(Tuple2<T1, T2> t1, T3 v3) {
        super(t1.v1, t1.v2);
        this.v3 = v3;
    }

    public T3 v3() {
        return v3;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Tuple3<?, ?, ?> tuple3 = (Tuple3<?, ?, ?>) o;

        return Objects.equals(v3, tuple3.v3);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (v3 != null ? v3.hashCode() : 0);
        return result;
    }
}
