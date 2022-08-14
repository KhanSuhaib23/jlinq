package com.snk.jlinq.stream;

import com.snk.jlinq.data.StreamContext;
import com.snk.jlinq.stream.join.ExpectingJoin1Stream;
import com.snk.jlinq.stream.pipeline.RootStreamOp;

import java.util.Collections;
import java.util.stream.Stream;

public class JLinq {
    public static <T> ExpectingJoin1Stream<T> from(Stream<T> stream, Class<?> clazz) {
        return new ExpectingJoin1Stream<>(new RootStreamOp<>(new EnrichedStream<>(stream, StreamContext.init(clazz), Collections.emptyList())));
    }
}
