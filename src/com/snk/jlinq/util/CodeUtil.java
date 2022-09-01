package com.snk.jlinq.util;

import java.util.function.BiFunction;

public class CodeUtil {
    @SuppressWarnings("unchecked")
    public static <T, R> T cast(R v) {
        return (T) v;
    }

    public static <T1, T2> BiFunction<T1, T2, Boolean> negate(BiFunction<T1, T2, Boolean> mapper) {
        return mapper.andThen(b -> !b);
    }
}
