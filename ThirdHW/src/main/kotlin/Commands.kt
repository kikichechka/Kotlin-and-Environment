sealed class Commands : CommandProperties, Designation {
    val regexEmail = Regex("""^[A-Za-z0-9+_.-]{2,}+@[A-Za-z0-9]{2,}+(.+)[A-Za-z]{2,}${'$'}""")
    val regexPhone = Regex("""^((\+)[0-9]{5,})$""")
    val regexShow = Regex("""^(show )+[A-Za-z]{2,}$""")
    val regexFindPhone = Regex("""^(find )+((\+)[0-9]{5,})${'$'}""")
    val regexFindEmail = Regex("""^(find )+[A-Za-z0-9+_.-]{2,}+@[A-Za-z0-9]{2,}+(.+)[A-Za-z]{2,}${'$'}""")

    class Exit : Commands() {
        override val name: String = EXIT
        override fun isValid(string: String): Boolean {
            return string.matches(Regex("""^(exit)$"""))
        }

        override fun launch() {
            println("Программа завершена!")
        }
    }

    class Help : Commands() {
        override val name: String = HELP
        override fun isValid(string: String): Boolean {
            return string.matches(Regex("""^(help)$"""))
        }

        override fun launch() {
            println(
                """
            exit - выход
            add <Имя> phone <Номер телефона> - добавить контакт с номером телефона
            add <Имя> email <Адрес электронной почты> - добавить контакт с электронной почтой
            show <Имя> - поиск контакта по имени
            find <Номер телефона или Адрес электронной почты> - вывести список людей, для которых записано такое значение
        """.trimIndent()
            )
        }
    }

    class Add(private val phoneBook: PhoneBook) : Commands() {
        override val name: String = ADD
        private lateinit var person: Person
        private lateinit var optionAddCommand: OptionAddCommand

        override fun isValid(string: String): Boolean {
            return checkingAddCommand(string)
        }

        override fun launch() {
            searchAndSaveContact(phoneBook)
        }

        private fun searchAndSaveContact(phoneBook: PhoneBook) {
            when (optionAddCommand) {
                OptionAddCommand.PHONE -> {
                    if (phoneBook.searchContactByName(person.name)) {
                        phoneBook.addContactPhone(person.name, person.getListPhone().last())
                    } else {
                        phoneBook.addPerson(person)
                    }
                    printSavePhone(person.name, person.getListPhone().last())
                }

                OptionAddCommand.EMAIL -> {
                    if (phoneBook.searchContactByName(person.name)) {
                        phoneBook.addContactEmail(person.name, person.getListEmail().last())
                    } else {
                        phoneBook.addPerson(person)
                    }
                    printSaveEmail(person.name, person.getListEmail().last())
                }
            }
        }

        private fun checkingAddCommand(str: String): Boolean {
            return if (checkingLengthCommandAdd(str)) {
                val arrString = str.split(" ")
                implementationAddCommand(arrString[1], arrString[2].lowercase(), arrString[3])
            } else {
                false
            }
        }

        private fun implementationAddCommand(name: String, command: String, saveInformation: String): Boolean {
            when (command) {
                "phone" -> {
                    return if (checkingPhone(saveInformation)) {
                        optionAddCommand = OptionAddCommand.PHONE
                        person = Person(name)
                        person.addPhone(saveInformation)
                        true
                    } else {
                        printErrorSavePhone()
                        false
                    }
                }

                "email" -> {
                    return if (checkingEmail(saveInformation)) {
                        optionAddCommand = OptionAddCommand.EMAIL
                        person = Person(name)
                        person.addEmail(saveInformation)
                        true
                    } else {
                        printErrorSaveEmail()
                        false
                    }
                }

                else -> {
                    return false
                }
            }
        }

        private fun checkingLengthCommandAdd(str: String): Boolean {
            val arrString = str.split(" ")
            return arrString.size == 4 && arrString[1] != ""
        }

        private fun printSavePhone(name: String, phone: String) {
            println("Контакт сохранен: $name $phone")
        }

        private fun printSaveEmail(name: String, phone: String) {
            println("Email сохранен: $name $phone")
        }

        private fun printErrorSavePhone() {
            println("Ошибка! Номер должен начинаться с + и содержать не менее 5 цифр")
        }

        private fun printErrorSaveEmail() {
            println("Ошибка! Имя почтового ящика, почтовый сервис и домер должны содержать не менее двух символов")
        }

        private fun checkingPhone(phone: String): Boolean {
            return phone.matches(regexPhone)
        }

        private fun checkingEmail(email: String): Boolean {
            return email.matches(regexEmail)
        }
    }

    class Show(private val enteredCommand: String, private val phoneBook: PhoneBook) : Commands() {
        override val name: String = SHOW
        override fun isValid(string: String): Boolean {
            return string.matches(regexShow)
        }

        override fun launch() {
            val namePerson = enteredCommand.split(" ").last()
            if (phoneBook.searchContactByName(namePerson)) {
                val person = phoneBook.getPerson(namePerson)
                println(person)
            } else {
                println("Контакт не найден")
            }
        }
    }

    class Find(private val enteredCommand: String, private val phoneBook: PhoneBook) : Commands() {
        override val name: String = SHOW
        override fun isValid(string: String): Boolean {
            return string.matches(regexFindPhone) || string.matches(regexFindEmail)
        }

        override fun launch() {
            val listContacts = phoneBook.searchContactByPhoneOrEmail(enteredCommand.split(" ").last())
            if (listContacts.isNotEmpty())
                listContacts.forEach { p -> println(p) }
            else println("Контакт не найден")
        }
    }
}
