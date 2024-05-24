package org.jukeboxmc.server.item

import io.netty.buffer.ByteBufOutputStream
import io.netty.buffer.Unpooled
import org.cloudburstmc.nbt.NbtMap
import org.cloudburstmc.nbt.NbtType
import org.cloudburstmc.nbt.NbtUtils
import org.cloudburstmc.protocol.bedrock.data.definitions.SimpleItemDefinition
import org.cloudburstmc.protocol.bedrock.data.inventory.ItemData
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.Block
import org.jukeboxmc.api.block.BlockType
import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.api.item.*
import org.jukeboxmc.api.item.enchantment.Enchantment
import org.jukeboxmc.api.item.enchantment.EnchantmentType
import org.jukeboxmc.api.math.Location
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.api.player.GameMode
import org.jukeboxmc.api.world.Sound
import org.jukeboxmc.server.block.BlockRegistry
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.block.RuntimeBlockDefinition
import org.jukeboxmc.server.extensions.contentEquals
import org.jukeboxmc.server.extensions.toJukeboxItem
import org.jukeboxmc.server.item.enchantment.EnchantmentRegistry
import org.jukeboxmc.server.item.enchantment.JukeboxEnchantment
import org.jukeboxmc.server.player.JukeboxPlayer
import org.jukeboxmc.server.util.BlockPalette
import org.jukeboxmc.server.util.ItemPalette
import org.jukeboxmc.server.util.Utils
import java.util.*

open class JukeboxItem : Item, Cloneable {

    private var itemType: ItemType
    private var identifier: Identifier
    private var networkId: Int
    private var blockNetworkId: Int
    private var stackNetworkId: Int = 0
    private var amount: Int
    private var meta: Int
    private var durability: Int
    private var displayName: String
    private var lore: MutableList<String>
    private var nbtMap: NbtMap? = null
    private var enchantments: MutableMap<EnchantmentType, Enchantment> = mutableMapOf()
    private var unbreakable: Boolean
    private var lockType: LockType

    constructor(itemType: ItemType, countNetworkId: Boolean) {
        this.itemType = itemType
        this.identifier = ItemRegistry.getIdentifier(itemType)
        this.networkId = ItemPalette.getRuntimeId(this.identifier.getFullName())
        if (BlockRegistry.hasItemBlock(this.identifier)) {
            this.blockNetworkId = Block.create(BlockType.valueOf(this.itemType.name)).getNetworkId()
        } else {
            this.blockNetworkId = 0
        }
        if (countNetworkId && this.itemType != ItemType.AIR) {
            this.stackNetworkId = stackNetworkCount++
        }
        this.amount = 1
        this.meta = 0
        this.durability = 0
        this.displayName = ""
        this.lore = mutableListOf()
        this.nbtMap = null
        this.enchantments = mutableMapOf()
        this.unbreakable = false
        this.lockType = LockType.NONE
    }

    constructor(itemData: ItemData, countNetworkId: Boolean) {
        this.identifier = Identifier.fromString(itemData.definition.identifier)
        this.itemType = ItemRegistry.getItemType(this.identifier)
        this.networkId = ItemPalette.getRuntimeId(this.identifier.getFullName())
        this.blockNetworkId = if (itemData.blockDefinition != null) itemData.blockDefinition!!.runtimeId else 0
        if (countNetworkId && this.itemType != ItemType.AIR) {
            this.stackNetworkId = stackNetworkCount++
        }
        this.amount = itemData.count
        this.meta = itemData.damage
        this.durability = 0
        this.displayName = ""
        this.lore = mutableListOf()
        this.enchantments = mutableMapOf()
        this.unbreakable = false
        this.lockType = LockType.NONE
        if (itemData.tag != null) {
            this.setNbt(itemData.tag!!)
        }
    }

    companion object {
        var stackNetworkCount: Int = 0

        val AIR by lazy { Item.create(ItemType.AIR).toJukeboxItem() }

        fun create(itemData: ItemData): JukeboxItem {
            val item =
                Item.create(ItemRegistry.getItemType(Identifier.fromString(itemData.definition.identifier)))
                    .toJukeboxItem()
            itemData.blockDefinition?.let { item.blockNetworkId = it.runtimeId }
            item.meta = itemData.damage
            item.amount = itemData.count
            item.setNbt(itemData.tag)
            return item
        }
    }

    override fun getNetworkId(): Int {
        return this.networkId
    }

    override fun getBlockNetworkId(): Int {
        return this.blockNetworkId
    }

    open fun setBlockNetworkId(blockNetworkId: Int) {
        this.blockNetworkId = blockNetworkId
    }

    override fun getStackNetworkId(): Int {
        return this.stackNetworkId
    }

    fun setStackNetworkId(stackNetworkId: Int) {
        this.stackNetworkId = stackNetworkId
    }

    override fun getIdentifier(): Identifier {
        return this.identifier
    }

    override fun getType(): ItemType {
        return this.itemType
    }

    override fun getAmount(): Int {
        return if (this.itemType == ItemType.AIR) 0 else this.amount
    }

    override fun setAmount(amount: Int) {
        this.amount = amount
    }

    override fun getMeta(): Int {
        return this.meta
    }

    override fun setMeta(meta: Int) {
        this.meta = meta
    }

    override fun getDurability(): Int {
        return this.durability
    }

    override fun setDurability(durability: Int) {
        this.durability = durability
    }

    override fun getDisplayName(): String {
        return this.displayName
    }

    override fun setDisplayName(displayName: String) {
        this.displayName = displayName
    }

    override fun getLore(): MutableList<String> {
        return this.lore
    }

    override fun setLore(lore: MutableList<String>) {
        this.lore = lore
    }

    override fun getNbt(): NbtMap? {
        return this.nbtMap
    }

    override fun getMaxStackSize(): Int {
        return this.itemType.getMaxStackSize()
    }

    override fun isUnbreakable(): Boolean {
        return this.unbreakable
    }

    override fun setUnbreakable(unbreakable: Boolean) {
        this.unbreakable = unbreakable
    }

    override fun getLockType(): LockType {
        return this.lockType
    }

    override fun setLockType(lockType: LockType) {
        this.lockType = lockType
    }

    override fun hasEnchantment(enchantmentType: EnchantmentType): Boolean {
        return this.enchantments.containsKey(enchantmentType)
    }

    override fun addEnchantment(enchantmentType: EnchantmentType, level: Int) {
        val enchantment = JukeboxEnchantment.create(enchantmentType)
        (enchantment as JukeboxEnchantment).setLevel(level)
        this.enchantments[enchantmentType] = enchantment
    }

    override fun getEnchantment(enchantmentType: EnchantmentType): Enchantment? {
        return this.enchantments[enchantmentType]
    }

    override fun getEnchantments(): Collection<Enchantment> {
        return this.enchantments.values
    }

    override fun removeEnchantments() {
        this.enchantments.clear()
    }

    override fun getToolType(): ToolType {
        return ToolType.NONE
    }

    override fun getTierType(): TierType {
        return TierType.NONE
    }

    override fun hasBlock(): Boolean {
        return BlockRegistry.hasItemBlock(this.identifier)
    }

    override fun toBlock(): Block {
        return if (this.blockNetworkId == 0) {
            JukeboxBlock.AIR.clone()
        } else {
            val blockNbt = BlockPalette.getBlockNbt(this.blockNetworkId)
            return BlockPalette.getBlockByNBT(blockNbt).clone()
        }
    }

    override fun isSimilar(item: Item): Boolean {
        return this.isSimilarInternal(item, false)
    }

    override fun toBase64(): String {
        val compound = NbtMap.builder()
            .putString("Name", this.identifier.getFullName())
            .putInt("Meta", this.meta)
            .putInt("Amount", this.amount)
            .putBoolean("Unbreakable", this.unbreakable)
            .putCompound("BlockState", this.toBlock().getBlockStates())
            .putCompound("Tag", if (this.toNbt() != null) this.toNbt() else NbtMap.EMPTY)
            .build()
        val buffer = Unpooled.buffer()
        try {
            NbtUtils.createWriterLE(ByteBufOutputStream(buffer)).use {
                it.writeTag(compound)
            }
        } catch (e : Exception) {
            e.printStackTrace()
        }
        return Base64.getMimeEncoder().encodeToString(Utils.array(buffer))
    }

    fun isSimilarInternal(item: Item, ignoreBlockNetworkId: Boolean): Boolean {
        val identifier = item.getIdentifier() == this.getIdentifier()
        val blockNetworkId = item.getBlockNetworkId() == this.getBlockNetworkId()
        val durability = item.getDurability() == this.getDurability()
        val meta = item.getMeta() == this.getMeta()
        val displayName = item.getDisplayName() == this.getDisplayName()
        val lore = item.getLore() == this.getLore()
        val enchantments = if (item.getEnchantments().isEmpty() && this.getEnchantments().isEmpty()
        ) true else item.getEnchantments().contentEquals(this.getEnchantments())
        val unbreakable = item.isUnbreakable() == this.isUnbreakable()
        if (ignoreBlockNetworkId) {
            return identifier && durability && meta && displayName && lore && enchantments && unbreakable
        }
        return identifier && blockNetworkId && durability && meta && displayName && lore && enchantments && unbreakable
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is JukeboxItem) return false
        val similar = this.isSimilar(other)
        return this.getAmount() == other.getAmount() && similar
    }

    override fun clone(): JukeboxItem {
        val item: JukeboxItem = super.clone() as JukeboxItem
        item.itemType = this.itemType
        item.identifier = this.identifier
        item.networkId = this.networkId
        item.blockNetworkId = this.blockNetworkId
        item.stackNetworkId = this.stackNetworkId
        item.amount = this.amount
        item.meta = this.meta
        item.durability = this.durability
        item.displayName = this.displayName
        item.lore = this.lore
        item.nbtMap = this.nbtMap
        item.enchantments = this.enchantments
        item.unbreakable = this.unbreakable
        item.lockType = this.lockType
        return item
    }

    open fun addToHand(player: JukeboxPlayer) {}

    open fun removeFromHand(player: JukeboxPlayer) {}

    open fun interact(
        player: JukeboxPlayer,
        blockFace: BlockFace,
        clickedPosition: Vector,
        clickedBlock: Block?
    ): Boolean {
        return false
    }

    open fun useOnBlock(
        player: JukeboxPlayer,
        block: Block,
        placeLocation: Location
    ): Boolean {
        return false
    }

    open fun useInAir(player: JukeboxPlayer, clickVector: Vector): Boolean {
        return false
    }

    open fun onUse(player: JukeboxPlayer): Boolean {
        return false
    }

    fun setNbt(nbtMap: NbtMap?) {
        this.nbtMap = nbtMap
        nbtMap?.listenForByte("Count") { amount: Byte ->
            setAmount(
                amount.toInt()
            )
        }
        nbtMap?.listenForInt("Damage") { durability: Int -> setDurability(durability) }
        nbtMap?.listenForCompound("display") { displayTag: NbtMap ->
            displayTag.listenForString(
                "Name"
            ) {
                this.displayName = it
            }
            displayTag.listenForList("Lore", NbtType.STRING) { lore: List<String> ->
                this.lore.addAll(lore)
            }
        }
        nbtMap?.listenForList(
            "ench", NbtType.COMPOUND
        ) { compound: List<NbtMap> ->
            for (map in compound) {
                val id = map.getShort("id")
                val level = map.getShort("lvl")
                this.addEnchantment(EnchantmentRegistry.getEnchantmentTypeById(id.toInt()), level.toInt())
            }
        }
        nbtMap?.listenForByte("minecraft:item_lock") { locktype: Byte ->
            this.lockType = LockType.entries.toTypedArray()[locktype.toInt()]
        }
    }

    fun toNbt(identifier: Boolean = false): NbtMap? {
        val nbtBuilder = if (this.nbtMap == null) NbtMap.builder() else this.nbtMap!!.toBuilder()

        if (identifier) nbtBuilder.putString("Name", this.identifier.getFullName())
        if (this.durability > 0) {
            nbtBuilder.putInt("Damage", this.durability)
        }
        val displayBuilder = NbtMap.builder()
        if (this.displayName.isNotEmpty()) {
            displayBuilder["Name"] = this.displayName
        }
        if (this.lore.isNotEmpty()) {
            displayBuilder.putList("Lore", NbtType.STRING, this.lore)
        }
        if (!displayBuilder.isEmpty()) {
            nbtBuilder.putCompound("display", displayBuilder.build())
        }
        if (this.enchantments.isNotEmpty()) {
            val enchantmentNBT: MutableList<NbtMap> = ArrayList()
            for (enchantment in this.enchantments.values) {
                enchantmentNBT.add(
                    NbtMap.builder()
                        .putShort("id", enchantment.getId().toShort())
                        .putShort("lvl", enchantment.getLevel().toShort())
                        .build()
                )
            }
            nbtBuilder.putList("ench", NbtType.COMPOUND, enchantmentNBT)
        }
        if (this.lockType != LockType.NONE) {
            nbtBuilder.putByte("minecraft:item_lock", this.lockType.ordinal.toByte())
        }
        return if (nbtBuilder.isEmpty()) null else nbtBuilder.build()
    }

    fun toItemData(): ItemData {
        if (this.itemType == ItemType.AIR) {
            return ItemData.AIR
        }
        return ItemData.builder()
            .netId(this.stackNetworkId)
            .usingNetId(this.stackNetworkId > 0)
            .definition(SimpleItemDefinition(this.identifier.getFullName(), this.networkId, false))
            .blockDefinition(RuntimeBlockDefinition(this.blockNetworkId))
            .damage(this.meta)
            .count(this.amount)
            .tag(this.toNbt())
            .canPlace(*arrayOfNulls(0))
            .canBreak(*arrayOfNulls(0)).build()
    }

    fun updateDurability(player: JukeboxPlayer, amount: Int) {
        if (player.getGameMode() == GameMode.SURVIVAL) {
            if (calculateDurability(amount)) {
                player.getInventory().setItemInHand(Item.create(ItemType.AIR))
                player.playSound(Sound.RANDOM_BREAK, 1F, 1F)
            } else {
                player.getInventory().setItemInHand(this)
            }
        }
    }

    fun calculateDurability(durability: Int): Boolean {
        if (this is Durability) {
            if (this.unbreakable) return false
            val enchantment = this.getEnchantment(EnchantmentType.UNBREAKING)
            if (enchantment != null) {
                val chance = Random().nextInt(100)
                val percent = 100 / (enchantment.getLevel() + 1)
                if (!(enchantment.getLevel() > 0 && percent <= chance)) {
                    this.durability += durability
                }
            } else {
                this.durability += durability
            }
            return this.durabilityAndCheckAmount(this.durability)
        }
        return false
    }

    private fun durabilityAndCheckAmount(durability: Int): Boolean {
        if (this is Durability) {
            val maxDurability = this.getMaxDurability()
            var intdurability = durability
            if (intdurability >= maxDurability) {
                if (--this.amount <= 0) {
                    return true
                }
                intdurability = 0
            }
            this.durability = intdurability
        }
        return false
    }


    override fun hashCode(): Int {
        var result = itemType.hashCode()
        result = 31 * result + identifier.hashCode()
        result = 31 * result + networkId
        result = 31 * result + blockNetworkId
        result = 31 * result + stackNetworkId
        result = 31 * result + amount
        result = 31 * result + meta
        result = 31 * result + durability
        result = 31 * result + displayName.hashCode()
        result = 31 * result + lore.hashCode()
        result = 31 * result + (nbtMap?.hashCode() ?: 0)
        result = 31 * result + enchantments.hashCode()
        result = 31 * result + unbreakable.hashCode()
        result = 31 * result + lockType.hashCode()
        return result
    }

    override fun toString(): String {
        return "JukeboxItem(itemType=$itemType, amount=$amount, meta=$meta, durability=$durability, displayName='$displayName')"
    }
}

