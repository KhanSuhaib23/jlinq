package com.snk.jlinq.stream;

import com.snk.jlinq.data.Condition;
import com.snk.jlinq.stream.pipeline.CombinedStreamOp;
import com.snk.jlinq.stream.pipeline.JoinStreamOp;
import com.snk.jlinq.stream.pipeline.StreamOp;
import com.snk.jlinq.tuple.Tuple2;

public class InJoin1ExpectingOn<T1, T2> extends InJoinExpectingOn<T1, T2, Tuple2<T1, T2>, JoinableJoin2Stream<T1, T2>> {
    public InJoin1ExpectingOn(StreamOp<T1> original, StreamOp<T2> join) {
        super(original, join);
    }

    protected JoinableJoin2Stream<T1, T2> createJoinedStreamFromConditions(StreamOp<Tuple2<T1, T2>> stream, Condition condition) {
        CombinedStreamOp<T1, T2, Tuple2<T1, T2>> combinedStreamOp = (CombinedStreamOp<T1, T2, Tuple2<T1,T2>>) stream;

        return new JoinableJoin2Stream<>(new JoinStreamOp<>(combinedStreamOp.left(), combinedStreamOp.right(), condition, (v1, v2) -> new Tuple2<>(v1, v2)));
    }
}
