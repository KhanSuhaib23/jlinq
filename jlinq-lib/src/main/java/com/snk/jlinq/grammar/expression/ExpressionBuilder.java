package com.snk.jlinq.grammar.expression;

import com.snk.jlinq.function.ConditionBuilder;
import com.snk.jlinq.grammar.ExpectingSelect;
import com.snk.jlinq.stream.expression.Condition;
import com.snk.jlinq.stream.operation.StreamOp;

import java.util.function.Function;

public class ExpressionBuilder<GroupedType, OriginalType, OutputStreamType extends ExpectingSelect<GroupedType, OriginalType>> {
    private final StreamOp<GroupedType, OriginalType> operatingStream;
    private final Condition condition;
    private final Function<ExpressionBuilder<GroupedType, OriginalType, OutputStreamType>, OutputStreamType> outputStreamConstructor;

    public ExpressionBuilder(StreamOp<GroupedType, OriginalType> operatingStream, Condition condition, Function<ExpressionBuilder<GroupedType, OriginalType, OutputStreamType>, OutputStreamType> outputStreamConstructor) {
        this.operatingStream = operatingStream;
        this.condition = condition;
        this.outputStreamConstructor = outputStreamConstructor;
    }

    public ExpressionBuilder(ExpressionBuilder<GroupedType, OriginalType, OutputStreamType> baseExpression) {
        this(baseExpression.operatingStream, baseExpression.condition, baseExpression.outputStreamConstructor);
    }

    public ExpressionBuilder(ExpressionBuilder<GroupedType, OriginalType, OutputStreamType> baseExpression, Condition newCondition, ConditionBuilder conditionBuilder) {
        this(baseExpression.operatingStream,
                baseExpression.condition == null ? newCondition : conditionBuilder.build(baseExpression.condition, newCondition),
                baseExpression.outputStreamConstructor);
    }

    public StreamOp<GroupedType, OriginalType> operatingStream() {
        return operatingStream;
    }

    public Condition condition() {
        return condition;
    }

    public OutputStreamType outputStream() {
        return outputStreamConstructor.apply(this);
    }

}
