package com.snk.jlinq.stream.pipeline;

import com.snk.jlinq.data.Condition;
import com.snk.jlinq.stream.EnrichedStream;
import com.snk.jlinq.stream.util.StreamFilter;


public class FilterStreamOp<T> implements StreamOp<T> {
    private final StreamOp<T> baseStream;
    private final Condition filterCondition;

    public FilterStreamOp(StreamOp<T> baseStream, Condition filterCondition) {
        this.baseStream = baseStream;
        this.filterCondition = filterCondition;
    }

    @Override
    public EnrichedStream<T> outputStream() {
        return StreamFilter.streamFilter(baseStream.outputStream(), filterCondition);
    }
}
