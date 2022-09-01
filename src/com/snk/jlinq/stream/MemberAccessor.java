package com.snk.jlinq.stream;

import com.snk.jlinq.api.AggregationFunction;
import com.snk.jlinq.function.Function1;
import com.snk.jlinq.util.MethodUtil;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

public class MemberAccessor<T> {
    private final AggregationFunction.Type type;
    private final Method method;
    private final String alias;

    public MemberAccessor(Method method, String alias, AggregationFunction.Type type) {
        this.type = type;
        this.method = method;
        this.alias = alias;
    }

    public AggregationFunction.Type type() {
        return type;
    }

    public Method method() {
        return method;
    }

    public String alias() {
        return alias;
    }

    public StreamAlias streamAlias() {
        return StreamAlias.of(method.getDeclaringClass(), alias);
    }

    public static <IN, OUT> MemberAccessor<OUT> from(String alias, Function1<IN, OUT> function) {
        return new MemberAccessor<>(MethodUtil.getMethodFromMethodReference(function), alias, AggregationFunction.Type.NONE);
    }

    public static <IN, OUT> MemberAccessor<OUT> from(Function1<IN, OUT> function) {
        return new MemberAccessor<>(MethodUtil.getMethodFromMethodReference(function), "", AggregationFunction.Type.NONE);
    }

    public static <IN, OUT> MemberAccessor<List<OUT>> list(Function1<IN, OUT> function) {
        return new MemberAccessor<>(MethodUtil.getMethodFromMethodReference(function), "", AggregationFunction.Type.LIST);
    }

    public static <IN, OUT> MemberAccessor<Long> count(Function1<IN,OUT> mapper) {
        return new MemberAccessor<>(MethodUtil.getMethodFromMethodReference(mapper), "", AggregationFunction.Type.COUNT);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MemberAccessor that = (MemberAccessor) o;

        if (!Objects.equals(method, that.method)) return false;
        return Objects.equals(alias, that.alias);
    }

    @Override
    public int hashCode() {
        int result = method != null ? method.hashCode() : 0;
        result = 31 * result + (alias != null ? alias.hashCode() : 0);
        return result;
    }
}
