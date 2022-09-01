package com.snk.jlinq.util;

import java.lang.reflect.Method;
import java.util.Optional;

public class ReflectionUtil {
    public static <T> Optional<Method> findMethod(Class<T> clazz, String name) {
        try {
            return Optional.of(clazz.getDeclaredMethod(name));
        } catch (NoSuchMethodException e) {
            return Optional.empty();
        }
    }

    public static Optional<Method> findMethod(String className, String methodName) {
        try {
            return Optional.of(Class.forName(className).getDeclaredMethod(methodName));
        } catch (NoSuchMethodException | ClassNotFoundException e) {
            return Optional.empty();
        }
    }
}
