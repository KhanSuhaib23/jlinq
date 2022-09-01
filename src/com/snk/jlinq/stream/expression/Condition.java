package com.snk.jlinq.stream.expression;

import com.snk.jlinq.stream.StreamContext;

public abstract class Condition {
    public abstract boolean value(StreamContext context, Object object);

    public static <T> Condition eq(ExpressionValue<T> left, ExpressionValue<T> right) {
        return new BooleanExpression<>(left, right, ExpressionType.EQ);
    }

    public static <T> Condition neq(ExpressionValue<T> left, ExpressionValue<T> right) {
        return new BooleanExpression<>(left, right, ExpressionType.NEQ);
    }

    public Condition and(Condition other) {
        return new InternalNode(this, other, BooleanOperator.AND);
    }

    public Condition or(Condition other) {
        return new InternalNode(this, other, BooleanOperator.OR);
    }

    public static class InternalNode extends Condition {
        private final Condition left;
        private final Condition right;
        private final BooleanOperator operator;

        public InternalNode(Condition left, Condition right, BooleanOperator operator) {
            this.left = left;
            this.right = right;
            this.operator = operator;
        }

        @Override
        public boolean value(StreamContext context, Object object) {
            return operator.eval(left.value(context, object), right.value(context, object));
        }
    }

    public static class BooleanExpression<T> extends Condition {
        private final ExpressionValue<T> left;
        private final ExpressionValue<T> right;
        private final ExpressionType expressionType;

        private BooleanExpression(ExpressionValue<T> left, ExpressionValue<T> right, ExpressionType expressionType) {
            this.left = left;
            this.right = right;
            this.expressionType = expressionType;
        }

        @Override
        public boolean value(StreamContext context, Object object) {
            Object o1 = left.getValue(context, object);
            Object o2 = right.getValue(context, object);

            return expressionType.value(o1, o2);
        }
    }
}
