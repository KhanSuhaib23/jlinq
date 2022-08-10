package com.snk.jlinq.stream;

import com.snk.jlinq.data.StreamContext;
import com.snk.jlinq.stream.pipeline.RootStreamOp;

import java.util.Collections;
import java.util.stream.Stream;

public class JLinq {
    public static <T> JoinableJoin1Stream<T> from(Stream<T> stream, Class<?> clazz) {
        return new JoinableJoin1Stream<>(new RootStreamOp<>(new EnrichedStream<>(stream, StreamContext.init(clazz), Collections.emptyList())));
    }
}
