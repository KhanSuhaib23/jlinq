package com.snk.jlinq.stream.pipeline;

import com.snk.jlinq.stream.EnrichedStream;

public class NoOpMapStreamOp<IT, OT> implements StreamOp<OT> {
    private final StreamOp<IT> streamOp;

    public NoOpMapStreamOp(StreamOp<IT> streamOp) {
        this.streamOp = streamOp;
    }

    public StreamOp<IT> streamOp() {
        return streamOp;
    }

    public <OT2> NoOpMapStreamOp<IT, OT2> noOp() {
        return new NoOpMapStreamOp<>(streamOp);
    }

    @Override
    public EnrichedStream<OT> outputStream() {
        return null;
    }
}
