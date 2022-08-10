package com.snk.jlinq.stream;

import com.snk.jlinq.data.Condition;
import com.snk.jlinq.stream.pipeline.CombinedStreamOp;
import com.snk.jlinq.stream.pipeline.JoinStreamOp;
import com.snk.jlinq.stream.pipeline.StreamOp;
import com.snk.jlinq.tuple.Tuple2;
import com.snk.jlinq.tuple.Tuple3;

public class InJoin2ExpectingOn<T1, T2, T3> extends InJoinExpectingOn<Tuple2<T1, T2>, T3, Tuple3<T1, T2, T3>, JoinableJoin3Stream<T1, T2, T3>> {
    public InJoin2ExpectingOn(StreamOp<Tuple2<T1, T2>> original, StreamOp<T3> join) {
        super(original, join);
    }

    protected JoinableJoin3Stream<T1, T2, T3> createJoinedStreamFromConditions(StreamOp<Tuple3<T1, T2, T3>> stream, Condition condition) {
        CombinedStreamOp<Tuple2<T1, T2>, T3, Tuple3<T1, T2, T3>> combinedStreamOp = (CombinedStreamOp<Tuple2<T1, T2>, T3, Tuple3<T1, T2, T3>>) stream;
        return new JoinableJoin3Stream<>(new JoinStreamOp<>(combinedStreamOp.left(), combinedStreamOp.right(), condition, (t1, v3) -> new Tuple3<>(t1, v3)));
    }
}
