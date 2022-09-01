package com.snk.jlinq.grammar;

import com.snk.jlinq.function.Function1;
import com.snk.jlinq.grammar.projection.InSelectExpectingComma1;
import com.snk.jlinq.stream.EnrichedStream;
import com.snk.jlinq.stream.MemberAccessor;
import com.snk.jlinq.stream.operation.StreamOp;


// RT: Return Type. Type of the stream being returned.
// OT: Operating Type. Type of the stream on which we are operating on.
// Select essentially apply some operation on type OT (type of underlying stream) to convert is to RT, which is the
// type of the returned stream
public class ExpectingSelect<GT, OT> extends SelectStream<GT, OT> {
    protected final StreamOp<GT, OT> operatingStream;

    public ExpectingSelect(StreamOp<GT, OT> operatingStream) {
        this.operatingStream = operatingStream;
    }

    public <IN, OUT> InSelectExpectingComma1<OUT, GT, OT> select(String alias, Function1<IN, OUT> mapper) {
        return new InSelectExpectingComma1<>(operatingStream(), MemberAccessor.from(alias, mapper));
    }

    public <IN, OUT> InSelectExpectingComma1<OUT, GT, OT> select(Function1<IN, OUT> mapper) {
        return new InSelectExpectingComma1<>(operatingStream(), MemberAccessor.from(mapper));
    }

    public <IN, OUT> InSelectExpectingComma1<OUT, GT, OT> select(MemberAccessor<OUT> memberAccessor) {
        return new InSelectExpectingComma1<>(operatingStream(), memberAccessor);
    }

    public StreamOp<GT, OT> operatingStream() {
        return operatingStream;
    }

    @Override
    public EnrichedStream<GT, OT> outputStream() {
        return operatingStream().outputStream();
    }
}
