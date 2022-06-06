package product.dp.io.sdk.observer

interface Observer {
    fun update(category: String,
               action: String? = null,
               value: Int? = null)
}