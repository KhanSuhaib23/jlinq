package com.snk.jlinq.stream.pipeline;

import com.snk.jlinq.stream.EnrichedStream;

public interface StreamOp<GroupedType, OriginalType> {
    EnrichedStream<GroupedType, OriginalType> outputStream();
}
