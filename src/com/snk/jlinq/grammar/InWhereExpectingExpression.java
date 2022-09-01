package com.snk.jlinq.grammar;

import com.snk.jlinq.grammar.expression.ExpressionBuilder;
import com.snk.jlinq.grammar.expression.InWhereExpressionExtender;

public class InWhereExpectingExpression<GroupedType, OriginalType> extends InWhereExpressionExtender<GroupedType, OriginalType> {
    public InWhereExpectingExpression(ExpressionBuilder<GroupedType, OriginalType, InWhereExpectingExpression<GroupedType, OriginalType>> baseExpression) {
        super(baseExpression);
    }
}
