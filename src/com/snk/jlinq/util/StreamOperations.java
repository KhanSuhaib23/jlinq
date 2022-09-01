package com.snk.jlinq.util;

import com.snk.jlinq.api.AggregationFunction;
import com.snk.jlinq.stream.DataSelector;
import com.snk.jlinq.stream.EnrichedStream;
import com.snk.jlinq.stream.StreamContext;
import com.snk.jlinq.stream.expression.Condition;
import com.snk.jlinq.udt.Pair;
import com.snk.jlinq.udt.Tuple;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamOperations {
    public static <GT, OT> EnrichedStream<GT, OT> where(EnrichedStream<GT, OT> stream, Condition condition) {

        Predicate<Pair<GT, Stream<OT>>> predicate = v -> condition.value(stream.context(), v);

        return EnrichedStream.pairedStream(stream.pairStream().filter(predicate), stream.context(), stream.orderedBy());
    }

    public static <OT, GT> EnrichedStream<GT, OT> groupBy(EnrichedStream<OT, OT> stream, List<DataSelector> groupBys) {

        Stream<Pair<GT, Stream<OT>>> os = stream.pairStream()
                .map(t -> new Pair<GT, OT>(
                        Tuple.create(groupBys.stream().map(a -> stream.context().extractValue(a, t)).toList()),
                        t.left()))
                .collect(Collectors.groupingBy(
                        Pair::left,
                        Collectors.toList()))
                .entrySet().stream()
                .map(e -> Pair.of(e.getKey(), e.getValue().stream().map(Pair::right)));

        return EnrichedStream.pairedStream(os, stream.context().groupBy(groupBys), stream.orderedBy());
    }

    public static <T1, T2, OT> EnrichedStream<OT, OT> join(EnrichedStream<T1, T1> left, EnrichedStream<T2, T2> right, Condition condition, BiFunction<T1, T2, OT> mapper) {
        List<Pair<T2, Stream<T2>>> t2 = right.pairStream().toList();
        StreamContext newStreamContext = left.context().merge(right.context());
        Predicate<Pair<OT, Stream<OT>>> predicate = v -> condition.value(newStreamContext, v);

        Stream<OT> stream = left.pairStream()
                .flatMap(l -> t2.stream().filter(r -> predicate.test(Pair.of(mapper.apply(l.left(), r.left()), Stream.empty()))).map(r -> mapper.apply(l.left(), r.left())));

        return EnrichedStream.singleStream(stream, newStreamContext, Collections.emptyList());
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static <GT, OT> int compare(EnrichedStream<GT, OT> stream, DataSelector selector, Pair<GT, Stream<OT>> v1, Pair<GT, Stream<OT>> v2) {
        Comparable c1 = stream.context().extractValue(selector, v1);
        Comparable c2 = stream.context().extractValue(selector, v2);

        return c1.compareTo(c2);
    }

    public static <GT, OT> EnrichedStream<GT, OT> orderBy(EnrichedStream<GT, OT> stream, List<DataSelector> orderBys) {
        if (stream.isOrderedBy(orderBys)) {
            return stream;
        } else {
            Comparator<Pair<GT, Stream<OT>>> comparator = (v1, v2) -> {
                for (DataSelector orderBy : orderBys) {
                    int c = compare(stream, orderBy, v1, v2);
                    if (c != 0) { return c; }
                }
                return 0;
            };

            return EnrichedStream.pairedStream(stream.pairStream().sorted(comparator), stream.context(), orderBys);
        }
    }

    public static <RT, GT, OT> EnrichedStream<RT, RT> project(EnrichedStream<GT, OT> enrichedStream, List<DataSelector> projections) {
        Function<Pair<GT, Stream<OT>>, RT> f = v -> {
            Map<Integer, Object> map = new HashMap<>();

            for (int i = 0; i < projections.size(); ++i) {
                DataSelector m = projections.get(i);
                if (m.type() == AggregationFunction.Type.NONE) {
                    map.put(i, enrichedStream.accessMapper(m).apply(v));
                }
            }

            Map<Integer, BiFunction<Object, Object, Object>> mapper = new HashMap<>(); // (access object, newObject) -> new access

            for (int i = 0; i < projections.size(); ++i) {
                DataSelector m = projections.get(i);
                switch (m.type()) {
                    case LIST -> {
                        List<Object> l = new ArrayList<>();
                        map.put(i, l);
                        mapper.put(i, (a, o) -> {
                            List<Object> list = CodeUtil.cast(a);
                            list.add(o);
                            return list;
                        });
                    }
                    case COUNT -> {
                        Integer in = 0;
                        map.put(i, in);
                        mapper.put(i, (a, o) -> {
                            Integer i1 = (Integer) a;
                            return i1 + 1;
                        });
                    }
                }
            }

            v.right().forEach(v1 -> {
                for (int i = 0; i < projections.size(); ++i) {
                    DataSelector m = projections.get(i);
                    if (m.type() != AggregationFunction.Type.NONE) {
                        Object newObj = mapper.get(i).apply(map.get(i), enrichedStream.aggregateMapper(m).apply(v1));
                        map.put(i, newObj);
                    }
                }
            });

            List<Object> tupleObjects = map.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .map(Map.Entry::getValue)
                    .collect(Collectors.toList());

            return Tuple.create(tupleObjects);
        };

        return EnrichedStream.withNewStream(enrichedStream, enrichedStream.pairStream().map(f));
    }
}
