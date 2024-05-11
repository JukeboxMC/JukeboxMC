package org.jukeboxmc.api.item

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.JukeboxMC
import org.jukeboxmc.api.block.Block
import org.jukeboxmc.api.item.enchantment.Enchantment
import org.jukeboxmc.api.item.enchantment.EnchantmentType

interface Item {

    companion object {
        fun create(itemType: ItemType): Item {
            return JukeboxMC.getServer().createItem(itemType)
        }

        fun <T> create(itemType: ItemType): T {
            return JukeboxMC.getServer().createItem(itemType)
        }

        fun create(itemType: ItemType, amount: Int): Item {
            return JukeboxMC.getServer().createItem(itemType, amount)
        }

        fun <T> create(itemType: ItemType, amount: Int): T {
            return JukeboxMC.getServer().createItem(itemType, amount)
        }

        fun create(itemType: ItemType, amount: Int, meta: Int): Item {
            return JukeboxMC.getServer().createItem(itemType, amount, meta)
        }

        fun <T> create(itemType: ItemType, amount: Int, meta: Int): T {
            return JukeboxMC.getServer().createItem(itemType, amount, meta)
        }

        fun fromBase64(base64: String): Item? {
            return JukeboxMC.getServer().fromBase64(base64)
        }
    }

    fun getNetworkId(): Int

    fun getBlockNetworkId(): Int

    fun getStackNetworkId(): Int

    fun getIdentifier(): Identifier

    fun getType(): ItemType

    fun getAmount(): Int

    fun setAmount(amount: Int)

    fun getMeta(): Int

    fun setMeta(meta: Int)

    fun getDurability(): Int

    fun setDurability(durability: Int)

    fun getDisplayName(): String

    fun setDisplayName(displayName: String)

    fun getLore(): MutableList<String>

    fun setLore(lore: MutableList<String>)

    fun getNbt(): NbtMap?

    fun getMaxStackSize(): Int

    fun isUnbreakable(): Boolean

    fun setUnbreakable(unbreakable: Boolean)

    fun getLockType(): LockType

    fun setLockType(lockType: LockType)

    fun hasEnchantment(enchantmentType: EnchantmentType): Boolean

    fun addEnchantment(enchantmentType: EnchantmentType, level: Int)

    fun getEnchantment(enchantmentType: EnchantmentType): Enchantment?

    fun getEnchantments(): Collection<Enchantment>

    fun getToolType(): ToolType

    fun getTierType(): TierType

    fun hasBlock(): Boolean

    fun toBlock(): Block

    fun isSimilar(item: Item) : Boolean

    fun toBase64(): String

    override fun equals(other: Any?): Boolean

    fun clone(): Item
}