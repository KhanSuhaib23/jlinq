package com.snk.jlinq.stream.join;

import com.snk.jlinq.stream.expression.ExpressionBuilder;
import com.snk.jlinq.stream.expression.InJoinExpressionExtender;
import com.snk.jlinq.stream.pipeline.CombinedStreamOp;
import com.snk.jlinq.stream.pipeline.JoinStreamOp;
import com.snk.jlinq.stream.pipeline.StreamOp;
import com.snk.jlinq.tuple.Tuple2;
import com.snk.jlinq.tuple.Tuple3;

public class ExpectingJoin3Stream<T1, T2, T3> extends InJoinExpressionExtender<Tuple3<T1, T2, T3>, ExpectingJoin3Stream<T1, T2, T3>> {

    public ExpectingJoin3Stream(ExpressionBuilder<Tuple3<T1, T2, T3>, ExpectingJoin3Stream<T1, T2, T3>> baseExpression) {
        super(baseExpression);
    }

    @Override
    public StreamOp<Tuple3<T1, T2, T3>> operatingStream() {
        CombinedStreamOp<Tuple2<T1, T2>, T3, Tuple3<T1, T2, T3>> streamOp = (CombinedStreamOp<Tuple2<T1, T2>, T3, Tuple3<T1, T2, T3>>) baseExpression.operatingStream();

        return new JoinStreamOp<>(streamOp.left(), streamOp.right(), baseExpression.condition(), Tuple3::new);
    }
}
