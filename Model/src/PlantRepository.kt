interface PlantRepository {
    fun save(
        userId: Int,
        name: String,
        nameLatin: String,
        location: String,
        description: String,
        imageData: ByteArray): Boolean
    fun findByName(userId: Int, name: String): Plant?
    fun findAll(userId: Int): List<Plant>
    fun update(
        userId: Int,
        plantId: Int,
        name: String,
        uname: String,
        unameLatin: String,
        ulocation: String,
        udescription: String,
        uimageData: ByteArray): Boolean
    fun delete(userId: Int, plantId: Int): Boolean
    fun getPlantNames(userId: Int): List<String>
}