package com.snk.jlinq.stream;

import com.snk.jlinq.stream.expression.ExpressionBuilder;
import com.snk.jlinq.stream.expression.InWhereExpressionExtender;

public class InWhereExpectingExpression<T> extends InWhereExpressionExtender<T> {
    public InWhereExpectingExpression(ExpressionBuilder<T, InWhereExpectingExpression<T>> baseExpression) {
        super(baseExpression);
    }


}
