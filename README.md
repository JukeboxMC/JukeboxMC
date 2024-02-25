# JukeboxMC
Why did I start JukeboxMC ?
Well, I wanted to learn more about the Minecraft Bedrock Edition protocol. Also, I always wanted to have my own server software that I can design the way I want :P

## Download
[Jenkins](https://jenkins.jukeboxmc.eu/job/JukeboxMC/)

## Connect with us:
__Discord__: https://discord.gg/Jatt7tfRBF

__Twitter__: https://twitter.com/LucGamesHD

## How to build a jar file:
1. git clone [https://github.com/JukeboxMC/JukeboxMC.git](https://github.com/JukeboxMC/JukeboxMC.git)
2. cd /JukeboxMC
3. ./gradlew build OR gradlew.bat build on Microsoft Windows
4. Done!

*Please note that you must have Gradle installed on your computer as well as git

Gradle: https://gradle.org/install

Git: https://git-scm.com/downloads

## How to create a plugin:

_Notice: Please use **Java 17**!_

**_With Maven:_**

Please add this code to your pom.xml! (Only **for Maven**!)

```xml
<repositories>
    <repository>
        <id>jukeboxmc-releases</id>
        <url>https://repo.jukeboxmc.eu/releases</url>
    </repository>
    <repository>
        <id>jukeboxmc-snapshots</id>
        <url>https://repo.jukeboxmc.eu/snapshots</url>
    </repository>
</repositories>

<dependency>
    <groupId>org.jukeboxmc</groupId>
    <artifactId>JukeboxMC-API</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```
**_With Gradle:_**

Please add this code to your build.gradle! (Only **for Gradle**!)

```groovy
allprojects {
    repositories {
        maven {
            name "jukeboxmcReleases"
            url "https://repo.jukeboxmc.eu/releases"
        }
        maven {
            name "jukeboxmcSnapshots"
            url "https://repo.jukeboxmc.eu/snapshots"
        }
    }
}

dependencies {
    implementation 'org.jukeboxmc:JukeboxMC-API:1.0.0-SNAPSHOT'
}
```

## IMPORTANT TO KNOW
This server software is not yet ready! Feel free to improve code and contribute!