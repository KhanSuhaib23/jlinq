package com.snk.jlinq.stream.util;

import com.snk.jlinq.data.Condition;
import com.snk.jlinq.data.StreamContext;
import com.snk.jlinq.stream.EnrichedStream;
import com.snk.jlinq.tuple.Pair;
import com.snk.jlinq.tuple.Tuple0;

import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamJoin {
    public static <T1, T2, OT> EnrichedStream<OT, OT> streamJoin(EnrichedStream<T1, T1> left, EnrichedStream<T2, T2> right, Condition condition, BiFunction<T1, T2, OT> mapper) {
        List<Pair<T2, Stream<T2>>> t2 = right.pairStream().collect(Collectors.toList());
        StreamContext newStreamContext = left.context().merge(right.context());
        Predicate<Pair<OT, Stream<OT>>> predicate = v -> condition.value(newStreamContext, v);

        Stream<OT> stream = left.pairStream()
                .flatMap(l -> t2.stream().filter(r -> predicate.test(Pair.of(mapper.apply(l.left(), r.left()), Stream.empty()))).map(r -> mapper.apply(l.left(), r.left())));

        return EnrichedStream.singleStream(stream, newStreamContext, Collections.emptyList());
    }
}
