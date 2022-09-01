package com.snk.jlinq.grammar.expression;

import com.snk.jlinq.function.Function1;
import com.snk.jlinq.grammar.ExpectingSelect;

public interface ExpressionExtender<GroupedType, OriginalType,
        OutputStreamType extends ExpectingSelect<GroupedType, OriginalType>,
        ExtenderType extends ExpressionExtender<GroupedType, OriginalType, OutputStreamType, ExtenderType>> {
    <IN, OUT> GotPartialExpression<GroupedType, OriginalType, OUT, OutputStreamType> and(String alias, Function1<IN, OUT> mapper);
    <IN, OUT> GotPartialExpression<GroupedType, OriginalType, OUT, OutputStreamType> and(Function1<IN, OUT> mapper);
    <OUT> GotPartialExpression<GroupedType, OriginalType, OUT, OutputStreamType> and(OUT value);

    <IN, OUT> GotPartialExpression<GroupedType, OriginalType, OUT, OutputStreamType> or(String alias, Function1<IN, OUT> mapper);
    <IN, OUT> GotPartialExpression<GroupedType, OriginalType, OUT, OutputStreamType> or(Function1<IN, OUT> mapper);
    <OUT> GotPartialExpression<GroupedType, OriginalType, OUT, OutputStreamType> or(OUT value);
}
