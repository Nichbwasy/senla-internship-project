package com.senla.common.reflections;

import java.lang.reflect.ParameterizedType;

public class GenericUtils {

    public static <T> Class<T> getGenericParameterClass(Class actualClass, Integer parameterIndex) {
        return (Class<T>) ((ParameterizedType) actualClass.getGenericSuperclass()).getActualTypeArguments()[parameterIndex];
    }

}
