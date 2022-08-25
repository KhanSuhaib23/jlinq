package com.snk.jlinq.stream.expression;

import com.snk.jlinq.function.Function1;
import com.snk.jlinq.stream.SelectableStream;

public interface ExpressionExtender<GT, OT, OS extends SelectableStream<GT, OT>, EX extends ExpressionExtender<GT, OT, OS, EX>> {
    <IN, OUT> GotPartialExpression<GT, OT, OUT, EX, OS> and(String alias, Function1<IN, OUT> mapper);
    <IN, OUT> GotPartialExpression<GT, OT, OUT, EX, OS> and(Function1<IN, OUT> mapper);
    <OUT> GotPartialExpression<GT, OT, OUT, EX, OS> and(OUT value);

    <IN, OUT> GotPartialExpression<GT, OT, OUT, EX, OS> or(String alias, Function1<IN, OUT> mapper);
    <IN, OUT> GotPartialExpression<GT, OT, OUT, EX, OS> or(Function1<IN, OUT> mapper);
    <OUT> GotPartialExpression<GT, OT, OUT, EX, OS> or(OUT value);
}
