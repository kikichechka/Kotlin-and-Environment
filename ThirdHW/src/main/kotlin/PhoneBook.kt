class PhoneBook {
    private val listContact: MutableSet<Person> = mutableSetOf()

    fun searchContactByName(name: String): Boolean = listContact.any { p ->
        p.name == name
    }

    fun searchContactByPhoneOrEmail(phone: String): List<Person> {
        return listContact.filter { p -> p.getListPhone().contains(phone) || p.getListEmail().contains(phone) }
    }

    fun addContactPhone(name: String, phone: String) {
        listContact.last { p -> p.name == name }.addPhone(phone)
    }

    fun addContactEmail(name: String, email: String) {
        listContact.last { p -> p.name == name }.addEmail(email)
    }

    fun addPerson(person: Person) {
        listContact.add(person)
    }

    fun getPerson(name: String): Person {
        return listContact.first { p -> p.name == name }
    }
}
