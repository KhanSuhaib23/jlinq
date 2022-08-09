package com.snk.jlinq.stream;

public class BaseStream<T> extends SelectStream<T> {
    private final EnrichedStream<T> enrichedStream;

    public BaseStream(EnrichedStream<T> enrichedStream) {
        this.enrichedStream = enrichedStream;
    }

    @Override
    public EnrichedStream<T> outputStream() {
        return enrichedStream;
    }
}
