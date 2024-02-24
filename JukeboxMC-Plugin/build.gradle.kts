import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("java")
}

group = rootProject.group
version = rootProject.version

repositories {
    mavenCentral()
}

dependencies {
    api(project(":JukeboxMC-API"))
    compileOnly(kotlin("stdlib"))
    compileOnly(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
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

