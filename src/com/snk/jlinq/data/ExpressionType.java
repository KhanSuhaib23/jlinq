package com.snk.jlinq.data;

import java.util.function.BiFunction;
import java.util.function.BinaryOperator;

public enum ExpressionType {
    EQ(Object::equals);

    private BiFunction<Object, Object, Boolean> op;

    ExpressionType(BiFunction<Object, Object, Boolean> op) {
        this.op = op;
    }

    public boolean value(Object l, Object r) {
        return op.apply(l, r);
    }
}
