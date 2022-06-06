package product.dp.io.sdk.observer

interface Publisher {
    fun add(observer: Observer)
    fun delete(observer: Observer)
    fun notifyObserver()
}