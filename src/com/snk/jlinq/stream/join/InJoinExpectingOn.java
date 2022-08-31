package com.snk.jlinq.stream.join;

import com.snk.jlinq.data.ExpressionValue;
import com.snk.jlinq.function.Function1;
import com.snk.jlinq.function.MemberAccessor;
import com.snk.jlinq.stream.expression.ExpressionBuilder;
import com.snk.jlinq.stream.expression.GotPartialExpression;
import com.snk.jlinq.stream.expression.InJoinExpressionExtender;
import com.snk.jlinq.stream.pipeline.StreamOp;

import java.util.function.Function;

public class InJoinExpectingOn<T1, T2, OT, JT extends InJoinExpressionExtender<OT, JT>> {
    private final StreamOp<T1, T1> original;
    private final StreamOp<T2, T2> join;
    private final Function<ExpressionBuilder<OT, OT, JT>, JT> joinConstructor;

    public InJoinExpectingOn(StreamOp<T1, T1> original, StreamOp<T2, T2> join, Function<ExpressionBuilder<OT, OT, JT>, JT> joinConstructor) {
        this.original = original;
        this.join = join;
        this.joinConstructor = joinConstructor;
    }

    public <IN, OUT> GotPartialExpression<OT, OT, OUT, JT> on(String alias, Function1<IN, OUT> mapper) {
        return on(ExpressionValue.fromExtractor(MemberAccessor.from(alias, mapper)));
    }

    public <IN, OUT> GotPartialExpression<OT, OT, OUT, JT> on(Function1<IN, OUT> mapper) {
        return on(ExpressionValue.fromExtractor(MemberAccessor.from(mapper)));
    }

    public <IN, OUT> GotPartialExpression<OT, OT, OUT, JT> on(ExpressionValue<OUT> expressionValue) {
        return GotPartialExpression.forJoin(original, join, expressionValue, (baseExpression) -> createJoinedStreamFromConditions(baseExpression));
    }

    private JT createJoinedStreamFromConditions(ExpressionBuilder<OT, OT, JT> baseExpression) {
        return joinConstructor.apply(baseExpression);
    }

}
