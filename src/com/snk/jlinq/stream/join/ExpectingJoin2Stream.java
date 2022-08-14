package com.snk.jlinq.stream.join;

import com.snk.jlinq.data.StreamContext;
import com.snk.jlinq.stream.EnrichedStream;
import com.snk.jlinq.stream.expression.ExpressionBuilder;
import com.snk.jlinq.stream.expression.InJoinExpressionExtender;
import com.snk.jlinq.stream.pipeline.*;
import com.snk.jlinq.tuple.Tuple2;
import com.snk.jlinq.tuple.Tuple3;

import java.util.Collections;
import java.util.stream.Stream;

public class ExpectingJoin2Stream<T1, T2> extends InJoinExpressionExtender<Tuple2<T1, T2>, ExpectingJoin2Stream<T1, T2>> {

    public ExpectingJoin2Stream(ExpressionBuilder<Tuple2<T1, T2>, ExpectingJoin2Stream<T1, T2>> baseExpression) {
        super(baseExpression);
    }

    public <TN> InJoinExpectingOn<Tuple2<T1, T2>, TN, Tuple3<T1, T2, TN>, ExpectingJoin3Stream<T1, T2, TN>> join(String alias, Stream<TN> stream, Class<?> clazz) {
        return new InJoinExpectingOn<>(operatingStream(),
                new RootStreamOp<>(new EnrichedStream<>(stream, StreamContext.init(alias, clazz), Collections.emptyList())),
                (t1, v3) -> new Tuple3<>(t1, v3), baseExp -> new ExpectingJoin3Stream<>(baseExp));
    }

    public <TN> InJoinExpectingOn<Tuple2<T1, T2>, TN, Tuple3<T1, T2, TN>, ExpectingJoin3Stream<T1, T2, TN>> join(String alias, EnrichedStream<TN> stream) {
        return new InJoinExpectingOn<>(operatingStream(),
                new RootStreamOp<>(new EnrichedStream<>(stream.stream(), StreamContext.init(alias, stream.context().classAt(1)), Collections.emptyList())),
                (t1, v3) -> new Tuple3<>(t1, v3), baseExp -> new ExpectingJoin3Stream<>(baseExp));
    }

    public <TN> InJoinExpectingOn<Tuple2<T1, T2>, TN, Tuple3<T1, T2, TN>, ExpectingJoin3Stream<T1, T2, TN>> join(EnrichedStream<TN> stream) {
        return new InJoinExpectingOn<>(operatingStream(),
                new RootStreamOp<>(new EnrichedStream<>(stream.stream(), StreamContext.init(stream.context().classAt(1)), Collections.emptyList())),
                (t1, v3) -> new Tuple3<>(t1, v3), baseExp -> new ExpectingJoin3Stream<>(baseExp));
    }

    @Override
    public StreamOp<Tuple2<T1, T2>> operatingStream() {
        CombinedStreamOp<T1, T2, Tuple2<T1, T2>> streamOp = (CombinedStreamOp<T1, T2, Tuple2<T1,T2>>) baseExpression.operatingStream();

        return new JoinStreamOp<>(streamOp.left(), streamOp.right(), baseExpression.condition(), Tuple2::new);
    }
}
