package com.snk.jlinq.stream;

import com.snk.jlinq.data.Condition;
import com.snk.jlinq.data.ConditionValue;
import com.snk.jlinq.function.Function1;
import com.snk.jlinq.function.MemberAccessor;
import com.snk.jlinq.stream.expression.GotPartialExpression;
import com.snk.jlinq.stream.expression.SelectableStreamExpressionExtender;
import com.snk.jlinq.stream.pipeline.FilteredStream;
import com.snk.jlinq.stream.util.StreamFilter;

import java.util.List;


public class FilterableStream<T> extends SelectableStream<T> {

    public FilterableStream(SelectStream<T> baseStream) {
        super(baseStream);
    }

    public <IN, OUT> GotPartialExpression<T, OUT, SelectableStreamExpressionExtender<T>, SelectableStream<T>> where(String alias, Function1<IN, OUT> mapper) {
        return where(ConditionValue.fromExtractor(MemberAccessor.from(alias, mapper)));
    }

    public <IN, OUT> GotPartialExpression<T, OUT, SelectableStreamExpressionExtender<T>, SelectableStream<T>> where(Function1<IN, OUT> mapper) {
        return where(ConditionValue.fromExtractor(MemberAccessor.from(mapper)));
    }

    public <IN, OUT> GotPartialExpression<T, OUT, SelectableStreamExpressionExtender<T>, SelectableStream<T>> where(ConditionValue<OUT> conditionValue) {
        return GotPartialExpression.forSelect(selectStream(), conditionValue, FilterableStream::createStreamFromFilters);
    }

    private static <T> SelectableStream<T> createStreamFromFilters(SelectStream<T> stream, List<Condition> filters) {
        return new SelectableStream<>(new FilteredStream<>(stream, filters));
    }
}
