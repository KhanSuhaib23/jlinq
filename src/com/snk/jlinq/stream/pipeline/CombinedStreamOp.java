package com.snk.jlinq.stream.pipeline;

import com.snk.jlinq.stream.EnrichedStream;
import com.snk.jlinq.tuple.Tuple0;

public class CombinedStreamOp<T1, T2, OT> implements StreamOp<OT, Tuple0> {
    private final StreamOp<T1, Tuple0> left;
    private final StreamOp<T2, Tuple0> right;

    public CombinedStreamOp(StreamOp<T1, Tuple0> left, StreamOp<T2, Tuple0> right) {
        this.left = left;
        this.right = right;
    }

    public StreamOp<T1, Tuple0> left() {
        return left;
    }

    public StreamOp<T2, Tuple0> right() {
        return right;
    }

    @Override
    public EnrichedStream<OT> outputStream() {
        throw new RuntimeException("never to be called");
    }
}
