package com.snk.jlinq.stream.util;

import com.snk.jlinq.function.Function1;
import com.snk.jlinq.function.MemberAccessor;
import com.snk.jlinq.stream.AggregationFunction;
import com.snk.jlinq.stream.EnrichedStream;
import com.snk.jlinq.tuple.Pair;
import com.snk.jlinq.tuple.Tuple0;
import com.snk.jlinq.tuple.TupleUtil;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamProjector {
    public static <RT, GT, OT> EnrichedStream<RT, RT> project(EnrichedStream<GT, OT> enrichedStream, List<MemberAccessor> projections) {
        Function<Pair<GT, Stream<OT>>, RT> f = v -> {
            Map<Integer, Object> map = new HashMap<>();

            for (int i = 0; i < projections.size(); ++i) {
                MemberAccessor m = projections.get(i);
                if (m.type() == AggregationFunction.Type.NONE) {
                    map.put(i, enrichedStream.accessMapper(m).apply(v));
                }
            }

            Map<Integer, BiFunction<Object, Object, Object>> mapper = new HashMap<>(); // (access object, newObject) -> new access

            for (int i = 0; i < projections.size(); ++i) {
                MemberAccessor m = projections.get(i);
                switch (m.type()) {
                    case LIST:
                        List l = new ArrayList();
                        map.put(i, l);
                        mapper.put(i, (a, o) -> {
                            List list = (List) a;
                            list.add(o);
                            return list;
                        });
                        break;
                    case COUNT:
                        Integer in = 0;
                        map.put(i, in);
                        mapper.put(i, (a, o) -> {
                            Integer i1 = (Integer) a;
                            return i1 + 1;
                        });
                        break;
                }
            }

            v.right().forEach(v1 -> {
                for (int i = 0; i < projections.size(); ++i) {
                    MemberAccessor m = projections.get(i);
                    if (m.type() != AggregationFunction.Type.NONE) {
                        Object newObj = mapper.get(i).apply(map.get(i), enrichedStream.context().getAggregate(m).apply(v1));
                        map.put(i, newObj);
                    }
                }
            });

            List<Object> tupleObjects = map.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .map(Map.Entry::getValue)
                    .collect(Collectors.toList());

            return (RT) TupleUtil.createTuple(tupleObjects);
        };

        return EnrichedStream.withNewStream(enrichedStream, enrichedStream.pairStream().map(f));
    }
}
