package com.snk.jlinq.grammar.group;

import com.snk.jlinq.stream.DataSelector;
import com.snk.jlinq.stream.operation.StreamOp;
import com.snk.jlinq.udt.Tuple2;

import java.util.List;

public class InGroupByExpectingComma2<GroupType1, GroupType2, OriginalType> extends InGroupBy<Tuple2<GroupType1, GroupType2>, OriginalType> {

    public InGroupByExpectingComma2(StreamOp<Tuple2<GroupType1, GroupType2>, OriginalType> operatingStream, List<DataSelector> groupBys) {
        super(operatingStream, groupBys);
    }

    public InGroupByExpectingComma2(StreamOp<Tuple2<GroupType1, GroupType2>, OriginalType> operatingStream, DataSelector groupBys) {
        super(operatingStream, groupBys);
    }

    public InGroupByExpectingComma2(StreamOp<Tuple2<GroupType1, GroupType2>, OriginalType> operatingStream, List<DataSelector> groupBys, DataSelector additionalGroupBy) {
        super(operatingStream, groupBys, additionalGroupBy);
    }
}
