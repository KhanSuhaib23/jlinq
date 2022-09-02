package com.snk.jlinq.stream.operation;

import com.snk.jlinq.stream.DataSelector;
import com.snk.jlinq.stream.EnrichedStream;
import com.snk.jlinq.util.StreamOperations;

import java.util.List;

public class OrderedStreamOp<GroupedType, OriginalType> implements StreamOp<GroupedType, OriginalType> {
    private final StreamOp<GroupedType, OriginalType> stream;
    private final List<DataSelector> orderBys;

    public OrderedStreamOp(StreamOp<GroupedType, OriginalType> stream, List<DataSelector> orderBys) {
        this.stream = stream;
        this.orderBys = orderBys;
    }
    @Override
    public EnrichedStream<GroupedType, OriginalType> outputStream() {
        return StreamOperations.orderBy(stream.outputStream(), orderBys);
    }
}
