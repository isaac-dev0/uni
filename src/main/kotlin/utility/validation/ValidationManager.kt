package utility.validation

interface ValidationManager {

    fun isValidUsername(username: String): Boolean

    fun isValidPassword(password: String): Boolean

}