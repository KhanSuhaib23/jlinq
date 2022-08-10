package com.snk.jlinq.stream.util;

import com.snk.jlinq.data.Condition;
import com.snk.jlinq.data.StreamContext;
import com.snk.jlinq.stream.EnrichedStream;
import com.snk.jlinq.stream.pipeline.StreamOp;
import com.snk.jlinq.tuple.Tuple2;

import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamJoin {
    public static <T1, T2, OT> EnrichedStream<OT> streamJoin(EnrichedStream<T1> left, EnrichedStream<T2> right, Condition condition, BiFunction<T1, T2, OT> mapper) {
        List<T2> t2 = right.stream().collect(Collectors.toList());
        StreamContext newStreamContext = left.context().merge(right.context());
        Predicate<OT> predicate = v -> condition.value(newStreamContext, v);

        Stream<OT> stream = left.stream()
                .flatMap(l -> t2.stream().filter(r -> predicate.test(mapper.apply(l, r))).map(r -> mapper.apply(l, r)));

        return new EnrichedStream<>(stream, newStreamContext, Collections.emptyList());
    }
}
