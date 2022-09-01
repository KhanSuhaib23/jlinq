package com.snk.jlinq.grammar.group;

import com.snk.jlinq.function.Function1;
import com.snk.jlinq.stream.MemberAccessor;
import com.snk.jlinq.stream.operation.GroupedStreamBuilderOp;
import com.snk.jlinq.stream.operation.StreamOp;

import java.util.List;

public class InGroupByExpectingComma1<FT1, OT> extends InGroupBy<FT1, OT> {

    public InGroupByExpectingComma1(StreamOp<FT1, OT> operatingStream, List<MemberAccessor> groupBys) {
        super(operatingStream, groupBys);
    }

    public InGroupByExpectingComma1(StreamOp<FT1, OT> operatingStream, MemberAccessor groupBys) {
        super(operatingStream, groupBys);
    }

    public InGroupByExpectingComma1(StreamOp<FT1, OT> operatingStream, List<MemberAccessor> groupBys, MemberAccessor additionalGroupBy) {
        super(operatingStream, groupBys, additionalGroupBy);
    }

    public <IN, OUT> InGroupByExpectingComma2<FT1, OUT, OT> comma(Function1<IN, OUT> mapper) {
        GroupedStreamBuilderOp<FT1, OT> builderOp = (GroupedStreamBuilderOp<FT1, OT>) operatingStream;
        return new InGroupByExpectingComma2<>(builderOp.newGroup(), groupBys, MemberAccessor.from(mapper));
    }
}
