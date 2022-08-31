package com.snk.jlinq.stream;

import com.snk.jlinq.core.ListUtil;
import com.snk.jlinq.function.Function1;
import com.snk.jlinq.function.MemberAccessor;
import com.snk.jlinq.stream.pipeline.OrderedStreamOp;
import com.snk.jlinq.stream.pipeline.StreamOp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GotOrderByExpectingThen<GT, OT> extends FilterableStream<GT, OT> {
    private final List<MemberAccessor> orderBys;

    public GotOrderByExpectingThen(StreamOp<GT, OT> operatingStream, List<MemberAccessor> orderBy) {
        super(operatingStream);
        this.orderBys = orderBy;
    }

    public GotOrderByExpectingThen(StreamOp<GT, OT> operatingStream, MemberAccessor orderBy) {
        this(operatingStream, Arrays.asList(orderBy));
    }

    public GotOrderByExpectingThen(StreamOp<GT, OT> operatingStream, List<MemberAccessor> orderBy, MemberAccessor additionalOrderBy) {
        this(operatingStream, ListUtil.addOne(orderBy, additionalOrderBy));
    }

    public <IN, OUT extends Comparable<OUT>> GotOrderByExpectingThen<GT, OT> then(Function1<IN, OUT> mapper) {
        return then (MemberAccessor.from(mapper));
    }

    public <IN, OUT extends Comparable<OUT>> GotOrderByExpectingThen<GT, OT> then(String alias, Function1<IN, OUT> mapper) {
        return then(MemberAccessor.from(alias, mapper));
    }

    private <OUT extends Comparable<OUT>> GotOrderByExpectingThen<GT, OT> then(MemberAccessor<OUT> memberAccessor) {
        return new GotOrderByExpectingThen<>(operatingStream, orderBys, memberAccessor);
    }

    @Override
    public StreamOp<GT, OT> operatingStream() {
        return new OrderedStreamOp<>(operatingStream, orderBys);
    }
}
