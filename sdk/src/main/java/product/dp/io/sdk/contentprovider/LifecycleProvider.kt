package product.dp.io.sdk.contentprovider

import android.app.Activity
import android.app.Application
import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import product.dp.io.sdk.CustomLog

class LifecycleProvider : ContentProvider(), Application.ActivityLifecycleCallbacks {

    override fun onCreate(): Boolean {
        val app = context?.applicationContext as Application
        app.unregisterActivityLifecycleCallbacks(this)
        app.registerActivityLifecycleCallbacks(this)
        return true
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        CustomLog.sdkDebug(TAG, "onActivityCreated()")
    }

    override fun onActivityStarted(activity: Activity) {
        CustomLog.sdkDebug(TAG, "onActivityStarted()")
    }

    override fun onActivityResumed(activity: Activity) {
        CustomLog.sdkDebug(TAG, "onActivityResumed()")
    }

    override fun onActivityPaused(activity: Activity) {
        CustomLog.sdkDebug(TAG, "onActivityPaused()")
    }

    override fun onActivityStopped(activity: Activity) {
        CustomLog.sdkDebug(TAG, "onActivityStopped()")
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
        CustomLog.sdkDebug(TAG, "onActivitySaveInstanceState()")
    }

    override fun onActivityDestroyed(activity: Activity) {
        CustomLog.sdkDebug(TAG, "onActivityDestroyed()")
    }

    override fun query(uri: Uri, projection: Array<out String>?, selection: String?, selectionArgs: Array<out String>?, sortOrder: String?): Cursor? = null
    override fun getType(uri: Uri): String? = null
    override fun insert(uri: Uri, values: ContentValues?): Uri? = null
    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int = 0
    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int = 0

    companion object {
        private const val TAG = "ContentProvider"
    }
}