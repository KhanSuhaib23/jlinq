package com.snk.jlinq.stream.util;

import com.snk.jlinq.data.Condition;
import com.snk.jlinq.stream.EnrichedStream;
import com.snk.jlinq.tuple.Pair;

import java.util.function.Predicate;
import java.util.stream.Stream;

public class StreamFilter {
    // TODO for now everything is an and
    public static <GT, OT> EnrichedStream<GT, OT> streamFilter(EnrichedStream<GT, OT> stream, Condition condition) {

        Predicate<Pair<GT, Stream<OT>>> predicate = v -> condition.value(stream.context(), v);

        return EnrichedStream.pairedStream(stream.pairStream().filter(predicate), stream.context(), stream.orderedBy());
    }
}
