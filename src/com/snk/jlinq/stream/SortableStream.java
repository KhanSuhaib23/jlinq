package com.snk.jlinq.stream;

import com.snk.jlinq.function.Function1;
import com.snk.jlinq.function.MemberAccessor;
import com.snk.jlinq.stream.pipeline.StreamOp;
import com.snk.jlinq.tuple.Tuple0;

import java.util.Comparator;

public class SortableStream<GT, OT> extends FilterableStream<GT, OT> {
    public SortableStream(StreamOp<GT, OT> operatingStream) {
        super(operatingStream);
    }

    public <IN, OUT extends Comparable<OUT>> GotOrderByExpectingThen<GT, OT> orderBy(Function1<IN, OUT> mapper) {
        return new GotOrderByExpectingThen<>(operatingStream(), MemberAccessor.from(mapper));
    }

    public <IN, OUT extends Comparable<OUT>> GotOrderByExpectingThen<GT, OT> orderBy(String alias, Function1<IN, OUT> mapper) {
        return new GotOrderByExpectingThen<>(operatingStream(), MemberAccessor.from(alias, mapper));
    }
}
