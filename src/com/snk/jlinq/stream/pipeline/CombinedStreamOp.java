package com.snk.jlinq.stream.pipeline;

import com.snk.jlinq.stream.EnrichedStream;

public class CombinedStreamOp<T1, T2, OT> implements StreamOp<OT, OT> {
    private final StreamOp<T1, T1> left;
    private final StreamOp<T2, T2> right;

    public CombinedStreamOp(StreamOp<T1, T1> left, StreamOp<T2, T2> right) {
        this.left = left;
        this.right = right;
    }

    public StreamOp<T1, T1> left() {
        return left;
    }

    public StreamOp<T2, T2> right() {
        return right;
    }

    @Override
    public EnrichedStream<OT> outputStream() {
        throw new RuntimeException("never to be called");
    }
}
