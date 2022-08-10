package com.snk.jlinq.stream;

import com.snk.jlinq.data.StreamContext;
import com.snk.jlinq.stream.pipeline.RootStreamOp;
import com.snk.jlinq.stream.pipeline.StreamOp;

import java.util.Collections;
import java.util.stream.Stream;

public class JoinableJoin1Stream<T> extends FilterableStream<T> {
    public JoinableJoin1Stream(StreamOp<T> operatingStream) {
        super(operatingStream);
    }

    public <TN> InJoin1ExpectingOn<T, TN> join(String alias, EnrichedStream<TN> stream) {
        return new InJoin1ExpectingOn<>(operatingStream(),
                new RootStreamOp<>(new EnrichedStream<>(stream.stream(),
                        StreamContext.init(alias, stream.context().classAt(1)), Collections.emptyList())));
    }

    public <TN> InJoin1ExpectingOn<T, TN> join(String alias, Stream<TN> stream, Class<?> clazz) {
        return new InJoin1ExpectingOn<>(operatingStream(),
                new RootStreamOp<>(new EnrichedStream<>(stream,
                        StreamContext.init(alias, clazz), Collections.emptyList())));
    }
}
