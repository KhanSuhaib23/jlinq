package com.snk.jlinq.grammar.join;

import com.snk.jlinq.grammar.expression.ExpressionBuilder;
import com.snk.jlinq.grammar.expression.InJoinExpressionExtender;
import com.snk.jlinq.stream.operation.JoinStreamBuilderOp;
import com.snk.jlinq.stream.operation.StreamOp;
import com.snk.jlinq.udt.Tuple2;
import com.snk.jlinq.udt.Tuple3;

public class ExpectingJoin3Stream<T1, T2, T3> extends InJoinExpressionExtender<Tuple3<T1, T2, T3>, ExpectingJoin3Stream<T1, T2, T3>> {

    public ExpectingJoin3Stream(ExpressionBuilder<Tuple3<T1, T2, T3>, Tuple3<T1, T2, T3>, ExpectingJoin3Stream<T1, T2, T3>> baseExpression) {
        super(baseExpression);
    }

    @Override
    public StreamOp<Tuple3<T1, T2, T3>, Tuple3<T1, T2, T3>> operatingStream() {
        JoinStreamBuilderOp<Tuple2<T1, T2>, T3, Tuple3<T1, T2, T3>> streamOp = (JoinStreamBuilderOp<Tuple2<T1, T2>, T3, Tuple3<T1, T2, T3>>) baseExpression.operatingStream();

        return streamOp.buildJoin(baseExpression.condition(), Tuple3::new);
    }
}
