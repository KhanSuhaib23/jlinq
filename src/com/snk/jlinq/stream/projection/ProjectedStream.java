package com.snk.jlinq.stream.projection;

import com.snk.jlinq.function.MemberAccessor;
import com.snk.jlinq.stream.EnrichedStream;
import com.snk.jlinq.stream.SelectStream;
import com.snk.jlinq.stream.pipeline.StreamOp;
import com.snk.jlinq.stream.util.StreamProjector;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// Projection are essentially taking some stream and selecting/projecting parts of it to the output
// it operates on some operating type OT and returns some new type RT
public abstract class ProjectedStream<RT, GT, OT> extends SelectStream<RT> {
    private final StreamOp<GT, OT> operatingStream;
    private final List<MemberAccessor> projections;

    public ProjectedStream(StreamOp<GT, OT> operatingStream, MemberAccessor projection) {
        this.operatingStream = operatingStream;
        this.projections = Arrays.asList(projection);
    }

    public ProjectedStream(StreamOp<GT, OT> operatingStream, List<MemberAccessor> projections) {
        this.operatingStream = operatingStream;
        this.projections = projections;
    }

    public ProjectedStream(StreamOp<GT, OT> operatingStream, List<MemberAccessor> projections, MemberAccessor additionalProjection) {
        this.operatingStream = operatingStream;
        this.projections = Stream.concat(projections.stream(), Stream.of(additionalProjection)).collect(Collectors.toList());
    }

    public List<MemberAccessor> projections() {
        return projections;
    }

    protected StreamOp<GT, OT> baseOperatingStream() {
        return operatingStream;
    }

    @Override
    protected Stream<RT> underlyingStream() {
        return outputStream().stream();
    }

    @Override
    public EnrichedStream<RT> outputStream() {
        return StreamProjector.project(operatingStream.outputStream(), projections());
    }
}
