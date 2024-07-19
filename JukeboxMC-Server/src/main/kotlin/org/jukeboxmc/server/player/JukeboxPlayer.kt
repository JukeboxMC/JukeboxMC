package org.jukeboxmc.server.player

import com.google.common.collect.ImmutableSet
import it.unimi.dsi.fastutil.ints.Int2ObjectMap
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap
import org.cloudburstmc.math.vector.Vector3f
import org.cloudburstmc.protocol.bedrock.BedrockServerSession
import org.cloudburstmc.protocol.bedrock.data.ClientPlayMode
import org.cloudburstmc.protocol.bedrock.data.InputInteractionModel
import org.cloudburstmc.protocol.bedrock.data.InputMode
import org.cloudburstmc.protocol.bedrock.data.PlayerBlockActionData
import org.cloudburstmc.protocol.bedrock.data.command.CommandData
import org.cloudburstmc.protocol.bedrock.data.entity.EntityDataTypes
import org.cloudburstmc.protocol.bedrock.data.entity.EntityEventType
import org.cloudburstmc.protocol.bedrock.data.inventory.ContainerType
import org.cloudburstmc.protocol.bedrock.packet.*
import org.cloudburstmc.protocol.common.PacketSignal
import org.jukeboxmc.api.Server
import org.jukeboxmc.api.effect.EffectType
import org.jukeboxmc.api.entity.Entity
import org.jukeboxmc.api.entity.EntityType
import org.jukeboxmc.api.event.entity.EntityDamageByEntityEvent
import org.jukeboxmc.api.event.entity.EntityDamageEvent
import org.jukeboxmc.api.event.inventory.InventoryCloseEvent
import org.jukeboxmc.api.event.inventory.InventoryOpenEvent
import org.jukeboxmc.api.event.player.*
import org.jukeboxmc.api.extensions.asType
import org.jukeboxmc.api.extensions.isType
import org.jukeboxmc.api.form.Form
import org.jukeboxmc.api.form.FormListener
import org.jukeboxmc.api.inventory.EnderChestInventory
import org.jukeboxmc.api.inventory.Inventory
import org.jukeboxmc.api.item.Armor
import org.jukeboxmc.api.item.Item
import org.jukeboxmc.api.item.ItemType
import org.jukeboxmc.api.item.enchantment.EnchantmentType
import org.jukeboxmc.api.math.Location
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.api.player.AdventureSettings
import org.jukeboxmc.api.player.Emote
import org.jukeboxmc.api.player.GameMode
import org.jukeboxmc.api.player.Player
import org.jukeboxmc.api.player.info.DeviceInfo
import org.jukeboxmc.api.player.skin.Skin
import org.jukeboxmc.api.world.Dimension
import org.jukeboxmc.api.world.Sound
import org.jukeboxmc.api.world.chunk.Chunk
import org.jukeboxmc.server.JukeboxServer
import org.jukeboxmc.server.anticheat.module.combat.AntiCheatCombatModule
import org.jukeboxmc.server.blockentity.JukeboxBlockEntitySign
import org.jukeboxmc.server.entity.Attribute
import org.jukeboxmc.server.entity.JukeboxEntity
import org.jukeboxmc.server.entity.JukeboxEntityLiving
import org.jukeboxmc.server.entity.passive.JukeboxEntityHuman
import org.jukeboxmc.server.event.PacketReceiveEvent
import org.jukeboxmc.server.event.PacketSendEvent
import org.jukeboxmc.server.extensions.*
import org.jukeboxmc.server.inventory.*
import org.jukeboxmc.server.network.handler.HandlerRegistry
import org.jukeboxmc.server.network.handler.PacketHandler
import org.jukeboxmc.server.util.Utils
import org.jukeboxmc.server.world.chunk.ChunkLoader
import org.jukeboxmc.server.world.chunk.JukeboxChunk
import java.util.*
import kotlin.math.max
import kotlin.math.min

class JukeboxPlayer(
    private val server: JukeboxServer,
    private val session: BedrockServerSession
) : JukeboxEntityHuman(), Player, ChunkLoader {

    private var loginData: LoginData? = null
    private var loggedIn: Boolean = false
    private var spawned: Boolean = false
    private var disconnectMessage: String? = null
    private val adventureSettings = JukeboxAdventureSettings(this)
    private var playerChunkManager = PlayerChunkManager(this)
    private var emotes: MutableList<UUID> = mutableListOf()
    private var signFrontSide: Boolean? = null

    private var lastBreakTime: Long = 0
    private var breakingBlock: Boolean = false
    private var lastBreakPosition: Vector = Vector(0, 0, 0)
    private val permissions: MutableMap<UUID, MutableSet<String>> = mutableMapOf()

    private var currentChunk: JukeboxChunk? = null
    private var respawnLocation: Location? = null

    private var currentInventory: Inventory? = null
    private var craftingGridInventory: CraftingGridInventory = JukeboxSmallCraftingGridInventory(this)
    private val creativeCacheInventory = CreativeItemCacheInventory(this)
    private val cursorInventory = JukeboxCursorInventory(this)
    private val enderChestInventory = JukeboxEnderChestInventory(this)
    private val craftingTableInventory = JukeboxCraftingTableInventory(this)
    private val grindstoneInventory = JukeboxGrindstoneInventory(this)
    private val cartographyTableInventory = JukeboxCartographyTableInventory(this)
    private val smithingTableInventory = JukeboxSmithingTableInventory(this)
    private val anvilInventory = JukeboxAnvilInventory(this)
    private val stoneCutterInventory = JukeboxStoneCutterInventory(this)
    private val loomInventory = JukeboxLoomInventory(this)

    private var hasOpenForm = false
    private var formId = 0
    private var serverSettingsForm = -1
    private val forms: Int2ObjectMap<Form<out Any>> = Int2ObjectOpenHashMap()
    private val formListeners: Int2ObjectMap<FormListener<out Any>> = Int2ObjectOpenHashMap()

    private val printDebugMessage: Boolean = false

    private var lastBlockAction: PlayerBlockActionData? = null

    private var inputMode: InputMode = InputMode.UNDEFINED
    private var playMode: ClientPlayMode = ClientPlayMode.NORMAL
    private var inputInteractionModel: InputInteractionModel = InputInteractionModel.TOUCH

    init {
        session.packetHandler = object : BedrockPacketHandler {
            override fun handlePacket(packet: BedrockPacket): PacketSignal {
                JukeboxServer.getInstance().getScheduler().execute {
                    val packetReceiveEvent = PacketReceiveEvent(this@JukeboxPlayer, packet)
                    server.getPluginManager().callEvent(packetReceiveEvent)
                    if (packetReceiveEvent.isCancelled()) return@execute

                    val packetHandler =
                        HandlerRegistry.getPacketHandler(packetReceiveEvent.getBedrockPacket()::class.java)
                    if (packetHandler != null) {
                        if (packetHandler.isType<PacketHandler<BedrockPacket>>()) {
                            packetHandler.asType<PacketHandler<BedrockPacket>>()
                                .handle(packetReceiveEvent.getBedrockPacket(), server, this@JukeboxPlayer)
                        }
                    } else {
                        if (printDebugMessage) {
                            server.getLogger()
                                .info("Handler missing for packet: " + packetReceiveEvent.getBedrockPacket().javaClass.simpleName)
                        }
                    }
                }
                return PacketSignal.HANDLED
            }

            override fun onDisconnect(reason: String) {
                server.getScheduler().execute {
                    this@JukeboxPlayer.onDisconnect()
                }
            }
        }
    }

    override fun tick(currentTick: Long) {
        if (this.isClosed() || !this.loggedIn) {
            return
        }
        super.tick(currentTick)

        this.getWorld().getNearbyEntities(this.getBoundingBox().grow(1F, 0.5f, 1F), this.getLocation().getDimension(), null)
            .forEach {
                (it as JukeboxEntity).onCollideWithPlayer(this)
            }

        if (this.spawned) {
            this.playerChunkManager.queueNewChunks()
        }

        this.playerChunkManager.sendQueued()

        if (playerChunkManager.getChunksSent() >= 25 && !this.spawned) {
            this.doFirstSpawn()
        }

        this.updateAttributes()
    }

    private fun doFirstSpawn() {
        this.getMetadata().setString(EntityDataTypes.NAME, this.getName())

        val setEntityDataPacket = SetEntityDataPacket()
        setEntityDataPacket.runtimeEntityId = this.getEntityId()
        setEntityDataPacket.metadata.putAll(this.getMetadata().getEntityDataMap())
        setEntityDataPacket.tick = this.server.getCurrentTick()
        this.sendPacket(setEntityDataPacket)

        val adventureSettings: AdventureSettings = this.getAdventureSettings()
        if (this.isOperator()) {
            adventureSettings.set(AdventureSettings.Type.OPERATOR, true)
        }
        adventureSettings.set(AdventureSettings.Type.WORLD_IMMUTABLE, this.getGameMode().ordinal == 3)
        adventureSettings.set(AdventureSettings.Type.ALLOW_FLIGHT, this.getGameMode().ordinal > 0)
        adventureSettings.set(AdventureSettings.Type.NO_CLIP, this.getGameMode().ordinal == 3)
        adventureSettings.set(AdventureSettings.Type.FLYING, this.getGameMode().ordinal == 3)
        adventureSettings.set(AdventureSettings.Type.ATTACK_MOBS, this.getGameMode().ordinal < 2)
        adventureSettings.set(AdventureSettings.Type.ATTACK_PLAYERS, this.getGameMode().ordinal < 2)
        adventureSettings.set(AdventureSettings.Type.NO_PVM, this.getGameMode().ordinal == 3)
        adventureSettings.update()

        this.sendAvailableCommands()

        val updateAttributesPacket = UpdateAttributesPacket()
        updateAttributesPacket.runtimeEntityId = this.getEntityId()
        for (attribute in this.getAttributes()) {
            updateAttributesPacket.attributes.add(attribute.toNetwork())
        }
        updateAttributesPacket.tick = server.getCurrentTick()
        this.sendPacket(updateAttributesPacket)

        this.server.addToTabList(this)
        if (this.server.getOnlinePlayers().size > 1) {
            val playerListPacket = PlayerListPacket()
            playerListPacket.action = PlayerListPacket.Action.ADD
            this.server.getPlayerListEntry().forEach { (uuid, entry) ->
                if (uuid !== this.getUUID()) {
                    playerListPacket.entries.add(entry)
                }
            }
            this.sendPacket(playerListPacket)
        }

        this.getInventory().addViewer(this)
        this.getInventory().sendContents(this)

        this.getOffHandInventory().addViewer(this)
        this.getOffHandInventory().sendContents(this)

        this.getCursorInventory().addViewer(this)
        this.getCursorInventory().sendContents(this)

        val playStatusPacket = PlayStatusPacket()
        playStatusPacket.status = PlayStatusPacket.Status.PLAYER_SPAWN
        this.sendPacket(playStatusPacket)

        this.getWorld().addEntity(this)
        this.getWorld().setTime(this.getWorld().getTime())
        this.getLoadedChunk()?.toJukeboxChunk()?.addEntity(this)

        this.playerChunkManager.getLoadedChunks()?.forEach { hash ->
            val chunkX = Utils.fromHashX(hash)
            val chunkZ = Utils.fromHashZ(hash)

            this.getWorld().getLoadedChunkEntities(chunkX, chunkZ, this.getDimension()).forEach {
                if (this != it && !it.isClosed() && !it.isDead()) {
                    it.spawn(this)
                }
            }
        }

        this.spawn()

        this.spawned = true

        val playerJoinEvent = PlayerJoinEvent(this, "§e" + this.getName() + " has joined the game")
        this.server.getPluginManager().callEvent(playerJoinEvent)
        playerJoinEvent.getJoinMessage()?.let {
            if (it.isNotEmpty()) {
                this.server.broadcastMessage(it)
            }
        }

        this.server.getLogger().info(
            this.getName() + " logged in [World=" + this.getWorld().getName() + ", X=" +
                    this.getBlockX() + ", Y=" + this.getBlockY() + ", Z=" + this.getBlockZ() +
                    ", Dimension=" + this.getLocation().getDimension().name + "]"
        )
    }

    fun onDisconnect() {
        this.server.removeJukeboxPlayer(this)
        this.getWorld().removeEntity(this)
        this.getLoadedChunk()?.toJukeboxChunk()?.removeEntity(this)

        if (!this.spawned) return

        this.getInventory().removeViewer(this)
        this.getArmorInventory().removeViewer(this)
        this.getCursorInventory().removeViewer(this)
        this.getOffHandInventory().removeViewer(this)
        this.getCreativeCacheInventory().removeViewer(this)
        this.getCraftingGridInventory().removeViewer(this)

        this.getCraftingTableInventory().removeViewer(this)
        this.getGrindstoneInventory().removeViewer(this)
        this.getCartographyTableInventory().removeViewer(this)
        this.getSmithingTableInventory().removeViewer(this)
        this.getAnvilInventory().removeViewer(this)
        this.getStoneCutterInventory().removeViewer(this)

        this.server.removeFromTabList(this)

        this.playerChunkManager.clear()

        this.remove()

        val playerQuitEvent = PlayerQuitEvent(this, "§e" + this.getName() + " left the game")
        this.server.getPluginManager().callEvent(playerQuitEvent)
        playerQuitEvent.getQuitMessage()?.let {
            if (it.isNotEmpty()) {
                this.server.broadcastMessage(it)
            }
        }

        this.server.getAntiCheatRegistry().getModule(AntiCheatCombatModule::class.java).cleanUp(this)

        this.server.getLogger()
            .info(this.getName() + " logged out reason: " + (this.disconnectMessage ?: "Disconnected"))
    }

    fun getLoginData(): LoginData? {
        return this.loginData
    }

    fun setLoginData(loginData: LoginData) {
        this.loginData = loginData
        this.setSkinInternal(loginData.skin)
    }

    fun getPlayerChunkManager(): PlayerChunkManager {
        return this.playerChunkManager
    }

    fun sendPacket(packet: BedrockPacket) {
        val packetSendEvent = PacketSendEvent(this, packet)
        this.server.getPluginManager().callEvent(packetSendEvent)
        if (packetSendEvent.isCancelled()) return
        this.session.sendPacket(packetSendEvent.getBedrockPacket())
    }

    fun sendPacketImmediately(packet: BedrockPacket) {
        val packetSendEvent = PacketSendEvent(this, packet)
        this.server.getPluginManager().callEvent(packetSendEvent)
        if (packetSendEvent.isCancelled()) return
        this.session.sendPacketImmediately(packetSendEvent.getBedrockPacket())
    }

    fun getSession(): BedrockServerSession {
        return this.session
    }

    fun isLoggedIn(): Boolean {
        return this.loggedIn
    }

    fun setLoggedIn(loggedIn: Boolean) {
        this.loggedIn = loggedIn
    }

    fun isSpawned(): Boolean {
        return this.spawned
    }

    override fun isClosed(): Boolean {
        return !this.session.isConnected
    }

    override fun getEntityType(): EntityType {
        return EntityType.PLAYER
    }

    override fun getName(): String {
        return this.loginData?.displayName!!
    }

    override fun getXuid(): String {
        return this.loginData?.xuid!!
    }

    override fun getUUID(): UUID {
        return this.loginData?.uuid!!
    }

    override fun getDeviceInfo(): DeviceInfo {
        return this.loginData?.deviceInfo!!
    }

    override fun getLanguageCode(): String {
        return this.loginData?.languageCode!!
    }

    override fun getGameVersion(): String {
        return this.loginData?.gameVersion!!
    }

    override fun getSkin(): Skin {
        return this.loginData?.skin!!
    }

    override fun isXboxAuthenticated(): Boolean {
        return this.loginData?.xboxAuthenticated!!
    }

    override fun isChunkLoaded(chunkX: Int, chunkZ: Int): Boolean {
        return this.playerChunkManager.isChunkInView(chunkX, chunkZ)
    }

    override fun isWhitelisted(): Boolean {
        return this.server.getWhitelist().isWhitelisted(this.getName())
    }

    override fun setWhitelist(value: Boolean) {
        if (value) this.server.getWhitelist().add(this.getName()) else this.server.getWhitelist().remove(this.getName())
    }

    override fun isOperator(): Boolean {
        return this.server.getOperators().isOperator(this.getName())
    }

    override fun setOperator(value: Boolean) {
        this.adventureSettings.set(AdventureSettings.Type.OPERATOR, value)
        this.adventureSettings.update()
        if (value) this.server.getOperators().add(this.getName()) else this.server.getWhitelist().remove(this.getName())
        this.sendAvailableCommands()
    }

    override fun setGameMode(gameMode: GameMode) {
        super.setGameMode(gameMode)

        this.adventureSettings.set(AdventureSettings.Type.WORLD_IMMUTABLE, this.getGameMode().ordinal == 3)
        this.adventureSettings.set(AdventureSettings.Type.ALLOW_FLIGHT, this.getGameMode().ordinal > 0)
        this.adventureSettings.set(AdventureSettings.Type.NO_CLIP, this.getGameMode().ordinal == 3)
        this.adventureSettings.set(AdventureSettings.Type.FLYING, this.getGameMode().ordinal == 3)
        this.adventureSettings.set(AdventureSettings.Type.ATTACK_MOBS, this.getGameMode().ordinal < 2)
        this.adventureSettings.set(AdventureSettings.Type.ATTACK_PLAYERS, this.getGameMode().ordinal < 2)
        this.adventureSettings.set(AdventureSettings.Type.NO_PVM, this.getGameMode().ordinal == 3)
        this.adventureSettings.update()

        val setPlayerGameTypePacket = SetPlayerGameTypePacket()
        setPlayerGameTypePacket.gamemode = gameMode.ordinal
        this.sendPacket(setPlayerGameTypePacket)
    }

    override fun getAdventureSettings(): AdventureSettings {
        return this.adventureSettings
    }

    override fun sendMessage(message: String) {
        val textPacket = TextPacket()
        textPacket.type = TextPacket.Type.RAW
        textPacket.message = message
        textPacket.isNeedsTranslation = false
        textPacket.xuid = this.getXuid()
        textPacket.platformChatId = this.getDeviceInfo().getDeviceId()
        this.sendPacket(textPacket)
    }

    override fun sendTip(message: String) {
        val textPacket = TextPacket()
        textPacket.type = TextPacket.Type.TIP
        textPacket.message = message
        textPacket.isNeedsTranslation = false
        textPacket.xuid = this.getXuid()
        textPacket.platformChatId = this.getDeviceInfo().getDeviceId()
        this.sendPacket(textPacket)
    }

    override fun sendPopup(message: String) {
        val textPacket = TextPacket()
        textPacket.type = TextPacket.Type.POPUP
        textPacket.message = message
        textPacket.isNeedsTranslation = false
        textPacket.xuid = this.getXuid()
        textPacket.platformChatId = this.getDeviceInfo().getDeviceId()
        this.sendPacket(textPacket)
    }

    override fun getServer(): Server {
        return this.server
    }

    override fun getEmotes(): List<UUID> {
        return this.emotes
    }

    override fun openInventory(inventory: Inventory) {
        this.openInventory(inventory, this.getLocation())
    }

    override fun setSkin(skin: Skin) {
        val playerChangeSkinEvent = PlayerChangeSkinEvent(this, skin)
        if (playerChangeSkinEvent.isCancelled()) return
        val skinPacket = PlayerSkinPacket()
        skinPacket.uuid = this.getUUID()
        skinPacket.skin = skin.toNetwork()
        skinPacket.newSkinName = ""
        skinPacket.oldSkinName = ""
        skinPacket.isTrustedSkin = skin.isTrusted()
        this.server.broadcastPacket(skinPacket)
        super.setSkin(skin)
    }

    override fun setExperience(value: Float) {
        val playerExperienceChangeEvent = PlayerExperienceChangeEvent(
            this,
            this.getExperience(),
            this.getLevel(),
            value,
            0
        )
        if (playerExperienceChangeEvent.isCancelled()) return
        if (playerExperienceChangeEvent.getNewLevel() > 0) {
            this.setLevel(playerExperienceChangeEvent.getNewLevel())
        }
        var percent = value / this.calculateRequireExperience(this.getLevel())
        percent = max(0f, min(1f, percent))
        super.setExperience(percent)
    }

    override fun setLevel(value: Int) {
        val playerExperienceChangeEvent = PlayerExperienceChangeEvent(
            this,
            this.getExperience(),
            this.getLevel(),
            0.0f,
            value
        )
        if (playerExperienceChangeEvent.isCancelled()) return
        if (playerExperienceChangeEvent.getNewExperience() > 0) {
            this.setExperience(playerExperienceChangeEvent.getNewExperience())
        }
        super.setLevel(value)
    }

    fun openInventory(inventory: Inventory, location: Vector) {
        if (this.currentInventory != null) return

        val inventoryOpenEvent = InventoryOpenEvent(inventory, this)
        this.server.getPluginManager().callEvent(inventoryOpenEvent)
        if (inventoryOpenEvent.isCancelled()) return

        (inventory as ContainerInventory).addViewer(this, location, WindowId.OPEN_CONTAINER.getId().toByte())

        this.currentInventory = inventory
    }

    override fun closeInventory(inventory: Inventory) {
        if (this.currentInventory == null) return

        val containerClosePacket = ContainerClosePacket()
        containerClosePacket.id = WindowId.OPEN_CONTAINER.getId().toByte()
        containerClosePacket.type = (inventory as ContainerInventory).getContainerType()
        this.sendPacket(containerClosePacket)

        (this.currentInventory as ContainerInventory).removeViewer(this)

        this.server.getPluginManager().callEvent(InventoryCloseEvent(this.currentInventory!!, this))

        this.currentInventory = null
    }

    override fun closeInventory() {
        this.currentInventory?.let { this.closeInventory(it) }
    }

    fun closeInventory(windowId: Byte, serverInitiated: Boolean) {
        if (currentInventory != null) {
            val containerClosePacket = ContainerClosePacket()
            containerClosePacket.id = windowId
            containerClosePacket.isServerInitiated = serverInitiated
            containerClosePacket.type = (currentInventory as ContainerInventory).getContainerType()
            this.sendPacket(containerClosePacket)
            (currentInventory as ContainerInventory).removeViewer(this)
            this.server.getPluginManager().callEvent(InventoryCloseEvent(currentInventory!!, this))
            this.currentInventory = null
        } else {
            val containerClosePacket = ContainerClosePacket()
            containerClosePacket.id = windowId
            containerClosePacket.isServerInitiated = serverInitiated
            containerClosePacket.type = ContainerType.CONTAINER
            this.sendPacket(containerClosePacket)
        }
    }

    override fun getCurrentInventory(): Inventory? {
        return this.currentInventory
    }

    fun getCraftingGridInventory(): CraftingGridInventory {
        return this.craftingGridInventory
    }

    fun setCraftingGridInventory(craftingGridInventory: CraftingGridInventory) {
        this.craftingGridInventory = craftingGridInventory
    }

    override fun getCursorInventory(): JukeboxCursorInventory {
        return this.cursorInventory
    }

    override fun playSound(sound: Sound, volume: Float, pitch: Float) {
        this.playSound(sound.getIdentifier(), volume, pitch, false)
    }

    override fun playSound(sound: String, volume: Float, pitch: Float) {
        this.playSound(sound, volume, pitch, false)
    }

    override fun getEnderChestInventory(): EnderChestInventory {
        return this.enderChestInventory
    }

    fun getCraftingTableInventory(): JukeboxCraftingTableInventory {
        return this.craftingTableInventory
    }

    fun getGrindstoneInventory(): JukeboxGrindstoneInventory {
        return this.grindstoneInventory
    }

    fun getCartographyTableInventory(): JukeboxCartographyTableInventory {
        return this.cartographyTableInventory
    }

    fun getSmithingTableInventory(): JukeboxSmithingTableInventory {
        return this.smithingTableInventory
    }

    fun getAnvilInventory(): JukeboxAnvilInventory {
        return this.anvilInventory
    }

    fun getStoneCutterInventory(): JukeboxStoneCutterInventory {
        return this.stoneCutterInventory
    }

    fun getLoomInventory(): JukeboxLoomInventory {
        return this.loomInventory
    }

    override fun hasPermission(permission: String): Boolean {
        return this.permissions.containsKey(this.getUUID()) && this.permissions[this.getUUID()]!!.contains(permission.lowercase()) || this.isOperator()
    }

    override fun addPermission(permission: String) {
        if (!this.permissions.containsKey(this.getUUID())) {
            this.permissions[this.getUUID()] = HashSet()
        }
        this.permissions[this.getUUID()]?.add(permission.lowercase())
        this.sendAvailableCommands()
    }

    override fun addPermission(permissions: Collection<String>) {
        if (!this.permissions.containsKey(this.getUUID())) {
            this.permissions[this.getUUID()] = HashSet(permissions)
        } else {
            this.permissions[this.getUUID()]!!.addAll(permissions)
        }
        this.sendAvailableCommands()
    }

    override fun removePermission(permission: String) {
        if (this.permissions.containsKey(this.getUUID())) {
            this.permissions[this.getUUID()]!!.remove(permission)
        }
        this.sendAvailableCommands()
    }

    override fun removePermission(permissions: Collection<String>) {
        if (this.permissions.containsKey(this.getUUID())) {
            this.permissions[this.getUUID()]!!.removeAll(permissions.toSet())
        }
        this.sendAvailableCommands()
    }

    override fun getPermissions(): Collection<String> {
        return if (this.permissions.containsKey(this.getUUID())) this.permissions[this.getUUID()]!! else emptyList()
    }

    override fun kick(reason: String) {
        this.kick(reason, false)
    }

    override fun kick(reason: String, hideReason: Boolean) {
        val playerKickEvent = PlayerKickEvent(this, reason)
        this.server.getPluginManager().callEvent(playerKickEvent)
        if (playerKickEvent.isCancelled()) return
        this.disconnectMessage = playerKickEvent.getReason()
        this.session.disconnect(this.disconnectMessage, hideReason)
    }

    override fun teleport(entity: Entity) {
        this.teleport(entity.getLocation())
    }

    override fun teleport(location: Location) {
        val currentWorld = this.getWorld()
        val currentDimension = this.getDimension()
        val targetWorld = location.getWorld()
        val targetDimension = location.getDimension()

        if (currentWorld.getName() != targetWorld.getName()) {
            this.despawn()
            this.getLoadedChunk()?.toJukeboxChunk()?.removeEntity(this)
            this.getWorld().removeEntity(this)

            this.setLocation(location)
            this.getWorld().addEntity(this)
            this.spawn()
            this.playerChunkManager.prepareRegion(location)
        } else if (currentDimension != targetDimension) {
            this.playerChunkManager.clear()

            val setSpawnPositionPacket = SetSpawnPositionPacket()
            setSpawnPositionPacket.dimensionId = location.getDimension().ordinal
            setSpawnPositionPacket.spawnPosition = location.toVector3i().div(8, 8, 8)
            setSpawnPositionPacket.spawnType = SetSpawnPositionPacket.Type.PLAYER_SPAWN
            this.sendPacket(setSpawnPositionPacket)

            val changeDimensionPacket = ChangeDimensionPacket()
            changeDimensionPacket.position = location.toVector3f()
            changeDimensionPacket.dimension = location.getDimension().ordinal
            changeDimensionPacket.isRespawn = false
            this.sendPacket(changeDimensionPacket)
        } else {
            this.setLocation(location)
        }
        this.move(location)
    }

    override fun playEmote(emote: Emote) {
        val emotePacket = EmotePacket()
        emotePacket.runtimeEntityId = this.getEntityId()
        emotePacket.xuid = this.getXuid()
        emotePacket.platformId = ""
        emotePacket.emoteId = emote.getUUID().toString()
        this.getWorld().sendChunkPacket(this.getChunkX(), this.getChunkZ(), emotePacket)
    }

    override fun getRespawnLocation(): Location? {
        return this.respawnLocation
    }

    override fun allowFlying(): Boolean {
        return this.adventureSettings.get(AdventureSettings.Type.ALLOW_FLIGHT)
    }

    override fun setAllowFlying(allowFlying: Boolean) {
        this.adventureSettings.set(AdventureSettings.Type.ALLOW_FLIGHT, allowFlying)
        this.adventureSettings.update()
    }

    override fun isFlying(): Boolean {
        return this.adventureSettings.get(AdventureSettings.Type.FLYING)
    }

    override fun setFlying(flying: Boolean) {
        this.adventureSettings.set(AdventureSettings.Type.FLYING, flying)
        this.adventureSettings.update()
    }

    override fun sendServerSettings(player: Player) {
        if (this.serverSettingsForm != -1) {
            val form = this.forms[this.serverSettingsForm]
            val packet = ServerSettingsResponsePacket()
            packet.formId = this.serverSettingsForm
            packet.formData = form.toJson().toString()
            this.sendPacket(packet)
        }
    }

    override fun <R : Any> showForm(form: Form<R>): FormListener<R> {
        if (this.hasOpenForm) {
            return FormListener()
        }
        val formId = this.formId++
        this.forms[formId] = form
        val formListener = FormListener<R>()
        this.formListeners[formId] = formListener
        val json = form.toJson().toString()
        val packet = ModalFormRequestPacket()
        packet.formId = formId
        packet.formData = json
        this.sendPacket(packet)
        this.hasOpenForm = true
        // dirty fix to show image on button list
        this.server.getScheduler().scheduleDelayed({
            this.setAttributes(Attribute.PLAYER_LEVEL, this.getLevel().toFloat())
        }, 5)
        return formListener
    }

    override fun <R : Any> setSettingsForm(form: Form<R>): FormListener<R> {
        if (this.serverSettingsForm != -1) {
            this.removeSettingsForm()
        }
        val formId = this.formId++
        this.forms[formId] = form
        val formListener = FormListener<R>()
        this.formListeners[formId] = formListener
        this.serverSettingsForm = formId
        return formListener
    }

    override fun removeSettingsForm() {
        if (this.serverSettingsForm != -1) {
            this.forms.remove(this.serverSettingsForm)
            this.formListeners.remove(this.serverSettingsForm)
            this.serverSettingsForm = -1
        }
    }

    override fun sendToast(content: String) {
        this.sendPacket(ToastRequestPacket().apply {
            this.content = content
        })
    }

    fun parseGUIResponse(formId: Int, json: String) {
        val form = this.forms[formId]
        if (form != null) {
            val formListener = this.formListeners[formId]
            if (this.serverSettingsForm != formId) {
                this.forms.remove(formId)
                this.formListeners.remove(formId)
            }
            this.hasOpenForm = false
            if (json == "null") {
                formListener.getCloseConsumer().accept(null)
            } else {
                val response = form.parseResponse(json)
                if (response == null) {
                    formListener.getCloseConsumer().accept(null)
                } else {
                    formListener.getResponseConsumer().accept(response)
                }
            }
        }
    }

    override fun damage(event: EntityDamageEvent): Boolean {
        return if (adventureSettings.get(AdventureSettings.Type.ALLOW_FLIGHT) && event.getDamageSource() == EntityDamageEvent.DamageSource.FALL) {
            false
        } else this.getGameMode() != GameMode.CREATIVE && this.getGameMode() != GameMode.SPECTATOR && super.damage(event)
    }

    override fun applyArmorReduction(event: EntityDamageEvent, damageArmor: Boolean): Float {
        if (event.getDamageSource() == EntityDamageEvent.DamageSource.FALL ||
            event.getDamageSource() == EntityDamageEvent.DamageSource.VOID ||
            event.getDamageSource() == EntityDamageEvent.DamageSource.DROWNING
        ) {
            return event.getDamage()
        }
        val damage = event.getDamage()
        val totalArmorValue = this.getArmorInventory().getTotalArmorValue()

        if (damageArmor) {
            this.getArmorInventory().damageEvenly(damage)
        }
        val value = -damage * totalArmorValue * 0.04f
        return event.getDamage() + value
    }

    override fun applyFeatherFallingReduction(event: EntityDamageEvent, damage: Float): Float {
        if (event.getDamageSource() == EntityDamageEvent.DamageSource.FALL) {
            val boots = this.getArmorInventory().getBoots()
            if (boots.getType() != ItemType.AIR) {
                val enchantment = boots.getEnchantment(EnchantmentType.FEATHER_FALLING)
                if (enchantment != null) {
                    val featherFallingReduction = 12 * enchantment.getLevel()
                    val value = -(damage * (featherFallingReduction / 100f))
                    return damage + value
                }
            }
        }
        return damage
    }

    override fun applyProtectionReduction(event: EntityDamageEvent, damage: Float): Float {
        if (event.getDamageSource() == EntityDamageEvent.DamageSource.ENTITY_ATTACK) {
            val protectionReduction = this.getProtectionReduction()
            val value = -(damage * (protectionReduction / 100f))
            return (damage + value)
        }
        return damage
    }

    override fun applyProjectileProtectionReduction(event: EntityDamageEvent, damage: Float): Float {
        if (event.getDamageSource() == EntityDamageEvent.DamageSource.PROJECTILE) {
            val protectionReduction = this.getProjectileProtectionReduction()
            val value = -(damage * (protectionReduction / 100f))
            return damage + value
        }
        return damage
    }

    override fun applyFireProtectionReduction(event: EntityDamageEvent, damage: Float): Float {
        if (event.getDamageSource() == EntityDamageEvent.DamageSource.ON_FIRE) {
            val protectionReduction = this.getFireProtectionReduction()
            val value = -(damage * (protectionReduction / 100f))
            return damage + value
        }
        return damage
    }

    private fun getProtectionReduction(): Float {
        var protectionReduction = 0f
        for (content in this.getArmorInventory().getContents()) {
            if (content is Armor) {
                val enchantment = content.getEnchantment(EnchantmentType.PROTECTION)
                if (enchantment != null) {
                    protectionReduction += 4 * enchantment.getLevel()
                }
            }
        }
        return protectionReduction
    }

    private fun getProjectileProtectionReduction(): Float {
        var protectionReduction = 0f
        for (content in this.getArmorInventory().getContents()) {
            if (content is Armor) {
                val enchantment = content.getEnchantment(EnchantmentType.PROJECTILE_PROTECTION)
                if (enchantment != null) {
                    protectionReduction += enchantment.getLevel() * 8
                }
            }
        }
        return protectionReduction
    }

    private fun getFireProtectionReduction(): Float {
        var protectionReduction = 0f
        for (content in this.getArmorInventory().getContents()) {
            if (content is Armor) {
                val enchantment = content.getEnchantment(EnchantmentType.FIRE_PROTECTION)
                if (enchantment != null) {
                    protectionReduction += 15 * enchantment.getLevel()
                }
            }
        }
        return protectionReduction
    }

    override fun handleKill() {
        if (!this.isDead()) {
            super.handleKill()
            val entityEventPacket = EntityEventPacket()
            entityEventPacket.runtimeEntityId = this.getEntityId()
            entityEventPacket.type = EntityEventType.DEATH
            this.sendPacket(entityEventPacket)

            this.setFallingDistance(0F)
            this.setHighestPosition(0F)
            this.setInAirTicks(0L)

            val deathMessage: String = when (this.getLastDamageSource()) {
                EntityDamageEvent.DamageSource.ENTITY_ATTACK -> this.getNameTag() + " was slain by " + this.getLastDamageEntity()!!
                    .getNameTag()

                EntityDamageEvent.DamageSource.FALL -> this.getNameTag() + " fell from a high place"
                EntityDamageEvent.DamageSource.LAVA -> this.getNameTag() + " tried to swim in lava"
                EntityDamageEvent.DamageSource.FIRE -> this.getNameTag() + " went up in flames"
                EntityDamageEvent.DamageSource.VOID -> this.getNameTag() + " fell out of the world"
                EntityDamageEvent.DamageSource.CACTUS -> this.getNameTag() + " was pricked to death"
                EntityDamageEvent.DamageSource.STARVE -> this.getNameTag() + " starved to death"
                EntityDamageEvent.DamageSource.ON_FIRE -> this.getNameTag() + " burned to death"
                EntityDamageEvent.DamageSource.DROWNING -> this.getNameTag() + " drowned"
                EntityDamageEvent.DamageSource.MAGIC_EFFECT -> this.getNameTag() + " was killed by magic"
                EntityDamageEvent.DamageSource.ENTITY_EXPLODE -> this.getNameTag() + " blew up"
                EntityDamageEvent.DamageSource.PROJECTILE -> this.getNameTag() + " has been shot"
                EntityDamageEvent.DamageSource.API -> this.getNameTag() + " was killed by setting health to 0"
                EntityDamageEvent.DamageSource.COMMAND -> this.getNameTag() + " died"
                else -> {
                    this.getNameTag() + " died"
                }
            }
            val playerDeathEvent = PlayerDeathEvent(this, deathMessage, null, true, this.getDrops())
            this.server.getPluginManager().callEvent(playerDeathEvent)
            if (playerDeathEvent.dropInventory()) {
                for (drop in playerDeathEvent.getDrops()) {
                    this.getWorld().dropItemNaturally(this.getLocation(), drop)
                }
                this.getInventory().clear()
                this.getCursorInventory().clear()
                this.getArmorInventory().clear()
            }
            playerDeathEvent.getDeathMessage()?.let {
                if (it.isNotEmpty()) {
                    this.server.broadcastMessage(it)
                }
            }

            this.respawnLocation =
                this.getWorld().getSpawnLocation(Dimension.OVERWORLD).add(0F, this.getEyeHeight(), 0F)
            val respawnPositionPacket = RespawnPacket()
            respawnPositionPacket.runtimeEntityId = this.getEntityId()
            respawnPositionPacket.state = RespawnPacket.State.SERVER_SEARCHING
            respawnPositionPacket.position = this.respawnLocation!!.toVector3f()
            this.sendPacket(respawnPositionPacket)

            playerDeathEvent.getDeathScreenMessage()?.let {
                if (it.isNotEmpty()) {
                    val deathInfoPacket = DeathInfoPacket()
                    deathInfoPacket.messageList.add(getName())
                    deathInfoPacket.causeAttackName = playerDeathEvent.getDeathScreenMessage()
                    this.sendPacket(deathInfoPacket)
                }
            }
        }
        this.setLastDamageSource(EntityDamageEvent.DamageSource.COMMAND)
    }

    fun respawn() {
        if (this.isDead()) {
            val playerRespawnEvent = PlayerRespawnEvent(this, this.respawnLocation!!)
            this.server.getPluginManager().callEvent(playerRespawnEvent)
            this.setLastDamageEntity(null)
            this.setLastDamageSource(EntityDamageEvent.DamageSource.COMMAND)
            this.setLastDamage(0F)
            this.updateMetadata()
            for (attribute in this.getAttributes()) {
                attribute.reset()
            }
            this.updateAttributes()
            this.teleport(playerRespawnEvent.getRespawnLocation())
            this.respawnLocation = null

            this.spawn()
            this.setOnFire(false)
            this.setVelocity(Vector.zero())

            this.getInventory().sendContents(this)
            this.cursorInventory.sendContents(this)
            this.getArmorInventory().sendContents(this)

            val entityEventPacket = EntityEventPacket()
            entityEventPacket.runtimeEntityId = this.getEntityId()
            entityEventPacket.type = EntityEventType.RESPAWN
            this.server.broadcastPacket(entityEventPacket)

            this.getInventory().getItemInHand().toJukeboxItem().addToHand(this)
            this.setDead(false)
        }
    }

    fun attackWithItemInHand(target: Entity): Boolean {
        if (target is JukeboxEntityLiving) {
            var success = false
            var damage = this.getAttackDamage()
            if (this.hasEffect(EffectType.STRENGTH)) {
                damage *= 0.3f * (this.getEffect(EffectType.STRENGTH)!!.getAmplifier() + 1)
            }
            if (this.hasEffect(EffectType.WEAKNESS)) {
                damage = -(damage * 0.2f * this.getEffect(EffectType.WEAKNESS)!!.getAmplifier() + 1)
            }
            val sharpness = this.getInventory().getItemInHand().getEnchantment(EnchantmentType.SHARPNESS)
            if (sharpness != null) {
                damage += sharpness.getLevel() * 1.25f
            }
            if (damage > 0) {
                val crit =
                    this.getFallingDistance() > 0 && !this.isOnGround() && !this.isOnLadder() && !this.isInWater()
                if (crit && damage > 0.0f) {
                    damage *= 1.5.toFloat()
                }
                target.damage(
                    EntityDamageByEntityEvent(
                        target,
                        this,
                        damage,
                        EntityDamageEvent.DamageSource.ENTITY_ATTACK,
                    )
                ).also {
                    success = it
                }
            }
            if (success && this.getGameMode() == GameMode.SURVIVAL) {
                this.exhaust(0.3f)
            }
            return success
        }
        return false
    }

    fun getDrops(): MutableList<Item> {
        val drops: MutableList<Item> = ArrayList<Item>()
        for (content in this.getInventory().getContents()) {
            if (content.getType() != ItemType.AIR) {
                if (content.getEnchantment(EnchantmentType.CURSE_OF_VANISHING) == null) {
                    drops.add(content)
                }
            }
        }
        for (content in this.getCursorInventory().getContents()) {
            if (content.getType() != ItemType.AIR) {
                if (content.getEnchantment(EnchantmentType.CURSE_OF_VANISHING) == null) {
                    drops.add(content)
                }
            }
        }
        for (content in this.getArmorInventory().getContents()) {
            if (content.getType() != ItemType.AIR) {
                if (content.getEnchantment(EnchantmentType.CURSE_OF_VANISHING) == null) {
                    drops.add(content)
                }
            }
        }
        return drops
    }

    private fun move(location: Location) {
        val movePlayerPacket = MovePlayerPacket()
        movePlayerPacket.runtimeEntityId = this.getEntityId()
        movePlayerPacket.position = location.toVector3f().add(0.0, getEyeHeight().toDouble(), 0.0)
        movePlayerPacket.rotation = Vector3f.from(location.getPitch(), location.getYaw(), location.getYaw())
        movePlayerPacket.mode = MovePlayerPacket.Mode.TELEPORT
        movePlayerPacket.teleportationCause = MovePlayerPacket.TeleportationCause.BEHAVIOR
        movePlayerPacket.isOnGround = this.isOnGround()
        movePlayerPacket.ridingRuntimeEntityId = 0
        movePlayerPacket.tick = server.getCurrentTick()
        this.sendPacket(movePlayerPacket)
    }

    fun playSound(location: Vector, sound: Sound, volume: Float, pitch: Float, chunkPlayers: Boolean) {
        val soundPacket = PlaySoundPacket()
        soundPacket.sound = sound.getIdentifier()
        soundPacket.volume = volume
        soundPacket.pitch = pitch
        soundPacket.position = location.toVector3f()
        if (chunkPlayers) {
            this.getWorld().sendChunkPacket(this.getChunkX(), this.getChunkZ(), soundPacket)
        } else {
            this.sendPacket(soundPacket)
        }
    }

    fun playSound(sound: Sound, volume: Float, pitch: Float, chunkPlayers: Boolean) {
        val soundPacket = PlaySoundPacket()
        soundPacket.sound = sound.getIdentifier()
        soundPacket.volume = volume
        soundPacket.pitch = pitch
        soundPacket.position = this.getLocation().toVector3f()
        if (chunkPlayers) {
            this.getWorld().sendChunkPacket(this.getChunkX(), this.getChunkZ(), soundPacket)
        } else {
            this.sendPacket(soundPacket)
        }
    }

    fun playSound(sound: String, volume: Float, pitch: Float, chunkPlayers: Boolean) {
        val soundPacket = PlaySoundPacket()
        soundPacket.sound = sound
        soundPacket.volume = volume
        soundPacket.pitch = pitch
        soundPacket.position = this.getLocation().toVector3f()
        if (chunkPlayers) {
            this.getWorld().sendChunkPacket(this.getChunkX(), this.getChunkZ(), soundPacket)
        } else {
            this.sendPacket(soundPacket)
        }
    }

    fun getCreativeCacheInventory(): CreativeItemCacheInventory {
        return this.creativeCacheInventory
    }

    fun setEmotes(emotes: MutableList<UUID>) {
        this.emotes = emotes
    }

    fun isSignFrontSide(): Boolean? {
        return this.signFrontSide
    }

    fun setSignFrontSide(signFrontSide: Boolean?) {
        this.signFrontSide = signFrontSide
    }

    fun openSignEditor(location: Vector, frontSide: Boolean) {
        if (this.signFrontSide != null) return
        this.getWorld().getBlockEntity(location)?.let {
            if (it is JukeboxBlockEntitySign) {
                if (it.getUsedByEntity() == -1L) {
                    it.setUsedByEntity(this.getEntityId())

                    val openSignPacket = OpenSignPacket()
                    openSignPacket.position = location.toVector3i()
                    openSignPacket.isFrontSide = frontSide
                    this.sendPacket(openSignPacket)
                    this.signFrontSide = frontSide
                }
            }
        }
    }

    fun getLastBreakTime(): Long {
        return this.lastBreakTime
    }

    fun setLastBreakTime(lastBreakTime: Long) {
        this.lastBreakTime = lastBreakTime
    }

    fun getLastBreakPosition(): Vector {
        return this.lastBreakPosition
    }

    fun setLastBreakPosition(lastBreakPosition: Vector) {
        this.lastBreakPosition = lastBreakPosition
    }

    fun isBreakingBlock(): Boolean {
        return this.breakingBlock
    }

    fun setBreakingBlock(breakingBlock: Boolean) {
        this.breakingBlock = breakingBlock
    }

    fun sendAvailableCommands() {
        val availableCommandsPacket = AvailableCommandsPacket()
        val commandList: MutableSet<CommandData> = mutableSetOf()

        for (command in this.server.getCommandManager().commands) {
            val commandData = command.getCommandData() ?: continue
            if (commandData.permission == null) continue
            if (!this.hasPermission(commandData.permission!!)) continue
            commandList.add(commandData.toNetwork())
        }
        availableCommandsPacket.commands.addAll(commandList)
        this.sendPacket(availableCommandsPacket)
    }

    fun updateAttributes() {
        var updateAttributesPacket: UpdateAttributesPacket? = null
        for (attribute in getAttributes()) {
            if (attribute.isDirty()) {
                if (updateAttributesPacket == null) {
                    updateAttributesPacket = UpdateAttributesPacket()
                    updateAttributesPacket.runtimeEntityId = this.getEntityId()
                }
                updateAttributesPacket.attributes.add(attribute.toNetwork())
            }
        }
        if (updateAttributesPacket != null) {
            updateAttributesPacket.tick = server.getCurrentTick()
            this.sendPacket(updateAttributesPacket)
        }
    }

    override fun setLocation(location: Location) {
        super.setLocation(location)
        this.checkChunks()
    }

    override fun getLoadedChunk(): Chunk? {
        return this.currentChunk
    }

    fun checkChunks() {
        if (this.currentChunk == null || currentChunk!!.getX() != this.getChunkX() || currentChunk!!.getZ() != this.getChunkZ()) {
            if (this.currentChunk != null) {
                this.currentChunk!!.removeEntity(this)
            }
            this.currentChunk =
                this.getWorld().getChunk(this.getChunkX(), this.getChunkZ(), this.getDimension())?.toJukeboxChunk()
            if (this.currentChunk == null) return

            val loaders = ImmutableSet.copyOf(this.currentChunk!!.getLoaders())

            this.spawnedPlayers().forEach { player ->
                if (!loaders.contains(player)) {
                    this.despawn(player)
                }
            }

            this.currentChunk?.toJukeboxChunk()?.getLoaders()?.forEach {
                if (it is JukeboxPlayer) {
                    this.spawn(it)
                }
            }
            this.currentChunk!!.addEntity(this)
        }
    }

    fun setLastBlockAction(actionData: PlayerBlockActionData) {
        this.lastBlockAction = actionData
    }

    fun getLastBlockAction() = this.lastBlockAction

    fun setInputMode(inputMode: InputMode) {
        this.inputMode = inputMode
    }

    fun getInputMode() = this.inputMode

    fun setPlayMode(playMode: ClientPlayMode) {
        this.playMode = playMode
    }

    fun getPlayMode() = this.playMode

    fun setInputInteractionModel(interactionModel: InputInteractionModel) {
        this.inputInteractionModel = interactionModel
    }

    fun getInputInteractionModel() = this.inputInteractionModel
}