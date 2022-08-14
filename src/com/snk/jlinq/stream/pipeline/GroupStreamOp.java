package com.snk.jlinq.stream.pipeline;

import com.snk.jlinq.core.ListUtil;
import com.snk.jlinq.function.MemberAccessor;
import com.snk.jlinq.stream.EnrichedStream;

import java.util.List;

public class GroupStreamOp<T, OT> implements StreamOp<T> {
    private final StreamOp<OT> underlyingStream;
    private final List<MemberAccessor> groupBy;

    public GroupStreamOp(StreamOp<OT> underlyingStream, MemberAccessor groupBy) {
        this.underlyingStream = underlyingStream;
        this.groupBy = List.of(groupBy);
    }

    public GroupStreamOp(StreamOp<OT> underlyingStream, List<MemberAccessor> groupBy) {
        this.underlyingStream = underlyingStream;
        this.groupBy = groupBy;
    }

    public GroupStreamOp(StreamOp<OT> underlyingStream, List<MemberAccessor> groupBy, MemberAccessor additionalGroupBy) {
        this.underlyingStream = underlyingStream;
        this.groupBy = ListUtil.addOne(groupBy, additionalGroupBy);
    }

    public StreamOp<OT> underlyingStream() {
        return underlyingStream;
    }

    public List<MemberAccessor> groupBy() {
        return groupBy;
    }

    @Override
    public EnrichedStream<T> outputStream() {
        return null; // TODO implement this
    }
}
