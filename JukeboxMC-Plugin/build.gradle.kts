import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.net.URI

plugins {
    id("java")
}

group = rootProject.group
version = rootProject.version

repositories {
    mavenCentral()
    maven {
        name = "jukeboxmcSnapshots"
        url = URI("https://repo.jukeboxmc.eu/snapshots")
    }
}

dependencies {
    compileOnly(kotlin("stdlib"))
    compileOnly(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    compileOnly("org.jukeboxmc:JukeboxMC-API:1.0.0-SNAPSHOT")
    compileOnly("org.jukeboxmc:JukeboxMC-Server:1.0.0-SNAPSHOT")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.jar {
    archiveFileName.set("JukeboxPlugin.jar")
    destinationDirectory.set(file("${project.rootDir}/plugins/"))
}

tasks {
    processResources {
        expand("version" to project.version)
    }
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

