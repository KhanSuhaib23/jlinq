package com.snk.jlinq.stream;

import com.snk.jlinq.data.Condition;
import com.snk.jlinq.data.ExpressionValue;
import com.snk.jlinq.function.Function1;
import com.snk.jlinq.function.MemberAccessor;
import com.snk.jlinq.stream.expression.GotPartialExpression;
import com.snk.jlinq.stream.expression.JoinStreamExpressionExtender;
import com.snk.jlinq.stream.expression.SelectableStreamExpressionExtender;
import com.snk.jlinq.stream.pipeline.DualStreamOp;
import com.snk.jlinq.stream.pipeline.JoinStreamOp;
import com.snk.jlinq.stream.pipeline.StreamOp;
import com.snk.jlinq.tuple.Tuple2;

public class InJoin1ExpectingOn<T1, T2> {
    private final StreamOp<T1> original;
    private final StreamOp<T2> join;

    public InJoin1ExpectingOn(StreamOp<T1> original, StreamOp<T2> join) {
        this.original = original;
        this.join = join;
    }

    public <IN, OUT> GotPartialExpression<Tuple2<T1, T2>, OUT, JoinStreamExpressionExtender<T1, T2>, JoinableJoin2Stream<T1, T2>> on(String alias, Function1<IN, OUT> mapper) {
        return on(ExpressionValue.fromExtractor(MemberAccessor.from(alias, mapper)));
    }

    public <IN, OUT> GotPartialExpression<Tuple2<T1, T2>, OUT, JoinStreamExpressionExtender<T1, T2>, JoinableJoin2Stream<T1, T2>> on(Function1<IN, OUT> mapper) {
        return on(ExpressionValue.fromExtractor(MemberAccessor.from(mapper)));
    }

    public <IN, OUT> GotPartialExpression<Tuple2<T1, T2>, OUT, JoinStreamExpressionExtender<T1, T2>, JoinableJoin2Stream<T1, T2>> on(ExpressionValue<OUT> expressionValue) {
        return GotPartialExpression.forJoin(original, join, expressionValue, InJoin1ExpectingOn::createJoinedStreamFromConditions);
    }

    private static <T1, T2> JoinableJoin2Stream<T1, T2> createJoinedStreamFromConditions(StreamOp<Tuple2<T1, T2>> stream, Condition condition) {
        DualStreamOp<T1, T2> dualStreamOp = (DualStreamOp<T1, T2>) stream;
        return new JoinableJoin2Stream<>(new JoinStreamOp<>(dualStreamOp.left(), dualStreamOp.right(), condition));
    }
}
