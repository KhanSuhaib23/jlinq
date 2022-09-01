package com.snk.jlinq.grammar;

import com.snk.jlinq.function.Function1;
import com.snk.jlinq.stream.DataSelector;
import com.snk.jlinq.stream.operation.OrderedStreamOp;
import com.snk.jlinq.stream.operation.StreamOp;
import com.snk.jlinq.util.ListUtil;

import java.util.Arrays;
import java.util.List;

public class GotOrderByExpectingThen<GroupedType, OriginalType> extends FilterableStream<GroupedType, OriginalType> {
    private final List<DataSelector> orderBys;

    public GotOrderByExpectingThen(StreamOp<GroupedType, OriginalType> operatingStream, List<DataSelector> orderBy) {
        super(operatingStream);
        this.orderBys = orderBy;
    }

    public GotOrderByExpectingThen(StreamOp<GroupedType, OriginalType> operatingStream, DataSelector orderBy) {
        this(operatingStream, Arrays.asList(orderBy));
    }

    public GotOrderByExpectingThen(StreamOp<GroupedType, OriginalType> operatingStream, List<DataSelector> orderBy, DataSelector additionalOrderBy) {
        this(operatingStream, ListUtil.concat(orderBy, additionalOrderBy));
    }

    public <IN, OUT extends Comparable<OUT>> GotOrderByExpectingThen<GroupedType, OriginalType> then(Function1<IN, OUT> mapper) {
        return then (DataSelector.from(mapper));
    }

    public <IN, OUT extends Comparable<OUT>> GotOrderByExpectingThen<GroupedType, OriginalType> then(String alias, Function1<IN, OUT> mapper) {
        return then(DataSelector.from(alias, mapper));
    }

    private <OUT extends Comparable<OUT>> GotOrderByExpectingThen<GroupedType, OriginalType> then(DataSelector<OUT> selector) {
        return new GotOrderByExpectingThen<>(operatingStream, orderBys, selector);
    }

    @Override
    public StreamOp<GroupedType, OriginalType> operatingStream() {
        return new OrderedStreamOp<>(operatingStream, orderBys);
    }
}
