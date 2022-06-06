package product.dp.io.sdk

import android.content.Context
import android.provider.Settings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import product.dp.io.sdk.ipc.google.identifier.AndroidAdvertisingId
import product.dp.io.sdk.reflection.google.identifier.AdvertisingIdClient
import product.dp.io.sdk.reflection.google.identifier.AdvertisingIdInfo
import java.util.*
import kotlin.coroutines.resume

class UniqueId() {

    fun getUUID() = UUID.randomUUID().toString()

    // Android Advertising ID (AIDL)
    suspend fun getAAID(context: Context): String? {
        return suspendCancellableCoroutine { continuation ->
            val androidAdvertisingId = AndroidAdvertisingId(context)
            androidAdvertisingId.startConnection { id ->
                androidAdvertisingId.endConnection()
                continuation.resume(id)
            }
        }
    }

    // Android Advertising ID (Reflection)
    suspend fun reflectionGetAAID(context: Context): String? =
        withContext(Dispatchers.Default) {
            try {
                AdvertisingIdInfo(AdvertisingIdClient().getAdvertisingIdInfo(context)).id
            } catch (e: Exception) { null }
        }


    // Secure Android ID (NOT RECOMMENDED)
    fun getSecureAndroidId(context: Context): String? =
        Settings.Secure.getString(
            context.applicationContext.contentResolver,
            Settings.Secure.ANDROID_ID)
}