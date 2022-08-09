package com.snk.jlinq.stream.projection;

import com.snk.jlinq.function.MemberAccessor;
import com.snk.jlinq.stream.EnrichedStream;
import com.snk.jlinq.stream.SelectStream;
import com.snk.jlinq.stream.util.StreamProjector;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// Projection are essentially taking some stream and selecting/projecting parts of it to the output
// it operates on some operating type OT and returns some new type RT
public abstract class ProjectedStream<RT, OT> extends SelectStream<RT> {
    private final SelectStream<OT> baseSelectStream;
    private final List<MemberAccessor> projections;

    public ProjectedStream(SelectStream<OT> baseSelectStream, MemberAccessor projection) {
        this.baseSelectStream = baseSelectStream;
        this.projections = Arrays.asList(projection);
    }

    public ProjectedStream(SelectStream<OT> baseSelectStream, List<MemberAccessor> projections) {
        this.baseSelectStream = baseSelectStream;
        this.projections = projections;
    }

    public ProjectedStream(SelectStream<OT> baseSelectStream, List<MemberAccessor> projections, MemberAccessor additionalProjection) {
        this.baseSelectStream = baseSelectStream;
        this.projections = Stream.concat(projections.stream(), Stream.of(additionalProjection)).collect(Collectors.toList());
    }

    public List<MemberAccessor> projections() {
        return projections;
    }

    @Override
    protected Stream<RT> underlyingStream() {
        return StreamProjector.project(baseSelectStream().outputStream(), projections()); // TODO implement projections
    }

    protected SelectStream<OT> baseSelectStream() {
        return baseSelectStream;
    }

    @Override
    public EnrichedStream<RT> outputStream() {
        throw new RuntimeException("Illegal Operation Exception");
    }
}
