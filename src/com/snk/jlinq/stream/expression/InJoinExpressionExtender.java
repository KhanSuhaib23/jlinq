package com.snk.jlinq.stream.expression;

import com.snk.jlinq.data.Condition;
import com.snk.jlinq.data.ExpressionValue;
import com.snk.jlinq.function.Function1;
import com.snk.jlinq.function.MemberAccessor;
import com.snk.jlinq.stream.EnrichedStream;
import com.snk.jlinq.stream.ExpectingGroupBy;
import com.snk.jlinq.stream.FilterableStream;
import com.snk.jlinq.stream.SortableStream;
import com.snk.jlinq.stream.pipeline.CombinedStreamOp;
import com.snk.jlinq.stream.pipeline.FilterStreamOp;
import com.snk.jlinq.stream.pipeline.JoinStreamOp;
import com.snk.jlinq.stream.pipeline.StreamOp;

import java.util.stream.Stream;

public class InJoinExpressionExtender<OT, JT extends InJoinExpressionExtender<OT, JT>>
        extends ExpectingGroupBy<OT> implements ExpressionExtender<OT, JT, InJoinExpressionExtender<OT, JT>> {
    protected final ExpressionBuilder<OT, JT> baseExpression;

    public InJoinExpressionExtender(ExpressionBuilder<OT, JT> baseExpression) {
        super(baseExpression.operatingStream());
        this.baseExpression = baseExpression;
    }

    public static <OT, JT extends InJoinExpressionExtender<OT, JT>> InJoinExpressionExtender<OT, JT> of(ExpressionBuilder<OT, JT> baseExpression) {
        return new InJoinExpressionExtender<>(baseExpression);
    }

    @Override
    public <IN, OUT> GotPartialExpression<OT, OUT, InJoinExpressionExtender<OT, JT>, JT> and(String alias, Function1<IN, OUT> mapper) {
        return and(ExpressionValue.fromExtractor(MemberAccessor.from(alias, mapper)));
    }

    @Override
    public <IN, OUT> GotPartialExpression<OT, OUT, InJoinExpressionExtender<OT, JT>, JT> and(Function1<IN, OUT> mapper) {
        return and(ExpressionValue.fromExtractor(MemberAccessor.from(mapper)));
    }

    @Override
    public <OUT> GotPartialExpression<OT, OUT, InJoinExpressionExtender<OT, JT>, JT> and(OUT value) {
        return and(ExpressionValue.fromScalar(value));
    }

    @Override
    public <IN, OUT> GotPartialExpression<OT, OUT, InJoinExpressionExtender<OT, JT>, JT> or(String alias, Function1<IN, OUT> mapper) {
        return or(ExpressionValue.fromExtractor(MemberAccessor.from(alias, mapper)));
    }

    @Override
    public <IN, OUT> GotPartialExpression<OT, OUT, InJoinExpressionExtender<OT, JT>, JT> or(Function1<IN, OUT> mapper) {
        return or(ExpressionValue.fromExtractor(MemberAccessor.from(mapper)));
    }

    @Override
    public <OUT> GotPartialExpression<OT, OUT, InJoinExpressionExtender<OT, JT>, JT> or(OUT value) {
        return or(ExpressionValue.fromScalar(value));
    }

    private <OUT> GotPartialExpression<OT, OUT, InJoinExpressionExtender<OT, JT>, JT> and(ExpressionValue<OUT> expressionValue) {
        return new GotPartialExpression<>(baseExpression, expressionValue, Condition::and);
    }

    private <OUT> GotPartialExpression<OT, OUT, InJoinExpressionExtender<OT, JT>, JT> or(ExpressionValue<OUT> expressionValue) {
        return new GotPartialExpression<>(baseExpression, expressionValue, Condition::or);
    }

}
