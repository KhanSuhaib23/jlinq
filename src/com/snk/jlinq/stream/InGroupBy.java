package com.snk.jlinq.stream;

import com.snk.jlinq.core.ListUtil;
import com.snk.jlinq.data.Condition;
import com.snk.jlinq.function.MemberAccessor;
import com.snk.jlinq.stream.pipeline.GroupStreamOp;
import com.snk.jlinq.stream.pipeline.StreamOp;

import java.util.Arrays;
import java.util.List;

public class InGroupBy<GT, OT> extends SortableStream<GT, OT> {
    protected final List<MemberAccessor> groupBys;
    protected final StreamOp<OT, OT> originalStream;

    public InGroupBy(StreamOp<GT, OT> operatingStream, StreamOp<OT, OT> originalStream, List<MemberAccessor> groupBys) {
        super(operatingStream);
        this.originalStream = originalStream;
        this.groupBys = groupBys;
    }

    public InGroupBy(StreamOp<GT, OT> operatingStream, StreamOp<OT, OT> originalStream, MemberAccessor groupBys) {
        super(operatingStream);
        this.originalStream = originalStream;
        this.groupBys = Arrays.asList(groupBys);
    }

    public InGroupBy(StreamOp<GT, OT> operatingStream, StreamOp<OT, OT> originalStream, List<MemberAccessor> groupBys, MemberAccessor additionalGroupBy) {
        super(operatingStream);
        this.originalStream = originalStream;
        this.groupBys = ListUtil.addOne(groupBys, additionalGroupBy);
    }

    @Override
    public StreamOp<GT, OT> operatingStream() {
        return new GroupStreamOp<>(originalStream, groupBys);
    }
}
