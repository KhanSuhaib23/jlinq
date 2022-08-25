package com.snk.jlinq.stream.pipeline;

import com.snk.jlinq.stream.EnrichedStream;

public interface StreamOp<GT, OT> {
    EnrichedStream<GT> outputStream();
}
