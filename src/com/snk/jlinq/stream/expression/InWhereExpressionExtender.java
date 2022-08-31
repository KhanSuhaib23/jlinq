package com.snk.jlinq.stream.expression;

import com.snk.jlinq.data.Condition;
import com.snk.jlinq.data.ExpressionValue;
import com.snk.jlinq.function.Function1;
import com.snk.jlinq.function.MemberAccessor;
import com.snk.jlinq.stream.InWhereExpectingExpression;
import com.snk.jlinq.stream.ExpectingSelect;
import com.snk.jlinq.stream.pipeline.FilterStreamOp;
import com.snk.jlinq.stream.pipeline.StreamOp;

public class InWhereExpressionExtender<GroupedType, OriginalType> extends ExpectingSelect<GroupedType, OriginalType>
        implements ExpressionExtender<GroupedType, OriginalType, InWhereExpectingExpression<GroupedType, OriginalType>,
        InWhereExpressionExtender<GroupedType, OriginalType>> {
    
    private final ExpressionBuilder<GroupedType, OriginalType, InWhereExpectingExpression<GroupedType, OriginalType>> baseExpression;

    public InWhereExpressionExtender(ExpressionBuilder<GroupedType, OriginalType, InWhereExpectingExpression<GroupedType, OriginalType>> baseExpression) {
        super(baseExpression.operatingStream());
        this.baseExpression = baseExpression;
    }

    public static <GT, OT> InWhereExpressionExtender<GT, OT> of(ExpressionBuilder<GT, OT, InWhereExpectingExpression<GT, OT>> expressionBuilderConstructor) {
        return new InWhereExpressionExtender<>(expressionBuilderConstructor);
    }

    @Override
    public <IN, OUT> GotPartialExpression<GroupedType, OriginalType, OUT, InWhereExpectingExpression<GroupedType, OriginalType>> and(String alias, Function1<IN, OUT> mapper) {
        return and(ExpressionValue.fromExtractor(MemberAccessor.from(alias, mapper)));
    }

    @Override
    public <IN, OUT> GotPartialExpression<GroupedType, OriginalType, OUT, InWhereExpectingExpression<GroupedType, OriginalType>> and(Function1<IN, OUT> mapper) {
        return and(ExpressionValue.fromExtractor(MemberAccessor.from(mapper)));
    }

    @Override
    public <OUT> GotPartialExpression<GroupedType, OriginalType, OUT, InWhereExpectingExpression<GroupedType, OriginalType>> and(OUT value) {
        return and(ExpressionValue.fromScalar(value));
    }

    @Override
    public <IN, OUT> GotPartialExpression<GroupedType, OriginalType, OUT, InWhereExpectingExpression<GroupedType, OriginalType>> or(String alias, Function1<IN, OUT> mapper) {
        return or(ExpressionValue.fromExtractor(MemberAccessor.from(alias, mapper)));
    }

    @Override
    public <IN, OUT> GotPartialExpression<GroupedType, OriginalType, OUT, InWhereExpectingExpression<GroupedType, OriginalType>> or(Function1<IN, OUT> mapper) {
        return or(ExpressionValue.fromExtractor(MemberAccessor.from(mapper)));
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
