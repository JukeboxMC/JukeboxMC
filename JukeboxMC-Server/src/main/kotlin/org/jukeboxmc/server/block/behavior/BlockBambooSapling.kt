package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.BambooSapling
import org.jukeboxmc.api.block.Block
import org.jukeboxmc.api.block.BlockType
import org.jukeboxmc.api.block.data.BambooLeafSize
import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.api.block.data.SaplingType
import org.jukeboxmc.api.event.block.BlockGrowEvent
import org.jukeboxmc.api.item.Item
import org.jukeboxmc.api.item.ItemType
import org.jukeboxmc.api.math.Location
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.api.world.Particle
import org.jukeboxmc.server.JukeboxServer
import org.jukeboxmc.server.UpdateReason
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.extensions.isOneOf
import org.jukeboxmc.server.extensions.toByte
import org.jukeboxmc.server.extensions.toJukeboxItem
import org.jukeboxmc.server.extensions.toJukeboxPlayer
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer
import org.jukeboxmc.server.world.JukeboxWorld
import java.util.concurrent.ThreadLocalRandom

class BlockBambooSapling(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates),
    BambooSapling {

    override fun placeBlock(
        player: JukeboxPlayer,
        world: JukeboxWorld,
        blockPosition: Vector,
        placePosition: Vector,
        clickedPosition: Vector,
        itemInHand: JukeboxItem,
        blockFace: BlockFace
    ): Boolean {
        val blockDown = this.getRelative(BlockFace.DOWN)
        if (!blockDown.getType().isOneOf(
                BlockType.GRASS,
                BlockType.DIRT,
                BlockType.SAND,
                BlockType.GRASS,
                BlockType.PODZOL
            )
        ) {
            return false
        }
        return super.placeBlock(player, world, blockPosition, placePosition, clickedPosition, itemInHand, blockFace)
    }

    override fun interact(
        player: JukeboxPlayer,
        world: JukeboxWorld,
        blockPosition: Vector,
        clickedPosition: Vector,
        blockFace: BlockFace,
        itemInHand: JukeboxItem
    ): Boolean {
        if (itemInHand.getType() == ItemType.BONE_MEAL) {
            var success = false
            val block = this.getRelative(BlockFace.UP)
            if (block.getType() == BlockType.AIR) {
                success = this.grow(block)
            }
            if (success) {
                //TODO itemInHand.updateItem(player, 1)
                this.getWorld().spawnParticle(Particle.PARTICLE_CROP_GROWTH, this.getLocation())
            }
            return true
        }
        return false
    }

    override fun onUpdate(updateReason: UpdateReason): Long {
        if (updateReason == UpdateReason.NORMAL) {
            val blockDown = this.getRelative(BlockFace.DOWN)
            if (!blockDown.getType().isOneOf(
                    BlockType.GRASS,
                    BlockType.DIRT,
                    BlockType.SAND,
                    BlockType.GRAVEL,
                    BlockType.PODZOL
                )
            ) {
                breakNaturally()
            } else {
                val blockUp = this.getRelative(BlockFace.UP)
                if (blockUp.getType() == BlockType.BAMBOO) {
                    val blockBambooUp = blockUp as BlockBamboo
                    val blockBamboo = Block.create(BlockType.BAMBOO) as BlockBamboo
                    blockBamboo.setBambooStalkThickness(blockBambooUp.getBambooStalkThickness())
                    this.getWorld().setBlock(this.getLocation(), blockBamboo)
                }
            }
            return 0
        } else if (updateReason == UpdateReason.RANDOM) {
            val blockUp = this.getRelative(BlockFace.UP)
            if (!hasAge() && blockUp.getType() == BlockType.AIR && ThreadLocalRandom.current().nextInt(3) == 0) {
                val blockBamboo = Block.create(BlockType.BAMBOO) as BlockBamboo
                blockBamboo.setBambooLeafSize(BambooLeafSize.SMALL_LEAVES)
                val blockGrowEvent = BlockGrowEvent(blockUp, blockBamboo)
                JukeboxServer.getInstance().getPluginManager().callEvent(blockGrowEvent)
                if (!blockGrowEvent.isCancelled()) {
                    val block = blockGrowEvent.getNewState() as BlockBamboo
                    block.setLocation(
                        Location(
                            this.getWorld(),
                            this.getBlockX(),
                            blockUp.getBlockY(),
                            this.getBlockZ()
                        )
                    )
                    block.placeBlock(
                        JukeboxServer.getInstance().getPlayer("null")!!.toJukeboxPlayer(),
                        this.getWorld(),
                        Vector(0, 0, 0),
                        block.getLocation(),
                        Vector(0.5f, 0.5f, 0.5f),
                        Item.create(ItemType.AIR).toJukeboxItem(),
                        BlockFace.DOWN
                    )
                }
            }
        }
        return 0
    }

    override fun canPassThrough(): Boolean {
        return true
    }

    fun grow(block: Block): Boolean {
        val blockBamboo: BlockBamboo = Block.create<BlockBamboo>(BlockType.BAMBOO)
        blockBamboo.setLocation(this.getLocation())
        return blockBamboo.grow(block)
    }

    override fun hasAge(): Boolean {
        return this.getBooleanState("age_bit")
    }

    override fun setAge(value: Boolean): BlockBambooSapling {
        return this.setState("age_bit", value.toByte())
    }

    override fun getSaplingType(): SaplingType {
        return SaplingType.valueOf(this.getStringState("sapling_type"))
    }

    override fun setSaplingType(value: SaplingType): BlockBambooSapling {
        return this.setState("sapling_type", value.name.lowercase())
    }
}