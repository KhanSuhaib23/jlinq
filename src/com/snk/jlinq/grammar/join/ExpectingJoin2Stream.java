package com.snk.jlinq.grammar.join;

import com.snk.jlinq.grammar.expression.ExpressionBuilder;
import com.snk.jlinq.grammar.expression.InJoinExpressionExtender;
import com.snk.jlinq.stream.EnrichedStream;
import com.snk.jlinq.stream.StreamContext;
import com.snk.jlinq.stream.operation.JoinStreamBuilderOp;
import com.snk.jlinq.stream.operation.RootStreamOp;
import com.snk.jlinq.stream.operation.StreamOp;
import com.snk.jlinq.udt.Tuple2;
import com.snk.jlinq.udt.Tuple3;

import java.util.Collections;
import java.util.stream.Stream;

public class ExpectingJoin2Stream<T1, T2> extends InJoinExpressionExtender<Tuple2<T1, T2>, ExpectingJoin2Stream<T1, T2>> {

    public ExpectingJoin2Stream(ExpressionBuilder<Tuple2<T1, T2>, Tuple2<T1, T2>, ExpectingJoin2Stream<T1, T2>> baseExpression) {
        super(baseExpression);
    }

    public <NewType> InJoinExpectingOn<Tuple2<T1, T2>, NewType, Tuple3<T1, T2, NewType>, ExpectingJoin3Stream<T1, T2, NewType>>
    join(String alias, Stream<NewType> stream, Class<?> clazz) {
        return join(alias, EnrichedStream.singleStream(stream, StreamContext.init(alias, clazz)));
    }

    public <NewType> InJoinExpectingOn<Tuple2<T1, T2>, NewType, Tuple3<T1, T2, NewType>, ExpectingJoin3Stream<T1, T2, NewType>>
    join(EnrichedStream<NewType, NewType> stream) {
        return join("", stream);
    }

    public <NewType> InJoinExpectingOn<Tuple2<T1, T2>, NewType, Tuple3<T1, T2, NewType>, ExpectingJoin3Stream<T1, T2, NewType>>
    join(String alias, EnrichedStream<NewType, NewType> stream) {
        return new InJoinExpectingOn<>(operatingStream(),
                new RootStreamOp<>(EnrichedStream.singleStream(stream.singleStream(), StreamContext.init(alias, stream.context().classAt(0)), Collections.emptyList())),
                ExpectingJoin3Stream::new);
    }

    @Override
    public StreamOp<Tuple2<T1, T2>, Tuple2<T1, T2>> operatingStream() {
        JoinStreamBuilderOp<T1, T2, Tuple2<T1, T2>> streamOp = (JoinStreamBuilderOp<T1, T2, Tuple2<T1,T2>>) baseExpression.operatingStream();

        return streamOp.buildJoin(baseExpression.condition(), Tuple2::new);
    }
}
