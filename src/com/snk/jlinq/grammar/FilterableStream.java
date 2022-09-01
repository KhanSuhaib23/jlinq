package com.snk.jlinq.grammar;

import com.snk.jlinq.function.Function1;
import com.snk.jlinq.grammar.expression.ExpressionBuilder;
import com.snk.jlinq.grammar.expression.GotPartialExpression;
import com.snk.jlinq.stream.MemberAccessor;
import com.snk.jlinq.stream.expression.ExpressionValue;
import com.snk.jlinq.stream.operation.StreamOp;

public class FilterableStream<GT, OT> extends ExpectingSelect<GT, OT> {

    public FilterableStream(StreamOp<GT, OT> operatingStream) {
        super(operatingStream);
    }

    public <IN, OUT> GotPartialExpression<GT, OT, OUT, InWhereExpectingExpression<GT, OT>> where(String alias, Function1<IN, OUT> mapper) {
        return where(ExpressionValue.fromExtractor(MemberAccessor.from(alias, mapper)));
    }

    public <IN, OUT> GotPartialExpression<GT, OT, OUT, InWhereExpectingExpression<GT, OT>> where(Function1<IN, OUT> mapper) {
        return where(ExpressionValue.fromExtractor(MemberAccessor.from(mapper)));
    }

    public <OUT> GotPartialExpression<GT, OT, OUT, InWhereExpectingExpression<GT, OT>> where(OUT value) {
        return where(ExpressionValue.fromScalar(value));
    }

    private <OUT> GotPartialExpression<GT, OT, OUT, InWhereExpectingExpression<GT, OT>> where(ExpressionValue<OUT> expressionValue) {
        return GotPartialExpression.forSelect(operatingStream(), expressionValue, this::createStreamFromFilters);
    }

    private InWhereExpectingExpression<GT, OT> createStreamFromFilters(ExpressionBuilder<GT, OT, InWhereExpectingExpression<GT, OT>> baseExpression) {
        return new InWhereExpectingExpression<>(baseExpression);
    }
}
