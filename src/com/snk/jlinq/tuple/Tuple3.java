package com.snk.jlinq.tuple;

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
}
