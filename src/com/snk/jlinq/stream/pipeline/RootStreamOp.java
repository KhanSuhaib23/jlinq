package com.snk.jlinq.stream.pipeline;

import com.snk.jlinq.stream.EnrichedStream;

public class RootStreamOp<OutputType> implements StreamOp<OutputType, OutputType> {
    private final EnrichedStream<OutputType, OutputType> enrichedStream;

    public RootStreamOp(EnrichedStream<OutputType, OutputType> enrichedStream) {
        this.enrichedStream = enrichedStream;
    }


    @Override
    public EnrichedStream<OutputType, OutputType> outputStream() {
        return enrichedStream;
    }
}
