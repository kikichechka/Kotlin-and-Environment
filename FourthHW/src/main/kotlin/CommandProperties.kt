interface CommandProperties {
    fun isValid(string: String): Boolean
    fun launch()
}