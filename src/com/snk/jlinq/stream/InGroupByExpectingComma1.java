package com.snk.jlinq.stream;

import com.snk.jlinq.function.Function1;
import com.snk.jlinq.function.MemberAccessor;
import com.snk.jlinq.stream.pipeline.GroupStreamOp;
import com.snk.jlinq.stream.pipeline.NoOpMapStreamOp;
import com.snk.jlinq.stream.pipeline.StreamOp;

import java.util.List;

public class InGroupByExpectingComma1<FT1, OT> extends InGroupBy<FT1, OT> {

    public InGroupByExpectingComma1(NoOpMapStreamOp<OT, FT1> operatingStream, List<MemberAccessor> groupBys) {
        super(operatingStream, groupBys);
    }

    public InGroupByExpectingComma1(NoOpMapStreamOp<OT, FT1> operatingStream, MemberAccessor groupBys) {
        super(operatingStream, groupBys);
    }

    public InGroupByExpectingComma1(NoOpMapStreamOp<OT, FT1> operatingStream, List<MemberAccessor> groupBys, MemberAccessor additionalGroupBy) {
        super(operatingStream, groupBys, additionalGroupBy);
    }

    public <IN, OUT> InGroupByExpectingComma2<FT1, OUT, OT> comma(Function1<IN, OUT> mapper) {
        return new InGroupByExpectingComma2<>(new NoOpMapStreamOp<>(underlyingStream), groupBys, MemberAccessor.from(mapper));
    }
}
