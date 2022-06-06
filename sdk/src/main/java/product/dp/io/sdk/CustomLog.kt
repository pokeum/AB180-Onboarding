package product.dp.io.sdk

import android.util.Log

class CustomLog(private val module: Module,
                private val level: Level,
                private val tag: String,
                private val msg: String) {

    enum class Module { APP, SDK }
    enum class Level { VERBOSE, DEBUG, INFO, WARN, ERROR, WTF }

    private fun tag(): String = "${TAG}:${if (module == Module.APP) "APP" else "SDK"}"
    private fun message(): String = "[$tag] $msg"

    fun show() {
        when (level) {
            Level.VERBOSE -> Log.v(tag(), message())
            Level.DEBUG -> Log.d(tag(), message())
            Level.INFO -> Log.i(tag(), message())
            Level.WARN -> Log.w(tag(), message())
            Level.ERROR -> Log.e(tag(), message())
            Level.WTF -> Log.wtf(tag(), message())
        }
    }

    companion object {
        private const val TAG = "CUSTOM_LOG"

        @JvmStatic
        fun debug(tag: String, log: String) = CustomLog(Module.APP, Level.DEBUG, tag, log).show()
        @JvmStatic
        fun sdkVerbose(tag: String, log: String) = CustomLog(Module.SDK, Level.VERBOSE, tag, log).show()
        @JvmStatic
        fun sdkDebug(tag: String, log: String) = CustomLog(Module.SDK, Level.DEBUG, tag, log).show()
        @JvmStatic
        fun sdkInfo(tag: String, log: String) = CustomLog(Module.SDK, Level.INFO, tag, log).show()
    }
}