package com.snk.jlinq.stream.util;

import com.snk.jlinq.function.MemberAccessor;
import com.snk.jlinq.stream.EnrichedStream;
import com.snk.jlinq.tuple.TupleUtil;

import java.util.Comparator;
import java.util.List;

public class StreamOrderBy {
    public static <T> EnrichedStream<T> orderBy(EnrichedStream<T> stream, List<MemberAccessor<?>> orderBys) {
        if (stream.isOrderedBy(orderBys)) {
            return stream;
        } else {
            Comparator<T> comparator = (v1, v2) -> {
                for (MemberAccessor orderBy : orderBys) {
                    Comparable c1 = (Comparable) TupleUtil.getAt(v1, stream.aliasIndex(orderBy.streamAlias()));
                    Comparable c2 = (Comparable) TupleUtil.getAt(v2, stream.aliasIndex(orderBy.streamAlias()));
                    int c = c1.compareTo(c2);

                    if (c != 0) {
                        return c;
                    }
                }

                return 0;
            };

            return new EnrichedStream(stream.stream().sorted(comparator), stream.context(), orderBys);
        }
    }
}
