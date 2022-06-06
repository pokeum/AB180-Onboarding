package product.dp.io.sdk.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import product.dp.io.sdk.CustomLog;

public abstract class Reflection {

    private final String TAG = this.getClass().getSimpleName();

    private Class targetClass;
    private Object targetObject;

    public Reflection(Object targetObject) throws ReflectionException {
        if (targetObject != null) {
            targetClass = targetObject.getClass();
            this.targetObject = targetObject;
        } else {
            CustomLog.sdkInfo(TAG, "Reflection()");
            throw new ReflectionException("Reflection(): target object is null");
        }
    }

    public Reflection(String className) throws ReflectionException {
        try {
            targetClass = Class.forName(className);
        } catch (ClassNotFoundException e) {
            CustomLog.sdkInfo(TAG, "Reflection()-Exception: " + e.getMessage());
            throw new ReflectionException(e);
        }
    }

    public void createObject(Class[] paramsTypes, Object[] params, boolean isPublic) throws ReflectionException {
        try {
            Constructor cons;
            if (isPublic) { // Access to all public constructor
                cons = targetClass.getConstructor(paramsTypes);
            } else {    // Access to all declared(private) constructor
                cons = targetClass.getDeclaredConstructor(paramsTypes);
            }
            targetObject = cons.newInstance(params);
        } catch (NoSuchMethodException
                | SecurityException
                | InstantiationException
                | IllegalAccessException
                | IllegalArgumentException
                | InvocationTargetException e) {
            CustomLog.sdkInfo(TAG, "createObject()-Exception: " + e.getMessage());
            throw new ReflectionException(e);
        }
    }

    private Object invokeMethod(String methodName, Class[] paramsTypes, Object[] params, boolean isStatic) throws ReflectionException {
        try {
            Method method = targetClass.getMethod(methodName, paramsTypes);
            if (isStatic) {
                return method.invoke(null, params);
            } else {
                return method.invoke(targetObject, params);
            }
        } catch (NoSuchMethodException
                | SecurityException
                | IllegalAccessException
                | IllegalArgumentException
                | InvocationTargetException e) {
            CustomLog.sdkInfo(TAG, "invokeMethod(" + methodName + ")-Exception: " + e.getMessage());
            throw new ReflectionException(e);
        }
    }

    public Object invokeMethod(String methodName, Class[] paramsTypes, Object[] params) throws ReflectionException {
        return invokeMethod(methodName, paramsTypes, params, false);
    }

    public Object invokeStaticMethod(String methodName, Class[] paramsTypes, Object[] params) throws ReflectionException {
        return invokeMethod(methodName, paramsTypes, params, true);
    }

    public <T> T getField(String fieldName) throws ReflectionException {
        try {
            Field field = targetClass.getField(fieldName);
            return (T) field.get(targetObject);
        } catch (NoSuchFieldException
                | SecurityException
                | IllegalAccessException
                | IllegalArgumentException e) {
            CustomLog.sdkInfo(TAG, "getField(" + fieldName +")-Exception: " + e.getMessage());
            throw new ReflectionException(e);
        }
    }

    public <T> boolean setField(String fieldName, T value) throws ReflectionException {
        try {
            Field field = targetClass.getField(fieldName);
            field.set(targetObject, value);
            return true;
        } catch (NoSuchFieldException
                | SecurityException
                | IllegalAccessException
                | IllegalArgumentException e) {
            CustomLog.sdkInfo(TAG, "setField(" + fieldName +")-Exception: " + e.getMessage());
            throw new ReflectionException(e);
        }
    }

    public Class getTargetClass() { return targetClass; }
}