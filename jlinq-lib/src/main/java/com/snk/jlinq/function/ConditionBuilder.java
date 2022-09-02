package com.snk.jlinq.function;

import com.snk.jlinq.stream.expression.Condition;

@FunctionalInterface
public interface ConditionBuilder {
    ConditionBuilder AND = Condition::and;
    ConditionBuilder OR = Condition::or;

    Condition build(Condition baseCondition, Condition newCondition);
}
