package com.snk.jlinq.stream.expression;

import com.snk.jlinq.data.Condition;
import com.snk.jlinq.data.ExpressionValue;
import com.snk.jlinq.function.Function1;
import com.snk.jlinq.function.MemberAccessor;
import com.snk.jlinq.stream.EnrichedStream;
import com.snk.jlinq.stream.InWhereExpectingExpression;
import com.snk.jlinq.stream.SelectableStream;
import com.snk.jlinq.stream.pipeline.FilterStreamOp;
import com.snk.jlinq.stream.pipeline.StreamOp;

import java.util.stream.Stream;

public class InWhereExpressionExtender<T> extends SelectableStream<T> implements ExpressionExtender<T, InWhereExpectingExpression<T>, InWhereExpressionExtender<T>> {
    private final ExpressionBuilder<T, InWhereExpectingExpression<T>> baseExpression;

    public InWhereExpressionExtender(ExpressionBuilder<T, InWhereExpectingExpression<T>> baseExpression) {
        super(baseExpression.operatingStream());
        this.baseExpression = baseExpression;
    }

    public static <T> InWhereExpressionExtender<T> of(ExpressionBuilder<T, InWhereExpectingExpression<T>> expressionBuilderConstructor) {
        return new InWhereExpressionExtender<>(expressionBuilderConstructor);
    }

    @Override
    public <IN, OUT> GotPartialExpression<T, OUT, InWhereExpressionExtender<T>, InWhereExpectingExpression<T>> and(String alias, Function1<IN, OUT> mapper) {
        return and(ExpressionValue.fromExtractor(MemberAccessor.from(alias, mapper)));
    }

    @Override
    public <IN, OUT> GotPartialExpression<T, OUT, InWhereExpressionExtender<T>, InWhereExpectingExpression<T>> and(Function1<IN, OUT> mapper) {
        return and(ExpressionValue.fromExtractor(MemberAccessor.from(mapper)));
    }

    @Override
    public <OUT> GotPartialExpression<T, OUT, InWhereExpressionExtender<T>, InWhereExpectingExpression<T>> and(OUT value) {
        return and(ExpressionValue.fromScalar(value));
    }

    @Override
    public <IN, OUT> GotPartialExpression<T, OUT, InWhereExpressionExtender<T>, InWhereExpectingExpression<T>> or(String alias, Function1<IN, OUT> mapper) {
        return or(ExpressionValue.fromExtractor(MemberAccessor.from(alias, mapper)));
    }

    @Override
    public <IN, OUT> GotPartialExpression<T, OUT, InWhereExpressionExtender<T>, InWhereExpectingExpression<T>> or(Function1<IN, OUT> mapper) {
        return or(ExpressionValue.fromExtractor(MemberAccessor.from(mapper)));
    }

    @Override
    public <OUT> GotPartialExpression<T, OUT, InWhereExpressionExtender<T>, InWhereExpectingExpression<T>> or(OUT value) {
        return or(ExpressionValue.fromScalar(value));
    }

    private <OUT> GotPartialExpression<T, OUT, InWhereExpressionExtender<T>, InWhereExpectingExpression<T>> and(ExpressionValue<OUT> expressionValue) {
        return new GotPartialExpression<>(baseExpression, expressionValue, Condition::and);
    }

    private <OUT> GotPartialExpression<T, OUT, InWhereExpressionExtender<T>, InWhereExpectingExpression<T>> or(ExpressionValue<OUT> expressionValue) {
        return new GotPartialExpression<>(baseExpression, expressionValue, Condition::or);
    }

    @Override
    public StreamOp<T> operatingStream() {
        return new FilterStreamOp<>(baseExpression.operatingStream(), baseExpression.condition());
    }
}
