package com.snk.jlinq.stream.expression;

import com.snk.jlinq.stream.DataSelector;
import com.snk.jlinq.stream.StreamContext;

public abstract class ExpressionValue<T> {

    public static <T> ExpressionValue<T> fromScalar(T value) {
        return new Scalar<>(value);
    }

    public static <T> ExpressionValue<T> fromExtractor(DataSelector<T> extractor) {
        return new Extractor<>(extractor);
    }

    public abstract <R> T getValue(StreamContext streamContext, R in);

    public static class Extractor<T> extends ExpressionValue<T> {
        private final DataSelector<T> reference;

        public Extractor(DataSelector<T> reference) {
            this.reference = reference;
        }

        @Override
        public <R> T getValue(StreamContext streamContext, R in) {
            return (T) streamContext.get(reference).apply(in);
        }
    }

    public static class Scalar<T> extends ExpressionValue<T> {
        private final T value;

        public Scalar(T value) {
            this.value = value;
        }

        @Override
        public <R> T getValue(StreamContext streamContext, R in) {
            return value;
        }
    }

}
