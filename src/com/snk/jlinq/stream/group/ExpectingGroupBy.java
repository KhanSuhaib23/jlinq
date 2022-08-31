package com.snk.jlinq.stream.group;

import com.snk.jlinq.function.Function1;
import com.snk.jlinq.function.MemberAccessor;
import com.snk.jlinq.stream.ExpectingOrderBy;
import com.snk.jlinq.stream.pipeline.GroupedStreamBuilderOp;
import com.snk.jlinq.stream.pipeline.StreamOp;

public class ExpectingGroupBy<T> extends ExpectingOrderBy<T, T> {
    public ExpectingGroupBy(StreamOp<T, T> operatingStream) {
        super(operatingStream);
    }

    public <IN, OUT> InGroupByExpectingComma1<OUT, T> groupBy(Function1<IN, OUT> mapper) {
        return new InGroupByExpectingComma1<>(new GroupedStreamBuilderOp<OUT, T>(operatingStream()), MemberAccessor.from(mapper));
    }
}
