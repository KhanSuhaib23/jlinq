package com.snk.jlinq.stream.operation;

import com.snk.jlinq.stream.EnrichedStream;
import com.snk.jlinq.stream.expression.Condition;
import com.snk.jlinq.util.StreamOperations;

import java.util.function.BiFunction;


public class JoinStreamOp<T1, T2, OutputType> extends JoinStreamBuilderOp<T1, T2, OutputType> {
    private final Condition condition;
    private final BiFunction<T1, T2, OutputType> mapper;

    public JoinStreamOp(JoinStreamBuilderOp<T1, T2, OutputType> builder, Condition condition, BiFunction<T1, T2, OutputType> mapper) {
        super(builder.left(), builder.right());
        this.condition = condition;
        this.mapper = mapper;
    }

    @Override
    public EnrichedStream<OutputType, OutputType> outputStream() {
        return StreamOperations.join(left.outputStream(), right.outputStream(), condition, mapper);
    }
}
