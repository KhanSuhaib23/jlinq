package com.snk.jlinq.core;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ListUtil {
    public static <T> List<T> addOne(List<T> list, T add) {
        return Stream.concat(list.stream(), Stream.of(add)).collect(Collectors.toList());
    }
}
