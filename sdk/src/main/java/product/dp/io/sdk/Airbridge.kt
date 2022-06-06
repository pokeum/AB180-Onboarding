package product.dp.io.sdk

import android.app.Application

object Airbridge {

    fun init(app: Application) {
        app.registerActivityLifecycleCallbacks(LifecycleProvider())
    }

    fun trackEvent(category: String, action: String? = null, value: Int? = null) {
        Tracker.track(category, action, value)
    }
}