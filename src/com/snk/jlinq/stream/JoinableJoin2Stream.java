package com.snk.jlinq.stream;

import com.snk.jlinq.data.StreamContext;
import com.snk.jlinq.stream.pipeline.RootStreamOp;
import com.snk.jlinq.stream.pipeline.StreamOp;
import com.snk.jlinq.tuple.Tuple2;

import java.util.Collections;
import java.util.stream.Stream;

public class JoinableJoin2Stream<T1, T2> extends JoinableStream<Tuple2<T1, T2>> {
    public JoinableJoin2Stream(StreamOp<Tuple2<T1, T2>> operatingStream) {
        super(operatingStream);
    }

    public <TN> InJoin2ExpectingOn<T1, T2, TN> join(String alias, EnrichedStream<TN> stream) {
        return new InJoin2ExpectingOn<>(operatingStream(),
                new RootStreamOp<>(new EnrichedStream<>(stream.stream(),
                        StreamContext.init(alias, stream.context().classAt(1)), Collections.emptyList())));
    }

    public <TN> InJoin2ExpectingOn<T1, T2, TN> join(String alias, Stream<TN> stream, Class<?> clazz) {
        return new InJoin2ExpectingOn<>(operatingStream(),
                new RootStreamOp<>(new EnrichedStream<>(stream,
                        StreamContext.init(alias, clazz), Collections.emptyList())));
    }
}
