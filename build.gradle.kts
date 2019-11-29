buildscript {
    val kotlinVersion = "1.3.61"
    val dokkaVersion = "0.9.18"
    val licenserVersion = "0.4.1"

    repositories {
        mavenCentral()
        jcenter()
    }
    dependencies {
        classpath(kotlin("gradle-plugin", kotlinVersion))
        classpath("org.jetbrains.dokka:dokka-gradle-plugin:$dokkaVersion")
        classpath("gradle.plugin.net.minecrell:licenser:$licenserVersion")
    }
}

// Have to apply these here too, because of Kotlin DSL :(
plugins {
    `java-library`
    `maven-publish`
    signing

    id("net.minecrell.licenser") version "0.4.1"
}

group = "eu.mikroskeem"
version = "0.0.1-SNAPSHOT"
extra["isReleaseVersion"] = !version.toString().endsWith("SNAPSHOT")

subprojects {
    apply(plugin = "maven-publish")
    apply(plugin = "signing")
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "org.jetbrains.dokka")
    apply(plugin = "net.minecrell.licenser")

    repositories {
        mavenCentral()
        jcenter()
        maven("https://repo.wut.ee/repository/mikroskeem-repo")
    }

    dependencies {
        api(kotlin("stdlib-jdk8"))
    }

    val sourcesJar by tasks.creating(Jar::class) {
        group = JavaBasePlugin.DOCUMENTATION_GROUP
        description = "Assembles sources jar"
        archiveClassifier.set("sources")
        from(sourceSets["main"].allJava)
    }

    val dokkaJar by tasks.creating(Jar::class) {
        group = JavaBasePlugin.DOCUMENTATION_GROUP
        description = "Assembles Kotlin docs with Dokka"
        archiveClassifier.set("javadoc")
        from(tasks["dokka"])
    }

    publishing {
        publications {
            create<MavenPublication>("maven") {
                groupId = "eu.mikroskeem.missingno"
                version = rootProject.version.toString()

                from(components["java"])
                artifact(sourcesJar)
                artifact(dokkaJar)
            }
        }
        repositories {
            mavenLocal()
            if (rootProject.hasProperty("wutee.repository.deploy.username") && rootProject.hasProperty("wutee.repository.deploy.password")) {
                maven("https://repo.wut.ee/repository/mikroskeem-repo") {
                    credentials {
                        username = rootProject.property("wutee.repository.deploy.username") as String
                        password = rootProject.property("wutee.repository.deploy.password") as String
                    }
                }
            }
        }
    }

    java {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    signing {
        setRequired { rootProject.extra["isReleaseVersion"] as Boolean && gradle.taskGraph.hasTask("publish") }
        sign(publishing.publications["maven"])

        // TODO: more solutions?
        useGpgCmd()
    }

    tasks.withType<Sign>().configureEach {
        onlyIf { rootProject.extra["isReleaseVersion"] as Boolean }
    }

    license {
        header = rootProject.file("etc/HEADER")
        filter.include("**/*.java")
        filter.include("**/*.kt")
    }
}
