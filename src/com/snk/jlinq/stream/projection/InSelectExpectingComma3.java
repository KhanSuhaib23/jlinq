package com.snk.jlinq.stream.projection;

import com.snk.jlinq.function.MemberAccessor;
import com.snk.jlinq.stream.pipeline.StreamOp;
import com.snk.jlinq.tuple.Tuple3;

import java.util.List;

public class InSelectExpectingComma3<SelectType1, SelectType2, SelectType3, GroupedType, OriginalType>
        extends ProjectedStream<Tuple3<SelectType1, SelectType2, SelectType3>, GroupedType, OriginalType> {
    public InSelectExpectingComma3(StreamOp<GroupedType, OriginalType> operatingStream, List<MemberAccessor> projections, MemberAccessor additionalProjection) {
        super(operatingStream, projections, additionalProjection);
    }
}
