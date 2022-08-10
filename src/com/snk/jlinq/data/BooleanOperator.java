package com.snk.jlinq.data;

import java.util.function.BinaryOperator;

public enum BooleanOperator {
    AND(Boolean::logicalAnd),
    OR(Boolean::logicalOr);

    private final BinaryOperator<Boolean> op;

    BooleanOperator(BinaryOperator<Boolean> op) {
        this.op = op;
    }

    public boolean eval(boolean l, boolean r) {
        return op.apply(l, r);
    }
}
