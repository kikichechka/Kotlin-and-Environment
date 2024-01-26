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

    fun getPhone(): String {
        return listPhone.last()
    }

    fun getEmail(): String {
        return listEmail.last()
    }
}
