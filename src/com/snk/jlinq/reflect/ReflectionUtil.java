package com.snk.jlinq.reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;

public class ReflectionUtil {
    public static <T> Optional<Method> getMethodOfName(Class<T> clazz, String name) {
        try {
            return Optional.of(clazz.getDeclaredMethod(name));
        } catch (NoSuchMethodException e) {
            return Optional.empty();
        }
    }

    public static <T> Optional<Method> getMethodOfName(String className, String methodName) {
        try {
            return Optional.of(Class.forName(className).getDeclaredMethod(methodName));
        } catch (NoSuchMethodException | ClassNotFoundException e) {
            return Optional.empty();
        }
    }

    public static <T> T invoke(Method method, Object object, Object ...params) {
        try {
            method.setAccessible(true);
            return (T) method.invoke(object, params);
        } catch (IllegalAccessException | InvocationTargetException | ClassCastException e) {
            throw new RuntimeException(e);
        }
    }
}
