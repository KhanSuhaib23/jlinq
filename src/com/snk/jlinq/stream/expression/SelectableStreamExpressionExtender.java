package com.snk.jlinq.stream.expression;

import com.snk.jlinq.data.ConditionValue;
import com.snk.jlinq.function.Function1;
import com.snk.jlinq.function.MemberAccessor;
import com.snk.jlinq.stream.EnrichedStream;
import com.snk.jlinq.stream.SelectStream;
import com.snk.jlinq.stream.SelectableStream;

import java.util.stream.Stream;

public class SelectableStreamExpressionExtender<T> extends SelectableStream<T> implements ExpressionExtender<T, SelectableStream<T>, SelectableStreamExpressionExtender<T>> {
    private final ExpressionBuilder<T, SelectableStream<T>> baseExpression;

    public SelectableStreamExpressionExtender(ExpressionBuilder<T, SelectableStream<T>> baseExpression) {
        super(baseExpression.baseStream());
        this.baseExpression = baseExpression;
    }

    public static <T> SelectableStreamExpressionExtender<T> of(ExpressionBuilder<T, SelectableStream<T>> expressionBuilderConstructor) {
        return new SelectableStreamExpressionExtender<>(expressionBuilderConstructor);
    }

    @Override
    public <IN, OUT> GotPartialExpression<T, OUT, SelectableStreamExpressionExtender<T>, SelectableStream<T>> and(String alias, Function1<IN, OUT> mapper) {
        return and(ConditionValue.fromExtractor(MemberAccessor.from(alias, mapper)));
    }

    @Override
    public <IN, OUT> GotPartialExpression<T, OUT, SelectableStreamExpressionExtender<T>, SelectableStream<T>> and(Function1<IN, OUT> mapper) {
        return and(ConditionValue.fromExtractor(MemberAccessor.from(mapper)));
    }

    @Override
    public <OUT> GotPartialExpression<T, OUT, SelectableStreamExpressionExtender<T>, SelectableStream<T>> and(OUT value) {
        return and(ConditionValue.fromScalar(value));
    }

    private <OUT> GotPartialExpression<T, OUT, SelectableStreamExpressionExtender<T>, SelectableStream<T>> and(ConditionValue<OUT> conditionValue) {
        return new GotPartialExpression<>(baseExpression, conditionValue, SelectableStreamExpressionExtender::of);
    }

    @Override
    protected Stream<T> underlyingStream() {
        return outputStream().stream();
    }

    @Override
    public SelectStream<T> selectStream() {
        return baseExpression.outputStream();
    }

    @Override
    public EnrichedStream<T> outputStream() {
        return baseExpression.outputStream().outputStream();
    }
}
