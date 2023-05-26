# JukeboxMC
Why did I start JukeboxMC ?
Well, I wanted to learn more about the Minecraft Bedrock Edition protocol. Also, I always wanted to have my own server software that I can design the way I want :P

## Connect with us:
__Discord__: https://discord.gg/Jatt7tfRBF

__Twitter__: https://twitter.com/LucGamesHD

## How to build a jar file:
1. git clone [https://github.com/JukeboxMC/JukeboxMC.git](https://github.com/JukeboxMC/JukeboxMC.git)
2. cd /JukeboxMC
3. mvn clean install
4. Done!

*Please note that you must have Maven installed on your computer as well as git

Maven:
- Download: https://maven.apache.org/download.cgi
- Install: https://maven.apache.org/install

Git: https://git-scm.com/downloads

## How to create a plugin:

_Notice: Please use **Java 17**!_

[![](https://jitpack.io/v/JukeboxMC/JukeboxMC.svg)](https://jitpack.io/#JukeboxMC/JukeboxMC)

**_With Maven:_**

Please add this code to your pom.xml! (Only **for Maven**!)

```xml
<repositories>
    <repository>
        <id>jukeboxmc</id>
        <url>http://jukeboxmc.eu:8081/repository/maven-releases/</url>
    </repository>
    <repository>
        <id>jukeboxmc</id>
        <url>http://jukeboxmc.eu:8081/repository/maven-snapshots/</url>
    </repository>
</repositories>

<dependency>
    <groupId>org.jukeboxmc</groupId>
    <artifactId>JukeboxMC</artifactId>
    <version>1.0.0-Beta-1</version>
</dependency>
```
**_With Gradle:_**

Please add this code to your build.gradle! (Only **for Gradle**!)

```groovy
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}

dependencies {
    implementation 'com.github.LucGamesYT:JukeboxMC:Tag'
}
```

## IMPORTANT TO KNOW
This server software is not yet ready! Feel free to improve code and contribute!
