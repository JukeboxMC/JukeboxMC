package org.jukeboxmc.api.player

import org.jukeboxmc.api.Server
import org.jukeboxmc.api.command.CommandSender
import org.jukeboxmc.api.entity.Entity
import org.jukeboxmc.api.entity.passive.EntityHuman
import org.jukeboxmc.api.inventory.CursorInventory
import org.jukeboxmc.api.inventory.EnderChestInventory
import org.jukeboxmc.api.inventory.Inventory
import org.jukeboxmc.api.math.Location
import org.jukeboxmc.api.player.info.DeviceInfo
import org.jukeboxmc.api.player.skin.Skin
import org.jukeboxmc.api.world.Sound
import java.util.*

interface Player : EntityHuman, CommandSender {

    /**
     * Returns the xbox identifier of the player
     *
     * @return the xbox id of the player
     */
    fun getXuid(): String

    /**
     * Returns the unique identifier of the player
     *
     * @return the uuid of the player
     */
    override fun getUUID(): UUID

    /**
     * Retrieves information about the player's device
     *
     * @return device information
     */
    override fun getDeviceInfo(): DeviceInfo

    /**
     * Retrieves the language code of the player.
     * It will be one of the languages that are supported by the client itself
     *
     * @return the language code string, e.g. "en_GB"
     */
    fun getLanguageCode(): String

    /**
     * Retrieves the game version of the player
     *
     * @return the client's version, e.g. "1.20.40"
     */
    fun getGameVersion(): String

    /**
     * Returns the player's skin
     *
     * @return a fresh skin
     */
    override fun getSkin(): Skin

    /**
     * Used to proof whether the player is authenticated with xbox live.
     * This should always return true if the online mode of the server is enabled,
     * otherwise the player can't connect to the server.
     *
     * @return if the user is authenticated
     */
    fun isXboxAuthenticated(): Boolean

    fun isChunkLoaded(chunkX: Int, chunkZ: Int): Boolean

    fun isWhitelisted(): Boolean

    fun setWhitelist(value: Boolean)

    fun isOperator(): Boolean

    fun setOperator(value: Boolean)

    override fun getGameMode(): GameMode

    override fun setGameMode(gameMode: GameMode)

    fun getAdventureSettings(): AdventureSettings

    override fun sendMessage(message: String)

    fun sendTip(message: String)

    fun sendPopup(message: String)

    override fun getServer(): Server

    fun getEmotes(): List<UUID>

    fun openInventory(inventory: Inventory)

    fun closeInventory(inventory: Inventory)

    fun getCurrentInventory(): Inventory?

    fun getCursorInventory(): CursorInventory

    fun playSound(sound: Sound, volume: Float, pitch: Float)

    fun playSound(sound: String, volume: Float, pitch: Float)

    fun getEnderChestInventory(): EnderChestInventory

    fun addPermission(permission: String)

    fun addPermission(permissions: Collection<String>)

    fun removePermission(permission: String)

    fun removePermission(permissions: Collection<String>)

    fun getPermissions(): Collection<String>

    fun kick(reason: String)

    fun kick(reason: String, hideReason: Boolean)

    fun teleport(location: Location)

    fun teleport(entity: Entity)

    override fun playEmote(emote: Emote)

    fun getRespawnLocation(): Location?

    fun allowFlying(): Boolean

    fun setAllowFlying(allowFlying: Boolean)

    fun isFlying(): Boolean

    fun setFlying(flying: Boolean)

}