object Program {

    fun startProgram() {
        var command: Commands? = null
        do {
            commandsDescription()
            print("Введите команду: ")
            val answer = readln()
            command = readCommand(answer, command)

            when (command) {
                is Commands.Add -> command.launch()
                is Commands.Exit -> command.launch()
                is Commands.Help -> command.launch()
                is Commands.Show -> command.launch()
                null -> println("Введена неверная команда")
            }

        } while (command !is Commands.Exit)
    }

    private fun commandsDescription() {
        println(
            """
            
            Доступные команды:
            exit
            help
            add <Имя> phone <Номер телефона>
            add <Имя> email <Адрес электронной почты>
            show
        """.trimIndent()
        )
    }

    private fun readCommand(str: String, lastCommand: Commands?): Commands? {
        val arrString = str.split(" ")
        val firstWord = arrString[0].lowercase()
        var command: Commands? = null
        when (firstWord) {
            Commands.Exit().name -> {
                command = if (Commands.Exit().isValid(str)) {
                    Commands.Exit()
                } else {
                    Commands.Help()
                }
            }

            Commands.Help().name -> if (Commands.Help().isValid(str)) command = Commands.Help()

            Commands.Add().name -> {
                command = Commands.Add()
                if (!command.isValid(str))
                    command = Commands.Help()
            }

            Commands.Show(lastCommand).name -> {
                command = Commands.Show(lastCommand)
                if (!command.isValid(str))
                    command = Commands.Help()
            }

            else -> command = null
        }
        return command
    }
}
