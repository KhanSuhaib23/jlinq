package com.snk.jlinq.stream.projection;

import com.snk.jlinq.function.Function1;
import com.snk.jlinq.function.MemberAccessor;
import com.snk.jlinq.stream.EnrichedStream;
import com.snk.jlinq.stream.SelectStream;
import com.snk.jlinq.stream.pipeline.StreamOp;

public class InSelectExpectingComma1<RT1, OT> extends ProjectedStream<RT1, OT> {
    public InSelectExpectingComma1(StreamOp<OT> operatingStream, MemberAccessor projection) {
        super(operatingStream, projection);
    }

    public <IN, OUT> InSelectExpectingComma2<RT1, OUT, OT> comma(Function1<IN, OUT> mapper) {
        return new InSelectExpectingComma2<>(baseOperatingStream(), projections(), MemberAccessor.from(mapper));
    }

    public <IN, OUT> InSelectExpectingComma2<RT1, OUT, OT> comma(String alias, Function1<IN, OUT> mapper) {
        return new InSelectExpectingComma2<>(baseOperatingStream(), projections(), MemberAccessor.from(alias, mapper));
    }
}
