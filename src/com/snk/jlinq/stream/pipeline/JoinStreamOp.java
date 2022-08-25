package com.snk.jlinq.stream.pipeline;

import com.snk.jlinq.data.Condition;
import com.snk.jlinq.stream.EnrichedStream;
import com.snk.jlinq.stream.util.StreamJoin;
import com.snk.jlinq.tuple.Tuple0;
import com.snk.jlinq.tuple.Tuple2;

import java.util.function.BiFunction;
import java.util.function.Function;

public class JoinStreamOp<T1, T2, OT> implements StreamOp<OT, Tuple0> {
    private final StreamOp<T1, Tuple0> left;
    private final StreamOp<T2, Tuple0> right;
    private final Condition condition;
    private final BiFunction<T1, T2, OT> mapper;

    public JoinStreamOp(StreamOp<T1, Tuple0> left, StreamOp<T2, Tuple0> right, Condition condition, BiFunction<T1, T2, OT> mapper) {
        this.left = left;
        this.right = right;
        this.condition = condition;
        this.mapper = mapper;
    }

    @Override
    public EnrichedStream<OT> outputStream() {
        return StreamJoin.streamJoin(left.outputStream(), right.outputStream(), condition, mapper);
    }
}
