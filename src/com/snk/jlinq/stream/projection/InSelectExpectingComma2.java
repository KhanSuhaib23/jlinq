package com.snk.jlinq.stream.projection;

import com.snk.jlinq.function.Function1;
import com.snk.jlinq.function.MemberAccessor;
import com.snk.jlinq.stream.EnrichedStream;
import com.snk.jlinq.stream.SelectStream;
import com.snk.jlinq.stream.pipeline.StreamOp;
import com.snk.jlinq.tuple.Tuple0;
import com.snk.jlinq.tuple.Tuple2;

import java.util.List;

public class InSelectExpectingComma2<RT1, RT2, GT, OT> extends ProjectedStream<Tuple2<RT1, RT2>, GT, OT> {
    public InSelectExpectingComma2(StreamOp<GT, OT> operatingStream, List<MemberAccessor> projections, MemberAccessor additionalProjection) {
        super(operatingStream, projections, additionalProjection);
    }

    public <IN, OUT> InSelectExpectingComma3<RT1, RT2, OUT, GT, OT> comma(Function1<IN, OUT> mapper) {
        return new InSelectExpectingComma3<>(baseOperatingStream(), projections(), MemberAccessor.from(mapper));
    }

    public <IN, OUT> InSelectExpectingComma3<RT1, RT2, OUT, GT, OT> comma(String alias, Function1<IN, OUT> mapper) {
        return new InSelectExpectingComma3<>(baseOperatingStream(), projections(), MemberAccessor.from(alias, mapper));
    }
}
