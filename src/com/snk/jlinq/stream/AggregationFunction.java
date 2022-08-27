package com.snk.jlinq.stream;

import com.snk.jlinq.function.Function1;
import com.snk.jlinq.function.MemberAccessor;
import com.snk.jlinq.function.MethodUtil;

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

    public static <IN, OUT> MemberAccessor<List<OUT>> count(Function1<IN, OUT> mapper) {
        return MemberAccessor.count(mapper);
    }
}
