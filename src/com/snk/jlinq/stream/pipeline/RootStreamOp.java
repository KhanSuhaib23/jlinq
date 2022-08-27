package com.snk.jlinq.stream.pipeline;

import com.snk.jlinq.stream.EnrichedStream;

public class RootStreamOp<GT> implements StreamOp<GT, GT> {
    private final EnrichedStream<GT, GT> enrichedStream;

    public RootStreamOp(EnrichedStream<GT, GT> enrichedStream) {
        this.enrichedStream = enrichedStream;
    }


    @Override
    public EnrichedStream<GT, GT> outputStream() {
        return enrichedStream;
    }
}
