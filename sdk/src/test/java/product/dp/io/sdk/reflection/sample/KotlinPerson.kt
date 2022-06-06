package product.dp.io.sdk.reflection.sample

data class KotlinPerson (@JvmField var name: String, val age: Int) {

    init {
        println("Create KotlinPerson...")
    }

    fun printAllProps(): String {
        println("name: ${name}, age: $age")
        return "printAllProps"
    }
}