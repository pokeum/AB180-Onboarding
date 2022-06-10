package product.dp.io.sdk

import kotlinx.coroutines.*
import java.net.SocketTimeoutException

class Tracker {

    private val ceh = CoroutineExceptionHandler { _, exc ->
        CustomLog.sdkDebug("Tracker-CEH", "Exception: ${exc.printStackTrace()}")
    }

    private val coroutineScope = CoroutineScope(ceh)    // Dispatchers.Default

    fun trackEvent(category: String,
                   action: String? = null,
                   value: Int? = null) {

        trackEvent(Event(category, action, value))
    }

    fun trackEvent(map: Map<String, String>) {
        for (key in map.keys) { trackEvent(Event(key, map[key])) }
    }

    private fun trackEvent(event: Event) {
        coroutineScope.launch(SupervisorJob()) {
            runBackgroundTask(event)
        }
    }

    private suspend fun runBackgroundTask(event: Event): String? {

        val tag = "Run Background Task (id-${event.id})"

        var isCompleted = false
        var response: String? = null

        return withContext(coroutineScope.coroutineContext) {
            while (!isCompleted && event.isProcessable() && isActive) {
                try {
                    response = event.postAndGetResponse()
                    CustomLog.sdkDebug(tag, "Response: $response")
                    isCompleted = true
                } catch (e: BadResponseCodeException) {
                    CustomLog.sdkDebug(tag, "BadResponseCodeException: ${e.message}")
                } catch (e: SocketTimeoutException) {
                    CustomLog.sdkDebug(tag, "SocketTimeoutException: ${e.message}")
                } catch (e: Exception) {
                    CustomLog.sdkDebug(tag, "Exception: ${e.message}")
                }
                if (isCompleted) { CustomLog.sdkDebug(tag, "Complete") }
            }
            response
        }
    }

    companion object {
        @JvmStatic
        fun track(category: String, action: String? = null, value: Int? = null) {
            Tracker().trackEvent(category, action, value)
        }
    }

    fun printCurrentThread(tag: String) {    // DEBUG
        CustomLog.sdkDebug(tag, "Current Thread: ${Thread.currentThread().name}")
    }
}