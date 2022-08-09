package com.snk.jlinq.stream;

import java.util.stream.Stream;

public abstract class SelectStream<T> extends PassThroughStream<T> {
    @Override
    protected Stream<T> underlyingStream() {
        return outputStream().stream();
    }

    public abstract EnrichedStream<T> outputStream();
}
