package com.snk.jlinq.stream;

import com.snk.jlinq.stream.pipeline.StreamOp;
import com.snk.jlinq.tuple.Tuple2;

public class JoinableJoin2Stream<T1, T2> extends FilterableStream<Tuple2<T1, T2>> {
    public JoinableJoin2Stream(StreamOp<Tuple2<T1, T2>> operatingStream) {
        super(operatingStream);
    }
}
