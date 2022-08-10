package com.snk.jlinq.stream.pipeline;

import com.snk.jlinq.stream.EnrichedStream;
import com.snk.jlinq.tuple.Tuple2;

public class DualStreamOp<T1, T2> implements StreamOp<Tuple2<T1, T2>> {
    private final StreamOp<T1> left;
    private final StreamOp<T2> right;

    public DualStreamOp(StreamOp<T1> left, StreamOp<T2> right) {
        this.left = left;
        this.right = right;
    }

    public StreamOp<T1> left() {
        return left;
    }

    public StreamOp<T2> right() {
        return right;
    }

    @Override
    public EnrichedStream<Tuple2<T1, T2>> outputStream() {
        throw new RuntimeException("Illegal Operation exception");
    }
}
