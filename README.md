# JukeboxMC
Why did I start JukeboxMC ?
Well, I wanted to learn more about the Minecraft Bedrock Edition protocol. Also I always wanted to have my own server software that I can design the way I want :P

## Links
__Discord__: https://discord.gg/Jatt7tfRBF

__Twitter__: https://twitter.com/LucGamesHD

## How to build a jar file
1. git clone https://github.com/LucGamesYT/JukeboxMC.git
2. cd /JukeboxMC
3. mvn clean install
4. Done! 

Please note that you must have Maven installed on your computer as well as git

Maven: 
 - Download: https://maven.apache.org/download.cgi
 - Install: https://maven.apache.org/install

Git: https://git-scm.com/downloads

## How to create a plugin

_Notice: Please use **Java 8**!_

**_With Maven:_**

Please add this code to your pom.xml! (Only **for Maven**!)

```xml
<repository>
    <id>jitpack.io</id>
    <url>https://jitpack.io</url>
</repository>

<dependency>
    <groupId>com.github.LucGamesYT</groupId>
    <artifactId>JukeboxMC</artifactId>
    <version>Tag</version>
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

## IMPORTANT
This server software is not yet ready!
