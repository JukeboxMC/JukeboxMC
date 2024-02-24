package org.jukeboxmc.server.resourcepack

import com.google.common.io.Files
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import org.apache.commons.math3.util.FastMath
import org.jukeboxmc.api.resourcepack.ResourcePack
import org.jukeboxmc.api.resourcepack.ResourcePackManager
import org.jukeboxmc.server.JukeboxServer
import java.io.File
import java.io.IOException
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*
import java.util.zip.ZipEntry
import java.util.zip.ZipFile

class JukeboxResourcePackManager(
    private val server: JukeboxServer
) : ResourcePackManager {

    private val resourcePacks: MutableMap<UUID, ResourcePack> = HashMap()

    fun loadResourcePacks() {
        val resourcePacksPath = File(System.getProperty("user.dir") + "/resource_packs")
        if (!resourcePacksPath.exists()) {
            resourcePacksPath.mkdirs()
        }
        if (!resourcePacksPath.isDirectory) {
            return
        }
        for (file in Objects.requireNonNull<Array<File>>(resourcePacksPath.listFiles())) {
            if (!file.isDirectory) {
                val fileEnding = Files.getFileExtension(file.name)
                if (fileEnding.equals("zip", ignoreCase = true) || fileEnding.equals("mcpack", ignoreCase = true)) {
                    try {
                        ZipFile(file).use { zipFile ->
                            val manifestFileName = "manifest.json"
                            var manifestEntry = zipFile.getEntry(manifestFileName)

                            // due to the mcpack file extension
                            if (manifestEntry == null) {
                                manifestEntry = zipFile.stream().filter { zipEntry: ZipEntry ->
                                    !zipEntry.isDirectory && zipEntry.name.lowercase()
                                        .endsWith(manifestFileName)
                                }.filter { zipEntry: ZipEntry ->
                                    val zipEntryFile = File(zipEntry.name)
                                    if (!zipEntryFile.name.equals(manifestFileName, ignoreCase = true)) {
                                        return@filter false
                                    }
                                    zipEntryFile.parent == null || zipEntryFile.parentFile.parent == null
                                }.findFirst()
                                    .orElseThrow {
                                        IllegalArgumentException(
                                            "The $manifestFileName file could not be found"
                                        )
                                    }
                            }
                            val manifest = JsonParser()
                                .parse(
                                    InputStreamReader(
                                        zipFile.getInputStream(manifestEntry),
                                        StandardCharsets.UTF_8
                                    )
                                ).asJsonObject
                            require(this.isManifestValid(manifest)) { "The $manifestFileName file is invalid" }
                            val manifestHeader = manifest.getAsJsonObject("header")
                            val resourcePackName = manifestHeader["name"].asString
                            val resourcePackUuid = manifestHeader["uuid"].asString
                            val resourcePackVersionArray =
                                manifestHeader["version"].asJsonArray
                            val resourcePackVersion = resourcePackVersionArray.toString()
                                .replace("[", "").replace("]", "")
                                .replace(",".toRegex(), ".")
                            val resourcePackSize = FastMath.toIntExact(file.length())
                            val resourcePackSha256 = MessageDigest.getInstance("SHA-256")
                                .digest(java.nio.file.Files.readAllBytes(file.toPath()))
                            this.server.getLogger().info("Read resource pack " + resourcePackName + " §rsuccessful from " + file.name)
                            val resourcePack = ResourcePack(
                                file, resourcePackName, resourcePackUuid,
                                resourcePackVersion, resourcePackSize.toLong(), resourcePackSha256, ByteArray(0)
                            )
                            resourcePacks.put(UUID.fromString(resourcePackUuid), resourcePack)
                        }
                    } catch (e: IOException) {
                        e.printStackTrace()
                    } catch (e: NoSuchAlgorithmException) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }

    private fun isManifestValid(manifest: JsonObject): Boolean {
        if (manifest.has("format_version") && manifest.has("header") &&
            manifest.has("modules")
        ) {
            val manifestHeader = manifest["header"].asJsonObject
            if (manifestHeader.has("description") && manifestHeader.has("name") &&
                manifestHeader.has("uuid") && manifestHeader.has("version")
            ) {
                val headerVersion = manifestHeader.getAsJsonArray("version")
                return headerVersion.size() == 3
            }
        }
        return false
    }

    override fun getResourcePack(uuid: UUID): ResourcePack? {
        return this.resourcePacks[uuid]
    }

    override fun getResourcePacks(): Collection<ResourcePack> {
        return this.resourcePacks.values
    }
}