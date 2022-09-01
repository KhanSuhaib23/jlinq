package com.snk.jlinq.grammar.group;

import com.snk.jlinq.grammar.ExpectingOrderBy;
import com.snk.jlinq.stream.MemberAccessor;
import com.snk.jlinq.stream.operation.GroupedStreamBuilderOp;
import com.snk.jlinq.stream.operation.StreamOp;
import com.snk.jlinq.util.ListUtil;

import java.util.Arrays;
import java.util.List;

public class InGroupBy<GT, OT> extends ExpectingOrderBy<GT, OT> {
    protected final List<MemberAccessor> groupBys;

    public InGroupBy(StreamOp<GT, OT> operatingStream, List<MemberAccessor> groupBys) {
        super(operatingStream);
        this.groupBys = groupBys;
    }

    public InGroupBy(StreamOp<GT, OT> operatingStream, MemberAccessor groupBys) {
        super(operatingStream);
        this.groupBys = Arrays.asList(groupBys);
    }

    public InGroupBy(StreamOp<GT, OT> operatingStream, List<MemberAccessor> groupBys, MemberAccessor additionalGroupBy) {
        super(operatingStream);
        this.groupBys = ListUtil.concat(groupBys, additionalGroupBy);
    }

    @Override
    public StreamOp<GT, OT> operatingStream() {
        GroupedStreamBuilderOp<GT, OT> builderOp = (GroupedStreamBuilderOp<GT, OT>) operatingStream;
        return builderOp.buildGroup(groupBys);
    }
}
