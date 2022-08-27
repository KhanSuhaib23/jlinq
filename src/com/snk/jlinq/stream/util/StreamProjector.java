package com.snk.jlinq.stream.util;

import com.snk.jlinq.function.MemberAccessor;
import com.snk.jlinq.stream.EnrichedStream;
import com.snk.jlinq.tuple.Pair;
import com.snk.jlinq.tuple.Tuple0;
import com.snk.jlinq.tuple.TupleUtil;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamProjector {
    public static <RT, GT, OT> EnrichedStream<RT, RT> project(EnrichedStream<GT, OT> enrichedStream, List<MemberAccessor> projections) {
        Function<Pair<GT, Stream<OT>>, RT> f = v -> {
            List<Object> tupleObjects = projections.stream()
                    .map(m -> enrichedStream.accessMapper(m).apply(v))
                    .collect(Collectors.toList());

            return (RT) TupleUtil.createTuple(tupleObjects);
        };

        return EnrichedStream.withNewStream(enrichedStream, enrichedStream.pairStream().map(f));
    }
}
