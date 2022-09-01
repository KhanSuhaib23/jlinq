package com.snk.jlinq.grammar.group;

import com.snk.jlinq.grammar.ExpectingOrderBy;
import com.snk.jlinq.stream.DataSelector;
import com.snk.jlinq.stream.operation.GroupedStreamBuilderOp;
import com.snk.jlinq.stream.operation.StreamOp;
import com.snk.jlinq.util.ListUtil;

import java.util.Arrays;
import java.util.List;

public class InGroupBy<GroupedType, OutputType> extends ExpectingOrderBy<GroupedType, OutputType> {
    protected final List<DataSelector> groupBys;

    public InGroupBy(StreamOp<GroupedType, OutputType> operatingStream, List<DataSelector> groupBys) {
        super(operatingStream);
        this.groupBys = groupBys;
    }

    public InGroupBy(StreamOp<GroupedType, OutputType> operatingStream, DataSelector groupBys) {
        super(operatingStream);
        this.groupBys = Arrays.asList(groupBys);
    }

    public InGroupBy(StreamOp<GroupedType, OutputType> operatingStream, List<DataSelector> groupBys, DataSelector additionalGroupBy) {
        super(operatingStream);
        this.groupBys = ListUtil.concat(groupBys, additionalGroupBy);
    }

    @Override
    public StreamOp<GroupedType, OutputType> operatingStream() {
        GroupedStreamBuilderOp<GroupedType, OutputType> builderOp = (GroupedStreamBuilderOp<GroupedType, OutputType>) operatingStream;
        return builderOp.buildGroup(groupBys);
    }
}
