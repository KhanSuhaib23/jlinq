package com.snk.jlinq.stream;

import com.snk.jlinq.data.Condition;
import com.snk.jlinq.data.ExpressionValue;
import com.snk.jlinq.function.Function1;
import com.snk.jlinq.function.MemberAccessor;
import com.snk.jlinq.stream.expression.GotPartialExpression;
import com.snk.jlinq.stream.expression.SelectableStreamExpressionExtender;
import com.snk.jlinq.stream.pipeline.FilterStreamOp;
import com.snk.jlinq.stream.pipeline.StreamOp;


public class FilterableStream<T> extends SelectableStream<T> {

    public FilterableStream(StreamOp<T> operatingStream) {
        super(operatingStream);
    }

    public <IN, OUT> GotPartialExpression<T, OUT, SelectableStreamExpressionExtender<T>, SelectableStream<T>> where(String alias, Function1<IN, OUT> mapper) {
        return where(ExpressionValue.fromExtractor(MemberAccessor.from(alias, mapper)));
    }

    public <IN, OUT> GotPartialExpression<T, OUT, SelectableStreamExpressionExtender<T>, SelectableStream<T>> where(Function1<IN, OUT> mapper) {
        return where(ExpressionValue.fromExtractor(MemberAccessor.from(mapper)));
    }

    public <IN, OUT> GotPartialExpression<T, OUT, SelectableStreamExpressionExtender<T>, SelectableStream<T>> where(ExpressionValue<OUT> expressionValue) {
        return GotPartialExpression.forSelect(operatingStream(), expressionValue, FilterableStream::createStreamFromFilters);
    }

    private static <T> SelectableStream<T> createStreamFromFilters(StreamOp<T> stream, Condition filterCondition) {
        return new SelectableStream<>(new FilterStreamOp<>(stream, filterCondition));
    }
}
