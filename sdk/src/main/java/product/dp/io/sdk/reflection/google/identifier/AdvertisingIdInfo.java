package product.dp.io.sdk.reflection.google.identifier;

import product.dp.io.sdk.reflection.Reflection;
import product.dp.io.sdk.reflection.ReflectionException;

public class AdvertisingIdInfo extends Reflection {

    public AdvertisingIdInfo(Object object) throws ReflectionException {
        super(object);
    }

    public String getId() {
        try {
            return (String) invokeMethod("getId", null, null);
        } catch (ReflectionException e) { return null; }
    }
}
