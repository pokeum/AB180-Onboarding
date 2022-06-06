package product.dp.io.sdk.reflection.google.installreferrer.obj;

import android.content.Context;

import product.dp.io.sdk.reflection.Reflection;
import product.dp.io.sdk.reflection.ReflectionException;

public class Builder extends Reflection {

    public Builder() throws ReflectionException {
        super("com.android.installreferrer.api.InstallReferrerClient$Builder");
    }

    public void init(Context context) throws ReflectionException {
        createObject(new Class[] { Context.class }, new Object[] { context }, false);
    }

    public Object build() throws ReflectionException {
        return invokeMethod("build", null, null);
    }
}