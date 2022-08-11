package com.snk.jlinq.stream;

import com.snk.jlinq.core.ListUtil;
import com.snk.jlinq.function.Function1;
import com.snk.jlinq.function.MemberAccessor;
import com.snk.jlinq.stream.pipeline.OrderedStreamOp;
import com.snk.jlinq.stream.pipeline.StreamOp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GotOrderByExpectingThen<T> extends FilterableStream<T> {
    private final List<MemberAccessor> orderBys;

    public GotOrderByExpectingThen(StreamOp<T> operatingStream, List<MemberAccessor> orderBys) {
        super(operatingStream);
        this.orderBys = orderBys;
    }

    public GotOrderByExpectingThen(StreamOp<T> operatingStream, MemberAccessor orderBy) {
        super(operatingStream);
        this.orderBys = Arrays.asList(orderBy);
    }

    public GotOrderByExpectingThen(GotOrderByExpectingThen<T> base, MemberAccessor orderBy) {
        super(base.operatingStream());
        this.orderBys = ListUtil.addOne(base.orderBys, orderBy);
    }

    public <IN, OUT extends Comparable<OUT>> GotOrderByExpectingThen<T> then(Function1<IN, OUT> mapper) {
        return new GotOrderByExpectingThen<T>(this, MemberAccessor.from(mapper));
    }

    public <IN, OUT extends Comparable<OUT>> GotOrderByExpectingThen<T> then(String alias, Function1<IN, OUT> mapper) {
        return new GotOrderByExpectingThen<T>(this, MemberAccessor.from(alias, mapper));
    }

    @Override
    public StreamOp<T> operatingStream() {
        return new OrderedStreamOp<>(operatingStream, orderBys);
    }
}
