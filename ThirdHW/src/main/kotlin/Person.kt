data class Person(
    val name: String,
    private val listPhone: MutableList<String> = mutableListOf(),
    private val listEmail: MutableList<String> = mutableListOf()
) {
    fun addPhone(number: String) {
        listPhone.add(number)
    }

    fun addEmail(email: String) {
        listEmail.add(email)
    }

    fun getListPhone(): List<String> {
        return listPhone
    }

    fun getListEmail(): List<String> {
        return listEmail
    }

    override fun toString(): String {
        return """
            Имя контакта: $name
            Телефон: $listPhone
            Email: $listEmail
        """.trimIndent()
    }
}
