package com.snk.jlinq.api;

import com.snk.jlinq.function.Function1;
import com.snk.jlinq.stream.DataSelector;

public class AggregationFunction {
    public enum Type {
        NONE,
        LIST,
        COUNT
    }

    public static <IN, OUT> DataSelector list(Function1<IN, OUT> mapper) {
        return DataSelector.list(mapper);
    }

    public static <IN, OUT> DataSelector count(Function1<IN, OUT> mapper) {
        return DataSelector.count(mapper);
    }
}
