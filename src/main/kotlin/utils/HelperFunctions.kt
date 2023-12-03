package utils

object HelperFunctions {

    @JvmStatic
    fun isValidListIndex(index: Int, list: List<Any>): Boolean = (index >= 0 && index < list.size)

    @JvmStatic
    fun capitalizeFirstLetter(string: String): String {
        return string.substring(0, 1).uppercase() + string.substring(1).lowercase()
    }
}