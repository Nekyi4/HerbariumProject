plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation(files("libs/postgresql-42.7.2.jar"))
    implementation(files("libs/commons-imaging-1.0.0-alpha5.jar"))
    implementation(files("libs/commons-io-2.18.0.jar"))
}

tasks.test {
    useJUnitPlatform()
}