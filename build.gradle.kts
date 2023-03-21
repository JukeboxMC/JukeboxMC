import org.jmailen.gradle.kotlinter.tasks.FormatTask
import org.jmailen.gradle.kotlinter.tasks.LintTask

plugins {
    kotlin("jvm") version "1.8.10"
    id("com.github.johnrengelman.shadow") version "8.1.0"
    id("org.jmailen.kotlinter") version "3.14.0"
}

repositories {
    mavenCentral()
    maven {
        name = "opencollab-snapshots"
        url = uri("https://repo.opencollab.dev/maven-snapshots/")
    }
    maven {
        name = "opencollab-releases"
        url = uri("https://repo.opencollab.dev/maven-releases/")
    }

    maven {
        name = "jitpack"
        url = uri("https://jitpack.io")
    }
}

dependencies {
    implementation("org.ow2.asm:asm:9.4")
    implementation("mysql:mysql-connector-java:8.0.30")
    implementation("org.apache.commons:commons-lang3:3.12.0")
    implementation("com.fasterxml.jackson.core:jackson-core:2.14.2")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.14.2")
    implementation("com.github.Kaooot.Protocol:bedrock-v575:215edc744c")
    implementation("com.google.code.gson:gson:2.10")
    implementation("com.googlecode.json-simple:json-simple:1.1.1")
    implementation("it.unimi.dsi:fastutil:8.5.12")
    implementation("net.daporkchop:leveldb-mcpe-jni:0.0.10-SNAPSHOT")
    implementation("com.google.guava:guava:31.1-jre")
    implementation("com.spotify:completable-futures:0.3.5")
    implementation("org.apache.commons:commons-math3:3.6.1")
    implementation("commons-io:commons-io:2.11.0")
    implementation("org.yaml:snakeyaml:2.0")
    implementation("org.jetbrains:annotations:23.1.0")
    implementation("org.apache.logging.log4j:log4j-api:2.19.0")
    implementation("org.apache.logging.log4j:log4j-core:2.19.0")
    implementation("org.jline:jline-terminal:3.21.0")
    implementation("org.jline:jline-terminal-jna:3.21.0")
    implementation("org.jline:jline-reader:3.21.0")
    implementation("net.minecrell:terminalconsoleappender:1.3.0") {
        exclude(group = "org.apache.logging.log4j", module = "log4j-core")
        exclude(group = "org.apache.logging.log4j", module = "log4j-api")
    }
    implementation("org.projectlombok:lombok:1.18.22")
}

group = "org.jukeboxmc"
version = "1.0.0-Beta-1"
description = "JukeboxMC"

java.sourceCompatibility = JavaVersion.VERSION_17
java.targetCompatibility = JavaVersion.VERSION_17

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

tasks.withType<Javadoc> {
    options.encoding = "UTF-8"
}

tasks.create<LintTask>("ktLint") {
    group = "verification"
    source(files("src"))
}

tasks.create<FormatTask>("ktFormat") {
    group = "formatting"
    source(files("src"))
}

tasks {
    shadowJar {
        dependsOn("ktLint")
        archiveClassifier.set("")
        manifest {
            attributes(
                "Main-Class" to "org.jukeboxmc.Bootstrap",
                "Multi-Release" to true,
            )
        }
    }
}
