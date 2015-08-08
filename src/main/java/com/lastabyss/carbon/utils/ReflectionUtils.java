package com.lastabyss.carbon.utils;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.spigotmc.SneakyThrow;

public class ReflectionUtils {

    /**
     * Sets final field to the provided value
     *
     * @param field - the field which should be modified
     * @param obj - the object whose field should be modified
     * @param newValue - the new value for the field of obj being modified
     * @throws NoSuchFieldException
     * @throws SecurityException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    public static void setFinalField(Field field, Object obj, Object newValue) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        ReflectionUtils.setAccessible(Field.class.getDeclaredField("modifiers")).setInt(field, field.getModifiers() & ~Modifier.FINAL);
        ReflectionUtils.setAccessible(Field.class.getDeclaredField("root")).set(field, null);
        ReflectionUtils.setAccessible(Field.class.getDeclaredField("overrideFieldAccessor")).set(field, null);
        ReflectionUtils.setAccessible(field).set(obj, newValue);
    }

    /**
     * Gets field reflectively
     * 
     * @param clazz
     * @param fieldName
     * @param obj
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T getFieldValue(Class<?> clazz, String fieldName, Object obj) {
        try {
            return (T) ReflectionUtils.setAccessible(clazz.getDeclaredField(fieldName)).get(obj);
        } catch (Throwable t) {
            SneakyThrow.sneaky(t);
        }
        return null;
    }

    /**
     * Sets accessibleobject accessible state an returns this object
     *
     * @param <T>
     * @param object
     * @return
     */
    public static <T extends AccessibleObject> T setAccessible(T object) {
        object.setAccessible(true);
        return object;
    }

    /**
     * Sets field reflectively
     * 
     * @param clazz
     * @param fieldName
     * @param obj
     * @param value
     */
    public static void setFieldValue(Class<?> clazz, String fieldName, Object obj, Object value) {
        try {
            setAccessible(clazz.getDeclaredField(fieldName)).set(obj, value);
        } catch (Throwable t) {
            SneakyThrow.sneaky(t);
        }
    }

}
