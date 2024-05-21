package utility.validation

object Validation: ValidationManager {

    override fun isValidUsername(username: String): Boolean {
        val usernameRegex = "^[A-Za-z0-9+_.-]+@(.+)\$".toRegex()
        return username.matches(usernameRegex)
    }

    override fun isValidPassword(password: String): Boolean {

        if (password.length < 8) return false

        val hasUppercase = password.any { it.isUpperCase() }
        val hasLowercase = password.any { it.isLowerCase() }
        val hasDigit = password.any { it.isDigit() }
        val hasSpecialChar = password.any { !it.isLetterOrDigit() }

        return hasUppercase && hasLowercase && hasDigit && hasSpecialChar

    }

}