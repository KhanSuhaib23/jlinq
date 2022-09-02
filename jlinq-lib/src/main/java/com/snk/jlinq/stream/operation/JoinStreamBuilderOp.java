package com.snk.jlinq.stream.operation;

import com.snk.jlinq.stream.EnrichedStream;
import com.snk.jlinq.stream.expression.Condition;

import java.util.function.BiFunction;

public class JoinStreamBuilderOp<T1, T2, OutputType> implements StreamOp<OutputType, OutputType> {
    protected final StreamOp<T1, T1> left;
    protected final StreamOp<T2, T2> right;

    public JoinStreamBuilderOp(StreamOp<T1, T1> left, StreamOp<T2, T2> right) {
        this.left = left;
        this.right = right;
    }

    public JoinStreamOp<T1, T2, OutputType> buildJoin(Condition condition, BiFunction<T1, T2, OutputType> mapper) {
        return new JoinStreamOp<>(this, condition, mapper);
    }

    public StreamOp<T1, T1> left() {
        return left;
    }

    public StreamOp<T2, T2> right() {
        return right;
    }

    @Override
    public EnrichedStream<OutputType, OutputType> outputStream() {
        throw new RuntimeException("never to be called");
    }
}
