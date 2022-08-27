package com.snk.jlinq.stream.util;

import com.snk.jlinq.function.MemberAccessor;
import com.snk.jlinq.stream.EnrichedStream;
import com.snk.jlinq.tuple.Pair;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

public class StreamOrderBy {
    public static <GT, OT> EnrichedStream<GT, OT> orderBy(EnrichedStream<GT, OT> stream, List<MemberAccessor> orderBys) {
        if (stream.isOrderedBy(orderBys)) {
            return stream;
        } else {
            Comparator<Pair<GT, Stream<OT>>> comparator = (v1, v2) -> {
                for (MemberAccessor orderBy : orderBys) {
                    Comparable c1 = (Comparable) stream.accessMapper(orderBy).apply(v1);
                    Comparable c2 = (Comparable) stream.accessMapper(orderBy).apply(v2);
                    int c = c1.compareTo(c2);

                    if (c != 0) {
                        return c;
                    }
                }

                return 0;
            };

            return EnrichedStream.pairedStream(stream.pairStream().sorted(comparator), stream.context(), orderBys);
        }
    }
}
