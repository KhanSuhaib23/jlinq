package com.snk.jlinq.data;

public class Condition<T> {

    private final ConditionValue<T> left;
    private final ConditionValue<T> right;
    private final ConditionType conditionType;

    private Condition(ConditionValue<T> left, ConditionValue<T> right, ConditionType conditionType) {
        this.left = left;
        this.right = right;
        this.conditionType = conditionType;
    }

    public ConditionValue<T> left() {
        return left;
    }

    public ConditionValue<T> right() {
        return right;
    }

    public ConditionType conditionType() {
        return conditionType;
    }

    public static <T> Condition<T> eq(ConditionValue<T> left, ConditionValue<T> right) {
        return new Condition<>(left, right, ConditionType.EQ);
    }
}
