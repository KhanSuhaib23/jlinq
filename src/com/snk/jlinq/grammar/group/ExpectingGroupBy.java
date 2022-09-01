package com.snk.jlinq.grammar.group;

import com.snk.jlinq.function.Function1;
import com.snk.jlinq.grammar.ExpectingOrderBy;
import com.snk.jlinq.stream.DataSelector;
import com.snk.jlinq.stream.operation.GroupedStreamBuilderOp;
import com.snk.jlinq.stream.operation.StreamOp;

public class ExpectingGroupBy<T> extends ExpectingOrderBy<T, T> {
    public ExpectingGroupBy(StreamOp<T, T> operatingStream) {
        super(operatingStream);
    }

    public <IN, OUT> InGroupByExpectingComma1<OUT, T> groupBy(Function1<IN, OUT> mapper) {
        return new InGroupByExpectingComma1<>(new GroupedStreamBuilderOp<OUT, T>(operatingStream()), DataSelector.from(mapper));
    }
}
