package com.snk.jlinq.stream.expression;

import com.snk.jlinq.data.Condition;
import com.snk.jlinq.data.ExpressionValue;
import com.snk.jlinq.function.Function1;
import com.snk.jlinq.function.MemberAccessor;
import com.snk.jlinq.stream.EnrichedStream;
import com.snk.jlinq.stream.InJoinExpectingOn;
import com.snk.jlinq.stream.JoinableStream;
import com.snk.jlinq.stream.pipeline.StreamOp;

import java.util.stream.Stream;

public class JoinStreamExpressionExtender<T1, T2, OT, JT extends JoinableStream<T1, T2, OT, JT, IJ>, IJ extends InJoinExpectingOn<T1, T2, OT, IJ, JT>>
        extends JoinableStream<T1, T2, OT, JT, IJ> implements ExpressionExtender<OT, JT, JoinStreamExpressionExtender<T1, T2, OT, JT, IJ>> {
    private final ExpressionBuilder<OT, JT> baseExpression;

    public JoinStreamExpressionExtender(ExpressionBuilder<OT, JT> baseExpression) {
        super(baseExpression.operatingStream());
        this.baseExpression = baseExpression;
    }

    public static <T1, T2, OT, JT extends JoinableStream<T1, T2, OT, JT, IJ>, IJ extends InJoinExpectingOn<T1, T2, OT, IJ, JT>, EX extends ExpressionExtender<OT, JT, EX>> EX of(ExpressionBuilder<OT, JT> expressionBuilderConstructor) {
        return (EX) (new JoinStreamExpressionExtender<>(expressionBuilderConstructor));
    }

    @Override
    public <IN, OUT> GotPartialExpression<OT, OUT, JoinStreamExpressionExtender<T1, T2, OT, JT, IJ>, JT> and(String alias, Function1<IN, OUT> mapper) {
        return and(ExpressionValue.fromExtractor(MemberAccessor.from(alias, mapper)));
    }

    @Override
    public <IN, OUT> GotPartialExpression<OT, OUT, JoinStreamExpressionExtender<T1, T2, OT, JT, IJ>, JT> and(Function1<IN, OUT> mapper) {
        return and(ExpressionValue.fromExtractor(MemberAccessor.from(mapper)));
    }

    @Override
    public <OUT> GotPartialExpression<OT, OUT, JoinStreamExpressionExtender<T1, T2, OT, JT, IJ>, JT> and(OUT value) {
        return and(ExpressionValue.fromScalar(value));
    }

    @Override
    public <IN, OUT> GotPartialExpression<OT, OUT, JoinStreamExpressionExtender<T1, T2, OT, JT, IJ>, JT> or(String alias, Function1<IN, OUT> mapper) {
        return or(ExpressionValue.fromExtractor(MemberAccessor.from(alias, mapper)));
    }

    @Override
    public <IN, OUT> GotPartialExpression<OT, OUT, JoinStreamExpressionExtender<T1, T2, OT, JT, IJ>, JT> or(Function1<IN, OUT> mapper) {
        return or(ExpressionValue.fromExtractor(MemberAccessor.from(mapper)));
    }

    @Override
    public <OUT> GotPartialExpression<OT, OUT, JoinStreamExpressionExtender<T1, T2, OT, JT, IJ>, JT> or(OUT value) {
        return or(ExpressionValue.fromScalar(value));
    }

    private <OUT> GotPartialExpression<OT, OUT, JoinStreamExpressionExtender<T1, T2, OT, JT, IJ>, JT> and(ExpressionValue<OUT> expressionValue) {
        return new GotPartialExpression<>(baseExpression, expressionValue, JoinStreamExpressionExtender::of, Condition::and);
    }

    private <OUT> GotPartialExpression<OT, OUT, JoinStreamExpressionExtender<T1, T2, OT, JT, IJ>, JT> or(ExpressionValue<OUT> expressionValue) {
        return new GotPartialExpression<>(baseExpression, expressionValue, JoinStreamExpressionExtender::of, Condition::or);
    }

    @Override
    protected Stream<OT> underlyingStream() {
        return outputStream().stream();
    }

    @Override
    public StreamOp<OT> operatingStream() {
        return baseExpression.outputStream().operatingStream();
    }

    @Override
    public EnrichedStream<OT> outputStream() {
        return operatingStream().outputStream();
    }


}
