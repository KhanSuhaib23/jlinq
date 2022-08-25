package com.snk.jlinq.stream.expression;

import com.snk.jlinq.data.Condition;
import com.snk.jlinq.data.ExpressionValue;
import com.snk.jlinq.function.Function1;
import com.snk.jlinq.function.MemberAccessor;
import com.snk.jlinq.stream.InWhereExpectingExpression;
import com.snk.jlinq.stream.SelectableStream;
import com.snk.jlinq.stream.pipeline.CombinedStreamOp;
import com.snk.jlinq.stream.pipeline.StreamOp;
import com.snk.jlinq.tuple.Tuple0;

import java.util.function.BinaryOperator;
import java.util.function.Function;

// BT = OT & RT base type, I want returned
// ET is expression type, type of the expression
// EX is the type of expression extender
public class GotPartialExpression<GT, OT, ET, EX, OS extends SelectableStream<GT, OT>> {
    private final ExpressionValue<ET> lValue;
    private final ExpressionBuilder<GT, OT, OS> baseExpression;
    private final BinaryOperator<Condition> conditionTransformer;

    public GotPartialExpression(StreamOp<GT, OT> operatingStream, ExpressionValue<ET> lValue,
                                Function<ExpressionBuilder<GT, OT, OS>, OS> outputStreamConstructor) {
        this.baseExpression = new ExpressionBuilder<>(operatingStream, null, outputStreamConstructor);
        this.lValue = lValue;
        this.conditionTransformer = null;
    }

    public GotPartialExpression(ExpressionBuilder<GT, OT, OS> baseExpression, ExpressionValue<ET> lValue,
                                BinaryOperator<Condition> conditionTransformer) {
        this.baseExpression = new ExpressionBuilder<>(baseExpression);
        this.lValue = lValue;
        this.conditionTransformer = conditionTransformer;
    }


    public static <GT, OT, ET> GotPartialExpression<GT, OT, ET, InWhereExpressionExtender<GT, OT>, InWhereExpectingExpression<GT, OT>>
    forSelect(StreamOp<GT, OT> operatingStream, ExpressionValue<ET> lValue, Function<ExpressionBuilder<GT, OT, InWhereExpectingExpression<GT, OT>>, InWhereExpectingExpression<GT, OT>> outputStreamConstructor) {
        return new GotPartialExpression<>(operatingStream, lValue, outputStreamConstructor);
    }

    public static <T1, T2, OT, ET, JT extends InJoinExpressionExtender<OT, JT>> GotPartialExpression<OT, Tuple0, ET, InJoinExpressionExtender<OT, JT>, JT>
    forJoin(StreamOp<T1, Tuple0> left, StreamOp<T2, Tuple0> right, ExpressionValue<ET> lValue, Function<ExpressionBuilder<OT, Tuple0, JT>, JT> outputStreamConstructor) {
        return new GotPartialExpression<>(new CombinedStreamOp<>(left, right), lValue, outputStreamConstructor);
    }

    public <IN> OS eq(String alias, Function1<IN, ET> mapper) {
        return eq(ExpressionValue.fromExtractor(MemberAccessor.from(alias, mapper)));
    }

    public <IN> OS eq(Function1<IN, ET> mapper) {
        return eq(ExpressionValue.fromExtractor(MemberAccessor.from(mapper)));
    }

    public <IN> OS eq(ET value) {
        return eq(ExpressionValue.fromScalar(value));
    }


    private OS eq(ExpressionValue<ET> expressionValue) {
        return new ExpressionBuilder<>(baseExpression, Condition.eq(lValue, expressionValue), conditionTransformer).outputStream();
    }
}
