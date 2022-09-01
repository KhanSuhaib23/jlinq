package com.snk.jlinq.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ListUtil {
    public static <T> List<T> concat(List<T> list, T ...add) {
        return Stream.concat(list.stream(), Arrays.stream(add)).collect(Collectors.toList());
    }
}
