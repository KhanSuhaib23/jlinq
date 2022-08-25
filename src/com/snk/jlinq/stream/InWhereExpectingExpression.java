package com.snk.jlinq.stream;

import com.snk.jlinq.stream.expression.ExpressionBuilder;
import com.snk.jlinq.stream.expression.InWhereExpressionExtender;

public class InWhereExpectingExpression<GT, OT> extends InWhereExpressionExtender<GT, OT> {
    public InWhereExpectingExpression(ExpressionBuilder<GT, OT, InWhereExpectingExpression<GT, OT>> baseExpression) {
        super(baseExpression);
    }


}
