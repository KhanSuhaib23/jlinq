package com.snk.jlinq.grammar.join;

import com.snk.jlinq.grammar.ExpectingOrderBy;
import com.snk.jlinq.stream.EnrichedStream;
import com.snk.jlinq.stream.StreamContext;
import com.snk.jlinq.stream.operation.RootStreamOp;
import com.snk.jlinq.stream.operation.StreamOp;
import com.snk.jlinq.udt.Tuple2;

import java.util.Collections;
import java.util.stream.Stream;

public class ExpectingJoin1Stream<T> extends ExpectingOrderBy<T, T> {
    public ExpectingJoin1Stream(StreamOp<T, T> operatingStream) {
        super(operatingStream);
    }

    public <TN> InJoinExpectingOn<T, TN, Tuple2<T, TN>, ExpectingJoin2Stream<T, TN>> join(String alias, Stream<TN> stream, Class<?> clazz) {
        return join(alias, EnrichedStream.singleStream(stream, StreamContext.init(alias, clazz)));
    }

    public <TN> InJoinExpectingOn<T, TN, Tuple2<T, TN>, ExpectingJoin2Stream<T, TN>> join(EnrichedStream<TN, TN> stream) {
        return join("", stream);
    }

    public <TN> InJoinExpectingOn<T, TN, Tuple2<T, TN>, ExpectingJoin2Stream<T, TN>> join(String alias, EnrichedStream<TN, TN> stream) {
        return new InJoinExpectingOn<>(operatingStream(),
                new RootStreamOp<>(EnrichedStream.singleStream(stream.singleStream(), StreamContext.init(alias, stream.context().classAt(0)), Collections.emptyList())),
                baseExp -> new ExpectingJoin2Stream<>(baseExp));
    }
}
