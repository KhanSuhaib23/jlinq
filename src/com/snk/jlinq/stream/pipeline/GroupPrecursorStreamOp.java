package com.snk.jlinq.stream.pipeline;

import com.snk.jlinq.function.MemberAccessor;
import com.snk.jlinq.stream.EnrichedStream;

import java.util.List;

public class GroupPrecursorStreamOp<GT0, GTN, OT> implements StreamOp<GTN, OT> {
    private final StreamOp<GT0, OT> baseStream;

    public GroupPrecursorStreamOp(StreamOp<GT0, OT> baseStream) {
        this.baseStream = baseStream;
    }

    public StreamOp<GT0, OT> baseStream() {
        return baseStream;
    }

    @Override
    public EnrichedStream<GTN> outputStream() {
        throw new RuntimeException("never called");
    }
}
