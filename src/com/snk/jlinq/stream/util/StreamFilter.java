package com.snk.jlinq.stream.util;

import com.snk.jlinq.data.Condition;
import com.snk.jlinq.stream.EnrichedStream;

import java.util.function.Predicate;

public class StreamFilter {
    // TODO for now everything is an and
    public static <T> EnrichedStream<T> streamFilter(EnrichedStream<T> stream, Condition condition) {

        Predicate<T> predicate = v -> condition.value(stream.context(), v);

        return new EnrichedStream<>(stream.stream().filter(predicate), stream.context(), stream.orderedBy());
    }
}
