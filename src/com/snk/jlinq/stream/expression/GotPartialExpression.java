package com.snk.jlinq.stream.expression;

import com.snk.jlinq.data.Condition;
import com.snk.jlinq.data.ExpressionValue;
import com.snk.jlinq.function.Function1;
import com.snk.jlinq.function.MemberAccessor;
import com.snk.jlinq.stream.JoinableJoin2Stream;
import com.snk.jlinq.stream.SelectableStream;
import com.snk.jlinq.stream.pipeline.DualStreamOp;
import com.snk.jlinq.stream.pipeline.StreamOp;
import com.snk.jlinq.tuple.Tuple2;

import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;

// BT = OT & RT base type, I want returned
// ET is expression type, type of the expression
// EX is the type of expression extender
public class GotPartialExpression<T, ET, EX extends ExpressionExtender<T, OS, EX>, OS extends SelectableStream<T>> {
    private final ExpressionValue<ET> lValue;
    private final ExpressionBuilder<T, OS> baseExpression;
    private final Function<ExpressionBuilder<T, OS>, EX> expressionExtenderConstructor;
    private final BinaryOperator<Condition> conditionTransformer;

    public GotPartialExpression(StreamOp<T> operatingStream, ExpressionValue<ET> lValue,
                                Function<ExpressionBuilder<T, OS>, EX> expressionExtenderConstructor,
                                BiFunction<StreamOp<T>, Condition, OS> outputStreamConstructor) {
        this.baseExpression = new ExpressionBuilder<>(operatingStream, null, outputStreamConstructor);
        this.expressionExtenderConstructor = expressionExtenderConstructor;
        this.lValue = lValue;
        this.conditionTransformer = null;
    }

    public GotPartialExpression(ExpressionBuilder<T, OS> baseExpression, ExpressionValue<ET> lValue,
                                Function<ExpressionBuilder<T, OS>, EX> expressionExtenderConstructor,
                                BinaryOperator<Condition> conditionTransformer) {
        this.baseExpression = new ExpressionBuilder<>(baseExpression);
        this.expressionExtenderConstructor = expressionExtenderConstructor;
        this.lValue = lValue;
        this.conditionTransformer = conditionTransformer;
    }

    public static <T, ET> GotPartialExpression<T, ET, SelectableStreamExpressionExtender<T>, SelectableStream<T>>
    forSelect(StreamOp<T> operatingStream, ExpressionValue<ET> lValue, BiFunction<StreamOp<T>, Condition, SelectableStream<T>> outputStreamConstructor) {
        return new GotPartialExpression<>(operatingStream, lValue, SelectableStreamExpressionExtender::of, outputStreamConstructor);
    }

    public static <T1, T2, ET> GotPartialExpression<Tuple2<T1, T2>, ET, JoinStreamExpressionExtender<T1, T2>, JoinableJoin2Stream<T1, T2>>
    forJoin(StreamOp<T1> left, StreamOp<T2> right, ExpressionValue<ET> lValue, BiFunction<StreamOp<Tuple2<T1, T2>>, Condition, JoinableJoin2Stream<T1, T2>> outputStreamConstructor) {
        return new GotPartialExpression<>(new DualStreamOp<>(left, right), lValue, JoinStreamExpressionExtender::of, outputStreamConstructor);
    }

    public <IN> EX eq(String alias, Function1<IN, ET> mapper) {
        return eq(ExpressionValue.fromExtractor(MemberAccessor.from(alias, mapper)));
    }

    public <IN> EX eq(Function1<IN, ET> mapper) {
        return eq(ExpressionValue.fromExtractor(MemberAccessor.from(mapper)));
    }

    public <IN> EX eq(ET value) {
        return eq(ExpressionValue.fromScalar(value));
    }


    private EX eq(ExpressionValue<ET> expressionValue) {
        return expressionExtenderConstructor.apply(new ExpressionBuilder<>(baseExpression, Condition.eq(lValue, expressionValue), conditionTransformer));
    }
}
