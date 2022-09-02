package com.snk.jlinq.stream.operation;

import com.snk.jlinq.stream.EnrichedStream;
import com.snk.jlinq.stream.expression.Condition;
import com.snk.jlinq.util.StreamOperations;


public class FilterStreamOp<GroupedType, OriginalType> implements StreamOp<GroupedType, OriginalType> {
    private final StreamOp<GroupedType, OriginalType> baseStream;
    private final Condition filterCondition;

    public FilterStreamOp(StreamOp<GroupedType, OriginalType> baseStream, Condition filterCondition) {
        this.baseStream = baseStream;
        this.filterCondition = filterCondition;
    }

    @Override
    public EnrichedStream<GroupedType, OriginalType> outputStream() {
        return StreamOperations.where(baseStream.outputStream(), filterCondition);
    }
}
