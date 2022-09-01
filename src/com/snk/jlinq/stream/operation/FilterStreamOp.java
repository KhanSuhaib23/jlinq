package com.snk.jlinq.stream.operation;

import com.snk.jlinq.stream.EnrichedStream;
import com.snk.jlinq.stream.expression.Condition;
import com.snk.jlinq.util.StreamOperations;


public class FilterStreamOp<GT, OT> implements StreamOp<GT, OT> {
    private final StreamOp<GT, OT> baseStream;
    private final Condition filterCondition;

    public FilterStreamOp(StreamOp<GT, OT> baseStream, Condition filterCondition) {
        this.baseStream = baseStream;
        this.filterCondition = filterCondition;
    }

    @Override
    public EnrichedStream<GT, OT> outputStream() {
        return StreamOperations.where(baseStream.outputStream(), filterCondition);
    }
}