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

public class InWhereExpressionExtender<GT, OT> extends SelectableStream<GT, OT> implements ExpressionExtender<GT, OT, InWhereExpectingExpression<GT, OT>, InWhereExpressionExtender<GT, OT>> {
    private final ExpressionBuilder<GT, OT, InWhereExpectingExpression<GT, OT>> baseExpression;

    public InWhereExpressionExtender(ExpressionBuilder<GT, OT, InWhereExpectingExpression<GT, OT>> baseExpression) {
        super(baseExpression.operatingStream());
        this.baseExpression = baseExpression;
    }

    public static <GT, OT> InWhereExpressionExtender<GT, OT> of(ExpressionBuilder<GT, OT, InWhereExpectingExpression<GT, OT>> expressionBuilderConstructor) {
        return new InWhereExpressionExtender<>(expressionBuilderConstructor);
    }

    @Override
    public <IN, OUT> GotPartialExpression<GT, OT, OUT, InWhereExpressionExtender<GT, OT>, InWhereExpectingExpression<GT, OT>> and(String alias, Function1<IN, OUT> mapper) {
        return and(ExpressionValue.fromExtractor(MemberAccessor.from(alias, mapper)));
    }

    @Override
    public <IN, OUT> GotPartialExpression<GT, OT, OUT, InWhereExpressionExtender<GT, OT>, InWhereExpectingExpression<GT, OT>> and(Function1<IN, OUT> mapper) {
        return and(ExpressionValue.fromExtractor(MemberAccessor.from(mapper)));
    }

    @Override
    public <OUT> GotPartialExpression<GT, OT, OUT, InWhereExpressionExtender<GT, OT>, InWhereExpectingExpression<GT, OT>> and(OUT value) {
        return and(ExpressionValue.fromScalar(value));
    }

    @Override
    public <IN, OUT> GotPartialExpression<GT, OT, OUT, InWhereExpressionExtender<GT, OT>, InWhereExpectingExpression<GT, OT>> or(String alias, Function1<IN, OUT> mapper) {
        return or(ExpressionValue.fromExtractor(MemberAccessor.from(alias, mapper)));
    }

    @Override
    public <IN, OUT> GotPartialExpression<GT, OT, OUT, InWhereExpressionExtender<GT, OT>, InWhereExpectingExpression<GT, OT>> or(Function1<IN, OUT> mapper) {
        return or(ExpressionValue.fromExtractor(MemberAccessor.from(mapper)));
    }

    @Override
    public <OUT> GotPartialExpression<GT, OT, OUT, InWhereExpressionExtender<GT, OT>, InWhereExpectingExpression<GT, OT>> or(OUT value) {
        return or(ExpressionValue.fromScalar(value));
    }

    private <OUT> GotPartialExpression<GT, OT, OUT, InWhereExpressionExtender<GT, OT>, InWhereExpectingExpression<GT, OT>> and(ExpressionValue<OUT> expressionValue) {
        return new GotPartialExpression<>(baseExpression, expressionValue, Condition::and);
    }

    private <OUT> GotPartialExpression<GT, OT, OUT, InWhereExpressionExtender<GT, OT>, InWhereExpectingExpression<GT, OT>> or(ExpressionValue<OUT> expressionValue) {
        return new GotPartialExpression<>(baseExpression, expressionValue, Condition::or);
    }

    @Override
    public StreamOp<GT, OT> operatingStream() {
        return new FilterStreamOp<>(baseExpression.operatingStream(), baseExpression.condition());
    }
}
