class InMemoryPlantRepository : PlantRepository {
    private val dbHandler = DataBaseHandler()

    override fun save(
        userId: Int,
        name: String,
        nameLatin: String,
        location: String,
        description: String,
        imageData: ByteArray
    ): Boolean {
        val plant = Plant(
            name = name,
            nameLatin = nameLatin,
            location = location,
            description = description,
            imageData = imageData,
        )
        return dbHandler.addPlant(userId, plant)
    }

    override fun findByName(userId: Int, name: String): Plant? {
        val plants = findAll(userId)
        return plants.find { it.name == name }
    }

    override fun findAll(userId: Int): List<Plant> {
        return dbHandler.fetchAllPlants(userId)
    }

    override fun update(
        userId: Int,
        plantId: Int,
        name: String,
        uname: String,
        unameLatin: String,
        ulocation: String,
        udescription: String,
        uimageData: ByteArray
    ): Boolean {
        val updatedPlant = Plant(
            plantId = plantId,
            name = uname,
            nameLatin = unameLatin,
            location = ulocation,
            description = udescription,
            imageData = uimageData,
        )
        return dbHandler.updatePlant(userId, plantId, updatedPlant)
    }

    override fun delete(userId: Int, plantId: Int): Boolean {
        return dbHandler.deletePlant(userId, plantId)
    }

    override fun getPlantNames(userId: Int): List<String> {
        val plants = dbHandler.fetchAllPlants(userId)
        return plants.map { it.name }
    }
}
