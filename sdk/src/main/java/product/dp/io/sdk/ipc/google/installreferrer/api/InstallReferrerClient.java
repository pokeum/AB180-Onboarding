package product.dp.io.sdk.ipc.google.installreferrer.api;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

import product.dp.io.sdk.CustomLog;
import product.dp.io.sdk.ipc.google.installreferrer.IGetInstallReferrerService;

public class InstallReferrerClient {
    private static final String TAG = "InstallReferrerClient(IPC)";
    private static final int PLAY_STORE_MIN_APP_VER = 80837300;
    private static final String SERVICE_PACKAGE_NAME = "com.android.vending";
    private static final String SERVICE_NAME = "com.google.android.finsky.externalreferrer.GetInstallReferrerService";
    private static final String SERVICE_ACTION_NAME = "com.google.android.finsky.BIND_GET_INSTALL_REFERRER_SERVICE";
    private int mClientState = ClientState.DISCONNECTED;
    private final Context mApplicationContext;
    private IGetInstallReferrerService mService;
    private ServiceConnection mServiceConnection;

    public InstallReferrerClient(Context context) {
        mApplicationContext = context.getApplicationContext();
    }

    public boolean isReady() {
        return mClientState == ClientState.CONNECTED && mService != null && mServiceConnection != null;
    }

    public void startConnection(InstallReferrerStateListener listener) {
        if (isReady()) {
            CustomLog.sdkVerbose(TAG, "Service connection is valid. No need to re-initialize.");
            listener.onInstallReferrerSetupFinished(InstallReferrerResponse.OK);
        } else {
            switch (mClientState) {
                case ClientState.CONNECTING:
                    CustomLog.sdkVerbose(TAG, "Client is already in the process of connecting to the service.");
                    listener.onInstallReferrerSetupFinished(InstallReferrerResponse.DEVELOPER_ERROR);
                    break;

                case ClientState.CLOSED:
                    CustomLog.sdkVerbose(TAG, "Client was already closed and can't be reused. Please create another instance.");
                    listener.onInstallReferrerSetupFinished(InstallReferrerResponse.DEVELOPER_ERROR);
                    break;

                default:
                    CustomLog.sdkVerbose(TAG, "Starting install referrer service setup.");

                    Intent intent = new Intent(SERVICE_ACTION_NAME);
                    intent.setComponent(new ComponentName(SERVICE_PACKAGE_NAME, SERVICE_NAME));

                    List intentServices = mApplicationContext.getPackageManager().queryIntentServices(intent, 0);
                    if (intentServices != null && !intentServices.isEmpty()) {
                        ResolveInfo resolveInfo = (ResolveInfo)intentServices.get(0);
                        if (resolveInfo.serviceInfo != null) {
                            ServiceInfo serviceInfo = resolveInfo.serviceInfo;
                            String packageName = serviceInfo.packageName;
                            if (SERVICE_PACKAGE_NAME.equals(packageName) &&
                                    resolveInfo.serviceInfo.name != null &&
                                    isPlayStoreCompatible()) {
                                try {
                                    mServiceConnection = new InstallReferrerServiceConnection(listener);
                                    if (mApplicationContext.bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE)) {
                                        CustomLog.sdkVerbose(TAG, "Service was bonded successfully.");
                                        return;
                                    }
                                } catch (SecurityException e) {
                                    CustomLog.sdkVerbose(TAG, "No permission to connect to service.");
                                    mClientState = ClientState.DISCONNECTED;
                                    listener.onInstallReferrerSetupFinished(InstallReferrerResponse.PERMISSION_ERROR);
                                    return;
                                }

                                CustomLog.sdkVerbose(TAG, "Connection to service is blocked.");
                                mClientState = ClientState.DISCONNECTED;
                                listener.onInstallReferrerSetupFinished(InstallReferrerResponse.SERVICE_UNAVAILABLE);
                                return;
                            }

                            CustomLog.sdkVerbose(TAG, "Play Store missing or incompatible. Version 8.3.73 or later required.");
                            mClientState = ClientState.DISCONNECTED;
                            listener.onInstallReferrerSetupFinished(InstallReferrerResponse.FEATURE_NOT_SUPPORTED);
                            return;
                        }
                    }

                    mClientState = ClientState.DISCONNECTED;
                    CustomLog.sdkVerbose(TAG, "Install Referrer service unavailable on device.");
                    listener.onInstallReferrerSetupFinished(InstallReferrerResponse.FEATURE_NOT_SUPPORTED);
                    break;
            }
        }
    }

    public void endConnection() {
        mClientState = ClientState.CLOSED;
        if (mServiceConnection != null) {
            CustomLog.sdkVerbose(TAG, "Unbinding from service.");
            mApplicationContext.unbindService(mServiceConnection);
            mServiceConnection = null;
        }
        mService = null;
    }

    public ReferrerDetails getInstallReferrer() throws RemoteException {
        if (isReady()) {
            Bundle bundle = new Bundle();
            bundle.putString("package_name", mApplicationContext.getPackageName());
            try {
                ReferrerDetails referrerDetails = new ReferrerDetails(mService.getInstallReferrer(bundle));
                return referrerDetails;
            } catch (RemoteException e) {
                CustomLog.sdkVerbose(TAG, "RemoteException getting install referrer information");
                mClientState = ClientState.DISCONNECTED;
                throw e;
            }
        } else {
            throw new IllegalStateException("Service not connected. Please start a connection before using the service.");
        }
    }

    private boolean isPlayStoreCompatible() {
        int versionCode;
        try {
            versionCode = mApplicationContext.getPackageManager()
                    .getPackageInfo(SERVICE_PACKAGE_NAME, PackageManager.GET_META_DATA).versionCode;
        } catch (NameNotFoundException e) {
            return false;
        }
        return versionCode >= PLAY_STORE_MIN_APP_VER;
    }

    private final class InstallReferrerServiceConnection implements ServiceConnection {
        private final InstallReferrerStateListener mListener;

        private InstallReferrerServiceConnection(InstallReferrerStateListener listener) {
            if (listener != null) {
                mListener = listener;
            } else {
                throw new RuntimeException("Please specify a listener to know when setup is done.");
            }
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            CustomLog.sdkVerbose(TAG, "Install Referrer service connected.");
            mService = IGetInstallReferrerService.Stub.asInterface(service);
            mClientState = ClientState.CONNECTED;
            mListener.onInstallReferrerSetupFinished(InstallReferrerResponse.OK);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            CustomLog.sdkVerbose(TAG, "Install Referrer service disconnected.");
            mService = null;
            mClientState = ClientState.DISCONNECTED;
            mListener.onInstallReferrerSetupFinished(InstallReferrerResponse.SERVICE_DISCONNECTED);
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface InstallReferrerResponse {
        int SERVICE_DISCONNECTED = -1;
        int OK = 0;
        int SERVICE_UNAVAILABLE = 1;
        int FEATURE_NOT_SUPPORTED = 2;
        int DEVELOPER_ERROR = 3;
        int PERMISSION_ERROR = 4;
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ClientState {
        int DISCONNECTED = 0;
        int CONNECTING = 1;
        int CONNECTED = 2;
        int CLOSED = 3;
    }
}
