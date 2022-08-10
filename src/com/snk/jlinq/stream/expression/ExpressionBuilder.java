package com.snk.jlinq.stream.expression;

import com.snk.jlinq.data.Condition;
import com.snk.jlinq.stream.SelectableStream;
import com.snk.jlinq.stream.pipeline.StreamOp;

import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;

public class ExpressionBuilder<T, OS extends SelectableStream<T>> {
    private final StreamOp<T> operatingStream;
    private final Condition condition;
    private final Function<ExpressionBuilder<T, OS>, OS> outputStreamConstructor;

    public ExpressionBuilder(ExpressionBuilder<T, OS> baseExpression) {
        this.operatingStream = baseExpression.operatingStream();
        this.condition = baseExpression.condition();
        this.outputStreamConstructor = baseExpression.outputStreamConstructor();
    }

    public ExpressionBuilder(StreamOp<T> operatingStream, Condition condition, Function<ExpressionBuilder<T, OS>, OS> outputStreamConstructor) {
        this.operatingStream = operatingStream;
        this.condition = condition;
        this.outputStreamConstructor = outputStreamConstructor;
    }

    public ExpressionBuilder(ExpressionBuilder<T, OS> baseExpression, Condition newCondition, BinaryOperator<Condition> conditionTransformer) {
        this.operatingStream = baseExpression.operatingStream();
        this.outputStreamConstructor = baseExpression.outputStreamConstructor();
        this.condition = baseExpression.condition() == null ? newCondition : conditionTransformer.apply(baseExpression.condition(), newCondition);
    }

    public StreamOp<T> operatingStream() {
        return operatingStream;
    }

    public Condition condition() {
        return condition;
    }

    public Function<ExpressionBuilder<T, OS>, OS> outputStreamConstructor() {
        return outputStreamConstructor;
    }

    public OS outputStream() {
        return outputStreamConstructor.apply(this);
    }

}
