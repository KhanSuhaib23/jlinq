package com.snk.jlinq.stream;

import com.snk.jlinq.data.StreamContext;
import com.snk.jlinq.stream.pipeline.RootStreamOp;

import java.util.Collections;
import java.util.stream.Stream;

public class JLinq {
    public static <T> FilterableStream<T> from(Stream<T> stream, Class<?> clazz) {
        return new FilterableStream<>(new RootStreamOp<>(new EnrichedStream<>(stream, StreamContext.init(clazz), Collections.emptyList())));
    }
}
