package com.snk.jlinq.api;

import com.snk.jlinq.function.Function1;
import com.snk.jlinq.stream.MemberAccessor;

import java.util.List;

public class AggregationFunction {
    public enum Type {
        NONE,
        LIST,
        COUNT
    }

    public static <IN, OUT> MemberAccessor<List<OUT>> list(Function1<IN, OUT> mapper) {
        return MemberAccessor.list(mapper);
    }

    public static <IN, OUT> MemberAccessor<Long> count(Function1<IN, OUT> mapper) {
        return MemberAccessor.count(mapper);
    }
}
