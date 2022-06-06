package product.dp.io.sdk.observer

class EventPublisher : Publisher {

    private val observers by lazy { ArrayList<Observer>() }

    private var category: String = ""
    private var action: String? = null
    private var value: Int? = null

    override fun add(observer: Observer) { observers.add(observer) }
    override fun delete(observer: Observer) { observers.remove(observer) }

    override fun notifyObserver() {
        for (observer in observers) {
            observer.update(category, action, value)
        }
    }

    fun sendEvent(category: String,
               action: String? = null,
               value: Int? = null) {
        this.category = category
        this.action = action
        this.value = value
        notifyObserver()
    }
}