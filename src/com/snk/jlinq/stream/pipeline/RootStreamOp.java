package com.snk.jlinq.stream.pipeline;

import com.snk.jlinq.stream.EnrichedStream;

public class RootStreamOp<T> implements StreamOp<T> {
    private final EnrichedStream<T> enrichedStream;

    public RootStreamOp(EnrichedStream<T> enrichedStream) {
        this.enrichedStream = enrichedStream;
    }


    @Override
    public EnrichedStream<T> outputStream() {
        return enrichedStream;
    }
}
