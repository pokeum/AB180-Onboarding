package product.dp.io.sdk.reflection.google.installreferrer.obj;

import product.dp.io.sdk.reflection.Reflection;
import product.dp.io.sdk.reflection.ReflectionException;

public class InstallReferrerClient extends Reflection {

    public InstallReferrerClient(Object object) throws ReflectionException {
        super(object);
    }

    public void startConnection(InstallReferrerStateListener listener) throws ReflectionException {
        invokeMethod("startConnection",
                new Class[] { listener.getTargetClass() },
                new Object[] { listener.getTargetClass().cast(listener.getProxy()) });
    }

    public void endConnection() throws ReflectionException {
        invokeMethod("endConnection", null, null);
    }

    public ReferrerDetails getInstallReferrer() throws ReflectionException {
        Object referrerDetails = invokeMethod("getInstallReferrer", null, null);
        return new ReferrerDetails(referrerDetails);
    }
}