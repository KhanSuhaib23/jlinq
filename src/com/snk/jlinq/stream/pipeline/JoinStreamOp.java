package com.snk.jlinq.stream.pipeline;

import com.snk.jlinq.data.Condition;
import com.snk.jlinq.stream.EnrichedStream;
import com.snk.jlinq.stream.util.StreamJoin;
import com.snk.jlinq.tuple.Tuple2;

public class JoinStreamOp<T1, T2> implements StreamOp<Tuple2<T1, T2>> {
    private final StreamOp<T1> left;
    private final StreamOp<T2> right;
    private final Condition condition;

    public JoinStreamOp(StreamOp<T1> left, StreamOp<T2> right, Condition condition) {
        this.left = left;
        this.right = right;
        this.condition = condition;
    }

    @Override
    public EnrichedStream<Tuple2<T1, T2>> outputStream() {
        return StreamJoin.streamJoin(left.outputStream(), right.outputStream(), condition);
    }
}
