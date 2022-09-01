package com.snk.jlinq.grammar.projection;

import com.snk.jlinq.grammar.SelectStream;
import com.snk.jlinq.stream.EnrichedStream;
import com.snk.jlinq.stream.MemberAccessor;
import com.snk.jlinq.stream.operation.StreamOp;
import com.snk.jlinq.util.ListUtil;
import com.snk.jlinq.util.StreamOperations;

import java.util.Arrays;
import java.util.List;

public abstract class ProjectedStream<SelectType, GroupedType, OriginalType> extends SelectStream<SelectType, SelectType> {
    private final StreamOp<GroupedType, OriginalType> operatingStream;
    private final List<MemberAccessor> projections;

    public ProjectedStream(StreamOp<GroupedType, OriginalType> operatingStream, List<MemberAccessor> projections) {
        this.operatingStream = operatingStream;
        this.projections = projections;
    }

    public ProjectedStream(StreamOp<GroupedType, OriginalType> operatingStream, MemberAccessor projection) {
        this(operatingStream, Arrays.asList(projection));
    }

    public ProjectedStream(StreamOp<GroupedType, OriginalType> operatingStream, List<MemberAccessor> projections, MemberAccessor additionalProjection) {
        this(operatingStream, ListUtil.concat(projections, additionalProjection));
    }

    public List<MemberAccessor> projections() {
        return projections;
    }

    protected StreamOp<GroupedType, OriginalType> baseOperatingStream() {
        return operatingStream;
    }

    @Override
    public EnrichedStream<SelectType, SelectType> outputStream() {
        return StreamOperations.project(operatingStream.outputStream(), projections());
    }
}
