package com.snk.jlinq.stream.expression;

import com.snk.jlinq.stream.DataSelector;
import com.snk.jlinq.stream.StreamContext;

public abstract class ExpressionValue<T> {

    public static <T> ExpressionValue<T> fromScalar(T value) {
        return new Scalar<>(value);
    }

    public static <T> ExpressionValue<T> fromExtractor(DataSelector extractor) {
        return new Extractor<>(extractor);
    }

    public abstract <R> T getValue(StreamContext streamContext, R in);

    public static class Extractor<T> extends ExpressionValue<T> {
        private final DataSelector reference;

        public Extractor(DataSelector reference) {
            this.reference = reference;
        }

        @Override
        public <R> T getValue(StreamContext streamContext, R in) {
            return streamContext.extractValue(reference, in);
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
