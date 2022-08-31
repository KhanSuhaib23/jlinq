package com.snk.jlinq.stream.group;

import com.snk.jlinq.core.ListUtil;
import com.snk.jlinq.function.MemberAccessor;
import com.snk.jlinq.stream.ExpectingOrderBy;
import com.snk.jlinq.stream.pipeline.GroupedStreamBuilderOp;
import com.snk.jlinq.stream.pipeline.StreamOp;

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
        this.groupBys = ListUtil.addOne(groupBys, additionalGroupBy);
    }

    @Override
    public StreamOp<GT, OT> operatingStream() {
        GroupedStreamBuilderOp<GT, OT> builderOp = (GroupedStreamBuilderOp<GT, OT>) operatingStream;
        return builderOp.buildGroup(groupBys);
    }
}
