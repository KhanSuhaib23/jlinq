package com.snk.jlinq.stream;

import com.snk.jlinq.function.Function1;
import com.snk.jlinq.function.MemberAccessor;
import com.snk.jlinq.stream.pipeline.GroupStreamOp;
import com.snk.jlinq.stream.pipeline.NoOpMapStreamOp;
import com.snk.jlinq.stream.pipeline.StreamOp;
import com.snk.jlinq.tuple.Tuple2;

import java.util.List;

public class InGroupByExpectingComma2<FT1, FT2, OT> extends InGroupBy<Tuple2<FT1, FT2>, OT> {
    public InGroupByExpectingComma2(NoOpMapStreamOp<OT, Tuple2<FT1, FT2>> operatingStream, List<MemberAccessor> groupBys, MemberAccessor additionalGroupBy) {
        super(operatingStream, groupBys, additionalGroupBy);
    }
}
