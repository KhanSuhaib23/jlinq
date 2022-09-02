package com.snk.jlinq.stream.expression;

import com.snk.jlinq.util.CodeUtil;

import java.util.function.BiFunction;

public enum ExpressionType {
    EQ(Object::equals),
    NEQ(CodeUtil.negate(Object::equals));

    private final BiFunction<Object, Object, Boolean> op;

    ExpressionType(BiFunction<Object, Object, Boolean> op) {
        this.op = op;
    }

    public boolean value(Object l, Object r) {
        return op.apply(l, r);
    }
}
