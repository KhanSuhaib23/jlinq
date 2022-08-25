package com.snk.jlinq.stream;

import com.snk.jlinq.function.Function1;
import com.snk.jlinq.function.MemberAccessor;
import com.snk.jlinq.stream.pipeline.StreamOp;
import com.snk.jlinq.stream.projection.InSelectExpectingComma1;
import com.snk.jlinq.tuple.Tuple0;

import java.util.stream.Stream;


// RT: Return Type. Type of the stream being returned.
// OT: Operating Type. Type of the stream on which we are operating on.
// Select essentially apply some operation on type OT (type of underlying stream) to convert is to RT, which is the
// type of the returned stream
public class SelectableStream<GT, OT> extends SelectStream<GT> {
    protected final StreamOp<GT, OT> operatingStream;

    public SelectableStream(StreamOp<GT, OT> operatingStream) {
        this.operatingStream = operatingStream;
    }

    public <IN, OUT> InSelectExpectingComma1<OUT, GT, OT> select(String alias, Function1<IN, OUT> mapper) {
        return new InSelectExpectingComma1<>(operatingStream(), MemberAccessor.from(alias, mapper));
    }

    public <IN, OUT> InSelectExpectingComma1<OUT, GT, OT> select(Function1<IN, OUT> mapper) {
        return new InSelectExpectingComma1<>(operatingStream(), MemberAccessor.from(mapper));
    }

    public StreamOp<GT, OT> operatingStream() {
        return operatingStream;
    }

    @Override
    protected Stream<GT> underlyingStream() {
        return outputStream().stream();
    }

    @Override
    public EnrichedStream<GT> outputStream() {
        return operatingStream.outputStream();
    }
}
