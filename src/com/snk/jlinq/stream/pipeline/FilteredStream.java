package com.snk.jlinq.stream.pipeline;

import com.snk.jlinq.data.Condition;
import com.snk.jlinq.stream.EnrichedStream;
import com.snk.jlinq.stream.SelectStream;
import com.snk.jlinq.stream.util.StreamFilter;

import java.util.List;

public class FilteredStream<T> extends SelectStream<T> {
    private final SelectStream<T> stream;
    private final List<Condition> filters;

    public FilteredStream(SelectStream<T> stream, List<Condition> filters) {
        this.stream = stream;
        this.filters = filters;
    }

    @Override
    public EnrichedStream<T> outputStream() {
        return StreamFilter.streamFilter(stream.outputStream(), filters);
    }
}
