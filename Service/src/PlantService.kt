interface PlantService {
    fun addPlant(userId: Int,
                 name: String,
                 nameLatin: String,
                 location: String,
                 description: String,
                 imageData: ByteArray): Boolean
    fun getPlantByName(userId: Int, name: String): Plant?
    fun getAllPlants(userId: Int): List<Plant>
    fun updatePlant(userId: Int,
                    name: String,
                    uname: String,
                    unameLatin: String,
                    ulocation: String,
                    udescription: String,
                    uimageData: ByteArray): Boolean
    fun deletePlant(userId: Int, plantId: Int): Boolean
    fun getAllPlantNames(userId: Int): List<String>
}