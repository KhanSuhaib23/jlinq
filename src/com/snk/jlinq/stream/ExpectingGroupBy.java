package com.snk.jlinq.stream;

import com.snk.jlinq.function.Function1;
import com.snk.jlinq.function.MemberAccessor;
import com.snk.jlinq.stream.pipeline.GroupStreamOp;
import com.snk.jlinq.stream.pipeline.NoOpMapStreamOp;
import com.snk.jlinq.stream.pipeline.StreamOp;

import java.util.Collections;
import java.util.List;

public class ExpectingGroupBy<T> extends SortableStream<T> {
    public ExpectingGroupBy(StreamOp<T> operatingStream) {
        super(operatingStream);
    }

    public <IN, OUT> InGroupByExpectingComma1<OUT, T> groupBy(Function1<IN, OUT> mapper) {
        return new InGroupByExpectingComma1<>(new NoOpMapStreamOp<>(operatingStream), MemberAccessor.from(mapper));
    }
}
