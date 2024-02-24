
plugins {
    kotlin("jvm") version "1.9.20"
    java
}
group = "org.jukeboxmc"
version = "1.0.0-SNAPSHOT"

java {
    java.sourceCompatibility = JavaVersion.VERSION_17
    java.targetCompatibility = JavaVersion.VERSION_17
}

allprojects {
    repositories {
        mavenCentral()
        maven {
            name = "opencollab-releases"
            url = uri("https://repo.opencollab.dev/maven-releases/")
        }
        maven {
            name = "opencollab-snapshots"
            url = uri("https://repo.opencollab.dev/maven-snapshots/")
        }
    }
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")

    dependencies {
        implementation(kotlin("stdlib"))
        implementation("org.apache.commons:commons-math3:3.6.1")
        implementation("commons-io:commons-io:2.11.0")
        implementation("it.unimi.dsi:fastutil:8.5.12")
        implementation("org.apache.commons:commons-lang3:3.12.0")
        implementation("org.yaml:snakeyaml:2.0")
        implementation("com.fasterxml.jackson.core:jackson-core:2.14.2")
        implementation("com.fasterxml.jackson.core:jackson-databind:2.14.2")
        implementation("com.google.code.gson:gson:2.10.1")
        implementation("org.projectlombok:lombok:1.18.26")
        implementation("org.jetbrains:annotations:24.0.1")
        implementation("org.apache.logging.log4j:log4j-api:2.17.1")
        implementation("org.apache.logging.log4j:log4j-core:2.17.1")
        implementation("org.slf4j:slf4j-api:2.0.7")
        implementation("org.slf4j:slf4j-simple:2.0.7")
        implementation("org.jline:jline-terminal:3.21.0")
        implementation("org.jline:jline-terminal-jna:3.21.0")
        implementation("org.jline:jline-reader:3.21.0")
        implementation("net.minecrell:terminalconsoleappender:1.3.0") {
            exclude(group = "org.apache.logging.log4j", module = "log4j-api")
            exclude(group = "org.apache.logging.log4j", module = "log4j-core")
            exclude(group = "org.jline", module = "jline-reader")
            exclude(group = "org.jline", module = "jline-terminal-jna")
            exclude(group = "org.jline", module = "jline-terminal")
        }
        annotationProcessor("org.projectlombok:lombok")
    }
}