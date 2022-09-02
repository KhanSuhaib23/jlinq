package com.snk.jlinq.grammar.expression;

import com.snk.jlinq.function.Function1;
import com.snk.jlinq.grammar.ExpectingSelect;
import com.snk.jlinq.grammar.InWhereExpectingExpression;
import com.snk.jlinq.stream.DataSelector;
import com.snk.jlinq.stream.expression.Condition;
import com.snk.jlinq.stream.expression.ExpressionValue;
import com.snk.jlinq.stream.operation.FilterStreamOp;
import com.snk.jlinq.stream.operation.StreamOp;

public class InWhereExpressionExtender<GroupedType, OriginalType> extends ExpectingSelect<GroupedType, OriginalType>
        implements ExpressionExtender<GroupedType, OriginalType, InWhereExpectingExpression<GroupedType, OriginalType>,
        InWhereExpressionExtender<GroupedType, OriginalType>> {
    
    private final ExpressionBuilder<GroupedType, OriginalType, InWhereExpectingExpression<GroupedType, OriginalType>> baseExpression;

    public InWhereExpressionExtender(ExpressionBuilder<GroupedType, OriginalType, InWhereExpectingExpression<GroupedType, OriginalType>> baseExpression) {
        super(baseExpression.operatingStream());
        this.baseExpression = baseExpression;
    }

    public static <GroupedType, OriginalType> InWhereExpressionExtender<GroupedType, OriginalType> of(ExpressionBuilder<GroupedType, OriginalType, InWhereExpectingExpression<GroupedType, OriginalType>> expressionBuilderConstructor) {
        return new InWhereExpressionExtender<>(expressionBuilderConstructor);
    }

    @Override
    public <IN, OUT> GotPartialExpression<GroupedType, OriginalType, OUT, InWhereExpectingExpression<GroupedType, OriginalType>> and(String alias, Function1<IN, OUT> mapper) {
        return and(ExpressionValue.fromExtractor(DataSelector.from(alias, mapper)));
    }

    @Override
    public <IN, OUT> GotPartialExpression<GroupedType, OriginalType, OUT, InWhereExpectingExpression<GroupedType, OriginalType>> and(Function1<IN, OUT> mapper) {
        return and(ExpressionValue.fromExtractor(DataSelector.from(mapper)));
    }

    @Override
    public <OUT> GotPartialExpression<GroupedType, OriginalType, OUT, InWhereExpectingExpression<GroupedType, OriginalType>> and(OUT value) {
        return and(ExpressionValue.fromScalar(value));
    }

    @Override
    public <IN, OUT> GotPartialExpression<GroupedType, OriginalType, OUT, InWhereExpectingExpression<GroupedType, OriginalType>> or(String alias, Function1<IN, OUT> mapper) {
        return or(ExpressionValue.fromExtractor(DataSelector.from(alias, mapper)));
    }

    @Override
    public <IN, OUT> GotPartialExpression<GroupedType, OriginalType, OUT, InWhereExpectingExpression<GroupedType, OriginalType>> or(Function1<IN, OUT> mapper) {
        return or(ExpressionValue.fromExtractor(DataSelector.from(mapper)));
    }

    @Override
    public <OUT> GotPartialExpression<GroupedType, OriginalType, OUT, InWhereExpectingExpression<GroupedType, OriginalType>> or(OUT value) {
        return or(ExpressionValue.fromScalar(value));
    }

    private <OUT> GotPartialExpression<GroupedType, OriginalType, OUT, InWhereExpectingExpression<GroupedType, OriginalType>> and(ExpressionValue<OUT> expressionValue) {
        return GotPartialExpression.extendExpression(baseExpression, expressionValue, Condition::and);
    }

    private <OUT> GotPartialExpression<GroupedType, OriginalType, OUT, InWhereExpectingExpression<GroupedType, OriginalType>> or(ExpressionValue<OUT> expressionValue) {
        return GotPartialExpression.extendExpression(baseExpression, expressionValue, Condition::or);
    }

    @Override
    public StreamOp<GroupedType, OriginalType> operatingStream() {
        return new FilterStreamOp<>(baseExpression.operatingStream(), baseExpression.condition());
    }
}
