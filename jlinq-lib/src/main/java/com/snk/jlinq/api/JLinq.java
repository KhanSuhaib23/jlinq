package com.snk.jlinq.api;

import com.snk.jlinq.grammar.join.ExpectingJoin1Stream;
import com.snk.jlinq.stream.EnrichedStream;
import com.snk.jlinq.stream.StreamContext;
import com.snk.jlinq.stream.operation.RootStreamOp;

import java.util.Collections;
import java.util.stream.Stream;

public class JLinq {
    public static <T> ExpectingJoin1Stream<T> from(Stream<T> stream, Class<?> clazz) {
        return new ExpectingJoin1Stream<>(new RootStreamOp<>(EnrichedStream.singleStream(stream, StreamContext.init(clazz), Collections.emptyList())));
    }
}
