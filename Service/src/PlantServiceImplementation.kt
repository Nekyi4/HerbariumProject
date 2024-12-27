class PlantServiceImplementation(private val dbHandler: DataBaseHandler) : PlantService {

    override fun addPlant(userId: Int,
                          name: String,
                          nameLatin: String,
                          location: String,
                          description: String,
                          imageData: ByteArray): Boolean {
        val plant = Plant(
            name = name,
            nameLatin = nameLatin,
            location = location,
            description = description,
            imageData = imageData,
        )
        return dbHandler.addPlant(userId, plant)
    }

    override fun getPlantByName(userId: Int, name: String): Plant? {
        val plants = getAllPlants(userId)
        return plants.find { it.name == name }
    }

    override fun getAllPlants(userId: Int): List<Plant> {
        return dbHandler.fetchAllPlants(userId)
    }

    override fun updatePlant(userId: Int,
                             name: String,
                             uname: String,
                             unameLatin: String,
                             ulocation: String,
                             udescription: String,
                             uimageData: ByteArray): Boolean {
        // Check if the plant exists
        val existingPlant = getPlantByName(userId, name)

        return if (existingPlant != null) {
            // Update the plant if it exists
            val plantId = existingPlant.plantId ?: return false
            val updatedPlant = Plant(
                plantId = plantId,
                name = uname,
                nameLatin = unameLatin,
                location = ulocation,
                description = udescription,
                imageData = uimageData,
            )
            return dbHandler.updatePlant(userId, plantId, updatedPlant)
        } else {
            // Return false if the plant does not exist
            false
        }
    }

    override fun deletePlant(userId: Int, plantId: Int): Boolean {
        return dbHandler.deletePlant(userId, plantId)
    }

    override fun getAllPlantNames(userId: Int): List<String> {
        val plants = dbHandler.fetchAllPlants(userId)
        return plants.map { it.name}
        }
}