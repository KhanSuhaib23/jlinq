package com.snk.jlinq.stream.projection;

import com.snk.jlinq.function.Function1;
import com.snk.jlinq.function.MemberAccessor;
import com.snk.jlinq.stream.pipeline.StreamOp;
import com.snk.jlinq.tuple.Tuple2;
import com.snk.jlinq.tuple.Tuple3;

import java.util.List;

public class InSelectExpectingComma3<RT1, RT2, RT3, OT> extends ProjectedStream<Tuple3<RT1, RT2, RT3>, OT> {
    public InSelectExpectingComma3(StreamOp<OT> operatingStream, List<MemberAccessor> projections, MemberAccessor additionalProjection) {
        super(operatingStream, projections, additionalProjection);
    }
}
