package com.snk.jlinq.stream.expression;

import com.snk.jlinq.core.ListUtil;
import com.snk.jlinq.data.Condition;
import com.snk.jlinq.stream.SelectStream;
import com.snk.jlinq.stream.SelectableStream;

import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ExpressionBuilder<T, OS extends SelectableStream<T>> {
    private final SelectStream<T> baseStream;
    private final List<Condition> conditions;
    private final BiFunction<SelectStream<T>, List<Condition>, OS> outputStreamConstructor;

    public ExpressionBuilder(ExpressionBuilder<T, OS> baseExpression) {
        this.baseStream = baseExpression.baseStream();
        this.conditions = baseExpression.conditions();
        this.outputStreamConstructor = baseExpression.outputStreamConstructor();
    }

    public ExpressionBuilder(SelectStream<T> baseStream, List<Condition> conditions, BiFunction<SelectStream<T>, List<Condition>, OS> outputStreamConstructor) {
        this.baseStream = baseStream;
        this.conditions = conditions;
        this.outputStreamConstructor = outputStreamConstructor;
    }

    public ExpressionBuilder(ExpressionBuilder<T, OS> baseExpression, Condition newCondition) {
        this.baseStream = baseExpression.baseStream();
        this.outputStreamConstructor = baseExpression.outputStreamConstructor();
        this.conditions = ListUtil.addOne(baseExpression.conditions(), newCondition);
    }

    public SelectStream<T> baseStream() {
        return baseStream;
    }

    public List<Condition> conditions() {
        return conditions;
    }

    public BiFunction<SelectStream<T>, List<Condition>, OS> outputStreamConstructor() {
        return outputStreamConstructor;
    }

    public OS outputStream() {
        return outputStreamConstructor.apply(baseStream, conditions);
    }

}
