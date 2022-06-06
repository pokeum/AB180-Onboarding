package product.dp.io.sdk.ipc.google.identifier

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.os.RemoteException
import product.dp.io.sdk.CustomLog.Companion.sdkVerbose

class AndroidAdvertisingId(val context: Context) : ServiceConnection {

    private var iRemoteService: IAdvertisingIdService? = null
    private var connected = false

    private var mListener: AndroidAdvertisingIdListener? = null

    fun startConnection(listener: AndroidAdvertisingIdListener) {
        mListener = listener
        if (!connected) {
            val intent = Intent("com.google.android.gms.ads.identifier.service.START")
            intent.setPackage("com.google.android.gms")
            context.applicationContext.bindService(intent, this, Context.BIND_AUTO_CREATE)
            connected = true
        }
    }

    fun endConnection() {
        if (connected) {
            context.applicationContext.unbindService(this)
            iRemoteService = null
            connected = false
        }
    }

    override fun onServiceConnected(className: ComponentName?, service: IBinder?) {
        iRemoteService = IAdvertisingIdService.Stub.asInterface(service)
        connected = true
        try {
            mListener?.getId(iRemoteService?.id)
        } catch (e: RemoteException) {
            sdkVerbose(TAG, "Fail getId(): ${e.message}")
        }
    }

    override fun onServiceDisconnected(className: ComponentName?) {
        iRemoteService = null
        connected = false
    }

    companion object {
        private const val TAG = "AndroidAdvertisingId(IPC)"
    }
}