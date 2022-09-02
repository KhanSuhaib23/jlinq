package com.snk.jlinq.grammar;

import com.snk.jlinq.function.Function1;
import com.snk.jlinq.grammar.expression.ExpressionBuilder;
import com.snk.jlinq.grammar.expression.GotPartialExpression;
import com.snk.jlinq.stream.DataSelector;
import com.snk.jlinq.stream.expression.ExpressionValue;
import com.snk.jlinq.stream.operation.StreamOp;

public class FilterableStream<GroupedType, OriginalType> extends ExpectingSelect<GroupedType, OriginalType> {

    public FilterableStream(StreamOp<GroupedType, OriginalType> operatingStream) {
        super(operatingStream);
    }

    public <IN, OUT> GotPartialExpression<GroupedType, OriginalType, OUT, InWhereExpectingExpression<GroupedType, OriginalType>> where(String alias, Function1<IN, OUT> mapper) {
        return where(ExpressionValue.fromExtractor(DataSelector.from(alias, mapper)));
    }

    public <IN, OUT> GotPartialExpression<GroupedType, OriginalType, OUT, InWhereExpectingExpression<GroupedType, OriginalType>> where(Function1<IN, OUT> mapper) {
        return where(ExpressionValue.fromExtractor(DataSelector.from(mapper)));
    }

    public <OUT> GotPartialExpression<GroupedType, OriginalType, OUT, InWhereExpectingExpression<GroupedType, OriginalType>> where(OUT value) {
        return where(ExpressionValue.fromScalar(value));
    }

    private <OUT> GotPartialExpression<GroupedType, OriginalType, OUT, InWhereExpectingExpression<GroupedType, OriginalType>> where(ExpressionValue<OUT> expressionValue) {
        return GotPartialExpression.forSelect(operatingStream(), expressionValue, this::createStreamFromFilters);
    }

    private InWhereExpectingExpression<GroupedType, OriginalType> createStreamFromFilters(ExpressionBuilder<GroupedType, OriginalType, InWhereExpectingExpression<GroupedType, OriginalType>> baseExpression) {
        return new InWhereExpectingExpression<>(baseExpression);
    }
}
