package com.snk.jlinq.stream.pipeline;

import com.snk.jlinq.function.MemberAccessor;
import com.snk.jlinq.stream.EnrichedStream;
import com.snk.jlinq.stream.util.StreamGroupBy;

import java.util.List;

// GT0 is old
// GTN is new
// group types
public class GroupStreamOp<GT, OT> implements StreamOp<GT, OT> {
    private final StreamOp<OT, OT> baseStream;
    private final List<MemberAccessor> memberAccessors;

    public GroupStreamOp(StreamOp<OT, OT> baseStream, List<MemberAccessor> memberAccessors) {
        this.baseStream = baseStream;
        this.memberAccessors = memberAccessors;
    }

    public StreamOp<OT, OT> baseStream() {
        return baseStream;
    }

    public List<MemberAccessor> memberAccessors() {
        return memberAccessors;
    }

    @Override
    public EnrichedStream<GT> outputStream() {
        return StreamGroupBy.streamGroupBy(baseStream.outputStream(), memberAccessors);
    }
}
