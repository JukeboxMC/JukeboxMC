plugins {
    kotlin("jvm") version "1.9.20"
    java
    `maven-publish`
}

group = rootProject.group
version = rootProject.version

dependencies {
    implementation("org.cloudburstmc:nbt:3.0.0.Final")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "17"
    }
}
configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}
publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
        repositories {
            maven {
                url = uri("https://repo.jukeboxmc.eu/snapshots")
                credentials.username = System.getenv("MAVEN_NAME")
                credentials.password = System.getenv("MAVEN_SECRET")
            }
        }
    }
}