package com.solvd.delivery.util.reflection;

import java.lang.reflect.Field;

public class ReflectionUtil {

    public static <T> T initWithDefaults(Class<T> clazz) {
        try {
            T object = clazz.getDeclaredConstructor().newInstance();

            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(DefaultValue.class)) {
                    DefaultValue annotation = field.getAnnotation(DefaultValue.class);
                    String value = annotation.value();
                    field.setAccessible(true);
                    setFieldValue(object, field, value);
                }
            }
            return object;
        } catch (Exception e) {
            throw new RuntimeException("Error initializing object", e);
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static void setFieldValue(Object object, Field field, String value) throws IllegalAccessException {
        Class<?> type = field.getType();

        if (type == String.class) {
            field.set(object, value);
        } else if (type == int.class || type == Integer.class) {
            field.set(object, Integer.parseInt(value));
        } else if (type == boolean.class || type == Boolean.class) {
            field.set(object, Boolean.parseBoolean(value));
        } else if (type == double.class || type == Double.class) {
            field.set(object, Double.parseDouble(value));
        } else if (type.isEnum() || type == Enum.class) {
            String normalized = value.trim()
                    .toUpperCase()
                    .replace(' ', '_')
                    .replace('-', '_');

            Object enumValue = Enum.valueOf((Class<? extends Enum>) type, normalized);
            field.set(object, enumValue);
        }
    }
}
