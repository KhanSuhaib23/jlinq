package com.snk.jlinq.stream.pipeline;

import com.snk.jlinq.function.MemberAccessor;
import com.snk.jlinq.stream.EnrichedStream;
import com.snk.jlinq.stream.util.StreamFilter;
import com.snk.jlinq.stream.util.StreamOrderBy;

import java.util.List;

public class OrderedStreamOp<GT, OT> implements StreamOp<GT, OT> {
    private final StreamOp<GT, OT> stream;
    private final List<MemberAccessor> orderBys;

    public OrderedStreamOp(StreamOp<GT, OT> stream, List<MemberAccessor> orderBys) {
        this.stream = stream;
        this.orderBys = orderBys;
    }
    @Override
    public EnrichedStream<GT> outputStream() {
        return StreamOrderBy.orderBy(stream.outputStream(), orderBys);
    }
}
