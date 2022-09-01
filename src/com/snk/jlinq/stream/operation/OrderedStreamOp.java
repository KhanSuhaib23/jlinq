package com.snk.jlinq.stream.operation;

import com.snk.jlinq.stream.EnrichedStream;
import com.snk.jlinq.stream.MemberAccessor;
import com.snk.jlinq.util.StreamOperations;

import java.util.List;

public class OrderedStreamOp<GroupedType, OriginalType> implements StreamOp<GroupedType, OriginalType> {
    private final StreamOp<GroupedType, OriginalType> stream;
    private final List<MemberAccessor> orderBys;

    public OrderedStreamOp(StreamOp<GroupedType, OriginalType> stream, List<MemberAccessor> orderBys) {
        this.stream = stream;
        this.orderBys = orderBys;
    }
    @Override
    public EnrichedStream<GroupedType, OriginalType> outputStream() {
        return StreamOperations.orderBy(stream.outputStream(), orderBys);
    }
}
