package com.snk.jlinq.stream.operation;

import com.snk.jlinq.stream.DataSelector;
import com.snk.jlinq.stream.EnrichedStream;
import com.snk.jlinq.util.StreamOperations;

import java.util.List;

public class GroupedStreamOp<GroupedType, OriginalType> implements StreamOp<GroupedType, OriginalType> {
    private final StreamOp<OriginalType, OriginalType> baseStream;
    private final List<DataSelector> selectors;

    public GroupedStreamOp(StreamOp<OriginalType, OriginalType> baseStream, List<DataSelector> selectors) {
        this.baseStream = baseStream;
        this.selectors = selectors;
    }

    public StreamOp<OriginalType, OriginalType> baseStream() {
        return baseStream;
    }

    @Override
    public EnrichedStream<GroupedType, OriginalType> outputStream() {
        return StreamOperations.groupBy(baseStream.outputStream(), selectors);
    }
}
