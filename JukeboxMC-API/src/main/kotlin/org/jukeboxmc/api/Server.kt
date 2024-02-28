package org.jukeboxmc.api

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.block.Block
import org.jukeboxmc.api.block.BlockType
import org.jukeboxmc.api.blockentity.BlockEntity
import org.jukeboxmc.api.blockentity.BlockEntityType
import org.jukeboxmc.api.command.CommandManager
import org.jukeboxmc.api.command.CommandSender
import org.jukeboxmc.api.command.ConsoleSender
import org.jukeboxmc.api.effect.Effect
import org.jukeboxmc.api.effect.EffectType
import org.jukeboxmc.api.item.Item
import org.jukeboxmc.api.item.ItemType
import org.jukeboxmc.api.logger.Logger
import org.jukeboxmc.api.player.GameMode
import org.jukeboxmc.api.player.Player
import org.jukeboxmc.api.plugin.PluginManager
import org.jukeboxmc.api.recipe.RecipeManager
import org.jukeboxmc.api.resourcepack.ResourcePackManager
import org.jukeboxmc.api.scheduler.Scheduler
import org.jukeboxmc.api.world.Dimension
import org.jukeboxmc.api.world.World
import org.jukeboxmc.api.world.generator.Generator
import java.io.File
import java.util.*

interface Server {

    fun getLogger(): Logger

    fun getAddress(): String

    fun getPort(): Int

    fun getMaxPlayers(): Int

    fun getViewDistance(): Int

    fun getSimulationDistance(): Int

    fun getMotd(): String

    fun getSubMotd(): String

    fun getDefaultWorldName(): String

    fun getDefaultGameMode(): GameMode

    fun getGeneratorName(): String

    fun isOnlineMode(): Boolean

    fun isForceResourcePacks(): Boolean

    fun isWhitelist(): Boolean

    fun setWhitelist(whitelist: Boolean)

    fun loadWorld(name: String)

    fun loadWorld(name: String, dimensionGenerator: Map<Dimension, String>)

    fun unloadWorld(name: String)

    fun getWorld(name: String) : World?

    fun getDefaultWorld(): World

    fun getWorlds() : Collection<World>

    fun getPlayer(name: String): Player?

    fun getPlayerExact(name: String): Player?

    fun getPlayer(uuid: UUID): Player?

    fun getOnlinePlayers(): Collection<Player>

    fun shutdown()

    fun getScheduler(): Scheduler

    fun getCurrentTick(): Long

    fun getPluginFolder(): File

    fun getTps(): Long

    fun isEncrypted() : Boolean

    fun getResourcePackManager(): ResourcePackManager

    fun getWhitelist(): Whitelist

    fun <T : Block> createBlock(blockType: BlockType): T

    fun <T : Block> createBlock(blockType: BlockType, blockStates: NbtMap?): T

    fun <T: Item> createItem(itemType: ItemType): T

    fun <T: Item> createItem(itemType: ItemType, amount: Int): T

    fun <T: Item> createItem(itemType: ItemType, amount: Int, meta: Int): T

    fun <T: BlockEntity> createBlockEntity(blockEntityType: BlockEntityType, block: Block): T?

    fun createEffect(effectType: EffectType): Effect

    fun getPluginManager(): PluginManager

    fun getCommandManager(): CommandManager

    fun getRecipeManager(): RecipeManager

    fun registerGenerator(name: String, clazz: Class<out Generator>, vararg dimension: Dimension)

    fun getMinecraftVersion(): String

    fun getMinecraftProtocol(): Int

    fun getOperators(): Operators

    fun broadcastMessage(message: String)

    fun getConsoleSender(): ConsoleSender

    fun dispatchCommand(commandSender: CommandSender, command: String)
}