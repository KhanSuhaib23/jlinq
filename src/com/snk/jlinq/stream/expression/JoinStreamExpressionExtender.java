package com.snk.jlinq.stream.expression;

import com.snk.jlinq.data.Condition;
import com.snk.jlinq.data.ExpressionValue;
import com.snk.jlinq.function.Function1;
import com.snk.jlinq.function.MemberAccessor;
import com.snk.jlinq.stream.EnrichedStream;
import com.snk.jlinq.stream.JoinableJoin2Stream;
import com.snk.jlinq.stream.SelectableStream;
import com.snk.jlinq.stream.pipeline.StreamOp;
import com.snk.jlinq.tuple.Tuple2;

import java.util.stream.Stream;

public class JoinStreamExpressionExtender<T1, T2> extends JoinableJoin2Stream<T1, T2> implements ExpressionExtender<Tuple2<T1, T2>, JoinableJoin2Stream<T1, T2>, JoinStreamExpressionExtender<T1, T2>> {
    private final ExpressionBuilder<Tuple2<T1, T2>, JoinableJoin2Stream<T1, T2>> baseExpression;

    public JoinStreamExpressionExtender(ExpressionBuilder<Tuple2<T1, T2>, JoinableJoin2Stream<T1, T2>> baseExpression) {
        super(baseExpression.operatingStream());
        this.baseExpression = baseExpression;
    }

    public static <T1, T2> JoinStreamExpressionExtender<T1, T2> of(ExpressionBuilder<Tuple2<T1, T2>, JoinableJoin2Stream<T1, T2>> expressionBuilderConstructor) {
        return new JoinStreamExpressionExtender<>(expressionBuilderConstructor);
    }

    @Override
    public <IN, OUT> GotPartialExpression<Tuple2<T1, T2>, OUT, JoinStreamExpressionExtender<T1, T2>, JoinableJoin2Stream<T1, T2>> and(String alias, Function1<IN, OUT> mapper) {
        return and(ExpressionValue.fromExtractor(MemberAccessor.from(alias, mapper)));
    }

    @Override
    public <IN, OUT> GotPartialExpression<Tuple2<T1, T2>, OUT, JoinStreamExpressionExtender<T1, T2>, JoinableJoin2Stream<T1, T2>> and(Function1<IN, OUT> mapper) {
        return and(ExpressionValue.fromExtractor(MemberAccessor.from(mapper)));
    }

    @Override
    public <OUT> GotPartialExpression<Tuple2<T1, T2>, OUT, JoinStreamExpressionExtender<T1, T2>, JoinableJoin2Stream<T1, T2>> and(OUT value) {
        return and(ExpressionValue.fromScalar(value));
    }

    @Override
    public <IN, OUT> GotPartialExpression<Tuple2<T1, T2>, OUT, JoinStreamExpressionExtender<T1, T2>, JoinableJoin2Stream<T1, T2>> or(String alias, Function1<IN, OUT> mapper) {
        return or(ExpressionValue.fromExtractor(MemberAccessor.from(alias, mapper)));
    }

    @Override
    public <IN, OUT> GotPartialExpression<Tuple2<T1, T2>, OUT, JoinStreamExpressionExtender<T1, T2>, JoinableJoin2Stream<T1, T2>> or(Function1<IN, OUT> mapper) {
        return or(ExpressionValue.fromExtractor(MemberAccessor.from(mapper)));
    }

    @Override
    public <OUT> GotPartialExpression<Tuple2<T1, T2>, OUT, JoinStreamExpressionExtender<T1, T2>, JoinableJoin2Stream<T1, T2>> or(OUT value) {
        return or(ExpressionValue.fromScalar(value));
    }

    private <OUT> GotPartialExpression<Tuple2<T1, T2>, OUT, JoinStreamExpressionExtender<T1, T2>, JoinableJoin2Stream<T1, T2>> and(ExpressionValue<OUT> expressionValue) {
        return new GotPartialExpression<>(baseExpression, expressionValue, JoinStreamExpressionExtender::of, Condition::and);
    }

    private <OUT> GotPartialExpression<Tuple2<T1, T2>, OUT, JoinStreamExpressionExtender<T1, T2>, JoinableJoin2Stream<T1, T2>> or(ExpressionValue<OUT> expressionValue) {
        return new GotPartialExpression<>(baseExpression, expressionValue, JoinStreamExpressionExtender::of, Condition::or);
    }

    @Override
    protected Stream<Tuple2<T1, T2>> underlyingStream() {
        return outputStream().stream();
    }

    @Override
    public StreamOp<Tuple2<T1, T2>> operatingStream() {
        return baseExpression.outputStream().operatingStream();
    }

    @Override
    public EnrichedStream<Tuple2<T1, T2>> outputStream() {
        return operatingStream().outputStream();
    }
}
