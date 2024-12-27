fun main(){
    //ModelTest()
    //ServiceTest()
    //DbTestUser()
    //UserTest()
    //DbTest()
    //DbTest2()
}

fun DbTestUser(){
    val db = DataBaseHandler()
    val userService = UserServiceImplementation(db)
    val plantService = PlantServiceImplementation(db)
    userService.createUser("Test", "test@test.com", "Alabama")
    println(userService.getAllUsers()[0].userName)
    val userId = userService.getAllUsers()[0].userId
    userService.updateUserDetails(userId!!,userService.getAllUsers()[0].userName, "TEST2@test.com")
    println(userService.getAllUsers()[0].userEmail)
    println(userService.deleteUser(userId))
}

fun DbTest(){
    val db = DataBaseHandler()
    val userService = UserServiceImplementation(db)
    val plantService = PlantServiceImplementation(db)
    userService.createUser("Test", "test@test.com", "Alabama")
    userService.createUser("Test2", "test2@test.com", "Alabama2")
    val list = userService.getAllUsers()
    for (user in list) {
        println(user.userName)
    }
    val image_path = "C:\\A Folder\\studiainfa\\Mobilki\\Mobilki\\Model\\Resources\\Mewing_Queen.png"
    val iH = imageHandler()
    val image = iH.imagineLoader(image_path)
    val userid = userService.getAllUsers()[0].userId
    val userid2 = userService.getAllUsers()[1].userId
    val plant = Plant(name = "Test Plant", nameLatin = "Testus Plantus",description = "Test Plant", location = "hihi", imageData =image)
    println(plantService.addPlant(userid!!,plant.name, plant.nameLatin!!, plant.location!!, plant.description!!, plant.imageData!!))
    val plantList = plantService.getAllPlants(userid)
    for(plant in plantList){
        println(plant.name)
    }
    if(plantService.getAllPlants(userid2!!) == null){
        println("Jackpot")
    } else{
        for(plant in plantList){
            println("JackpotListExistButEmpty")
            println(plant.name)
        }
    }
    println(plantService.updatePlant(userid, "Test Plant", "IT WAS ME DIO!", unameLatin = plant.nameLatin!!, udescription = plant.description!!, ulocation = plant.location!!, uimageData = plant.imageData!!))
    val plantListU = plantService.getAllPlants(userid)
    for(plant in plantListU){
        println(plant.name)
    }
    //println(plantService.deletePlant(userid, plantService.getAllPlants(userid)[0].plantId!!))
    val plantList2 = plantService.getAllPlants(userid2)
    for(plant in plantList2){
        println(plant.name)
    }
    //println(userService.deleteUser(userid2))
    //println(userService.deleteUser(userid))
}

fun DbTest2() {
    val db = DataBaseHandler()
    val userService = UserServiceImplementation(db)
    val plantService = PlantServiceImplementation(db)
    val userid = userService.getAllUsers()[0].userId
    val userid2 = userService.getAllUsers()[1].userId
    val plantList = plantService.getAllPlants(userid!!)
    for (plant in plantList) {
        println(plant.name)
    }
    val plantList2 = plantService.getAllPlants(userid2!!)
    if (plantService.getAllPlants(userid2!!) == null) {
        println("Jackpot")
    } else {
        for (plant in plantList2) {
            println("JackpotListExistButEmpty")
            println(plant.name)
        }
    }
}

fun UserTest() {
    val user = User(
        userName = "John Doe",
        userEmail = "john.doe@example.com",
        userPassword = "mypassword123"
    )

    // Hash the password before storing it in the database
    user.hashPassword()
    println("Hashed Password: ${user.userPassword}")
    // Validate the password
    val isValid = user.isPasswordValid("mypassword123")
    println("Password is valid: $isValid") // Should print: true
    // /imageTest()
}


