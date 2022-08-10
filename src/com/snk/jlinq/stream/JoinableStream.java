package com.snk.jlinq.stream;

import com.snk.jlinq.data.StreamContext;
import com.snk.jlinq.stream.pipeline.RootStreamOp;
import com.snk.jlinq.stream.pipeline.StreamOp;

import java.util.Collections;
import java.util.stream.Stream;

// ET Expression Type
public class JoinableStream<T1, T2, OT, JT extends JoinableStream<T1, T2, OT, JT, IJ>, IJ extends InJoinExpectingOn<T1, T2, OT, IJ, JT>> extends FilterableStream<OT> {
    public JoinableStream(StreamOp<OT> operatingStream) {
        super(operatingStream);
    }

    public <TN> IJ join(String alias, EnrichedStream<TN> stream) {
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
