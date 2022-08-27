package com.snk.jlinq.stream.util;

import com.snk.jlinq.function.MemberAccessor;
import com.snk.jlinq.stream.EnrichedStream;
import com.snk.jlinq.tuple.Pair;
import com.snk.jlinq.tuple.Tuple0;
import com.snk.jlinq.tuple.TupleUtil;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamGroupBy {
    public static <OT, GT> EnrichedStream<GT, OT> streamGroupBy(EnrichedStream<OT, OT> stream, List<MemberAccessor> groupBys) {
        Stream<Pair<GT, Stream<OT>>> os = stream.pairStream()
                .map(t -> Pair.of((GT) TupleUtil.createTuple(groupBys.stream()
                        .map(a -> stream.accessMapper(a).apply(t)).collect(Collectors.toList())), t.left()))
                .collect(Collectors.groupingBy(
                        p -> p.left(),
                        Collectors.toList()))
                .entrySet().stream()
                .map(e -> Pair.of(e.getKey(), e.getValue().stream().map(Pair::right)));

        return EnrichedStream.pairedStream(os, stream.context().groupBy(groupBys), stream.orderedBy());
    }
}
