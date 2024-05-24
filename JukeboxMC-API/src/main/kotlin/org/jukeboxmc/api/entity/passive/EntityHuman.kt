package org.jukeboxmc.api.entity.passive

import org.jukeboxmc.api.entity.EntityLiving
import org.jukeboxmc.api.inventory.ArmorInventory
import org.jukeboxmc.api.inventory.InventoryHolder
import org.jukeboxmc.api.inventory.OffHandInventory
import org.jukeboxmc.api.inventory.PlayerInventory
import org.jukeboxmc.api.player.Emote
import org.jukeboxmc.api.player.GameMode
import org.jukeboxmc.api.player.info.DeviceInfo
import org.jukeboxmc.api.player.skin.Skin
import java.util.*

/**
 * @author Kaooot
 * @version 1.0
 */
interface EntityHuman : EntityLiving, InventoryHolder {

    fun getUUID(): UUID

    fun getDeviceInfo(): DeviceInfo

    fun getSkin(): Skin?

    fun setSkin(skin: Skin)

    fun isSneaking(): Boolean

    fun setSneaking(value: Boolean)

    fun isSprinting(): Boolean

    fun setSprinting(value: Boolean)

    fun isSwimming(): Boolean

    fun setSwimming(value: Boolean)

    fun isGliding(): Boolean

    fun setGliding(value: Boolean)

    fun hasAction(): Boolean

    fun setAction(value: Boolean)

    fun isHungry(): Boolean

    fun getHunger(): Int

    fun setHunger(value: Int)

    fun addHunger(value: Int)

    fun getSaturation(): Float

    fun setSaturation(value: Float)

    fun addSaturation(value: Float)

    fun getExhaustion(): Float

    fun setExhaustion(value: Float)

    fun getExperience(): Float

    fun setExperience(value: Float)

    fun addExperience(value: Float)

    fun getLevel(): Int

    fun setLevel(value: Int)

    fun getInventory(): PlayerInventory

    fun getArmorInventory(): ArmorInventory

    fun getOffHandInventory(): OffHandInventory

    fun getGameMode(): GameMode

    fun setGameMode(gameMode: GameMode)

    fun playEmote(emote: Emote)

}