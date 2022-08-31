package com.snk.jlinq.stream.pipeline;

import com.snk.jlinq.function.MemberAccessor;
import com.snk.jlinq.stream.EnrichedStream;

import java.util.List;

public class GroupedStreamBuilderOp<GroupedType, OriginalType> implements StreamOp<GroupedType, OriginalType> {
    private final StreamOp<OriginalType, OriginalType> baseStream;

    public GroupedStreamBuilderOp(StreamOp<OriginalType, OriginalType> baseStream) {
        this.baseStream = baseStream;
    }

    public GroupedStreamOp<GroupedType, OriginalType> buildGroup(List<MemberAccessor> groupBys) {
        return new GroupedStreamOp<>(baseStream, groupBys);
    }

    public <NewGroupedType> GroupedStreamBuilderOp<NewGroupedType, OriginalType> newGroup() {
        return new GroupedStreamBuilderOp<>(baseStream);
    }

    @Override
    public EnrichedStream<GroupedType, OriginalType> outputStream() {
        throw new RuntimeException("never called");
    }
}
