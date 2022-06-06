package product.dp.io.sdk.reflection.google.identifier;

import android.content.Context;

import product.dp.io.sdk.reflection.Reflection;
import product.dp.io.sdk.reflection.ReflectionException;

public class AdvertisingIdClient extends Reflection {

    public AdvertisingIdClient() throws ReflectionException {
        super("com.google.android.gms.ads.identifier.AdvertisingIdClient");
    }

    public Object getAdvertisingIdInfo(Context context) throws ReflectionException {
        return invokeStaticMethod("getAdvertisingIdInfo",
                new Class[] { Context.class },
                new Object[] { context });
    }
}
