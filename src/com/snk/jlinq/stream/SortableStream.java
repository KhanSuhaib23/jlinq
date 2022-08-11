package com.snk.jlinq.stream;

import com.snk.jlinq.function.Function1;
import com.snk.jlinq.function.MemberAccessor;
import com.snk.jlinq.stream.pipeline.StreamOp;

import java.util.Comparator;

public class SortableStream<T> extends FilterableStream<T> {
    public SortableStream(StreamOp<T> operatingStream) {
        super(operatingStream);
    }

    public <IN, OUT extends Comparable<OUT>> GotOrderByExpectingThen<T> orderBy(Function1<IN, OUT> mapper) {
        return new GotOrderByExpectingThen<>(operatingStream(), MemberAccessor.from(mapper));
    }

    public <IN, OUT extends Comparable<OUT>> GotOrderByExpectingThen<T> orderBy(String alias, Function1<IN, OUT> mapper) {
        return new GotOrderByExpectingThen<>(operatingStream(), MemberAccessor.from(alias, mapper));
    }
}
