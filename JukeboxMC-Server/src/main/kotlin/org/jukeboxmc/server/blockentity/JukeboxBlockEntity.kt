package org.jukeboxmc.server.blockentity

import org.cloudburstmc.nbt.NbtMap
import org.cloudburstmc.nbt.NbtMapBuilder
import org.cloudburstmc.protocol.bedrock.packet.BlockEntityDataPacket
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.Block
import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.api.blockentity.BlockEntity
import org.jukeboxmc.api.blockentity.BlockEntityType
import org.jukeboxmc.api.item.Item
import org.jukeboxmc.api.item.ItemType
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.extensions.toJukeboxItem
import org.jukeboxmc.server.extensions.toVector3i
import org.jukeboxmc.server.item.ItemRegistry
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer

open class JukeboxBlockEntity(
    private val blockEntityType: BlockEntityType,
    private var block: JukeboxBlock
) : BlockEntity {

    private var spawned = false

    open fun interact(
        player: JukeboxPlayer,
        blockPosition: Vector,
        clickedPosition: Vector,
        blockFace: BlockFace,
        itemInHand: JukeboxItem
    ): Boolean {
        return false
    }

    open fun update(currentTick: Long) {}

    open fun fromCompound(compound: NbtMap) {}

    open fun toCompound(): NbtMapBuilder {
        val compound = NbtMap.builder()
        compound.putString("id", this.block.getBlockEntityName())
        compound.putInt("x", this.block.getBlockX())
        compound.putInt("y", this.block.getBlockY())
        compound.putInt("z", this.block.getBlockZ())
        return compound
    }

    fun fromItem(item: Item, builder: NbtMapBuilder) {
        builder.putString("Name", item.getIdentifier().getFullName())
        builder.putShort("Damage", item.getMeta().toShort())
        builder.putByte("Count", item.getAmount().toByte())
        item.getNbt()?.let {
            builder.putCompound("tag", it)
        }
    }

    fun toItem(compound: NbtMap?): Item {
        if (compound == null) {
            return Item.create(ItemType.AIR)
        }
        val name = compound.getString("Name", null)
        val data = compound.getShort("Damage", 0.toShort())
        val amount = compound.getByte("Count", 0.toByte())
        val tag = compound.getCompound("tag", null)

        return if (name != null) {
            Item.create(ItemRegistry.getItemType(Identifier.fromString(name))).toJukeboxItem().apply {
                this.setAmount(amount.toInt())
                this.setMeta(data.toInt())
                this.setNbt(tag)
            }
        } else {
            Item.create(ItemType.AIR)
        }
    }

    fun update(player: JukeboxPlayer) {
        val blockEntityDataPacket = BlockEntityDataPacket()
        blockEntityDataPacket.blockPosition = block.getLocation().toVector3i()
        blockEntityDataPacket.data = toCompound().build()
        player.sendPacket(blockEntityDataPacket)
    }

    override fun spawn() {
        val location = this.block.getLocation()
        val jukeboxWorld = this.block.getWorld()

        val blockEntityDataPacket = BlockEntityDataPacket()
        blockEntityDataPacket.blockPosition = location.toVector3i()
        blockEntityDataPacket.data = this.toCompound().build()
        jukeboxWorld.setBlockEntity(location, this)
        jukeboxWorld.sendDimensionPacket(location.getDimension(), blockEntityDataPacket)
        this.spawned = true
    }

    override fun getBlockEntityType(): BlockEntityType {
        return this.blockEntityType
    }

    override fun getBlock(): Block {
        return this.block
    }

    fun setBlock(block: JukeboxBlock) {
        this.block = block
    }

    override fun isSpawned(): Boolean {
        return this.spawned
    }

}