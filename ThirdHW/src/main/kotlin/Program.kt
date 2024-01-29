const val EXIT = "exit"
const val HELP = "help"
const val ADD = "add"
const val SHOW = "show"
const val FIND = "find"

object Program {
    private val phoneBook = PhoneBook()

    fun startProgram() {
        var command: Commands
        do {
            commandsDescription()
            print("Введите команду: ")
            val answer = readln()
            command = readCommand(answer)

            command.launch()

        } while (command !is Commands.Exit)
    }

    private fun commandsDescription() {
        println(
            """
            
            Доступные команды:
            $EXIT
            $HELP
            $ADD <Имя> phone <Номер телефона>
            $ADD <Имя> email <Адрес электронной почты>
            $SHOW <Имя>
            $FIND <Номер телефона или Адрес электронной почты>
        """.trimIndent()
        )
    }

    private fun readCommand(str: String): Commands {
        val arrString = str.split(" ")
        val firstWord = arrString[0].lowercase()
        var command: Commands = Commands.Help()
        when (firstWord) {
            EXIT -> if (Commands.Exit().isValid(str)) command = Commands.Exit()

            HELP -> if (Commands.Help().isValid(str)) command = Commands.Help()

            ADD -> {
                val addCommand = Commands.Add(phoneBook)
                if (addCommand.isValid(str))
                    command = addCommand
            }

            SHOW -> {
                val showCommand = Commands.Show(str, phoneBook)
                if (showCommand.isValid(str))
                    command = showCommand
            }

            FIND -> {
                val findCommand = Commands.Find(str, phoneBook)
                if (findCommand.isValid(str))
                    command = findCommand
            }
        }
        return command
    }
}
