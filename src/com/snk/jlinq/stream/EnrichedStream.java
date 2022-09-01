package com.snk.jlinq.stream;

import com.snk.jlinq.udt.Pair;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

// TODO(suhaibnk): many modifications needed
public class EnrichedStream<GroupedType, OriginalType> {
    private final List<DataSelector> orderedBy;
    private final Stream<Pair<GroupedType, Stream<OriginalType>>> stream;
    private final StreamContext context;

    public EnrichedStream(Stream<Pair<GroupedType, Stream<OriginalType>>> stream, StreamContext context, List<DataSelector> orderedBy) {
        this.stream = stream;
        this.orderedBy = orderedBy;
        this.context = context;
    }

    public static <GT, OT> EnrichedStream<GT, OT> pairedStream(Stream<Pair<GT, Stream<OT>>> stream, StreamContext context, List<DataSelector> orderedBy) {
        return new EnrichedStream<>(stream, context, orderedBy);
    }

    public static <GT> EnrichedStream<GT, GT> singleStream(Stream<GT> stream, StreamContext context, List<DataSelector> orderedBy) {
        return new EnrichedStream<>(stream.map(x -> Pair.of(x, Stream.empty())), context, orderedBy);
    }

    public static <GT> EnrichedStream<GT, GT> singleStream(Stream<GT> stream, StreamContext context) {
        return new EnrichedStream<>(stream.map(x -> Pair.of(x, Stream.empty())), context, Collections.emptyList());
    }

    public static <GT, OT, R> EnrichedStream<R, R> withNewStream(EnrichedStream<GT, OT> underlyingStream, Stream<R> stream) {
        return EnrichedStream.singleStream(stream, underlyingStream.context(), underlyingStream.orderedBy());
    }

    public Stream<GroupedType> singleStream() {
        return stream.map(Pair::left);
    }

    public Stream<Pair<GroupedType, Stream<OriginalType>>> pairStream() {
        return stream;
    }

    public StreamContext context() {
        return context;
    }

    public List<DataSelector> orderedBy() {
        return orderedBy;
    }

    public Function<Object, Object> accessMapper(DataSelector selector) {
        return context.get(selector); // TODO: possible null pointer exception
    }

    public Function<Object, Object> aggregateMapper(DataSelector selector) {
        return context.getAggregate(selector); // TODO: possible null pointer exception
    }


        public boolean isOrderedBy(List<DataSelector> toOrderBy) {
        if (toOrderBy.size() <= orderedBy.size()) {
            for (int i = 0; i < toOrderBy.size(); ++i) {
                if (!toOrderBy.get(i).equals(orderedBy.get(i))) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }
}
