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

    public <NewType> InJoinExpectingOn<T, NewType, Tuple2<T, NewType>, ExpectingJoin2Stream<T, NewType>> join(String alias, Stream<NewType> stream, Class<?> clazz) {
        return join(alias, EnrichedStream.singleStream(stream, StreamContext.init(alias, clazz)));
    }

    public <JoinStreamType> InJoinExpectingOn<T, JoinStreamType, Tuple2<T, JoinStreamType>, ExpectingJoin2Stream<T, JoinStreamType>>
    join(EnrichedStream<JoinStreamType, JoinStreamType> stream) {
        return join("", stream);
    }

    public <JoinStreamType> InJoinExpectingOn<T, JoinStreamType,
            Tuple2<T, JoinStreamType>,
            ExpectingJoin2Stream<T, JoinStreamType>>
    join(String alias, EnrichedStream<JoinStreamType, JoinStreamType> stream) {
        return new InJoinExpectingOn<>(operatingStream(),
                new RootStreamOp<>(EnrichedStream.singleStream(stream.singleStream(), StreamContext.init(alias, stream.context().classAt(0)), Collections.emptyList())),
                ExpectingJoin2Stream::new);
    }
}
