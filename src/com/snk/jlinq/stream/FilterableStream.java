package com.snk.jlinq.stream;

import com.snk.jlinq.data.Condition;
import com.snk.jlinq.data.ExpressionValue;
import com.snk.jlinq.function.Function1;
import com.snk.jlinq.function.MemberAccessor;
import com.snk.jlinq.stream.expression.ExpressionBuilder;
import com.snk.jlinq.stream.expression.GotPartialExpression;
import com.snk.jlinq.stream.expression.InJoinExpressionExtender;
import com.snk.jlinq.stream.expression.InWhereExpressionExtender;
import com.snk.jlinq.stream.pipeline.FilterStreamOp;
import com.snk.jlinq.stream.pipeline.StreamOp;
import com.snk.jlinq.tuple.Tuple0;

public class FilterableStream<GT, OT> extends SelectableStream<GT, OT> {

    public FilterableStream(StreamOp<GT, OT> operatingStream) {
        super(operatingStream);
    }

    public <IN, OUT> GotPartialExpression<GT, OT, OUT, InWhereExpressionExtender<GT, OT>, InWhereExpectingExpression<GT, OT>> where(String alias, Function1<IN, OUT> mapper) {
        return where(ExpressionValue.fromExtractor(MemberAccessor.from(alias, mapper)));
    }

    public <IN, OUT> GotPartialExpression<GT, OT, OUT, InWhereExpressionExtender<GT, OT>, InWhereExpectingExpression<GT, OT>> where(Function1<IN, OUT> mapper) {
        return where(ExpressionValue.fromExtractor(MemberAccessor.from(mapper)));
    }

    public <IN, OUT> GotPartialExpression<GT, OT, OUT, InWhereExpressionExtender<GT, OT>, InWhereExpectingExpression<GT, OT>> where(ExpressionValue<OUT> expressionValue) {
        return GotPartialExpression.forSelect(operatingStream(), expressionValue, this::createStreamFromFilters);
    }

    private InWhereExpectingExpression<GT, OT> createStreamFromFilters(ExpressionBuilder<GT, OT, InWhereExpectingExpression<GT, OT>> baseExpression) {
        return new InWhereExpectingExpression<>(baseExpression);
    }
}
