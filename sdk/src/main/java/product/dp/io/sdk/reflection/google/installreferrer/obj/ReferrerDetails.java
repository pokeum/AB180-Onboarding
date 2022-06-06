package product.dp.io.sdk.reflection.google.installreferrer.obj;

import product.dp.io.sdk.reflection.Reflection;
import product.dp.io.sdk.reflection.ReflectionException;

public class ReferrerDetails extends Reflection {

    public ReferrerDetails(Object object) throws ReflectionException {
        super(object);
    }

    public String getInstallReferrer() {
        try {
            return (String) invokeMethod("getInstallReferrer", null, null);
        } catch (ReflectionException e) { return null; }
    }

    public Long getReferrerClickTimestampSeconds() {
        try {
            return (Long) invokeMethod("getReferrerClickTimestampSeconds", null, null);
        } catch (ReflectionException e) { return null; }
    }

    public Long getInstallBeginTimestampSeconds() {
        try {
            return (Long) invokeMethod("getInstallBeginTimestampSeconds", null, null);
        } catch (ReflectionException e) { return null; }
    }

    public Boolean getGooglePlayInstantParam() {
        try {
            return (Boolean) invokeMethod("getGooglePlayInstantParam", null, null);
        } catch (ReflectionException e) { return null; }
    }

    public Long getReferrerClickTimestampServerSeconds() {
        try {
            return (Long) invokeMethod("getReferrerClickTimestampServerSeconds", null, null);
        } catch (ReflectionException e) { return null; }
    }

    public Long getInstallBeginTimestampServerSeconds() {
        try {
            return (Long) invokeMethod("getInstallBeginTimestampServerSeconds", null, null);
        } catch (ReflectionException e) { return null; }
    }

    public String getInstallVersion() {
        try {
            return (String) invokeMethod("getInstallVersion", null, null);
        } catch (ReflectionException e) { return null; }
    }
}