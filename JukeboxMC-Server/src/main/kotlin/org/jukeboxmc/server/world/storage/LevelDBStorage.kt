package org.jukeboxmc.server.world.storage

import io.netty.buffer.ByteBufInputStream
import io.netty.buffer.ByteBufOutputStream
import io.netty.buffer.Unpooled
import net.daporkchop.ldbjni.LevelDB
import org.apache.commons.lang3.EnumUtils
import org.cloudburstmc.nbt.NbtMap
import org.cloudburstmc.nbt.NbtUtils
import org.cloudburstmc.nbt.util.stream.LittleEndianDataOutputStream
import org.iq80.leveldb.CompressionType
import org.iq80.leveldb.DB
import org.iq80.leveldb.Options
import org.jukeboxmc.api.blockentity.BlockEntity
import org.jukeboxmc.api.blockentity.BlockEntityType
import org.jukeboxmc.api.player.GameMode
import org.jukeboxmc.api.world.*
import org.jukeboxmc.api.world.gamerule.GameRuleValue
import org.jukeboxmc.server.block.palette.Palette
import org.jukeboxmc.server.extensions.toJukeboxBlockEntity
import org.jukeboxmc.server.scheduler.JukeboxScheduler
import org.jukeboxmc.server.util.Utils
import org.jukeboxmc.server.world.JukeboxWorld
import org.jukeboxmc.server.world.chunk.ChunkState
import org.jukeboxmc.server.world.chunk.JukeboxChunk
import org.jukeboxmc.server.world.chunk.JukeboxSubChunk
import org.jukeboxmc.server.world.chunk.serializer.BiomeIdDeserializer
import org.jukeboxmc.server.world.chunk.serializer.BiomeIdSerializer
import org.jukeboxmc.server.world.chunk.serializer.BlockStoragePersistentDeserializer
import org.jukeboxmc.server.world.chunk.serializer.BlockStoragePersistentSerializer
import java.io.*
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.StandardCopyOption
import java.nio.file.StandardOpenOption
import java.util.concurrent.CompletableFuture
import kotlin.math.abs


class LevelDBStorage(
    val world: JukeboxWorld,
    val path: Path,
    options: Options = Options()
        .createIfMissing(true)
        .compressionType(CompressionType.ZLIB_RAW)
        .blockSize(2000 * 1024)
) {

    private var db: DB
    private var worldData: WorldData?

    init {
        val worldName = path.getName(path.nameCount - 1).toString()
        val file = path.toFile()
        if (!file.exists()) file.mkdirs()

        this.worldData = this.readWorldData()
        if (this.worldData == null) {
            this.worldData = this.createWorldData(worldName)
        }

        val dbFolder = path.resolve("db").toFile()
        if (!dbFolder.exists()) dbFolder.mkdirs()

        this.db = LevelDB.PROVIDER.open(dbFolder, options)
    }

    private fun readWorldData(): WorldData? {
        val levelDatFile = path.resolve("level.dat").toFile()
        if (!levelDatFile.exists()) return null
        FileInputStream(levelDatFile).use { fileInputStream ->
            fileInputStream.skip(8)
            NbtUtils.createReaderLE(ByteArrayInputStream(fileInputStream.readAllBytes())).use {
                val nbt = it.readTag() as NbtMap
                val abilities: NbtMap = nbt.getCompound("abilities")

                val gameRuleValueList = mutableListOf<GameRuleValue>()
                gameRuleValueList.add(GameRuleValue("commandblockoutput", nbt.getBoolean("commandblockoutput")))
                gameRuleValueList.add(GameRuleValue("commandblocksenabled", nbt.getBoolean("commandblocksenabled")))
                gameRuleValueList.add(GameRuleValue("dodaylightcycle", nbt.getBoolean("dodaylightcycle")))
                gameRuleValueList.add(GameRuleValue("doentitydrops", nbt.getBoolean("doentitydrops")))
                gameRuleValueList.add(GameRuleValue("dofiretick", nbt.getBoolean("dofiretick")))
                gameRuleValueList.add(GameRuleValue("doimmediaterespawn", nbt.getBoolean("doimmediaterespawn")))
                gameRuleValueList.add(GameRuleValue("doinsomnia", nbt.getBoolean("doinsomnia")))
                gameRuleValueList.add(GameRuleValue("dolimitedcrafting", nbt.getBoolean("dolimitedcrafting")))
                gameRuleValueList.add(GameRuleValue("domobloot", nbt.getBoolean("domobloot")))
                gameRuleValueList.add(GameRuleValue("domobspawning", nbt.getBoolean("domobspawning")))
                gameRuleValueList.add(GameRuleValue("dotiledrops", nbt.getBoolean("dotiledrops")))
                gameRuleValueList.add(GameRuleValue("doweathercycle", nbt.getBoolean("doweathercycle")))
                gameRuleValueList.add(GameRuleValue("drowningdamage", nbt.getBoolean("drowningdamage")))
                gameRuleValueList.add(GameRuleValue("falldamage", nbt.getBoolean("falldamage")))
                gameRuleValueList.add(GameRuleValue("firedamage", nbt.getBoolean("firedamage")))
                gameRuleValueList.add(GameRuleValue("freezedamage", nbt.getBoolean("freezedamage")))
                gameRuleValueList.add(GameRuleValue("functioncommandlimit", nbt.getInt("functioncommandlimit")))
                gameRuleValueList.add(GameRuleValue("keepinventory", nbt.getBoolean("keepinventory")))
                gameRuleValueList.add(GameRuleValue("maxcommandchainlength", nbt.getInt("maxcommandchainlength")))
                gameRuleValueList.add(GameRuleValue("mobgriefing", nbt.getBoolean("mobgriefing")))
                gameRuleValueList.add(GameRuleValue("naturalregeneration", nbt.getBoolean("naturalregeneration")))
                gameRuleValueList.add(
                    GameRuleValue(
                        "playerssleepingpercentage",
                        nbt.getBoolean("playerssleepingpercentage")
                    )
                )
                gameRuleValueList.add(GameRuleValue("pvp", nbt.getBoolean("pvp")))
                gameRuleValueList.add(GameRuleValue("randomtickspeed", nbt.getInt("randomtickspeed")))
                gameRuleValueList.add(GameRuleValue("recipesunlock", nbt.getBoolean("recipesunlock")))
                gameRuleValueList.add(GameRuleValue("respawnblocksexplode", nbt.getBoolean("respawnblocksexplode")))
                gameRuleValueList.add(GameRuleValue("sendcommandfeedback", nbt.getBoolean("sendcommandfeedback")))
                gameRuleValueList.add(GameRuleValue("showbordereffect", nbt.getBoolean("showbordereffect")))
                gameRuleValueList.add(GameRuleValue("showcoordinates", nbt.getBoolean("showcoordinates")))
                gameRuleValueList.add(GameRuleValue("showdeathmessages", nbt.getBoolean("showdeathmessages")))
                gameRuleValueList.add(GameRuleValue("showtags", nbt.getBoolean("showtags")))
                gameRuleValueList.add(GameRuleValue("spawnradius", nbt.getInt("spawnradius")))
                gameRuleValueList.add(GameRuleValue("tntexplodes", nbt.getBoolean("tntexplodes")))
                return WorldData(
                    gameRuleValueList,
                    nbt.getString("BiomeOverride"),
                    nbt.getBoolean("CenterMapsToOrigin"),
                    nbt.getBoolean("ConfirmedPlatformLockedContent"),
                    Difficulty.fromId(nbt.getInt("Difficulty")),
                    nbt.getString("FlatWorldLayers"),
                    nbt.getBoolean("ForceGameType"),
                    GameMode.fromId(nbt.getInt("GameType")),
                    nbt.getInt("Generator"),
                    nbt.getString("InventoryVersion"),
                    nbt.getBoolean("LANBroadcast"),
                    nbt.getBoolean("LANBroadcastIntent"),
                    nbt.getLong("LastPlayed"),
                    nbt.getString("LevelName"),
                    nbt.getInt("LimitedWorldOriginX"),
                    nbt.getInt("LimitedWorldOriginY"),
                    nbt.getInt("LimitedWorldOriginZ"),
                    nbt.getIntArray("MinimumCompatibleClientVersion"),
                    nbt.getBoolean("MultiplayerGame"),
                    nbt.getBoolean("MultiplayerGameIntent"),
                    nbt.getInt("NetherScale"),
                    nbt.getInt("NetworkVersion"),
                    nbt.getInt("Platform"),
                    nbt.getInt("PlatformBroadcastIntent"),
                    nbt.getLong("RandomSeed"),
                    nbt.getBoolean("SpawnV1Villagers"),
                    nbt.getInt("SpawnX"),
                    nbt.getInt("SpawnY"),
                    nbt.getInt("SpawnZ"),
                    nbt.getInt("StorageVersion"),
                    nbt.getLong("Time").toInt(),
                    nbt.getInt("WorldVersion"),
                    nbt.getInt("XBLBroadcastIntent"),
                    AbilityData(
                        abilities.getBoolean("attackmobs"),
                        abilities.getBoolean("attackplayers"),
                        abilities.getBoolean("build"),
                        abilities.getBoolean("doorsandswitches"),
                        abilities.getFloat("flySpeed"),
                        abilities.getBoolean("flying"),
                        abilities.getBoolean("instabuild"),
                        abilities.getBoolean("invulnerable"),
                        abilities.getBoolean("lightning"),
                        abilities.getBoolean("mayfly"),
                        abilities.getBoolean("mine"),
                        abilities.getBoolean("op"),
                        abilities.getBoolean("opencontainers"),
                        abilities.getBoolean("teleport"),
                        abilities.getFloat("walkSpeed"),
                    ),
                    nbt.getString("baseGameVersion"),
                    nbt.getBoolean("bonusChestEnabled"),
                    nbt.getBoolean("bonusChestSpawned"),
                    nbt.getBoolean("cheatsEnabled"),
                    nbt.getBoolean("commandsEnabled"),
                    nbt.getLong("currentTick"),
                    nbt.getInt("daylightCycle"),
                    nbt.getInt("editorWorldType"),
                    nbt.getInt("eduOffer"),
                    nbt.getBoolean("educationFeaturesEnabled"),
                    nbt.getCompound("experiments").toMap(),
                    nbt.getBoolean("hasBeenLoadedInCreative"),
                    nbt.getBoolean("hasLockedBehaviorPack"),
                    nbt.getBoolean("hasLockedResourcePack"),
                    nbt.getBoolean("immutableWorld"),
                    nbt.getBoolean("isCreatedInEditor"),
                    nbt.getBoolean("isExportedFromEditor"),
                    nbt.getBoolean("isFromLockedTemplate"),
                    nbt.getBoolean("isFromWorldTemplate"),
                    nbt.getBoolean("isRandomSeedAllowed"),
                    nbt.getBoolean("isSingleUseWorld"),
                    nbt.getBoolean("isWorldTemplateOptionLocked"),
                    nbt.getIntArray("lastOpenedWithVersion"),
                    nbt.getFloat("lightningLevel"),
                    nbt.getInt("lightningTime"),
                    nbt.getInt("limitedWorldDepth"),
                    nbt.getInt("limitedWorldWidth"),
                    nbt.getInt("permissionsLevel"),
                    nbt.getInt("playerPermissionsLevel"),
                    nbt.getString("prid"),
                    nbt.getFloat("rainLevel"),
                    nbt.getInt("rainTime"),
                    nbt.getBoolean("requiresCopiedPackRemovalCheck"),
                    nbt.getInt("serverChunkTickRange"),
                    nbt.getBoolean("spawnMobs"),
                    nbt.getBoolean("startWithMapEnabled"),
                    nbt.getBoolean("texturePacksRequired"),
                    nbt.getBoolean("useMsaGamertagsOnly"),
                    nbt.getLong("worldStartCount"),
                    mutableListOf()
                )
            }
        }
    }

    private fun createWorldData(worldName: String): WorldData? {
        val levelDatFile = path.resolve("level.dat").toFile()
        if (!levelDatFile.exists()) {
            levelDatFile.createNewFile()
        }
        this.worldData = WorldData()
        this.worldData?.levelName = worldName

        val tagBytes: ByteArray
        ByteArrayOutputStream().use { byteArray ->
            NbtUtils.createWriterLE(byteArray).use {
                it.writeTag(this.createWorldDataNBT())
                tagBytes = byteArray.toByteArray()
                it.close()
            }
            byteArray.close()
        }

        LittleEndianDataOutputStream(FileOutputStream(levelDatFile)).use {
            it.writeInt(10) //Storage version
            it.writeInt(tagBytes.size)
            it.write(tagBytes)
            it.close()
        }

        Files.copy(levelDatFile.toPath(), path.resolve("level.dat_old"), StandardCopyOption.REPLACE_EXISTING)
        Files.writeString(path.resolve("levelname.txt"), worldName, StandardOpenOption.CREATE)
        return this.worldData
    }

    fun saveWorldData() {
        val levelDatFile = path.resolve("level.dat").toFile()
        if (!levelDatFile.exists()) return
        val tagBytes: ByteArray
        ByteArrayOutputStream().use { byteArray ->
            NbtUtils.createWriterLE(byteArray).use {
                it.writeTag(this.createWorldDataNBT())
                tagBytes = byteArray.toByteArray()
                it.close()
            }
            byteArray.close()
        }

        LittleEndianDataOutputStream(FileOutputStream(levelDatFile)).use {
            it.writeInt(10) //Storage version
            it.writeInt(tagBytes.size)
            it.write(tagBytes)
            it.close()
        }

        Files.copy(levelDatFile.toPath(), path.resolve("level.dat_old"), StandardCopyOption.REPLACE_EXISTING)
    }

    private fun createWorldDataNBT(): NbtMap {
        val builder = NbtMap.builder()

        val spawnLocation = this.world.getGenerator(Dimension.OVERWORLD).spawnLocation()
        this.worldData?.let {
            builder.putString("BiomeOverride", it.biomeOverride)
            builder.putBoolean("CenterMapsToOrigin", it.centerMapsToOrigin)
            builder.putBoolean("ConfirmedPlatformLockedContent", it.confirmedPlatformLockedContent)
            builder.putInt("Difficulty", it.difficulty.ordinal)
            builder.putString("FlatWorldLayers", it.flatWorldLayers)
            builder.putBoolean("ForceGameType", it.forceGameType)
            builder.putInt("GameType", it.gameType.ordinal)
            builder.putInt("Generator", it.generator)
            builder.putString("InventoryVersion", it.inventoryVersion)
            builder.putBoolean("LANBroadcast", it.lANBroadcast)
            builder.putBoolean("LANBroadcastIntent", it.lANBroadcastIntent)
            builder.putLong("LastPlayed", it.lastPlayed)
            builder.putString("LevelName", it.levelName)
            builder.putInt("LimitedWorldOriginX", it.limitedWorldOriginX)
            builder.putInt("LimitedWorldOriginY", it.limitedWorldOriginY)
            builder.putInt("LimitedWorldOriginZ", it.limitedWorldOriginZ)
            builder.putIntArray("MinimumCompatibleClientVersion", it.minimumCompatibleClientVersion)
            builder.putBoolean("MultiplayerGame", it.multiplayerGame)
            builder.putBoolean("MultiplayerGameIntent", it.multiplayerGameIntent)
            builder.putInt("NetherScale", it.netherScale)
            builder.putInt("NetworkVersion", it.networkVersion)
            builder.putInt("Platform", it.platform)
            builder.putInt("PlatformBroadcastIntent", it.platformBroadcastIntent)
            builder.putLong("RandomSeed", it.randomSeed)
            builder.putBoolean("SpawnV1Villagers", it.spawnV1Villagers)
            builder.putInt("SpawnX", spawnLocation.getBlockX())
            builder.putInt("SpawnY", spawnLocation.getBlockY())
            builder.putInt("SpawnZ", spawnLocation.getBlockZ())
            builder.putInt("StorageVersion", it.storageVersion)
            builder.putLong("Time", it.time.toLong())
            builder.putInt("WorldVersion", it.worldVersion)
            builder.putInt("XBLBroadcastIntent", it.XBLBroadcastIntent)
            builder.putCompound(
                "abilities", NbtMap.builder()
                    .putBoolean("attackmobs", it.abilities.attackmobs)
                    .putBoolean("attackplayers", it.abilities.attackPlayers)
                    .putBoolean("build", it.abilities.build)
                    .putBoolean("doorsandswitches", it.abilities.doorsAndSwitches)
                    .putBoolean("flying", it.abilities.flying)
                    .putBoolean("instabuild", it.abilities.instaBuild)
                    .putBoolean("invulnerable", it.abilities.invulnerable)
                    .putBoolean("lightning", it.abilities.lightning)
                    .putBoolean("mayfly", it.abilities.mayFly)
                    .putBoolean("mine", it.abilities.mine)
                    .putBoolean("op", it.abilities.op)
                    .putBoolean("opencontainers", it.abilities.openContainers)
                    .putBoolean("teleport", it.abilities.teleport)
                    .putFloat("flySpeed", it.abilities.flySpeed)
                    .putFloat("walkSpeed", it.abilities.walkSpeed)
                    .build()
            )
            builder.putString("baseGameVersion", it.baseGameVersion)
            builder.putBoolean("bonusChestEnabled", it.bonusChestEnabled)
            builder.putBoolean("bonusChestSpawned", it.bonusChestSpawned)
            builder.putBoolean("cheatsEnabled", it.cheatsEnabled)
            builder.putBoolean("commandsEnabled", it.commandsEnabled)
            builder.putLong("currentTick", it.currentTick)
            builder.putInt("daylightCycle", it.daylightCycle)
            builder.putInt("editorWorldType", it.editorWorldType)
            builder.putInt("eduOffer", it.eduOffer)
            builder.putBoolean("educationFeaturesEnabled", it.educationFeaturesEnabled)
            builder.putCompound("experiments", NbtMap.builder().build())
            builder.putBoolean("hasBeenLoadedInCreative", it.hasBeenLoadedInCreative)
            builder.putBoolean("hasLockedBehaviorPack", it.hasLockedBehaviorPack)
            builder.putBoolean("hasLockedResourcePack", it.hasLockedResourcePack)
            builder.putBoolean("immutableWorld", it.immutableWorld)
            builder.putBoolean("isCreatedInEditor", it.isCreatedInEditor)
            builder.putBoolean("isExportedFromEditor", it.isExportedFromEditor)
            builder.putBoolean("isFromLockedTemplate", it.isFromLockedTemplate)
            builder.putBoolean("isFromWorldTemplate", it.isFromWorldTemplate)
            builder.putBoolean("isRandomSeedAllowed", it.isRandomSeedAllowed)
            builder.putBoolean("isSingleUseWorld", it.isSingleUseWorld)
            builder.putBoolean("isWorldTemplateOptionLocked", it.isWorldTemplateOptionLocked)
            builder.putIntArray("lastOpenedWithVersion", it.lastOpenedWithVersion)
            builder.putFloat("lightningLevel", it.lightningLevel)
            builder.putInt("lightningTime", it.lightningTime)
            builder.putInt("limitedWorldDepth", it.limitedWorldDepth)
            builder.putInt("limitedWorldWidth", it.limitedWorldWidth)
            builder.putInt("permissionsLevel", it.permissionsLevel)
            builder.putInt("playerPermissionsLevel", it.playerPermissionsLevel)
            builder.putString("prid", it.prid)
            builder.putFloat("rainLevel", it.rainLevel)
            builder.putInt("rainTime", it.rainTime)
            builder.putBoolean("requiresCopiedPackRemovalCheck", it.requiresCopiedPackRemovalCheck)
            builder.putInt("serverChunkTickRange", it.serverChunkTickRange)
            builder.putBoolean("spawnMobs", it.spawnMobs)
            builder.putBoolean("startWithMapEnabled", it.startWithMapEnabled)
            builder.putBoolean("texturePacksRequired", it.texturePacksRequired)
            builder.putBoolean("useMsaGamertagsOnly", it.useMsaGamertagsOnly)
            builder.putLong("worldStartCount", it.worldStartCount)
            builder.putCompound("worldPolicies", NbtMap.builder().build())

            for (value in it.gameRuleData) {
                builder[value.identifier.lowercase()] = value.value
            }
        }
        return builder.build()
    }

    fun getWorldData(): WorldData? {
        return this.worldData
    }

    fun readChunk(chunk: JukeboxChunk): CompletableFuture<JukeboxChunk> {
        return CompletableFuture.supplyAsync({
            try {
                var version = this.db.get(Utils.getKey(chunk, ','.code.toByte()))
                if (version == null) {
                    version = this.db.get(Utils.getKey(chunk, 'v'.code.toByte()))
                }
                if (version == null) {
                    return@supplyAsync chunk
                }
                val finalized = this.db.get(Utils.getKey(chunk, '6'.code.toByte()))
                if (finalized == null) {
                    chunk.setChunkState(ChunkState.FINISHED)
                } else {
                    chunk.setChunkState(ChunkState.entries[Unpooled.wrappedBuffer(finalized).readIntLE() + 1])
                }

                for (subChunkIndex in chunk.getMinY() shr 4 until (chunk.getMaxY() shr 4)) {
                    val chunkSection = this.db.get(Utils.getSubChunkKey(chunk, '/'.code.toByte(), subChunkIndex.toByte()))
                    if (chunkSection != null) {
                        val arrayIndex = subChunkIndex + (abs(chunk.getMinY()) shr 4)
                        this.loadSection(chunk.getOrCreateSubChunk(arrayIndex, true), chunkSection)
                    }
                }

                val heightAndBiomes = this.db.get(Utils.getKey(chunk, '+'.code.toByte()))
                if (heightAndBiomes != null) {
                    this.loadHeightAndBiomes(chunk, heightAndBiomes)
                }

                val blockEntities = this.db.get(Utils.getKey(chunk, '1'.code.toByte()))
                if (blockEntities != null) {
                    this.loadBlockEntities(chunk, blockEntities)
                }
            } catch (e: Throwable) {
                e.printStackTrace()
            }
            return@supplyAsync chunk
        }, JukeboxScheduler.getInstance().getChunkExecutor())
    }

    private fun loadSection(subChunk: JukeboxSubChunk, chunkData: ByteArray) {
        val buffer = Unpooled.wrappedBuffer(chunkData)
        try {
            val subChunkVersion: Byte = buffer.readByte()
            subChunk.setSubChunkVersion(subChunkVersion.toInt())
            var storages = 1
            when (subChunkVersion.toInt()) {
                9, 8 -> {
                    storages = buffer.readByte().toInt()
                    subChunk.setLayer(storages)
                    if (subChunkVersion.toInt() == 9) {
                        val subY = buffer.readByte()
                    }
                    for (layer in 0 until storages) {
                        try {
                            subChunk.getBlockPalette()[layer]?.readFromStoragePersistent(buffer, BlockStoragePersistentDeserializer())
                        } catch (e: Throwable) {
                            e.printStackTrace()
                        }
                    }
                }
            }
        } finally {
            buffer.release()
        }
    }

    private fun loadHeightAndBiomes(chunk: JukeboxChunk, heightAndBiomes: ByteArray) {
        val buffer = Unpooled.wrappedBuffer(heightAndBiomes)
        try {
            for (i in chunk.getHeight().indices) {
                chunk.getHeight()[i] = buffer.readShortLE()
            }
            if (buffer.readableBytes() <= 0) return

            var last: Palette<Biome>? = null
            var biomePalette: Palette<Biome>
            val minYChunk = chunk.getMinY() shr 4
            val maxYChunk = (chunk.getMaxY() + 1) shr 4
            for (y in minYChunk until maxYChunk) {
                try {
                    val subChunk = chunk.getOrCreateSubChunk(chunk.getSubChunkIndex(y shl 4))
                    biomePalette = subChunk.getBiomePalette()
                    biomePalette.readFromStorageRuntime(buffer, BiomeIdDeserializer(), last!!)
                    last = biomePalette
                } catch (ignored: Exception) {
                }
            }
        } finally {
            buffer.release()
        }
    }

    private fun loadBlockEntities(chunk: JukeboxChunk, blockEntityData: ByteArray) {
        val byteBuf = Unpooled.wrappedBuffer(blockEntityData)
        try {
            val reader = NbtUtils.createReaderLE(ByteBufInputStream(byteBuf))
            while (byteBuf.readableBytes() > 0) {
                try {
                    val nbtMap = reader.readTag() as NbtMap
                    val x = nbtMap.getInt("x", 0)
                    val y = nbtMap.getInt("y", 0)
                    val z = nbtMap.getInt("z", 0)
                    val block= chunk.getBlock(x, y, z, 0)

                    if (EnumUtils.isValidEnum(BlockEntityType::class.java, nbtMap.getString("id").uppercase())) {
                        val blockEntityType = BlockEntityType.valueOf(nbtMap.getString("id").uppercase())
                        val blockEntity = BlockEntity.create(blockEntityType, block)?.toJukeboxBlockEntity()
                        if (blockEntity != null) {
                            blockEntity.fromCompound(nbtMap)
                            chunk.setBlockEntity(x, y, z, blockEntity)
                        }
                    }
                } catch (e: IOException) {
                    break
                }
            }
        } finally {
            byteBuf.release()
        }
    }

    fun saveChunk(chunk: JukeboxChunk): CompletableFuture<Void> {
        return CompletableFuture.supplyAsync {
            if (!chunk.isGenerated()) return@supplyAsync null
            if (!chunk.isChanged()) return@supplyAsync null
            this.db.createWriteBatch().use { writeBatch ->
                val minY = chunk.getMinY() shr 4
                for (subY in 0 until chunk.getAvailableSubChunks()) {
                    if (chunk.getSubChunk(subY) == null) {
                        continue
                    }
                    val subChunkIndex = subY + minY
                    val blockPalettes = chunk.getSubChunk(subY)!!.getBlockPalette()

                    val chunkVersion = Utils.getKey(chunk, ','.code.toByte())
                    writeBatch.put(chunkVersion, ByteArray(JukeboxChunk.CHUNK_VERSION))

                    val subChunkBuffer = Unpooled.buffer()
                    val biomeAndHeightBuffer = Unpooled.buffer()
                    val blockEntityBuffer = Unpooled.buffer()
                    try {
                        subChunkBuffer.writeByte(JukeboxSubChunk.SUBCHUNK_VERSION)
                        subChunkBuffer.writeByte(blockPalettes.size)
                        subChunkBuffer.writeByte(subChunkIndex)

                        for (blockPalette in blockPalettes) {
                            blockPalette?.writeToStoragePersistent(subChunkBuffer, BlockStoragePersistentSerializer())
                        }
                        val subChunkKey = Utils.getSubChunkKey(chunk, '/'.code.toByte(), subChunkIndex.toByte())
                        writeBatch.put(subChunkKey, Utils.array(subChunkBuffer))

                        for (height in chunk.getHeight()) {
                            biomeAndHeightBuffer.writeShortLE(height.toInt())
                        }
                        var last: Palette<Biome>? = null
                        var biomePalette: Palette<Biome>
                        for (y in chunk.getMinY() shr 4 until (chunk.getMaxY() + 1 shr 4)) {
                            biomePalette = chunk.getOrCreateSubChunk(chunk.getSubChunkIndex(y shl 4)).getBiomePalette()
                            biomePalette.writeToStorageRuntime(biomeAndHeightBuffer, BiomeIdSerializer(), last)
                            last = biomePalette
                        }
                        val biomeAndHeightKey = Utils.getKey(chunk, '+'.code.toByte())
                        writeBatch.put(biomeAndHeightKey, Utils.array(biomeAndHeightBuffer))

                        val blockEntities = chunk.getBlockEntities()
                        if (blockEntities.isNotEmpty()) {
                            NbtUtils.createWriterLE(ByteBufOutputStream(blockEntityBuffer)).use {
                                for (blockEntity in blockEntities) {
                                    it.writeTag(blockEntity.toJukeboxBlockEntity().toCompound().build())
                                }
                            }
                            if (blockEntityBuffer.readableBytes() > 0) {
                                val blockEntityKey = Utils.getKey(chunk, '1'.code.toByte())
                                writeBatch.put(blockEntityKey, Utils.array(blockEntityBuffer))
                            }
                        }
                        this.db.write(writeBatch)
                    } finally {
                        subChunkBuffer.release()
                        biomeAndHeightBuffer.release()
                        blockEntityBuffer.release()
                    }
                }
            }
            return@supplyAsync null
        }
    }

    fun close() {
        this.db.close()
        this.worldData = null
    }

}