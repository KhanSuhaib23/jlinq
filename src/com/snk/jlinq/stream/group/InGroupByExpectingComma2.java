package com.snk.jlinq.stream.group;

import com.snk.jlinq.function.MemberAccessor;
import com.snk.jlinq.stream.pipeline.StreamOp;
import com.snk.jlinq.tuple.Tuple2;

import java.util.List;

public class InGroupByExpectingComma2<FT1, FT2, OT> extends InGroupBy<Tuple2<FT1, FT2>, OT> {

    public InGroupByExpectingComma2(StreamOp<Tuple2<FT1, FT2>, OT> operatingStream, List<MemberAccessor> groupBys) {
        super(operatingStream, groupBys);
    }

    public InGroupByExpectingComma2(StreamOp<Tuple2<FT1, FT2>, OT> operatingStream, MemberAccessor groupBys) {
        super(operatingStream, groupBys);
    }

    public InGroupByExpectingComma2(StreamOp<Tuple2<FT1, FT2>, OT> operatingStream, List<MemberAccessor> groupBys, MemberAccessor additionalGroupBy) {
        super(operatingStream, groupBys, additionalGroupBy);
    }
}