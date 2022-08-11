package com.snk.jlinq.stream;

import com.snk.jlinq.data.StreamAlias;
import com.snk.jlinq.data.StreamContext;
import com.snk.jlinq.function.MemberAccessor;

import java.util.*;
import java.util.stream.Stream;

public class EnrichedStream<T> {
    private final List<MemberAccessor> orderedBy;
    private final Stream<T> stream;
    private final StreamContext context;

    public EnrichedStream(Stream<T> stream, StreamContext context, List<MemberAccessor> orderedBy) {
        this.stream = stream;
        this.orderedBy = orderedBy;
        this.context = context;
    }

    public static <T, R> EnrichedStream<R> withNewStream(EnrichedStream<T> underlyingStream, Stream<R> stream) {
        return new EnrichedStream<>(stream, underlyingStream.context(), underlyingStream.orderedBy());
    }

    public Stream<T> stream() {
        return stream;
    }

    public StreamContext context() {
        return context;
    }

    public List<MemberAccessor> orderedBy() {
        return orderedBy;
    }

    public Integer aliasIndex(StreamAlias streamAlias) {
        return context.get(streamAlias); // TODO: possible null pointer exception
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
