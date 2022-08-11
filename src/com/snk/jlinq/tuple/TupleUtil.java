package com.snk.jlinq.tuple;

public class TupleUtil {

    public static Object getAt(Object tuple, int i) {
        switch (i) {
            case 1:
                try {
                    return ((Tuple1) tuple).v1();
                } catch (ClassCastException e) {
                    return tuple;
                }
            case 2:
                return ((Tuple2) tuple).v2();
            case 3:
                return ((Tuple3) tuple).v3();
            default:
                throw new RuntimeException("Index out of bound for the tuple");
        }
    }
}
