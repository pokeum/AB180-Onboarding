package product.dp.io.sdk.ipc.google.installreferrer

import android.content.Context
import kotlinx.coroutines.suspendCancellableCoroutine
import product.dp.io.sdk.CustomLog
import product.dp.io.sdk.ipc.google.installreferrer.api.InstallReferrerClient
import product.dp.io.sdk.ipc.google.installreferrer.api.ReferrerDetails
import kotlin.coroutines.resume

class GooglePlayInstallReferrer(private val context: Context) {

    private var referrerClient: InstallReferrerClient = InstallReferrerClient(context)

    suspend fun getReferrerDetails(): ReferrerDetails? {
        return suspendCancellableCoroutine { continuation ->
            referrerClient.startConnection { responseCode ->
                when (responseCode) {
                    InstallReferrerClient.InstallReferrerResponse.OK -> {
                        CustomLog.sdkInfo(TAG, "Connection established.")
                        val response: ReferrerDetails = referrerClient.installReferrer
                        referrerClient.endConnection()
                        continuation.resume(response)
                    }
                    InstallReferrerClient.InstallReferrerResponse.FEATURE_NOT_SUPPORTED -> {
                        CustomLog.sdkInfo(TAG, "API not available on the current Play Store app.")
                        continuation.resume(null)
                    }
                    InstallReferrerClient.InstallReferrerResponse.SERVICE_UNAVAILABLE -> {
                        CustomLog.sdkInfo(TAG, "Connection couldn't be established.")
                        continuation.resume(null)
                    }
                    InstallReferrerClient.InstallReferrerResponse.SERVICE_DISCONNECTED -> {
                        CustomLog.sdkInfo(TAG, "Lost connections to Google Play.")
                        continuation.resume(null)
                    }
                }
            }
        }
    }

    companion object {
        const val TAG: String = "GooglePlayInstallReferrer(IPC)"
    }
}