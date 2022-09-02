package com.snk.jlinq.api;

import com.snk.jlinq.function.Function1;
import com.snk.jlinq.stream.DataSelector;

import java.util.List;

public class AggregationFunction {
    public enum Type {
        NONE,
        LIST,
        COUNT
    }

    public static <IN, OUT> DataSelector<List<OUT>> list(Function1<IN, OUT> mapper) {
        return DataSelector.list(mapper);
    }

    public static <IN, OUT> DataSelector<Long> count(Function1<IN, OUT> mapper) {
        return DataSelector.count(mapper);
    }
}
