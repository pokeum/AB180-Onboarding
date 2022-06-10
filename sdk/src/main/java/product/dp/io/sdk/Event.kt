package product.dp.io.sdk

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import org.json.JSONObject

typealias EventID = Int

class Event(private val category: String,
            private val action: String? = null,
            private val value: Int? = null) {

    private var processCounter = 0

    val id: EventID = generatedEventCounter++
    init {
        CustomLog.sdkDebug("Add Background Task (id-$id)", getJsonString())
    }

    fun getJsonString() = convertToJson().toString()

    private fun convertToJson(): JSONObject {
        val jo = JSONObject()

        jo.put(JSON_CATEGORY, category)
        if (action != null)
            jo.put(JSON_ACTION, action)
        if (value != null)
            jo.put(JSON_VALUE, value)

        return jo
    }

    @Throws
    suspend fun postAndGetResponse(): String =
        withContext(Dispatchers.IO) {
            //delay(3000) // DEBUG
            processCounter++
            try {
                val simpleHttpJson = SimpleHttpJson(getJsonString())
                simpleHttpJson.sendPost()
            } catch (e: Exception) { throw Exception(e) }
        }

    @Throws
    suspend fun postAndGetResponse(scheme: String, host: String, path: String): String =
        withContext(Dispatchers.IO) {
            processCounter++
            try {
                val simpleHttpJson = SimpleHttpJson(scheme, host, path, getJsonString())
                simpleHttpJson.sendPost()
            } catch (e: Exception) { throw Exception(e) }
        }

    /* 수행 횟수를 제한한다. */
    fun stop() { processCounter = MAX_PROCESS_COUNT }
    fun isProcessable(): Boolean = (processCounter < MAX_PROCESS_COUNT)
    fun getProcessCounter(): Int = processCounter

    companion object {
        private const val JSON_CATEGORY = "category"
        private const val JSON_ACTION = "action"
        private const val JSON_VALUE = "value"

        private const val MAX_PROCESS_COUNT = 5

        private var generatedEventCounter: EventID = 0
        fun getCounter(): EventID = generatedEventCounter
    }
}