sealed class Commands : CommandProperties, Designation {
    class Exit(override val name: String = "exit") : Commands() {
        override fun isValid(string: String): Boolean {
            return string.matches(Regex("""^(exit)$"""))
        }

        override fun launch() {
            println("Программа завершена!")
        }
    }

    class Help(override val name: String = "help") : Commands() {
        override fun isValid(string: String): Boolean {
            return string.matches(Regex("""^(help)$"""))
        }

        override fun launch() {
            println(
                """
            exit - выход
            add <Имя> phone <Номер телефона> - добавить контакт с номером телефона
            add <Имя> email <Адрес электронной почты> - добавить контакт с электронной почтой
            show - просмотр последней команды
        """.trimIndent()
            )
        }
    }

    class Add(override val name: String = "add") : Commands() {
        private lateinit var person: Person
        private lateinit var optionAddCommand: OptionAddCommand

        override fun isValid(string: String): Boolean {
            return checkingAddCommand(string)
        }

        override fun launch() {
            when (optionAddCommand) {
                OptionAddCommand.PHONE -> printSavePhone(person.name, person.getPhone())
                OptionAddCommand.EMAIL -> printSaveEmail(person.name, person.getEmail())
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
//                    invalidCommandEntered()
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
            return phone.matches(Regex("""^((\+)[0-9]{5,})$"""))
        }

        private fun checkingEmail(email: String): Boolean {
            return email.matches(Regex("""^[A-Za-z0-9+_.-]{2,}+@[A-Za-z0-9]{2,}+(.+)[A-Za-z]{2,}${'$'}"""))
        }
    }

    class Show(private val commands: Commands?, override val name: String = "show") : Commands() {
        override fun isValid(string: String): Boolean {
            return string.matches(Regex("""^(show)$"""))
        }

        override fun launch() {
            commands?.launch()?: println("Not initialized")
        }
    }
}
