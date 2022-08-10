package com.snk.jlinq.stream.expression;

import com.snk.jlinq.function.Function1;
import com.snk.jlinq.stream.SelectableStream;

public interface ExpressionExtender<T, OS extends SelectableStream<T>, EX extends ExpressionExtender<T, OS, EX>> {
    <IN, OUT> GotPartialExpression<T, OUT, EX, OS> and(String alias, Function1<IN, OUT> mapper);
    <IN, OUT> GotPartialExpression<T, OUT, EX, OS> and(Function1<IN, OUT> mapper);
    <OUT> GotPartialExpression<T, OUT, EX, OS> and(OUT value);

    <IN, OUT> GotPartialExpression<T, OUT, EX, OS> or(String alias, Function1<IN, OUT> mapper);
    <IN, OUT> GotPartialExpression<T, OUT, EX, OS> or(Function1<IN, OUT> mapper);
    <OUT> GotPartialExpression<T, OUT, EX, OS> or(OUT value);
}
