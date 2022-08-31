package com.snk.jlinq.stream.join;

import com.snk.jlinq.data.StreamContext;
import com.snk.jlinq.stream.EnrichedStream;
import com.snk.jlinq.stream.ExpectingOrderBy;
import com.snk.jlinq.stream.pipeline.RootStreamOp;
import com.snk.jlinq.stream.pipeline.StreamOp;
import com.snk.jlinq.tuple.Tuple2;

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
                new RootStreamOp<>(EnrichedStream.singleStream(stream.singleStream(), StreamContext.init(alias, stream.context().mainClass()), Collections.emptyList())),
                baseExp -> new ExpectingJoin2Stream<>(baseExp));
    }
}
