package com.snk.jlinq.util;

import com.snk.jlinq.function.Function1;

import java.lang.invoke.SerializedLambda;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodUtil {
    public static <T, R> Method getMethodFromMethodReference(Function1<T, R> f) {
        return ReflectionUtil.findMethod(f.getClass(), "writeReplace")
                .map(writeReplace -> (SerializedLambda) MethodUtil.invoke(writeReplace, f))
                .map(lambda -> ReflectionUtil.findMethod(lambda.getImplClass().replace('/', '.'), lambda.getImplMethodName())
                        .orElseThrow(() -> new RuntimeException("Anonymous lambdas are not supported. Use ClassName::methodName syntax."))
                ).orElseThrow(() -> new RuntimeException("Cannot find method writeReplace method on the function. Use a Serialized lambda."));
    }

    @SuppressWarnings("unchecked")
    public static <T> T invoke(Method method, Object object, Object ...params) {
        try {
            method.setAccessible(true);
            return (T) method.invoke(object, params);
        } catch (IllegalAccessException | InvocationTargetException | ClassCastException e) {
            throw new RuntimeException(e);
        }
    }
}
