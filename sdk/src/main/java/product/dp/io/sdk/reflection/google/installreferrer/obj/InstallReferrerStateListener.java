package product.dp.io.sdk.reflection.google.installreferrer.obj;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import product.dp.io.sdk.CustomLog;
import product.dp.io.sdk.reflection.Reflection;
import product.dp.io.sdk.reflection.ReflectionException;
import product.dp.io.sdk.reflection.google.installreferrer.InstallReferrerResponseListener;

public class InstallReferrerStateListener extends Reflection implements InvocationHandler {

    private final String TAG = "InstallReferrerStateListener(Reflection)";

    private InstallReferrerResponseListener mListener;

    public InstallReferrerStateListener() throws ReflectionException {
        super("com.android.installreferrer.api.InstallReferrerStateListener");
    }

    public Object getProxy() {
        return Proxy.newProxyInstance(
                getTargetClass().getClassLoader(),
                new Class[] { getTargetClass() },
                this);
    }

    @Override
    public Object invoke(Object o, Method method, Object[] args) throws Throwable {
        String method_name = method.getName();

        CustomLog.sdkVerbose(TAG, "invoke working...");

        if (method_name.equals("onInstallReferrerSetupFinished")) {
            final int OK = 0;
            final int responseCode = (Integer) args[0];
            if (responseCode == OK) {
                CustomLog.sdkVerbose(TAG, "Connection established.");
                if (mListener != null) { mListener.onResponseOk(); }
            }
        }
        return null;
    }

    public void registerInstallReferrerResponseListener(InstallReferrerResponseListener listener) {
        mListener = listener;
    }
}