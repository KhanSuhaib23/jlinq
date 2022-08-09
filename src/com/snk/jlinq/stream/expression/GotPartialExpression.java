package com.snk.jlinq.stream.expression;

import com.snk.jlinq.core.ListUtil;
import com.snk.jlinq.data.Condition;
import com.snk.jlinq.data.ConditionValue;
import com.snk.jlinq.function.Function1;
import com.snk.jlinq.function.MemberAccessor;
import com.snk.jlinq.stream.SelectStream;
import com.snk.jlinq.stream.SelectableStream;

import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

// BT = OT & RT base type, I want returned
// ET is expression type, type of the expression
// EX is the type of expression extender
public class GotPartialExpression<T, ET, EX extends ExpressionExtender<T, OS, EX>, OS extends SelectableStream<T>> {
    private final ConditionValue<ET> lValue;
    private final ExpressionBuilder<T, OS> baseExpression;
    private final Function<ExpressionBuilder<T, OS>, EX> expressionExtenderConstructor;

    public GotPartialExpression(SelectStream<T> baseStream, ConditionValue<ET> lValue,
                                Function<ExpressionBuilder<T, OS>, EX> expressionExtenderConstructor,
                                BiFunction<SelectStream<T>, List<Condition>, OS> outputStreamConstructor) {
        this.baseExpression = new ExpressionBuilder<>(baseStream, Collections.emptyList(), outputStreamConstructor);
        this.expressionExtenderConstructor = expressionExtenderConstructor;
        this.lValue = lValue;
    }

    public GotPartialExpression(ExpressionBuilder<T, OS> baseExpression, ConditionValue<ET> lValue,
                                Function<ExpressionBuilder<T, OS>, EX> expressionExtenderConstructor) {
        this.baseExpression = new ExpressionBuilder<>(baseExpression);
        this.expressionExtenderConstructor = expressionExtenderConstructor;
        this.lValue = lValue;
    }

    public static <T, ET> GotPartialExpression<T, ET, SelectableStreamExpressionExtender<T>, SelectableStream<T>>
    forSelect(SelectStream<T> baseStream, ConditionValue<ET> lValue, BiFunction<SelectStream<T>, List<Condition>, SelectableStream<T>> outputStreamConstructor) {
        return new GotPartialExpression<>(baseStream, lValue, SelectableStreamExpressionExtender::of, outputStreamConstructor);
    }

    public <IN> EX eq(String alias, Function1<IN, ET> mapper) {
        return eq(ConditionValue.fromExtractor(MemberAccessor.from(alias, mapper)));
    }

    public <IN> EX eq(Function1<IN, ET> mapper) {
        return eq(ConditionValue.fromExtractor(MemberAccessor.from(mapper)));
    }

    public <IN> EX eq(ET value) {
        return eq(ConditionValue.fromScalar(value));
    }


    private EX eq(ConditionValue<ET> conditionValue) {
        return expressionExtenderConstructor.apply(new ExpressionBuilder<>(baseExpression, Condition.eq(lValue, conditionValue)));
    }
}
