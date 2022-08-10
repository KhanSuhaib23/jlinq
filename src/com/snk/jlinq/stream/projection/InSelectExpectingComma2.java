package com.snk.jlinq.stream.projection;

import com.snk.jlinq.function.MemberAccessor;
import com.snk.jlinq.stream.EnrichedStream;
import com.snk.jlinq.stream.SelectStream;
import com.snk.jlinq.stream.pipeline.StreamOp;
import com.snk.jlinq.tuple.Tuple2;

import java.util.List;

public class InSelectExpectingComma2<RT1, RT2, OT> extends ProjectedStream<Tuple2<RT1, RT2>, OT> {
    public InSelectExpectingComma2(StreamOp<OT> operatingStream, List<MemberAccessor> projections, MemberAccessor additionalProjection) {
        super(operatingStream, projections, additionalProjection);
    }
}
