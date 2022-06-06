package product.dp.io.sdk.reflection.sample;

import product.dp.io.sdk.reflection.Reflection;
import product.dp.io.sdk.reflection.ReflectionException;

public class ReflectionObject extends Reflection {

    public ReflectionObject(Object targetObject) throws ReflectionException {
        super(targetObject);
    }

    public ReflectionObject(String className) throws ReflectionException {
        super(className);
    }
}
