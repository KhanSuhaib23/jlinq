package com.snk.jlinq.data;

import com.snk.jlinq.function.MemberAccessor;
import com.snk.jlinq.reflect.ReflectionUtil;
import com.snk.jlinq.tuple.TupleUtil;

public abstract class ExpressionValue<T> {

    public static <T> ExpressionValue<T> fromScalar(T value) {
        return new Scalar<>(value);
    }

    public static <T> ExpressionValue<T> fromExtractor(MemberAccessor<T> extractor) {
        return new Extractor<>(extractor);
    }

    public abstract <R> T getValue(StreamContext streamContext, R in);

    public static class Extractor<T> extends ExpressionValue<T> {
        private final MemberAccessor<T> reference;

        public Extractor(MemberAccessor<T> reference) {
            this.reference = reference;
        }

        @Override
        public <R> T getValue(StreamContext streamContext, R in) {
            return (T) ReflectionUtil.invoke(reference.method(), streamContext.get(reference.streamAlias()).apply(in));
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
