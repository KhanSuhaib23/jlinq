package com.snk.jlinq.stream.util;

import com.snk.jlinq.function.MemberAccessor;
import com.snk.jlinq.stream.EnrichedStream;
import com.snk.jlinq.tuple.TupleUtil;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamGroupBy {
    public static <OT, GT> EnrichedStream<GT> streamGroupBy(EnrichedStream<OT> stream, List<MemberAccessor> groupBys) {
        Stream<GT> os = stream.stream()
                .map(t ->
                        (GT) TupleUtil.createTuple(groupBys.stream()
                                .map(a -> stream.accessMapper(a).apply(t)).collect(Collectors.toList()))
                ).distinct();

        return new EnrichedStream<>(os, stream.context().groupBy(groupBys), stream.orderedBy());
    }
}
