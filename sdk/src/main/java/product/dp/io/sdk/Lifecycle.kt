package product.dp.io.sdk

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import product.dp.io.sdk.ipc.google.installreferrer.GooglePlayInstallReferrer as GooglePlayInstallReferrerAIDL
import product.dp.io.sdk.reflection.google.installreferrer.GooglePlayInstallReferrer as GooglePlayInstallReferrerReflection

class Lifecycle(private val activity: Activity,
                private val state: State) {

    enum class State {
        LAUNCH,
        FOREGROUND,
        BACKGROUND,
        UNDEFINED,
        INBOUND_DEEPLINK_MOVED
    }

    private val isOpen: Boolean = isOpen()
    private val isDeepLink: Boolean = isDeepLink()

    fun trackEvent(setLog: Boolean = true) {
        val event: String? = getEvent()
        if (event != null) {
            if (setLog) { Log.d(TAG, event) }
            if (state != State.UNDEFINED) { Tracker.track(event) }
        }
    }

    private fun getEvent(): String? = when (state) {

        State.LAUNCH ->
            if (isOpen && isDeepLink) { EventType.DEEPLINK_OPEN.category }
            else if (isOpen && !isDeepLink) { EventType.ORGANIC_OPEN.category }
            else if (!isOpen && isDeepLink) { EventType.DEEPLINK_INSTALL.category }
            else { EventType.ORGANIC_INSTALL.category }

        State.FOREGROUND ->
            if (isDeepLink) EventType.DEEPLINK_OPEN.category else EventType.FOREGROUND.category

        State.BACKGROUND -> EventType.BACKGROUND.category

        State.UNDEFINED -> EventType.UNDEFINED.category

        State.INBOUND_DEEPLINK_MOVED ->
            if (isDeepLink) EventType.DEEPLINK_MOVE.category else null
    }

    /** ORGANIC, DEEPLINK 구분 */
    private fun isDeepLink(): Boolean {
        val ret = intent().action.equals(Intent.ACTION_VIEW) &&
                (intent().data != null) &&
                !intent().getBooleanExtra(DEEPLINK_OPENED, false)

        intent().putExtra(DEEPLINK_OPENED, true)

        return ret
    }

    /** INSTALL, OPEN 구분 */
    private fun isOpen(): Boolean {

        val sharedPref = activity.getSharedPreferences(INSTALL_FILE_NAME, Context.MODE_PRIVATE)
        val isOpen: Boolean = sharedPref.getBoolean(INSTALL_KEY, false)
        if (!isOpen) {
            onInstall()
            with(sharedPref.edit()) {
                putBoolean(INSTALL_KEY, true)
                apply()
            }
        }
        return isOpen
    }

    /** INSTALL 상태에만 실행되는 코드 */
    private fun onInstall() {

        val ceh = CoroutineExceptionHandler { _, exc ->
            CustomLog.sdkDebug("Lifecycle-onInstall()-CEH", "Exception: ${exc.printStackTrace()}")
        }

        CoroutineScope(ceh).launch {

            /** Android Advertising ID */
            CustomLog.sdkDebug("AndroidAdvertisingId(Reflection)", UniqueId().reflectionGetAAID(activity) ?: "null")
            CustomLog.sdkDebug("AndroidAdvertisingId(IPC)", UniqueId().getAAID(activity) ?: "null")

            /** Google Play Install Referrer */
            // Reflection
            Tracker.track("GooglePlayInstallReferrer(Reflection)",
               GooglePlayInstallReferrerReflection(activity).getReferrerDetails().installReferrer)
            // IPC (AIDL)
            val referrerDetails = GooglePlayInstallReferrerAIDL(activity).getReferrerDetails()
            if (referrerDetails != null) {
                Tracker.track("GooglePlayInstallReferrer(IPC)", referrerDetails.installReferrer)
            }
        }
    }

    private fun intent() = activity.intent

    enum class EventType(val category: String) {
        ORGANIC_INSTALL("ORGANIC_INSTALL"),
        DEEPLINK_INSTALL("DEEPLINK_INSTALL"),
        ORGANIC_OPEN("ORGANIC_OPEN"),
        DEEPLINK_OPEN("DEEPLINK_OPEN"),
        FOREGROUND("FOREGROUND"),
        BACKGROUND("BACKGROUND"),
        UNDEFINED("UNDEFINED"),
        DEEPLINK_MOVE("DEEPLINK_MOVE")
    }

    companion object {
        private const val TAG = "Airbridge"

        private const val INSTALL_FILE_NAME = "ab180_install"
        private const val INSTALL_KEY = "install"

        const val DEEPLINK_OPENED = "deeplink_opened"
    }
}