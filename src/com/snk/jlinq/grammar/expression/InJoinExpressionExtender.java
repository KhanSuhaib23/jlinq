package com.snk.jlinq.grammar.expression;

import com.snk.jlinq.function.ConditionBuilder;
import com.snk.jlinq.function.Function1;
import com.snk.jlinq.grammar.group.ExpectingGroupBy;
import com.snk.jlinq.stream.MemberAccessor;
import com.snk.jlinq.stream.expression.ExpressionValue;

public class InJoinExpressionExtender<OutputType, ExpectingJoinType extends InJoinExpressionExtender<OutputType, ExpectingJoinType>>
        extends ExpectingGroupBy<OutputType>
        implements ExpressionExtender<OutputType, OutputType, ExpectingJoinType,
                                        InJoinExpressionExtender<OutputType, ExpectingJoinType>> {
    protected final ExpressionBuilder<OutputType, OutputType, ExpectingJoinType> baseExpression;

    public InJoinExpressionExtender(ExpressionBuilder<OutputType, OutputType, ExpectingJoinType> baseExpression) {
        super(baseExpression.operatingStream());
        this.baseExpression = baseExpression;
    }

    public static <OT, JT extends InJoinExpressionExtender<OT, JT>> InJoinExpressionExtender<OT, JT> of(ExpressionBuilder<OT, OT, JT> baseExpression) {
        return new InJoinExpressionExtender<>(baseExpression);
    }

    @Override
    public <IN, OUT> GotPartialExpression<OutputType, OutputType, OUT, ExpectingJoinType> and(String alias, Function1<IN, OUT> mapper) {
        return and(ExpressionValue.fromExtractor(MemberAccessor.from(alias, mapper)));
    }

    @Override
    public <IN, OUT> GotPartialExpression<OutputType, OutputType, OUT, ExpectingJoinType> and(Function1<IN, OUT> mapper) {
        return and(ExpressionValue.fromExtractor(MemberAccessor.from(mapper)));
    }

    @Override
    public <OUT> GotPartialExpression<OutputType, OutputType, OUT, ExpectingJoinType> and(OUT value) {
        return and(ExpressionValue.fromScalar(value));
    }

    @Override
    public <IN, OUT> GotPartialExpression<OutputType, OutputType, OUT, ExpectingJoinType> or(String alias, Function1<IN, OUT> mapper) {
        return or(ExpressionValue.fromExtractor(MemberAccessor.from(alias, mapper)));
    }

    @Override
    public <IN, OUT> GotPartialExpression<OutputType, OutputType, OUT, ExpectingJoinType> or(Function1<IN, OUT> mapper) {
        return or(ExpressionValue.fromExtractor(MemberAccessor.from(mapper)));
    }

    @Override
    public <OUT> GotPartialExpression<OutputType, OutputType, OUT, ExpectingJoinType> or(OUT value) {
        return or(ExpressionValue.fromScalar(value));
    }

    private <OUT> GotPartialExpression<OutputType, OutputType, OUT, ExpectingJoinType> and(ExpressionValue<OUT> expressionValue) {
        return GotPartialExpression.extendExpression(baseExpression, expressionValue, ConditionBuilder.AND);
    }

    private <OUT> GotPartialExpression<OutputType, OutputType, OUT, ExpectingJoinType> or(ExpressionValue<OUT> expressionValue) {
        return GotPartialExpression.extendExpression(baseExpression, expressionValue, ConditionBuilder.OR);
    }

}
