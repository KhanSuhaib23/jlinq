package com.snk.jlinq.grammar.projection;

import com.snk.jlinq.function.Function1;
import com.snk.jlinq.stream.DataSelector;
import com.snk.jlinq.stream.operation.StreamOp;

public class InSelectExpectingComma1<SelectType1, GroupedType, OriginalType> extends ProjectedStream<SelectType1, GroupedType, OriginalType> {
    public InSelectExpectingComma1(StreamOp<GroupedType, OriginalType> operatingStream, DataSelector projection) {
        super(operatingStream, projection);
    }

    public <IN, OUT> InSelectExpectingComma2<SelectType1, OUT, GroupedType, OriginalType> comma(Function1<IN, OUT> mapper) {
        return new InSelectExpectingComma2<>(baseOperatingStream(), projections(), DataSelector.from(mapper));
    }

    public <OUT> InSelectExpectingComma2<SelectType1, OUT, GroupedType, OriginalType> comma(DataSelector<OUT> selector) {
        return new InSelectExpectingComma2<>(baseOperatingStream(), projections(), selector);
    }

    public <IN, OUT> InSelectExpectingComma2<SelectType1, OUT, GroupedType, OriginalType> comma(String alias, Function1<IN, OUT> mapper) {
        return new InSelectExpectingComma2<>(baseOperatingStream(), projections(), DataSelector.from(alias, mapper));
    }
}
