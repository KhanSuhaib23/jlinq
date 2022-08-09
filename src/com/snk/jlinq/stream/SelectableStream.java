package com.snk.jlinq.stream;

import com.snk.jlinq.function.Function1;
import com.snk.jlinq.function.MemberAccessor;
import com.snk.jlinq.stream.projection.InSelectExpectingComma1;

import java.util.stream.Stream;


// RT: Return Type. Type of the stream being returned.
// OT: Operating Type. Type of the stream on which we are operating on.
// Select essentially apply some operation on type OT (type of underlying stream) to convert is to RT, which is the
// type of the returned stream
public class SelectableStream<T> extends SelectStream<T> {
    private final SelectStream<T> selectStream;

    public SelectableStream(SelectStream<T> selectStream) {
        this.selectStream = selectStream;
    }

    public <IN, OUT> InSelectExpectingComma1<OUT, T> select(String alias, Function1<IN, OUT> mapper) {
        return new InSelectExpectingComma1<>(selectStream(), MemberAccessor.from(alias, mapper));
    }

    public <IN, OUT> InSelectExpectingComma1<OUT, T> select(Function1<IN, OUT> mapper) {
        return new InSelectExpectingComma1<>(selectStream(), MemberAccessor.from(mapper));
    }

    public SelectStream<T> selectStream() {
        return selectStream;
    }

    @Override
    protected Stream<T> underlyingStream() {
        return outputStream().stream();
    }

    @Override
    public EnrichedStream<T> outputStream() {
        return selectStream.outputStream();
    }
}
