@file:Suppress("UNCHECKED_CAST")

package org.jukeboxmc.server

import com.nimbusds.jose.jwk.Curve
import it.unimi.dsi.fastutil.objects.Object2ObjectMap
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap
import org.cloudburstmc.nbt.NbtMap
import org.cloudburstmc.protocol.bedrock.data.PacketCompressionAlgorithm
import org.cloudburstmc.protocol.bedrock.packet.BedrockPacket
import org.cloudburstmc.protocol.bedrock.packet.PlayerListPacket
import org.jukeboxmc.api.*
import org.jukeboxmc.api.block.Block
import org.jukeboxmc.api.block.BlockType
import org.jukeboxmc.api.blockentity.BlockEntity
import org.jukeboxmc.api.blockentity.BlockEntityType
import org.jukeboxmc.api.command.CommandSender
import org.jukeboxmc.api.command.ConsoleSender
import org.jukeboxmc.api.config.Config
import org.jukeboxmc.api.config.ConfigType
import org.jukeboxmc.api.effect.Effect
import org.jukeboxmc.api.effect.EffectType
import org.jukeboxmc.api.event.server.TpsChangeEvent
import org.jukeboxmc.api.item.Item
import org.jukeboxmc.api.item.ItemType
import org.jukeboxmc.api.logger.Logger
import org.jukeboxmc.api.player.GameMode
import org.jukeboxmc.api.player.Player
import org.jukeboxmc.api.player.info.DeviceInfo
import org.jukeboxmc.api.player.skin.Skin
import org.jukeboxmc.api.plugin.PluginLoadOrder
import org.jukeboxmc.api.resourcepack.ResourcePackManager
import org.jukeboxmc.api.scheduler.Scheduler
import org.jukeboxmc.api.world.Dimension
import org.jukeboxmc.api.world.World
import org.jukeboxmc.api.world.generator.EmptyGenerator
import org.jukeboxmc.api.world.generator.FlatGenerator
import org.jukeboxmc.api.world.generator.Generator
import org.jukeboxmc.server.block.BlockRegistry
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.blockentity.BlockEntityRegistry
import org.jukeboxmc.server.command.JukeboxCommandManager
import org.jukeboxmc.server.console.TerminalConsole
import org.jukeboxmc.server.effect.EffectRegistry
import org.jukeboxmc.server.extensions.toNetwork
import org.jukeboxmc.server.item.ItemRegistry
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.network.BedrockServer
import org.jukeboxmc.server.player.JukeboxPlayer
import org.jukeboxmc.server.plugin.JukeboxPluginManager
import org.jukeboxmc.server.resourcepack.JukeboxResourcePackManager
import org.jukeboxmc.server.scheduler.JukeboxScheduler
import org.jukeboxmc.server.util.ServerKiller
import org.jukeboxmc.server.world.JukeboxWorld
import java.io.File
import java.lang.reflect.Constructor
import java.lang.reflect.InvocationTargetException
import java.net.InetSocketAddress
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.LockSupport
import kotlin.math.abs
import kotlin.math.roundToLong

class JukeboxServer : Server {

    private val logger = Logger()
    private val players = mutableSetOf<JukeboxPlayer>()
    private val worlds = mutableMapOf<String, JukeboxWorld>()
    private val generators: MutableMap<Dimension, Object2ObjectMap<String, Class<out Generator>>> = mutableMapOf()
    private val playerListEntry: Object2ObjectMap<UUID, PlayerListPacket.Entry> = Object2ObjectOpenHashMap()
    private val keyPair: KeyPair

    private var running: Boolean = false
    private var startTime: Long
    private var currentTick: Long = 0
    private var lastTps: Long = 20
    private var currentTps: Long = 20
    private var sleepBalance: Long = 0

    private var mainThread: Thread
    private var bedrockServer: BedrockServer
    private var terminalConsole: TerminalConsole
    private var scheduler: JukeboxScheduler
    private var pluginFolder: File
    private var pluginManager: JukeboxPluginManager
    private var resourcePackManager: JukeboxResourcePackManager
    private var whitelist: JukeboxWhitelist
    private var operators: JukeboxOperators
    private var commandManager: JukeboxCommandManager
    private var consoleSender: ConsoleSender

    private lateinit var serverConfig: Config

    companion object {
        private var instance: JukeboxServer? = null

        fun setJukeboxServer(server: JukeboxServer) {
            if (this.instance == null) {
                this.instance = server
                return
            }
            throw UnsupportedOperationException("Cannot redefine singleton server")
        }

        fun getInstance(): JukeboxServer {
            return instance!!
        }
    }

    init {
        this.logger.info("Starting JukeboxMC (Bedrock Edition " + BedrockServer.BEDROCK_CODEC.minecraftVersion + " with Protocol " + BedrockServer.BEDROCK_CODEC.protocolVersion + ")")
        Thread.currentThread().name = "JukeboxMC-Main"
        this.mainThread = Thread.currentThread()
        setJukeboxServer(this)
        JukeboxMC.setServer(this)
        this.initServerConfig()

        this.startTime = System.currentTimeMillis()

        this.terminalConsole = TerminalConsole()
        this.terminalConsole.startConsole()
        this.consoleSender = ConsoleSender()
        this.scheduler = JukeboxScheduler()

        this.resourcePackManager = JukeboxResourcePackManager(this)
        this.resourcePackManager.loadResourcePacks()

        this.whitelist = JukeboxWhitelist()
        this.operators = JukeboxOperators()

        this.pluginFolder = File("./plugins")
        if (!this.pluginFolder.exists()) {
            this.pluginFolder.mkdirs()
        }

        this.commandManager = JukeboxCommandManager()

        this.pluginManager = JukeboxPluginManager(this)
        this.pluginManager.enableAllPlugins(PluginLoadOrder.STARTUP)

        this.registerGenerator("flat", FlatGenerator::class.java, Dimension.OVERWORLD)
        this.registerGenerator(
            "empty",
            EmptyGenerator::class.java,
            Dimension.OVERWORLD,
            Dimension.THE_END,
            Dimension.NETHER
        )

        this.loadWorld(this.getDefaultWorldName())

        this.pluginManager.enableAllPlugins(PluginLoadOrder.POSTWORLD)

        val keyPairGenerator = KeyPairGenerator.getInstance("EC")
        keyPairGenerator.initialize(Curve.P_384.toECParameterSpec())

        this.keyPair = keyPairGenerator.generateKeyPair()

        this.bedrockServer = BedrockServer(InetSocketAddress(this.getAddress(), this.getPort()), this)
        this.bedrockServer.bind()
        this.running = true
        this.logger.info("JukeboxMC started in " + TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - this.startTime) + " seconds!")
        this.startTick()
        this.shutdown()
    }

    private fun startTick() {
        var nextTickTime: Long = System.currentTimeMillis()

        while (this.running) {
            val startTimeMillis: Long = System.currentTimeMillis()

            if (nextTickTime - startTimeMillis > 25) {
                synchronized(this) {
                    Thread.sleep(5L.coerceAtLeast(nextTickTime - startTimeMillis - 25))
                }
            }
            this.tick()
            nextTickTime += 50
        }
    }

    private fun tick() {
        val skipNanos = TimeUnit.SECONDS.toNanos(1) / 20
        var lastTickTime: Float

        while (this.running) {
            val internalDiffTime = System.nanoTime()

            this.currentTick++

            this.bedrockServer.updateBedrockPong()
            this.scheduler.onTick(this.currentTick)

            synchronized(this) {
                players.removeIf { target: JukeboxPlayer ->
                    target.isClosed()
                }
                players.forEach {
                    if (it.isClosed()) {
                        return
                    }
                    it.tick(this.currentTick)
                }
            }

            for (value in worlds.values) {
                value.tick(this.currentTick)
            }

            if (!this.running) {
                break
            }

            val startSleep = System.nanoTime()
            var diff = startSleep - internalDiffTime
            if (diff <= skipNanos) {
                val sleepNeeded = (skipNanos - diff) - this.sleepBalance
                this.sleepBalance = 0

                LockSupport.parkNanos(sleepNeeded)

                val endSleep = System.nanoTime()
                val sleptFor = endSleep - startSleep
                diff = skipNanos
                if (sleptFor > sleepNeeded) {
                    this.sleepBalance = sleptFor - sleepNeeded
                }
            }
            if (this.currentTick % (20 * 20) == 0L) {
                System.gc()
            }

            lastTickTime = diff / TimeUnit.SECONDS.toNanos(1).toFloat()
            this.currentTps = (1 / lastTickTime).roundToLong()
            if (this.currentTps != this.lastTps) {
                pluginManager.callEvent(TpsChangeEvent(this, lastTps, currentTps))
            }
            this.lastTps = this.currentTps
        }
    }

    private fun initServerConfig() {
        this.serverConfig = Config(File(System.getProperty("user.dir"), "properties.json"), ConfigType.JSON)
        this.serverConfig.let {
            it.addDefault("address", "0.0.0.0")
            it.addDefault("port", 19132)
            it.addDefault("max-players", 20)
            it.addDefault("view-distance", 32)
            it.addDefault("simulation-distance", 4)
            it.addDefault("motd", "§bJukeboxMC")
            it.addDefault("sub-motd", "A fresh JukeboxMC Server")
            it.addDefault("default-world", "world")
            it.addDefault("default-gamemode", GameMode.CREATIVE.name)
            it.addDefault("generator", "flat")
            it.addDefault("online-mode", true)
            it.addDefault("force-resource-packs", false)
            it.addDefault("white-list", false)
            it.addDefault("compression", "zlib")
            it.addDefault("encryption", true)
            it.save()
        }
    }

    fun isRunnung(): Boolean {
        return this.running
    }

    @Synchronized
    fun createGenerator(generatorName: String, dimension: Dimension): Generator? {
        val generators = generators[dimension]
        val generator = generators!![generatorName.lowercase()]
        return if (generator != null) {
            try {
                generator.getConstructor().newInstance()
            } catch (e: InvocationTargetException) {
                throw RuntimeException(e)
            } catch (e: InstantiationException) {
                throw RuntimeException(e)
            } catch (e: IllegalAccessException) {
                throw RuntimeException(e)
            } catch (e: NoSuchMethodException) {
                throw RuntimeException(e)
            }
        } else null
    }

    fun addToTabList(player: Player) {
        this.addToTabList(
            player.getUUID(),
            player.getEntityId(),
            player.getName(),
            player.getDeviceInfo(),
            player.getXuid(),
            player.getSkin()
        )
    }

    fun removeFromTabList(player: Player) {
        val playerListPacket = PlayerListPacket()
        playerListPacket.action = PlayerListPacket.Action.REMOVE
        playerListPacket.entries.add(PlayerListPacket.Entry(player.getUUID()))
        broadcastPacket(playerListPacket)
        this.playerListEntry.remove(player.getUUID())
    }

    fun removeFromTabList(uuid: UUID, player: Player) {
        val playerListPacket = PlayerListPacket()
        playerListPacket.action = PlayerListPacket.Action.REMOVE
        playerListPacket.entries.add(PlayerListPacket.Entry(uuid))
        broadcastPacket(playerListPacket)
        this.playerListEntry.remove(player.getUUID())
    }

    fun addToTabList(uuid: UUID, entityId: Long, name: String, deviceInfo: DeviceInfo, xuid: String, skin: Skin) {
        val playerListPacket = PlayerListPacket()
        playerListPacket.action = PlayerListPacket.Action.ADD
        val entry = PlayerListPacket.Entry(uuid)
        entry.entityId = entityId
        entry.name = name
        entry.xuid = xuid
        entry.platformChatId = deviceInfo.getDeviceId()
        entry.buildPlatform = deviceInfo.getDevice().id
        entry.skin = skin.toNetwork()
        entry.isTrustedSkin = skin.isTrusted()
        playerListPacket.entries.add(entry)

        this.playerListEntry[uuid] = entry
        this.broadcastPacket(playerListPacket)
    }

    fun getPlayerListEntry(): Object2ObjectMap<UUID, PlayerListPacket.Entry> {
        return playerListEntry
    }

    fun broadcastPacket(packet: BedrockPacket) {
        for (player in this.players) {
            player.sendPacket(packet)
        }
    }

    fun getStartTime() : Long {
        return this.startTime
    }

    override fun registerGenerator(name: String, clazz: Class<out Generator>, vararg dimension: Dimension) {
        val lowercaseName = name.lowercase()
        for (value in dimension) {
            val generators = generators.computeIfAbsent(value) { Object2ObjectOpenHashMap() }
            if (!generators.containsKey(lowercaseName)) {
                generators[lowercaseName] = clazz
            }
        }
    }

    override fun getMinecraftVersion(): String {
        return BedrockServer.BEDROCK_CODEC.minecraftVersion
    }

    override fun getMinecraftProtocol(): Int {
        return BedrockServer.BEDROCK_CODEC.protocolVersion
    }

    override fun getOperators(): Operators {
        return this.operators
    }

    override fun broadcastMessage(message: String) {
        for (player in this.players) {
            player.sendMessage(message)
        }
        this.logger.info(message)
    }

    override fun getConsoleSender(): ConsoleSender {
        return this.consoleSender
    }

    override fun dispatchCommand(commandSender: CommandSender, command: String) {
        this.commandManager.handleCommandInput(commandSender, "/$command")
    }

    fun getMainThread(): Thread {
        return mainThread
    }

    fun addJukeboxPlayer(player: JukeboxPlayer) {
        this.players.add(player)
    }

    fun removeJukeboxPlayer(player: JukeboxPlayer) {
        this.players.removeIf { it.getEntityId() == player.getEntityId() }
    }

    override fun getLogger(): Logger {
        return this.logger
    }

    override fun getAddress(): String {
        return this.serverConfig.getString("address")
    }

    override fun getPort(): Int {
        return this.serverConfig.getInt("port")
    }

    override fun getMaxPlayers(): Int {
        return this.serverConfig.getInt("max-players")
    }

    override fun getViewDistance(): Int {
        return this.serverConfig.getInt("view-distance")
    }

    override fun getSimulationDistance(): Int {
        return this.serverConfig.getInt("simulation-distance")
    }

    override fun getMotd(): String {
        return this.serverConfig.getString("motd")
    }

    override fun getSubMotd(): String {
        return this.serverConfig.getString("sub-motd")
    }

    override fun getDefaultWorldName(): String {
        return this.serverConfig.getString("default-world")
    }

    override fun getDefaultGameMode(): GameMode {
        return GameMode.valueOf(this.serverConfig.getString("default-gamemode"))
    }

    override fun getGeneratorName(): String {
        return this.serverConfig.getString("generator")
    }

    override fun isOnlineMode(): Boolean {
        return this.serverConfig.getBoolean("online-mode")
    }

    override fun isForceResourcePacks(): Boolean {
        return this.serverConfig.getBoolean("force-resource-packs")
    }

    override fun isWhitelist(): Boolean {
        return this.serverConfig.getBoolean("white-list")
    }

    override fun setWhitelist(whitelist: Boolean) {
        this.serverConfig.set("white-list", whitelist)
    }

    fun getCompressionAlgorithm(): PacketCompressionAlgorithm {
        return PacketCompressionAlgorithm.valueOf(this.serverConfig.getString("compression").uppercase())
    }

    override fun loadWorld(name: String) {
        val dimensionGenerator: MutableMap<Dimension, String> = mutableMapOf()
        dimensionGenerator[Dimension.OVERWORLD] = this.getGeneratorName()
        this.worlds[name] = JukeboxWorld(name, dimensionGenerator)
    }

    override fun loadWorld(name: String, dimensionGenerator: Map<Dimension, String>) {
        this.worlds[name] = JukeboxWorld(name, dimensionGenerator)
    }

    override fun unloadWorld(name: String) {
        //TODO kick world players
        this.worlds.remove(name)
    }

    override fun getWorld(name: String): World? {
        return worlds.getOrDefault(name, null)
    }

    override fun getDefaultWorld(): World {
        return this.worlds[this.getDefaultWorldName()]!!
    }

    override fun getWorlds(): Collection<World> {
        return this.worlds.values
    }

    override fun getPlayer(name: String): Player? {
        var found = this.getPlayerExact(name)
        if (found != null) return found

        val lowerName = name.lowercase(Locale.getDefault())
        var delta = Int.MAX_VALUE
        for (player in getOnlinePlayers()) {
            if (player.getName().lowercase().startsWith(lowerName)) {
                val curDelta: Int = abs(player.getName().length - lowerName.length)
                if (curDelta < delta) {
                    found = player
                    delta = curDelta
                }
                if (curDelta == 0) break
            }
        }
        return found
    }

    override fun getPlayerExact(name: String): Player? {
        return this.players.firstOrNull { it.getLoginData()?.displayName.equals(name) }
    }

    override fun getPlayer(uuid: UUID): Player? {
        return this.players.firstOrNull { it.getLoginData()?.uuid == uuid }
    }

    override fun getOnlinePlayers(): Collection<Player> {
        return this.players
    }

    override fun shutdown() {
        if (!this.running) {
            return
        }
        this.running = false
        this.logger.info("Shutdown server...")
        for (player in this.players) {
            player.kick("Shutdown server")
        }
        this.pluginManager.disablePlugins()
        this.logger.info("Save all worlds...")
        for (world in worlds.values) {
            world.save()
            this.logger.info("The world \"" + world.getName() + "\" was saved!")
        }
        for (world in this.worlds.values) {
            world.close()
        }
        this.terminalConsole.stopConsole()
        this.scheduler.shutdown()
        this.bedrockServer.close()

        this.logger.info("Stopping other threads")
        for (thread in Thread.getAllStackTraces().keys) {
            if (thread.isAlive) {
                thread.interrupt()
            }
        }
        ServerKiller(this.logger).start()
    }

    override fun getScheduler(): Scheduler {
        return this.scheduler
    }

    override fun getCurrentTick(): Long {
        return this.currentTick
    }

    override fun getPluginFolder(): File {
        return this.pluginFolder
    }

    override fun getTps(): Long {
        return this.currentTps
    }

    override fun isEncrypted(): Boolean {
        return this.serverConfig.getBoolean("encryption")
    }

    fun getKeyPair(): KeyPair {
        return this.keyPair
    }

    override fun getResourcePackManager(): ResourcePackManager {
        return this.resourcePackManager
    }

    override fun getWhitelist(): Whitelist {
        return this.whitelist
    }

    override fun <T : Block> createBlock(blockType: BlockType): T {
        if (BlockRegistry.blockByBlockType.containsKey(blockType)) {
            return BlockRegistry.blockByBlockType[blockType]?.clone() as T
        }
        val createBlock = this.createBlock<JukeboxBlock>(blockType, null)
        BlockRegistry.blockByBlockType[blockType] = createBlock
        return createBlock as T
    }

    override fun <T : Block> createBlock(blockType: BlockType, blockStates: NbtMap?): T {
        if (BlockRegistry.blockClassExists(blockType)) {
            BlockRegistry.getBlockClass(blockType)?.let {
                val constructor: Constructor<out JukeboxBlock> =
                    it.getConstructor(Identifier::class.java, NbtMap::class.java)
                constructor.isAccessible = true
                return constructor.newInstance(BlockRegistry.getIdentifier(blockType), blockStates) as T
            }
        }
        return JukeboxBlock(BlockRegistry.getIdentifier(blockType), blockStates) as T
    }

    override fun <T : Item> createItem(itemType: ItemType): T {
        if (ItemRegistry.itemClassExists(itemType)) {
            ItemRegistry.getBlockClass(itemType)?.let {
                val constructor: Constructor<out JukeboxItem> =
                    it.getConstructor(ItemType::class.java, Boolean::class.java)
                constructor.isAccessible = true
                return constructor.newInstance(itemType, true) as T
            }
        }
        return JukeboxItem(itemType, true) as T
    }

    override fun <T : Item> createItem(itemType: ItemType, amount: Int): T {
        val item: Item = this.createItem(itemType)
        item.setAmount(amount)
        return item as T
    }

    override fun <T : Item> createItem(itemType: ItemType, amount: Int, meta: Int): T {
        val item: Item = this.createItem(itemType, amount)
        item.setMeta(meta)
        return item as T
    }

    override fun <T : BlockEntity> createBlockEntity(blockEntityType: BlockEntityType, block: Block): T? {
        if (BlockEntityRegistry.blockEntityClassExists(blockEntityType)) {
            val constructor: Constructor<out BlockEntity?> =
                BlockEntityRegistry.getBlockEntityClass(blockEntityType).getConstructor(
                    BlockEntityType::class.java,
                    JukeboxBlock::class.java
                )
            constructor.isAccessible = true
            return constructor.newInstance(blockEntityType, block) as T?
        }
        return null
    }

    override fun createEffect(effectType: EffectType): Effect {
        val constructor: Constructor<out Effect> = EffectRegistry.getEffectClass(effectType).getConstructor()
        constructor.isAccessible = true
        return constructor.newInstance()
    }

    override fun getPluginManager(): JukeboxPluginManager {
        return this.pluginManager
    }

    override fun getCommandManager(): JukeboxCommandManager {
        return this.commandManager
    }
}