package com.snk.jlinq.tuple;

public class Tuple1<T1> extends Tuple0 {
    protected final T1 v1;

    public Tuple1(T1 v1) {
        this.v1 = v1;
    }

    public T1 v1() {
        return v1;
    }
}
