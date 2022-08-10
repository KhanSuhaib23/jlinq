package com.snk.jlinq.stream.expression;

import com.snk.jlinq.data.Condition;
import com.snk.jlinq.data.ExpressionValue;
import com.snk.jlinq.function.Function1;
import com.snk.jlinq.function.MemberAccessor;
import com.snk.jlinq.stream.EnrichedStream;
import com.snk.jlinq.stream.SelectableStream;
import com.snk.jlinq.stream.pipeline.StreamOp;

import java.util.stream.Stream;

public class JoinStreamExpressionExtender<T> extends SelectableStream<T> implements ExpressionExtender<T, SelectableStream<T>, JoinStreamExpressionExtender<T>> {
    private final ExpressionBuilder<T, SelectableStream<T>> baseExpression;

    public JoinStreamExpressionExtender(ExpressionBuilder<T, SelectableStream<T>> baseExpression) {
        super(baseExpression.operatingStream());
        this.baseExpression = baseExpression;
    }

    public static <T> JoinStreamExpressionExtender<T> of(ExpressionBuilder<T, SelectableStream<T>> expressionBuilderConstructor) {
        return new JoinStreamExpressionExtender<>(expressionBuilderConstructor);
    }

    @Override
    public <IN, OUT> GotPartialExpression<T, OUT, JoinStreamExpressionExtender<T>, SelectableStream<T>> and(String alias, Function1<IN, OUT> mapper) {
        return and(ExpressionValue.fromExtractor(MemberAccessor.from(alias, mapper)));
    }

    @Override
    public <IN, OUT> GotPartialExpression<T, OUT, JoinStreamExpressionExtender<T>, SelectableStream<T>> and(Function1<IN, OUT> mapper) {
        return and(ExpressionValue.fromExtractor(MemberAccessor.from(mapper)));
    }

    @Override
    public <OUT> GotPartialExpression<T, OUT, JoinStreamExpressionExtender<T>, SelectableStream<T>> and(OUT value) {
        return and(ExpressionValue.fromScalar(value));
    }

    @Override
    public <IN, OUT> GotPartialExpression<T, OUT, JoinStreamExpressionExtender<T>, SelectableStream<T>> or(String alias, Function1<IN, OUT> mapper) {
        return or(ExpressionValue.fromExtractor(MemberAccessor.from(alias, mapper)));
    }

    @Override
    public <IN, OUT> GotPartialExpression<T, OUT, JoinStreamExpressionExtender<T>, SelectableStream<T>> or(Function1<IN, OUT> mapper) {
        return or(ExpressionValue.fromExtractor(MemberAccessor.from(mapper)));
    }

    @Override
    public <OUT> GotPartialExpression<T, OUT, JoinStreamExpressionExtender<T>, SelectableStream<T>> or(OUT value) {
        return or(ExpressionValue.fromScalar(value));
    }

    private <OUT> GotPartialExpression<T, OUT, JoinStreamExpressionExtender<T>, SelectableStream<T>> and(ExpressionValue<OUT> expressionValue) {
        return new GotPartialExpression<>(baseExpression, expressionValue, JoinStreamExpressionExtender::of, Condition::and);
    }

    private <OUT> GotPartialExpression<T, OUT, JoinStreamExpressionExtender<T>, SelectableStream<T>> or(ExpressionValue<OUT> expressionValue) {
        return new GotPartialExpression<>(baseExpression, expressionValue, JoinStreamExpressionExtender::of, Condition::or);
    }

    @Override
    protected Stream<T> underlyingStream() {
        return outputStream().stream();
    }

    @Override
    public StreamOp<T> operatingStream() {
        return baseExpression.outputStream().operatingStream();
    }

    @Override
    public EnrichedStream<T> outputStream() {
        return operatingStream().outputStream();
    }
}
