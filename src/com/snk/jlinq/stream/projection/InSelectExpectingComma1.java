package com.snk.jlinq.stream.projection;

import com.snk.jlinq.function.Function1;
import com.snk.jlinq.function.MemberAccessor;
import com.snk.jlinq.stream.EnrichedStream;
import com.snk.jlinq.stream.SelectStream;
import com.snk.jlinq.stream.pipeline.StreamOp;
import com.snk.jlinq.tuple.Tuple0;

public class InSelectExpectingComma1<RT1, GT, OT> extends ProjectedStream<RT1, GT, OT> {
    public InSelectExpectingComma1(StreamOp<GT, OT> operatingStream, MemberAccessor projection) {
        super(operatingStream, projection);
    }

    public <IN, OUT> InSelectExpectingComma2<RT1, OUT, GT, OT> comma(Function1<IN, OUT> mapper) {
        return new InSelectExpectingComma2<>(baseOperatingStream(), projections(), MemberAccessor.from(mapper));
    }

    public <IN, OUT> InSelectExpectingComma2<RT1, OUT, GT, OT> comma(String alias, Function1<IN, OUT> mapper) {
        return new InSelectExpectingComma2<>(baseOperatingStream(), projections(), MemberAccessor.from(alias, mapper));
    }
}
