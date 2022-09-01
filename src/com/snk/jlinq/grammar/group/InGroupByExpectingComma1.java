package com.snk.jlinq.grammar.group;

import com.snk.jlinq.function.Function1;
import com.snk.jlinq.stream.DataSelector;
import com.snk.jlinq.stream.operation.GroupedStreamBuilderOp;
import com.snk.jlinq.stream.operation.StreamOp;

import java.util.List;

public class InGroupByExpectingComma1<GroupType1, OriginalType> extends InGroupBy<GroupType1, OriginalType> {

    public InGroupByExpectingComma1(StreamOp<GroupType1, OriginalType> operatingStream, List<DataSelector> groupBys) {
        super(operatingStream, groupBys);
    }

    public InGroupByExpectingComma1(StreamOp<GroupType1, OriginalType> operatingStream, DataSelector groupBys) {
        super(operatingStream, groupBys);
    }

    public InGroupByExpectingComma1(StreamOp<GroupType1, OriginalType> operatingStream, List<DataSelector> groupBys, DataSelector additionalGroupBy) {
        super(operatingStream, groupBys, additionalGroupBy);
    }

    public <IN, OUT> InGroupByExpectingComma2<GroupType1, OUT, OriginalType> comma(Function1<IN, OUT> mapper) {
        GroupedStreamBuilderOp<GroupType1, OriginalType> builderOp = (GroupedStreamBuilderOp<GroupType1, OriginalType>) operatingStream;
        return new InGroupByExpectingComma2<>(builderOp.newGroup(), groupBys, DataSelector.from(mapper));
    }
}
