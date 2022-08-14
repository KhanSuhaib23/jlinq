package com.snk.jlinq.stream;

import com.snk.jlinq.core.ListUtil;
import com.snk.jlinq.data.Condition;
import com.snk.jlinq.function.MemberAccessor;
import com.snk.jlinq.stream.pipeline.NoOpMapStreamOp;
import com.snk.jlinq.stream.pipeline.StreamOp;

import java.util.Arrays;
import java.util.List;

public class InGroupBy<T, OT> extends SortableStream<T> {
    protected final StreamOp<OT> underlyingStream;
    protected final List<MemberAccessor> groupBys;

    public InGroupBy(NoOpMapStreamOp<OT, T> operatingStream, List<MemberAccessor> groupBys) {
        super(operatingStream);
        underlyingStream = operatingStream.streamOp();
        this.groupBys = groupBys;
    }

    public InGroupBy(NoOpMapStreamOp<OT, T> operatingStream, MemberAccessor groupBys) {
        super(operatingStream);
        underlyingStream = operatingStream.streamOp();
        this.groupBys = Arrays.asList(groupBys);
    }

    public InGroupBy(NoOpMapStreamOp<OT, T> operatingStream, List<MemberAccessor> groupBys, MemberAccessor additionalGroupBy) {
        super(operatingStream);
        underlyingStream = operatingStream.streamOp();
        this.groupBys = ListUtil.addOne(groupBys, additionalGroupBy);
    }

    @Override
    public StreamOp<T> operatingStream() {
        return null;
    }
}
