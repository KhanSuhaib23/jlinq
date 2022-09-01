package com.snk.jlinq.udt;

import java.util.List;

public class Tuple {

    public static Object create(List<Object> objectList) {
        switch (objectList.size()) {
            case 1: return objectList.get(0);
            case 2: return new Tuple2<>(objectList.get(0), objectList.get(1));
            case 3: return new Tuple3<>(objectList.get(0), objectList.get(1), objectList.get(2));
            default: throw new RuntimeException("Tuple index exceeded max tuple size");
        }
    }
}
