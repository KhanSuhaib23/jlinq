package com.snk.jlinq.grammar.projection;

import com.snk.jlinq.function.Function1;
import com.snk.jlinq.stream.DataSelector;
import com.snk.jlinq.stream.operation.StreamOp;
import com.snk.jlinq.udt.Tuple2;

import java.util.List;

public class InSelectExpectingComma2<SelectType1, SelectType2, GroupedType, OriginalType>
        extends ProjectedStream<Tuple2<SelectType1, SelectType2>, GroupedType, OriginalType> {
    public InSelectExpectingComma2(StreamOp<GroupedType, OriginalType> operatingStream, List<DataSelector> projections, DataSelector additionalProjection) {
        super(operatingStream, projections, additionalProjection);
    }

    public <IN, OUT> InSelectExpectingComma3<SelectType1, SelectType2, OUT, GroupedType, OriginalType> comma(Function1<IN, OUT> mapper) {
        return comma(DataSelector.from(mapper));
    }

    public <IN, OUT> InSelectExpectingComma3<SelectType1, SelectType2, OUT, GroupedType, OriginalType> comma(String alias, Function1<IN, OUT> mapper) {
        return comma(DataSelector.from(alias, mapper));
    }

    public <IN, OUT> InSelectExpectingComma3<SelectType1, SelectType2, OUT, GroupedType, OriginalType> comma(DataSelector<OUT> selector) {
        return new InSelectExpectingComma3<>(baseOperatingStream(), projections(), selector);
    }
}
