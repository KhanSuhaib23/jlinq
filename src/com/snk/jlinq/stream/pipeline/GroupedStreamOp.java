package com.snk.jlinq.stream.pipeline;

import com.snk.jlinq.function.MemberAccessor;
import com.snk.jlinq.stream.EnrichedStream;
import com.snk.jlinq.stream.util.StreamOperations;

import java.util.List;

public class GroupedStreamOp<GroupedType, OriginalType> implements StreamOp<GroupedType, OriginalType> {
    private final StreamOp<OriginalType, OriginalType> baseStream;
    private final List<MemberAccessor> memberAccessors;

    public GroupedStreamOp(StreamOp<OriginalType, OriginalType> baseStream, List<MemberAccessor> memberAccessors) {
        this.baseStream = baseStream;
        this.memberAccessors = memberAccessors;
    }

    public StreamOp<OriginalType, OriginalType> baseStream() {
        return baseStream;
    }

    @Override
    public EnrichedStream<GroupedType, OriginalType> outputStream() {
        return StreamOperations.groupBy(baseStream.outputStream(), memberAccessors);
    }
}
