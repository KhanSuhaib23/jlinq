package com.snk.jlinq.function;

import com.snk.jlinq.data.StreamAlias;

import java.lang.reflect.Method;
import java.util.Objects;

public class MemberAccessor<T> {
    private final Method method;
    private final String alias;

    public MemberAccessor(Method method, String alias) {
        this.method = method;
        this.alias = alias;
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
        return new MemberAccessor<>(MethodUtil.getMethodFromMethodReference(function), alias);
    }

    public static <IN, OUT> MemberAccessor<OUT> from(Function1<IN, OUT> function) {
        return new MemberAccessor<>(MethodUtil.getMethodFromMethodReference(function), "");
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
