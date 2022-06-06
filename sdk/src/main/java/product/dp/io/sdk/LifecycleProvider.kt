package product.dp.io.sdk

import android.app.Activity
import android.app.Application
import android.os.Bundle
import java.util.concurrent.atomic.AtomicBoolean

class LifecycleProvider: Application.ActivityLifecycleCallbacks {

    private var appInLaunched = AtomicBoolean(false)
    private var appInForegrounded = AtomicBoolean(false)
    private var appConfigurationChanged = AtomicBoolean(false)

    private var activityCreatedCount: Int = 0
    private var activityStartCount: Int = 0

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        activityCreatedCount ++
        if (activityCreatedCount == 1 &&
            !appInLaunched.getAndSet(true) &&
            !appInForegrounded.getAndSet(true) &&
            !appConfigurationChanged.get()) {
            Lifecycle(activity, Lifecycle.State.LAUNCH).trackEvent()
        }
    }

    override fun onActivityStarted(activity: Activity) { activityStartCount ++ }

    override fun onActivityResumed(activity: Activity) {
        if (appInLaunched.get() &&
            !appInForegrounded.getAndSet(true) &&
            !appConfigurationChanged.get()) {
            Lifecycle(activity, Lifecycle.State.FOREGROUND).trackEvent()
        } else if (appInLaunched.get() &&
            appInForegrounded.get() &&
            !appConfigurationChanged.get()) {
            Lifecycle(activity, Lifecycle.State.INBOUND_DEEPLINK_MOVED).trackEvent()
        } else if (appConfigurationChanged.getAndSet(false)) {
            Lifecycle(activity, Lifecycle.State.UNDEFINED).trackEvent()
        }
    }

    override fun onActivityPaused(activity: Activity) { }

    override fun onActivityStopped(activity: Activity) {
        activityStartCount --
        appConfigurationChanged.set(activity.isChangingConfigurations)
        if (activityStartCount <= 0 &&
            appInForegrounded.getAndSet(false) &&
            !appConfigurationChanged.get()) {
            Lifecycle(activity, Lifecycle.State.BACKGROUND).trackEvent()
        }
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) { }

    override fun onActivityDestroyed(activity: Activity) { activityCreatedCount -- }
}
