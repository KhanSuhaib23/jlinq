package com.snk.jlinq.stream.expression;

import com.snk.jlinq.data.Condition;

import java.util.function.BinaryOperator;

@FunctionalInterface
public interface ConditionBuilder {
    ConditionBuilder AND = Condition::and;
    ConditionBuilder OR = Condition::or;

    Condition build(Condition baseCondition, Condition newCondition);
}
