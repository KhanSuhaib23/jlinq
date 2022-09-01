package com.snk.jlinq.grammar.join;

import com.snk.jlinq.function.Function1;
import com.snk.jlinq.grammar.expression.ExpressionBuilder;
import com.snk.jlinq.grammar.expression.GotPartialExpression;
import com.snk.jlinq.grammar.expression.InJoinExpressionExtender;
import com.snk.jlinq.stream.DataSelector;
import com.snk.jlinq.stream.expression.ExpressionValue;
import com.snk.jlinq.stream.operation.StreamOp;

import java.util.function.Function;

public class InJoinExpectingOn<T1, T2, OutputType, JoinExtendType extends InJoinExpressionExtender<OutputType, JoinExtendType>> {
    private final StreamOp<T1, T1> original;
    private final StreamOp<T2, T2> join;
    private final Function<ExpressionBuilder<OutputType, OutputType, JoinExtendType>, JoinExtendType> joinConstructor;

    public InJoinExpectingOn(StreamOp<T1, T1> original, StreamOp<T2, T2> join, Function<ExpressionBuilder<OutputType, OutputType, JoinExtendType>, JoinExtendType> joinConstructor) {
        this.original = original;
        this.join = join;
        this.joinConstructor = joinConstructor;
    }

    public <IN, OUT> GotPartialExpression<OutputType, OutputType, OUT, JoinExtendType> on(String alias, Function1<IN, OUT> mapper) {
        return on(ExpressionValue.fromExtractor(DataSelector.from(alias, mapper)));
    }

    public <IN, OUT> GotPartialExpression<OutputType, OutputType, OUT, JoinExtendType> on(Function1<IN, OUT> mapper) {
        return on(ExpressionValue.fromExtractor(DataSelector.from(mapper)));
    }

    public <OUT> GotPartialExpression<OutputType, OutputType, OUT, JoinExtendType> on(ExpressionValue<OUT> expressionValue) {
        return GotPartialExpression.forJoin(original, join, expressionValue, (baseExpression) -> createJoinedStreamFromConditions(baseExpression));
    }

    private JoinExtendType createJoinedStreamFromConditions(ExpressionBuilder<OutputType, OutputType, JoinExtendType> baseExpression) {
        return joinConstructor.apply(baseExpression);
    }

}
