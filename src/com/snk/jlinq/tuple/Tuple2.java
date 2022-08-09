package com.snk.jlinq.tuple;

public class Tuple2<T1, T2> extends Tuple1<T1> {
    protected final T2 v2;

    public Tuple2(T1 v1, T2 v2) {
        super(v1);
        this.v2 = v2;
    }

    public T1 v1() {
        return v1;
    }

    public T2 v2() {
        return v2;
    }
}
