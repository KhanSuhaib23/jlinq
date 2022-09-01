package com.snk.jlinq.grammar;

import com.snk.jlinq.stream.EnrichedStream;
import com.snk.jlinq.stream.PassThroughStream;

import java.util.stream.Stream;

public abstract class SelectStream<GT, OT> extends PassThroughStream<GT> {
    @Override
    protected Stream<GT> underlyingStream() {
        return outputStream().singleStream();
    }

    public abstract EnrichedStream<GT, OT> outputStream();
}
