package com.snk.jlinq.grammar;

import com.snk.jlinq.function.Function1;
import com.snk.jlinq.stream.MemberAccessor;
import com.snk.jlinq.stream.operation.StreamOp;

public class ExpectingOrderBy<GT, OT> extends FilterableStream<GT, OT> {
    public ExpectingOrderBy(StreamOp<GT, OT> operatingStream) {
        super(operatingStream);
    }

    public <IN, OUT extends Comparable<OUT>> GotOrderByExpectingThen<GT, OT> orderBy(Function1<IN, OUT> mapper) {
        return orderBy(MemberAccessor.from(mapper));
    }

    public <IN, OUT extends Comparable<OUT>> GotOrderByExpectingThen<GT, OT> orderBy(String alias, Function1<IN, OUT> mapper) {
        return orderBy(MemberAccessor.from(alias, mapper));
    }

    private <OUT extends Comparable<OUT>> GotOrderByExpectingThen<GT, OT> orderBy(MemberAccessor<OUT> memberAccessor) {
        return new GotOrderByExpectingThen<>(operatingStream(), memberAccessor);
    }
}
