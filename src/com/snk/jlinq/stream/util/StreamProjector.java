package com.snk.jlinq.stream.util;

import com.snk.jlinq.function.MemberAccessor;
import com.snk.jlinq.reflect.ReflectionUtil;
import com.snk.jlinq.stream.EnrichedStream;
import com.snk.jlinq.tuple.Tuple2;
import com.snk.jlinq.tuple.Tuple3;
import com.snk.jlinq.tuple.TupleUtil;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public class StreamProjector {
    public static <RT, OT> EnrichedStream<RT> project(EnrichedStream<OT> enrichedStream, List<MemberAccessor> projections) {
        Function<OT, RT> f;
        switch (projections.size()) {
            case 1: {
                MemberAccessor m1 = projections.get(0);

                f = v -> (RT) ReflectionUtil.invoke(m1.method(), TupleUtil.getAt(v, enrichedStream.aliasIndex(m1.streamAlias())));
                return EnrichedStream.withNewStream(enrichedStream, enrichedStream.stream().map(f));
            }
            case 2: {
                MemberAccessor m1 = projections.get(0);
                MemberAccessor m2 = projections.get(1);
                f = v -> (RT) new Tuple2<>(ReflectionUtil.invoke(m1.method(), TupleUtil.getAt(v, enrichedStream.aliasIndex(m1.streamAlias()))),
                                            ReflectionUtil.invoke(m2.method(), TupleUtil.getAt(v, enrichedStream.aliasIndex(m2.streamAlias()))));

                return EnrichedStream.withNewStream(enrichedStream, enrichedStream.stream().map(f));
            }
            case 3: {
                MemberAccessor m1 = projections.get(0);
                MemberAccessor m2 = projections.get(1);
                MemberAccessor m3 = projections.get(2);
                f = v -> (RT) new Tuple3<>(ReflectionUtil.invoke(m1.method(), TupleUtil.getAt(v, enrichedStream.aliasIndex(m1.streamAlias()))),
                        ReflectionUtil.invoke(m2.method(), TupleUtil.getAt(v, enrichedStream.aliasIndex(m2.streamAlias()))),
                        ReflectionUtil.invoke(m3.method(), TupleUtil.getAt(v, enrichedStream.aliasIndex(m3.streamAlias()))));

                return EnrichedStream.withNewStream(enrichedStream, enrichedStream.stream().map(f));
            }

            default: {
                throw new RuntimeException("Baaad!");
            }
        }
    }
}
