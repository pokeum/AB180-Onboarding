package product.dp.io.sdk.observer

import product.dp.io.sdk.Tracker

class EventObserver(private val publisher: Publisher) : Observer {

    init { publisher.add(this) }

    override fun update(category: String, action: String?, value: Int?) {
        Tracker.track(category, action, value)
    }
}