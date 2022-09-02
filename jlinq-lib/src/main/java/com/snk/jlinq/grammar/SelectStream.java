package com.snk.jlinq.grammar;

import com.snk.jlinq.stream.EnrichedStream;
import com.snk.jlinq.stream.PassThroughStream;

import java.util.stream.Stream;

public abstract class SelectStream<GroupedType, OriginalType> extends PassThroughStream<GroupedType> {
    @Override
    protected Stream<GroupedType> underlyingStream() {
        return outputStream().singleStream();
    }

    public abstract EnrichedStream<GroupedType, OriginalType> outputStream();
}
