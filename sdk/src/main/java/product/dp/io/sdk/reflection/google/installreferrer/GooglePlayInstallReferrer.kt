package product.dp.io.sdk.reflection.google.installreferrer

import android.content.Context
import kotlinx.coroutines.suspendCancellableCoroutine
import product.dp.io.sdk.CustomLog
import product.dp.io.sdk.reflection.ReflectionException
import product.dp.io.sdk.reflection.google.installreferrer.obj.Builder
import product.dp.io.sdk.reflection.google.installreferrer.obj.InstallReferrerClient
import product.dp.io.sdk.reflection.google.installreferrer.obj.InstallReferrerStateListener
import product.dp.io.sdk.reflection.google.installreferrer.obj.ReferrerDetails
import kotlin.coroutines.resume

class GooglePlayInstallReferrer(private val context: Context) {

    suspend fun getReferrerDetails(): ReferrerDetails {
        return suspendCancellableCoroutine { continuation ->
            onResponseOk { referrerClient -> continuation.resume(referrerClient.installReferrer) }
        }
    }

    private fun onResponseOk(run: (InstallReferrerClient) -> Unit) {

        try {
            val builder = Builder()
            builder.init(context)
            val referrerClient = InstallReferrerClient(builder.build())

            val listener = InstallReferrerStateListener()
            listener.registerInstallReferrerResponseListener {
                run(referrerClient)
                referrerClient.endConnection()
            }

            referrerClient.startConnection(listener)
        } catch (e: ReflectionException) {
            CustomLog.sdkInfo(TAG, "Something went wrong while getting Google Install Referrer")
        }
    }

    companion object {
        const val TAG = "GooglePlayInstallReferrer(Reflection)"
    }
}