package com.snk.jlinq.stream.pipeline;

import com.snk.jlinq.function.MemberAccessor;
import com.snk.jlinq.stream.EnrichedStream;
import com.snk.jlinq.stream.util.StreamFilter;
import com.snk.jlinq.stream.util.StreamOrderBy;

import java.util.List;

public class OrderedStreamOp<T> implements StreamOp<T> {
    private final StreamOp<T> stream;
    private final List<MemberAccessor> orderBys;

    public OrderedStreamOp(StreamOp<T> stream, List<MemberAccessor> orderBys) {
        this.stream = stream;
        this.orderBys = orderBys;
    }
    @Override
    public EnrichedStream<T> outputStream() {
        return StreamOrderBy.orderBy(stream.outputStream(), orderBys);
    }
}
