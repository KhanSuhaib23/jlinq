package com.snk.jlinq.stream.pipeline;

import com.snk.jlinq.data.Condition;
import com.snk.jlinq.stream.EnrichedStream;
import com.snk.jlinq.stream.util.StreamJoin;

import java.util.function.BiFunction;
import java.util.function.Function;

public class JoinStreamOp<T1, T2, OT> implements StreamOp<OT, OT> {
    private final StreamOp<T1, T1> left;
    private final StreamOp<T2, T2> right;
    private final Condition condition;
    private final BiFunction<T1, T2, OT> mapper;

    public JoinStreamOp(StreamOp<T1, T1> left, StreamOp<T2, T2> right, Condition condition, BiFunction<T1, T2, OT> mapper) {
        this.left = left;
        this.right = right;
        this.condition = condition;
        this.mapper = mapper;
    }

    @Override
    public EnrichedStream<OT, OT> outputStream() {
        return StreamJoin.streamJoin(left.outputStream(), right.outputStream(), condition, mapper);
    }
}
