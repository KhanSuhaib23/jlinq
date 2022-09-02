package com.snk.jlinq.grammar;

import com.snk.jlinq.function.Function1;
import com.snk.jlinq.grammar.projection.InSelectExpectingComma1;
import com.snk.jlinq.stream.DataSelector;
import com.snk.jlinq.stream.EnrichedStream;
import com.snk.jlinq.stream.operation.StreamOp;

public class ExpectingSelect<GroupedType, OriginalType> extends SelectStream<GroupedType, OriginalType> {
    protected final StreamOp<GroupedType, OriginalType> operatingStream;

    public ExpectingSelect(StreamOp<GroupedType, OriginalType> operatingStream) {
        this.operatingStream = operatingStream;
    }

    public <IN, OUT> InSelectExpectingComma1<OUT, GroupedType, OriginalType> select(String alias, Function1<IN, OUT> mapper) {
        return new InSelectExpectingComma1<>(operatingStream(), DataSelector.from(alias, mapper));
    }

    public <IN, OUT> InSelectExpectingComma1<OUT, GroupedType, OriginalType> select(Function1<IN, OUT> mapper) {
        return new InSelectExpectingComma1<>(operatingStream(), DataSelector.from(mapper));
    }

    public <OUT> InSelectExpectingComma1<OUT, GroupedType, OriginalType> select(DataSelector<OUT> selector) {
        return new InSelectExpectingComma1<>(operatingStream(), selector);
    }

    public StreamOp<GroupedType, OriginalType> operatingStream() {
        return operatingStream;
    }

    @Override
    public EnrichedStream<GroupedType, OriginalType> outputStream() {
        return operatingStream().outputStream();
    }
}
