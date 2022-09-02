package com.snk.jlinq.udt;

import java.util.List;

public class Tuple {

    @SuppressWarnings("unchecked")
    public static <T> T create(List<Object> objectList) {
        return (T) switch (objectList.size()) {
            case 1 -> objectList.get(0);
            case 2 -> new Tuple2<>(objectList.get(0), objectList.get(1));
            case 3 -> new Tuple3<>(objectList.get(0), objectList.get(1), objectList.get(2));
            default -> throw new RuntimeException("Tuple index exceeded max tuple size");
        };
    }
}
