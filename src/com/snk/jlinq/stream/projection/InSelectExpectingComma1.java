package com.snk.jlinq.stream.projection;

import com.snk.jlinq.function.Function1;
import com.snk.jlinq.function.MemberAccessor;
import com.snk.jlinq.stream.pipeline.StreamOp;

public class InSelectExpectingComma1<SelectType1, GroupedType, OriginalType> extends ProjectedStream<SelectType1, GroupedType, OriginalType> {
    public InSelectExpectingComma1(StreamOp<GroupedType, OriginalType> operatingStream, MemberAccessor projection) {
        super(operatingStream, projection);
    }

    public <IN, OUT> InSelectExpectingComma2<SelectType1, OUT, GroupedType, OriginalType> comma(Function1<IN, OUT> mapper) {
        return new InSelectExpectingComma2<>(baseOperatingStream(), projections(), MemberAccessor.from(mapper));
    }

    public <IN, OUT> InSelectExpectingComma2<SelectType1, OUT, GroupedType, OriginalType> comma(MemberAccessor<OUT> memberAccessor) {
        return new InSelectExpectingComma2<>(baseOperatingStream(), projections(), memberAccessor);
    }

    public <IN, OUT> InSelectExpectingComma2<SelectType1, OUT, GroupedType, OriginalType> comma(String alias, Function1<IN, OUT> mapper) {
        return new InSelectExpectingComma2<>(baseOperatingStream(), projections(), MemberAccessor.from(alias, mapper));
    }
}
