import java.security.MessageDigest
import java.util.*

class UserServiceImplementation(private val dbHandler: DataBaseHandler) : UserService {

    override fun createUser(userName: String, userEmail: String, password: String): Boolean {
        // Hash the password before storing it
        val hashedPassword = hashPassword(password)
        val newUser = User(
            userName = userName,
            userEmail = userEmail,
            userPassword = hashedPassword
        )
        return dbHandler.addUser(newUser)
    }

    override fun getAllUsers(): List<User> {
        return dbHandler.getAllUsers()
    }

    override fun getUserById(userId: Int): User? {
        val allUsers = dbHandler.getAllUsers()
        return allUsers.find { it.userId == userId }
    }

    override fun updateUserDetails(userId: Int, userName: String, userEmail: String): Boolean {
        return dbHandler.updateUser(userId, userName, userEmail)
    }

    override fun updateUserPassword(userId: Int, newPassword: String): Boolean {
        // Hash the new password before storing it
        val hashedPassword = hashPassword(newPassword)
        return dbHandler.updateUserPassword(userId, hashedPassword)
    }

    override fun deleteUser(userId: Int): Boolean {
        return dbHandler.deleteUser(userId)
    }

    fun hashPassword(password: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hashedBytes = digest.digest(password.toByteArray())
        val hashedPassword = Base64.getEncoder().encodeToString(hashedBytes)
        return hashedPassword
    }
}