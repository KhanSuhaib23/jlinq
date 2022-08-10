package com.snk.jlinq.stream;

import com.snk.jlinq.data.StreamContext;
import com.snk.jlinq.stream.pipeline.RootStreamOp;
import com.snk.jlinq.stream.pipeline.StreamOp;
import com.snk.jlinq.tuple.Tuple2;
import com.snk.jlinq.tuple.Tuple3;

import java.util.Collections;
import java.util.stream.Stream;

public class JoinableJoin3Stream<T1, T2, T3> extends JoinableStream<Tuple3<T1, T2, T3>> {
    public JoinableJoin3Stream(StreamOp<Tuple3<T1, T2, T3>> operatingStream) {
        super(operatingStream);
    }

}
