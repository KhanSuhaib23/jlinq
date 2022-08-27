package com.snk.jlinq.stream;

import com.snk.jlinq.tuple.Tuple0;

import java.util.stream.Stream;

public abstract class SelectStream<GT, OT> extends PassThroughStream<GT> {
    @Override
    protected Stream<GT> underlyingStream() {
        return outputStream().singleStream();
    }

    public abstract EnrichedStream<GT, OT> outputStream();
}
