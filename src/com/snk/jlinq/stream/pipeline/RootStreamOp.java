package com.snk.jlinq.stream.pipeline;

import com.snk.jlinq.stream.EnrichedStream;
import com.snk.jlinq.tuple.Tuple0;

public class RootStreamOp<GT> implements StreamOp<GT, Tuple0> {
    private final EnrichedStream<GT> enrichedStream;

    public RootStreamOp(EnrichedStream<GT> enrichedStream) {
        this.enrichedStream = enrichedStream;
    }


    @Override
    public EnrichedStream<GT> outputStream() {
        return enrichedStream;
    }
}
