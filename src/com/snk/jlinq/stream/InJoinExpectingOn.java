package com.snk.jlinq.stream;

import com.snk.jlinq.data.Condition;
import com.snk.jlinq.data.ExpressionValue;
import com.snk.jlinq.function.Function1;
import com.snk.jlinq.function.MemberAccessor;
import com.snk.jlinq.stream.expression.GotPartialExpression;
import com.snk.jlinq.stream.expression.JoinStreamExpressionExtender;
import com.snk.jlinq.stream.pipeline.StreamOp;

public abstract class InJoinExpectingOn<T1, T2, OT, IJ extends InJoinExpectingOn<T1, T2, OT, IJ, JT>, JT extends JoinableStream<T1, T2, OT, JT, IJ>> {
    private final StreamOp<T1> original;
    private final StreamOp<T2> join;

    public InJoinExpectingOn(StreamOp<T1> original, StreamOp<T2> join) {
        this.original = original;
        this.join = join;
    }

    public <IN, OUT> GotPartialExpression<OT, OUT, JoinStreamExpressionExtender<T1, T2, OT, JT, IJ>, JT> on(String alias, Function1<IN, OUT> mapper) {
        return on(ExpressionValue.fromExtractor(MemberAccessor.from(alias, mapper)));
    }

    public <IN, OUT> GotPartialExpression<OT, OUT, JoinStreamExpressionExtender<T1, T2, OT, JT, IJ>, JT> on(Function1<IN, OUT> mapper) {
        return on(ExpressionValue.fromExtractor(MemberAccessor.from(mapper)));
    }

    public <IN, OUT> GotPartialExpression<OT, OUT, JoinStreamExpressionExtender<T1, T2, OT, JT, IJ>, JT> on(ExpressionValue<OUT> expressionValue) {
        return GotPartialExpression.forJoin(original, join, expressionValue, (stream, conditions) -> createJoinedStreamFromConditions(stream, conditions));
    }
//
//    private static <T1, T2> JT createJoinedStreamFromConditions(StreamOp<Tuple2<T1, T2>> stream, Condition condition) {
//        DualStreamOp<T1, T2> dualStreamOp = (DualStreamOp<T1, T2>) stream;
//        return new JoinableJoin2Stream<>(new JoinStreamOp<>(dualStreamOp.left(), dualStreamOp.right(), condition));
//    }

    protected abstract JT createJoinedStreamFromConditions(StreamOp<OT> stream, Condition condition);


}
