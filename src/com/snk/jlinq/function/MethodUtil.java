package com.snk.jlinq.function;

import com.snk.jlinq.reflect.ReflectionUtil;

import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Method;

public class MethodUtil {


    public static <T, R> Method getMethodFromMethodReference(Function1<T, R> f) {
        return ReflectionUtil.getMethodOfName(f.getClass(), "writeReplace")
                .map(writeReplace -> (SerializedLambda) ReflectionUtil.invoke(writeReplace, f))
                .map(lambda -> ReflectionUtil.getMethodOfName(lambda.getImplClass().replace('/', '.'), lambda.getImplMethodName())
                        .orElseThrow(() -> new RuntimeException("Anonymous lambdas are not supported. Use ClassName::methodName syntax."))
                ).orElseThrow(() -> new RuntimeException("Cannot find method writeReplace method on the function. Use a Serialized lambda."));
    }
}
