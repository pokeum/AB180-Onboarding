package product.dp.io.sdk.ipc.google.installreferrer.api;

import android.os.Bundle;

public class ReferrerDetails {
    private final Bundle mOriginalBundle;
    private static final String KEY_INSTALL_REFERRER = "install_referrer";
    private static final String KEY_REFERRER_CLICK_TIMESTAMP = "referrer_click_timestamp_seconds";
    private static final String KEY_INSTALL_BEGIN_TIMESTAMP = "install_begin_timestamp_seconds";
    private static final String KEY_GOOGLE_PLAY_INSTANT = "google_play_instant";
    private static final String KEY_REFERRER_CLICK_TIMESTAMP_SERVER = "referrer_click_timestamp_server_seconds";
    private static final String KEY_INSTALL_BEGIN_TIMESTAMP_SERVER = "install_begin_timestamp_server_seconds";
    private static final String KEY_INSTALL_VERSION = "install_version";

    public ReferrerDetails(Bundle bundle) {
        mOriginalBundle = bundle;
    }

    public String getInstallReferrer() {
        return mOriginalBundle.getString(KEY_INSTALL_REFERRER);
    }

    public long getReferrerClickTimestampSeconds() { return mOriginalBundle.getLong(KEY_REFERRER_CLICK_TIMESTAMP); }

    public long getInstallBeginTimestampSeconds() { return mOriginalBundle.getLong(KEY_INSTALL_BEGIN_TIMESTAMP); }

    public boolean getGooglePlayInstantParam() { return mOriginalBundle.getBoolean(KEY_GOOGLE_PLAY_INSTANT); }

    public long getReferrerClickTimestampServerSeconds() { return mOriginalBundle.getLong(KEY_REFERRER_CLICK_TIMESTAMP_SERVER); }

    public long getInstallBeginTimestampServerSeconds() { return mOriginalBundle.getLong(KEY_INSTALL_BEGIN_TIMESTAMP_SERVER); }

    public String getInstallVersion() {
        return mOriginalBundle.getString(KEY_INSTALL_VERSION);
    }
}