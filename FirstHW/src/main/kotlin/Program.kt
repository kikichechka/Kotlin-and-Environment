object Program {
    fun startProgram() {
        var command: Commands?
        do {
            commandsDescription()
            print("Введите команду: ")
            val answer = readln()
            command = lineFeed(answer)

            when (command) {
                Commands.EXIT -> println("Завершение программы!")
                Commands.HELP -> println("Введена команда help")
                Commands.ADD -> checkingAddCommand(answer)
                null -> invalidCommandEntered()
            }
        } while (command != Commands.EXIT)
    }

    private fun commandsDescription() {
        println(
            """
            
            Доступные команды:
            exit
            help
            add <Имя> phone <Номер телефона>
            add <Имя> email <Адрес электронной почты>
        """.trimIndent()
        )
    }

    private fun checkingAddCommand(str: String) {
        if (checkingLengthCommandAdd(str)) {
            val arrString = str.split(" ")
            implementationAddCommand(arrString[2].lowercase(), arrString[1], arrString[3])
        } else {
            invalidCommandEntered()
        }

    }

    private fun implementationAddCommand(command: String, name: String, saveInformation: String) {
        when (command) {
            "phone" -> {
                if (checkingPhone(saveInformation))
                    printSavePhone(name, saveInformation)
                else
                    printErrorSavePhone()
            }

            "email" -> {
                if (checkingEmail(saveInformation))
                    printSaveEmail(name, saveInformation)
                else
                    printErrorSaveEmail()
            }

            else -> invalidCommandEntered()
        }
    }

    private fun checkingLengthCommandAdd(str: String): Boolean {
        val arrString = str.split(" ")
        return arrString.size == 4 && arrString[1] != ""
    }

    private fun invalidCommandEntered() = println("Введена неверная команда")

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

    private fun lineFeed(str: String): Commands? {
        val arrString = str.split(" ")
        return when (arrString[0].lowercase()) {
            Commands.EXIT.name.lowercase() -> Commands.EXIT
            Commands.ADD.name.lowercase() -> Commands.ADD
            Commands.HELP.name.lowercase() -> Commands.HELP
            else -> null
        }
    }
}
