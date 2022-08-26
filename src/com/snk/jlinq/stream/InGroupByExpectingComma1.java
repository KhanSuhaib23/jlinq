package com.snk.jlinq.stream;

import com.snk.jlinq.function.Function1;
import com.snk.jlinq.function.MemberAccessor;
import com.snk.jlinq.stream.pipeline.GroupPrecursorStreamOp;
import com.snk.jlinq.stream.pipeline.GroupStreamOp;
import com.snk.jlinq.stream.pipeline.StreamOp;

import java.util.Collections;
import java.util.List;

public class InGroupByExpectingComma1<FT1, OT> extends InGroupBy<FT1, OT> {

    public InGroupByExpectingComma1(StreamOp<FT1, OT> operatingStream, StreamOp<OT, OT> originalStream, List<MemberAccessor> groupBys) {
        super(operatingStream, originalStream, groupBys);
    }

    public InGroupByExpectingComma1(StreamOp<FT1, OT> operatingStream, StreamOp<OT, OT> originalStream, MemberAccessor groupBys) {
        super(operatingStream, originalStream, groupBys);
    }

    public InGroupByExpectingComma1(StreamOp<FT1, OT> operatingStream, StreamOp<OT, OT> originalStream, List<MemberAccessor> groupBys, MemberAccessor additionalGroupBy) {
        super(operatingStream, originalStream, groupBys, additionalGroupBy);
    }

    public <IN, OUT> InGroupByExpectingComma2<FT1, OUT, OT> comma(Function1<IN, OUT> mapper) {
        return new InGroupByExpectingComma2<>(new GroupPrecursorStreamOp<>(operatingStream), originalStream, groupBys, MemberAccessor.from(mapper));
    }
}
