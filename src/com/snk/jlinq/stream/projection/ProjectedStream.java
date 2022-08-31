package com.snk.jlinq.stream.projection;

import com.snk.jlinq.core.ListUtil;
import com.snk.jlinq.function.MemberAccessor;
import com.snk.jlinq.stream.EnrichedStream;
import com.snk.jlinq.stream.SelectStream;
import com.snk.jlinq.stream.pipeline.StreamOp;
import com.snk.jlinq.stream.util.StreamOperations;

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
        this(operatingStream, ListUtil.addOne(projections, additionalProjection));
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
