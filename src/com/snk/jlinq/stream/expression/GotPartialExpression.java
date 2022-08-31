package com.snk.jlinq.stream.expression;

import com.snk.jlinq.data.Condition;
import com.snk.jlinq.data.ExpressionValue;
import com.snk.jlinq.function.Function1;
import com.snk.jlinq.function.MemberAccessor;
import com.snk.jlinq.stream.InWhereExpectingExpression;
import com.snk.jlinq.stream.ExpectingSelect;
import com.snk.jlinq.stream.pipeline.JoinStreamBuilderOp;
import com.snk.jlinq.stream.pipeline.StreamOp;

import java.util.function.Function;

public class GotPartialExpression<GroupedType, OriginalType, ExpressionType, OutputStreamType extends ExpectingSelect<GroupedType, OriginalType>> {
    private final ExpressionValue<ExpressionType> lValue;
    private final ExpressionBuilder<GroupedType, OriginalType, OutputStreamType> baseExpression;
    private final ConditionBuilder conditionBuilder;

    private GotPartialExpression(StreamOp<GroupedType, OriginalType> operatingStream,
                                ExpressionValue<ExpressionType> lValue,
                                Function<ExpressionBuilder<GroupedType, OriginalType, OutputStreamType>, OutputStreamType> outputStreamConstructor) {
        this.baseExpression = new ExpressionBuilder<>(operatingStream, null, outputStreamConstructor);
        this.lValue = lValue;
        this.conditionBuilder = null;
    }

    private GotPartialExpression(ExpressionBuilder<GroupedType, OriginalType, OutputStreamType> baseExpression,
                                ExpressionValue<ExpressionType> lValue,
                                ConditionBuilder conditionBuilder) {
        this.baseExpression = new ExpressionBuilder<>(baseExpression);
        this.lValue = lValue;
        this.conditionBuilder = conditionBuilder;
    }

    public static <GroupedType, OriginalType, ExpressionType, OutputType extends ExpectingSelect<GroupedType, OriginalType>>
            GotPartialExpression<GroupedType, OriginalType, ExpressionType, OutputType>
    beginExpression(StreamOp<GroupedType, OriginalType> operatingStream,
                    ExpressionValue<ExpressionType> lValue,
                    Function<ExpressionBuilder<GroupedType, OriginalType, OutputType>, OutputType> outputStreamConstructor) {
            return new GotPartialExpression<>(operatingStream, lValue, outputStreamConstructor);
    }

    public static <GroupedType, OriginalType, ExpressionType, OutputType extends ExpectingSelect<GroupedType, OriginalType>>
    GotPartialExpression<GroupedType, OriginalType, ExpressionType, OutputType>
    extendExpression(ExpressionBuilder<GroupedType, OriginalType, OutputType> baseExpression,
                     ExpressionValue<ExpressionType> lValue,
                     ConditionBuilder conditionBuilder) {
        return new GotPartialExpression<>(baseExpression, lValue, conditionBuilder);
    }


    public static <GroupedType, OriginalType, ExpressionType>
    GotPartialExpression<GroupedType, OriginalType, ExpressionType, InWhereExpectingExpression<GroupedType, OriginalType>>
    forSelect(StreamOp<GroupedType, OriginalType> operatingStream, ExpressionValue<ExpressionType> lValue, Function<ExpressionBuilder<GroupedType, OriginalType, InWhereExpectingExpression<GroupedType, OriginalType>>, InWhereExpectingExpression<GroupedType, OriginalType>> outputStreamConstructor) {
        return beginExpression(operatingStream, lValue, outputStreamConstructor);
    }

    public static <T1, T2, OutputType, ExpressionType, ExpectingJoinType extends InJoinExpressionExtender<OutputType, ExpectingJoinType>>
    GotPartialExpression<OutputType, OutputType, ExpressionType, ExpectingJoinType>
    forJoin(StreamOp<T1, T1> left, StreamOp<T2, T2> right, ExpressionValue<ExpressionType> lValue,
            Function<ExpressionBuilder<OutputType, OutputType, ExpectingJoinType>, ExpectingJoinType> outputStreamConstructor) {
        return beginExpression(new JoinStreamBuilderOp<>(left, right), lValue, outputStreamConstructor);
    }

    public <IN> OutputStreamType eq(String alias, Function1<IN, ExpressionType> mapper) {
        return eq(ExpressionValue.fromExtractor(MemberAccessor.from(alias, mapper)));
    }

    public <IN> OutputStreamType eq(Function1<IN, ExpressionType> mapper) {
        return eq(ExpressionValue.fromExtractor(MemberAccessor.from(mapper)));
    }

    public OutputStreamType eq(ExpressionType value) {
        return eq(ExpressionValue.fromScalar(value));
    }


    private OutputStreamType eq(ExpressionValue<ExpressionType> expressionValue) {
        return new ExpressionBuilder<>(baseExpression, Condition.eq(lValue, expressionValue), conditionBuilder).outputStream();
    }
}
