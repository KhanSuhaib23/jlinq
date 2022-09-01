package com.snk.jlinq.grammar;

import com.snk.jlinq.grammar.expression.ExpressionBuilder;
import com.snk.jlinq.grammar.expression.InWhereExpressionExtender;

public class InWhereExpectingExpression<GT, OT> extends InWhereExpressionExtender<GT, OT> {
    public InWhereExpectingExpression(ExpressionBuilder<GT, OT, InWhereExpectingExpression<GT, OT>> baseExpression) {
        super(baseExpression);
    }
}
