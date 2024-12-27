import java.security.MessageDigest
import java.util.Base64

data class User(
    val userId: Int? = null, // Nullable for users not yet stored in the database
    val userName: String,
    val userEmail: String,
    var userPassword: String
) {
    fun hashPassword() {
        val digest = MessageDigest.getInstance("SHA-256")
        val hashedBytes = digest.digest(userPassword.toByteArray())
        userPassword = Base64.getEncoder().encodeToString(hashedBytes)
    }

    fun isPasswordValid(inputPassword: String): Boolean {
        val digest = MessageDigest.getInstance("SHA-256")
        val hashedBytes = digest.digest(inputPassword.toByteArray())
        val hashedInput = Base64.getEncoder().encodeToString(hashedBytes)
        return hashedInput == userPassword
    }
}
