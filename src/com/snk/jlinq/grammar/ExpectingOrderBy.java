package com.snk.jlinq.grammar;

import com.snk.jlinq.function.Function1;
import com.snk.jlinq.stream.DataSelector;
import com.snk.jlinq.stream.operation.StreamOp;

public class ExpectingOrderBy<GroupedType, OriginalType> extends FilterableStream<GroupedType, OriginalType> {
    public ExpectingOrderBy(StreamOp<GroupedType, OriginalType> operatingStream) {
        super(operatingStream);
    }

    public <IN, OUT extends Comparable<OUT>> GotOrderByExpectingThen<GroupedType, OriginalType> orderBy(Function1<IN, OUT> mapper) {
        return orderBy(DataSelector.from(mapper));
    }

    public <IN, OUT extends Comparable<OUT>> GotOrderByExpectingThen<GroupedType, OriginalType> orderBy(String alias, Function1<IN, OUT> mapper) {
        return orderBy(DataSelector.from(alias, mapper));
    }

    private GotOrderByExpectingThen<GroupedType, OriginalType> orderBy(DataSelector selector) {
        return new GotOrderByExpectingThen<>(operatingStream(), selector);
    }
}
