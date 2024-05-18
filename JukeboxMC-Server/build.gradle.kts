plugins {
    java
    application
    kotlin("jvm") version "1.9.20"
    id("com.github.johnrengelman.shadow") version "7.0.0"
    `maven-publish`
}

group = rootProject.group
version = rootProject.version

dependencies {
    api(project(":JukeboxMC-API"))
    implementation(kotlin("stdlib"))
    implementation("com.nimbusds:nimbus-jose-jwt:9.10.1")
    implementation("org.ow2.asm:asm:9.4")
    implementation("org.cloudburstmc.protocol:bedrock-connection:3.0.0.Beta2-SNAPSHOT") {
        exclude(group = "io.netty", module = "netty-common")
    }
    implementation("org.cloudburstmc.protocol:bedrock-codec:3.0.0.Beta2-SNAPSHOT") {

        exclude(group = "io.netty", module = "netty-common")
    }
    implementation("org.cloudburstmc:nbt:3.0.0.Final")
    implementation("org.cloudburstmc:block-state-updater:1.20.80-SNAPSHOT")
    implementation("io.netty:netty-common:4.1.96.Final")
    implementation("net.daporkchop:leveldb-mcpe-jni:0.0.10-SNAPSHOT") {
        exclude(group = "io.netty", module = "netty-buffer")
        exclude(group = "com.google.guava", module = "guava")
    }
    implementation("com.google.guava:guava:32.0.0-android")
    implementation("com.spotify:completable-futures:0.3.5")
}

application {
    mainClass.set("org.jukeboxmc.server.Bootstrap")
}

tasks {
    build {
        dependsOn(shadowJar)
    }
}

tasks.jar {
    // Configure the JAR task to include the manifest
    manifest {
        attributes["Main-Class"] = "org.jukeboxmc.server.Bootstrap"
    }
    // To avoid the duplicate handling strategy error
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    from(sourceSets.main.get().output)

    dependsOn(configurations.runtimeClasspath)
    from({
        configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
    })
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