package com.snk.jlinq.stream;

import com.snk.jlinq.udt.Pair;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

// TODO(suhaibnk): many modifications needed
public class EnrichedStream<GT, OT> {
    private final List<MemberAccessor> orderedBy;
    private final Stream<Pair<GT, Stream<OT>>> stream;
    private final StreamContext context;

    public EnrichedStream(Stream<Pair<GT, Stream<OT>>> stream, StreamContext context, List<MemberAccessor> orderedBy) {
        this.stream = stream;
        this.orderedBy = orderedBy;
        this.context = context;
    }

    public static <GT, OT> EnrichedStream<GT, OT> pairedStream(Stream<Pair<GT, Stream<OT>>> stream, StreamContext context, List<MemberAccessor> orderedBy) {
        return new EnrichedStream<>(stream, context, orderedBy);
    }

    public static <GT> EnrichedStream<GT, GT> singleStream(Stream<GT> stream, StreamContext context, List<MemberAccessor> orderedBy) {
        return new EnrichedStream<>(stream.map(x -> Pair.of(x, Stream.empty())), context, orderedBy);
    }

    public static <GT> EnrichedStream<GT, GT> singleStream(Stream<GT> stream, StreamContext context) {
        return new EnrichedStream<>(stream.map(x -> Pair.of(x, Stream.empty())), context, Collections.emptyList());
    }

    public static <GT, OT, R> EnrichedStream<R, R> withNewStream(EnrichedStream<GT, OT> underlyingStream, Stream<R> stream) {
        return EnrichedStream.singleStream(stream, underlyingStream.context(), underlyingStream.orderedBy());
    }

    public Stream<GT> singleStream() {
        return stream.map(Pair::left);
    }

    public Stream<Pair<GT, Stream<OT>>> pairStream() {
        return stream;
    }

    public StreamContext context() {
        return context;
    }

    public List<MemberAccessor> orderedBy() {
        return orderedBy;
    }

    public Function<Object, Object> accessMapper(MemberAccessor memberAccessor) {
        return context.get(memberAccessor); // TODO: possible null pointer exception
    }

    public Function<Object, Object> aggregateMapper(MemberAccessor memberAccessor) {
        return context.getAggregate(memberAccessor); // TODO: possible null pointer exception
    }


        public boolean isOrderedBy(List<MemberAccessor> toOrderBy) {
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
