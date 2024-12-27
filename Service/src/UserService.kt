interface UserService {
    fun createUser(userName: String, userEmail: String, password: String): Boolean
    fun getAllUsers(): List<User>
    fun getUserById(userId: Int): User?
    fun updateUserDetails(userId: Int, userName: String, userEmail: String): Boolean
    fun updateUserPassword(userId: Int, newPassword: String): Boolean
    fun deleteUser(userId: Int): Boolean
}