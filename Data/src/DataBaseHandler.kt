import java.util.Properties
import java.io.FileInputStream
import java.sql.*

class DataBaseHandler {

    fun loadConfig(): Properties {
        val properties = Properties()
        val configFile = FileInputStream("config.properties")
        properties.load(configFile)
        configFile.close()
        return properties
    }

    private val properties = loadConfig()
    private val jdbcUrl = properties.getProperty("db.url", "jdbc:postgresql://localhost:5432/Herbarium")
    private val username = properties.getProperty("db.username", "postgres")
    private val password = properties.getProperty("db.password", "your_password")

    // Function to establish the database connection
    fun connect(): Connection {
        return DriverManager.getConnection(jdbcUrl, username, password)
    }

    fun fetchAllPlants(userID: Int): List<Plant> {
        val connection = connect()
        val sql = "SELECT plantId, name, nameLatin, location, description, image_data FROM Plants WHERE userId = ?"
        val preparedStatement: PreparedStatement = connection.prepareStatement(sql)
        preparedStatement.setInt(1, userID) // Correctly bind userID to the placeholder

        val resultSet: ResultSet = preparedStatement.executeQuery()
        val plants = mutableListOf<Plant>()

        while (resultSet.next()) {
            val plantId = resultSet.getInt("plantId")
            val name = resultSet.getString("name")
            val nameLatin = resultSet.getString("nameLatin")
            val location = resultSet.getString("location")
            val description = resultSet.getString("description")
            val imageData = resultSet.getBytes("image_data") // Fetch image data as ByteArray

            // Add the plant to the list
            plants.add(Plant(plantId, name, nameLatin, location, description, imageData))
        }

        connection.close()
        return plants
    }

    // Add a new plant to the database
    fun addPlant(userId: Int, plant: Plant): Boolean {
        val connection = connect()
        val sql = "INSERT INTO Plants (name, nameLatin, location, description, image_data, userId) VALUES (?, ?, ?, ?, ?, ?)"
        val preparedStatement: PreparedStatement = connection.prepareStatement(sql)

        // Set parameters
        preparedStatement.setString(1, plant.name)
        preparedStatement.setString(2, plant.nameLatin)
        preparedStatement.setString(3, plant.location)
        preparedStatement.setString(4, plant.description)
        preparedStatement.setBytes(5, plant.imageData) // Assuming imageData is a ByteArray
        preparedStatement.setInt(6, userId) // Associate the plant with the user

        val rowsAffected = preparedStatement.executeUpdate()
        connection.close()
        return rowsAffected > 0
    }

    // Update an existing plant in the database based on name
    fun updatePlant(userId: Int, plantId: Int, updatedPlant: Plant): Boolean {
        val connection = connect()
        val sql = """
        UPDATE Plants 
        SET name = ?, nameLatin = ?, location = ?, description = ?, image_data = ? 
        WHERE plantId = ? AND userId = ?
    """
        val preparedStatement: PreparedStatement = connection.prepareStatement(sql)

        // Set parameters
        preparedStatement.setString(1, updatedPlant.name)
        preparedStatement.setString(2, updatedPlant.nameLatin)
        preparedStatement.setString(3, updatedPlant.location)
        preparedStatement.setString(4, updatedPlant.description)
        preparedStatement.setBytes(5, updatedPlant.imageData) // Assuming imageData is a ByteArray
        preparedStatement.setInt(6, plantId) // Specify the plant to update
        preparedStatement.setInt(7, userId) // Ensure the plant belongs to the user

        val rowsAffected = preparedStatement.executeUpdate()
        connection.close()
        return rowsAffected > 0
    }


    // Delete a plant from the database based on name
    fun deletePlant(userId: Int, plantId: Int): Boolean {
        val connection = connect()
        val sql = "DELETE FROM Plants WHERE plantId = ? AND userId = ?"
        val preparedStatement: PreparedStatement = connection.prepareStatement(sql)

        // Set the plantId and userId to be deleted
        preparedStatement.setInt(1, plantId)
        preparedStatement.setInt(2, userId)

        val rowsAffected = preparedStatement.executeUpdate()
        connection.close()
        return rowsAffected > 0
    }

    // Add a new user to the database
    fun addUser(user: User): Boolean {
        val connection = connect()
        val sql = "INSERT INTO Users (userName, userEmail, userPassword) VALUES (?, ?, ?)"
        val preparedStatement: PreparedStatement = connection.prepareStatement(sql)

        // Set parameters
        preparedStatement.setString(1, user.userName)
        preparedStatement.setString(2, user.userEmail)
        preparedStatement.setString(3, user.userPassword) // Assume it's already hashed

        val rowsAffected = preparedStatement.executeUpdate()
        connection.close()
        return rowsAffected > 0
    }

    // Fetch all users
    fun getAllUsers(): List<User> {
        val connection = connect()
        val sql = "SELECT userId, userName, userEmail, userPassword FROM Users"
        val statement: Statement = connection.createStatement()
        val resultSet = statement.executeQuery(sql)

        val users = mutableListOf<User>()
        while (resultSet.next()) {
            users.add(
                User(
                    userId = resultSet.getInt("userId"),
                    userName = resultSet.getString("userName"),
                    userEmail = resultSet.getString("userEmail"),
                    userPassword = resultSet.getString("userPassword")
                )
            )
        }
        connection.close()
        return users
    }

    // Update user details (excluding password)
    fun updateUser(userId: Int, userName: String, userEmail: String): Boolean {
        val connection = connect()
        val sql = "UPDATE Users SET userName = ?, userEmail = ? WHERE userId = ?"
        val preparedStatement: PreparedStatement = connection.prepareStatement(sql)

        // Set parameters
        preparedStatement.setString(1, userName)
        preparedStatement.setString(2, userEmail)
        preparedStatement.setInt(3, userId)

        val rowsAffected = preparedStatement.executeUpdate()
        connection.close()
        return rowsAffected > 0
    }

    // Update user password
    fun updateUserPassword(userId: Int, hashedPassword: String): Boolean {
        val connection = connect()
        val sql = "UPDATE Users SET userPassword = ? WHERE userId = ?"
        val preparedStatement: PreparedStatement = connection.prepareStatement(sql)

        // Set parameters
        preparedStatement.setString(1, hashedPassword)
        preparedStatement.setInt(2, userId)

        val rowsAffected = preparedStatement.executeUpdate()
        connection.close()
        return rowsAffected > 0
    }

    // Delete a user by their ID
    fun deleteUser(userId: Int): Boolean {
        val connection = connect()
        val sql = "DELETE FROM Users WHERE userId = ?"
        val preparedStatement: PreparedStatement = connection.prepareStatement(sql)

        // Set the userId parameter
        preparedStatement.setInt(1, userId)

        val rowsAffected = preparedStatement.executeUpdate()
        connection.close()
        return rowsAffected > 0
    }
}


