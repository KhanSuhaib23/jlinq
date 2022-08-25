package com.snk.jlinq.stream;

import com.snk.jlinq.data.StreamContext;
import com.snk.jlinq.stream.pipeline.RootStreamOp;
import com.snk.jlinq.stream.pipeline.StreamOp;
import com.snk.jlinq.tuple.Tuple0;
import com.snk.jlinq.tuple.Tuple2;

import java.util.Collections;
import java.util.stream.Stream;

public class ExpectingJoin1Stream<T> extends SortableStream<T, Tuple0> {
    public ExpectingJoin1Stream(StreamOp<T, Tuple0> operatingStream) {
        super(operatingStream);
    }

    public <TN> InJoinExpectingOn<T, TN, Tuple2<T, TN>, ExpectingJoin2Stream<T, TN>> join(String alias, Stream<TN> stream, Class<?> clazz) {
        return new InJoinExpectingOn<>(operatingStream(),
                new RootStreamOp<>(new EnrichedStream<>(stream, StreamContext.init(alias, clazz), Collections.emptyList())),
                (v1, v2) -> new Tuple2<>(v1, v2), baseExp -> new ExpectingJoin2Stream<>(baseExp));
    }

    public <TN> InJoinExpectingOn<T, TN, Tuple2<T, TN>, ExpectingJoin2Stream<T, TN>> join(String alias, EnrichedStream<TN> stream) {
        return new InJoinExpectingOn<>(operatingStream(),
                new RootStreamOp<>(new EnrichedStream<>(stream.stream(), StreamContext.init(alias, stream.context().classAt(1)), Collections.emptyList())),
                (v1, v2) -> new Tuple2<>(v1, v2), baseExp -> new ExpectingJoin2Stream<>(baseExp));
    }

    public <TN> InJoinExpectingOn<T, TN, Tuple2<T, TN>, ExpectingJoin2Stream<T, TN>> join(EnrichedStream<TN> stream) {
        return new InJoinExpectingOn<>(operatingStream(),
                new RootStreamOp<>(new EnrichedStream<>(stream.stream(), StreamContext.init(stream.context().classAt(1)), Collections.emptyList())),
                (v1, v2) -> new Tuple2<>(v1, v2), baseExp -> new ExpectingJoin2Stream<>(baseExp));
    }
}
