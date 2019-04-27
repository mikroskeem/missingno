val paperApiVersion = "1.13.2-R0.1-SNAPSHOT"

repositories {
    maven("https://papermc.io/repo/repository/maven-public/")
}

dependencies {
    api("com.destroystokyo.paper:paper-api:$paperApiVersion")
}