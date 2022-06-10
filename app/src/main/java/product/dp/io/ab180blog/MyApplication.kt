package product.dp.io.ab180blog

import android.app.Application
import android.util.Log
import co.ab180.airbridge.Airbridge
import co.ab180.airbridge.AirbridgeConfig
import product.dp.io.sdk.CustomLog
import product.dp.io.sdk.Airbridge as MyAirbridge

class MyApplication : Application() {

    private val registerAll: Boolean = false
    private val registerMine: Boolean = true

    companion object {
        private const val TAG = "MyApplication"
    }

    override fun onCreate() {
        super.onCreate()
        CustomLog.debug(TAG, "onCreate()")

        if (registerAll || registerMine) { MyAirbridge.init(this) }
        if (registerAll || !registerMine) {
            val config = AirbridgeConfig
                .Builder("ablog", "38acf1efa9fc4f0987173f5a76516eb1")
                .setSessionTimeoutSeconds(10)   // 세션 시간 지정 (10초)
                .setLogLevel(Log.DEBUG)         // 로그 표시
                .build()
            Airbridge.init(this, config)
        }
    }
}