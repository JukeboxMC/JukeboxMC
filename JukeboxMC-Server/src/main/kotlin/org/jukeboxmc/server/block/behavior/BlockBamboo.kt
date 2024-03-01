package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.Bamboo
import org.jukeboxmc.api.block.Block
import org.jukeboxmc.api.block.BlockType
import org.jukeboxmc.api.block.data.BambooLeafSize
import org.jukeboxmc.api.block.data.BambooStalkThickness
import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.api.event.block.BlockGrowEvent
import org.jukeboxmc.api.item.Item
import org.jukeboxmc.api.item.ItemType
import org.jukeboxmc.api.math.Location
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.server.JukeboxServer
import org.jukeboxmc.server.UpdateReason
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.extensions.*
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer
import org.jukeboxmc.server.world.JukeboxWorld
import java.util.*

class BlockBamboo(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates), Bamboo {

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
        val blockDownType = blockDown.getType()

        if (!blockDownType.isOneOf(BlockType.BAMBOO, BlockType.BAMBOO_SAPLING)) {
            if (!blockDownType.isOneOf(
                    BlockType.GRASS_BLOCK,
                    BlockType.DIRT,
                    BlockType.SAND,
                    BlockType.GRAVEL,
                    BlockType.PODZOL
                )
            ) {
                return false
            }

            if (world.getBlock(placePosition) is BlockWater) {
                return false
            }

            world.setBlock(placePosition, Block.create(BlockType.BAMBOO_SAPLING))
            return true
        }

        var canGrow = true

        if (blockDownType == BlockType.BAMBOO_SAPLING) {
            this.setBambooLeafSize(BambooLeafSize.SMALL_LEAVES)
        }

        if (blockDown is BlockBamboo) {
            canGrow = !blockDown.hasAge()
            val thick = blockDown.getBambooStalkThickness() == BambooStalkThickness.THICK

            if (!thick) {
                var setThick = true

                for (i in 2..3) {
                    if (this.getRelative(BlockFace.DOWN, 0, i).getType() == BlockType.BAMBOO) {
                        setThick = false
                    }
                }

                if (setThick) {
                    this.setBambooStalkThickness(BambooStalkThickness.THICK, false)
                    this.setBambooLeafSize(BambooLeafSize.LARGE_LEAVES)
                    blockDown.setBambooLeafSize(BambooLeafSize.SMALL_LEAVES)
                    blockDown.setBambooStalkThickness(BambooStalkThickness.THICK, false)
                    blockDown.setAge(true)
                    world.setBlock(blockDown.getLocation(), blockDown)

                    var currentBlock = blockDown

                    while (currentBlock is BlockBamboo) {
                        currentBlock.setBambooStalkThickness(BambooStalkThickness.THICK, false)
                        currentBlock.setBambooLeafSize(BambooLeafSize.NO_LEAVES)
                        currentBlock.setAge(true)
                        world.setBlock(currentBlock.getLocation(), currentBlock)
                        currentBlock = currentBlock.getRelative(BlockFace.DOWN) as? BlockBamboo
                            ?: break
                    }
                } else {
                    this.setBambooLeafSize(BambooLeafSize.SMALL_LEAVES)
                    blockDown.setAge(true)
                    world.setBlock(blockDown.getLocation(), blockDown)
                }
            } else {
                this.setBambooStalkThickness(BambooStalkThickness.THICK, false)
                this.setBambooLeafSize(BambooLeafSize.LARGE_LEAVES)
                this.setAge(false)
                blockDown.setBambooLeafSize(BambooLeafSize.LARGE_LEAVES)
                blockDown.setAge(true)
                world.setBlock(blockDown.getLocation(), blockDown)

                if (blockDown.getRelative(BlockFace.DOWN) is BlockBamboo) {
                    var currentBlock = blockDown.getRelative(BlockFace.DOWN) as BlockBamboo
                    currentBlock.setBambooLeafSize(BambooLeafSize.SMALL_LEAVES)
                    currentBlock.setAge(true)
                    world.setBlock(currentBlock.getLocation(), currentBlock)

                    if (currentBlock.getRelative(BlockFace.DOWN) is BlockBamboo) {
                        currentBlock = currentBlock.getRelative(BlockFace.DOWN) as BlockBamboo
                        currentBlock.setBambooLeafSize(BambooLeafSize.NO_LEAVES)
                        currentBlock.setAge(true)
                        world.setBlock(currentBlock.getLocation(), currentBlock)
                    }
                }
            }
        } else if (!blockDownType.isOneOf(
                BlockType.BAMBOO,
                BlockType.GRASS_BLOCK,
                BlockType.DIRT,
                BlockType.SAND,
                BlockType.GRAVEL,
                BlockType.PODZOL,
                BlockType.BAMBOO_SAPLING
            )
        ) {
            return false
        }

        val height = if (canGrow) this.countHeight() else 0

        if (!canGrow || height >= 15 || (height >= 11 && Math.random().toFloat() < 0.25f)) {
            this.setAge(true)
        }
        return super.placeBlock(player, world, blockPosition, placePosition, clickedPosition, itemInHand, blockFace)
    }

    override fun onUpdate(updateReason: UpdateReason): Long {
        if (updateReason == UpdateReason.NORMAL) {
            val blockDown = this.getRelative(BlockFace.DOWN)
            if (!blockDown.getType().isOneOf(
                    BlockType.BAMBOO,
                    BlockType.BAMBOO_SAPLING,
                    BlockType.GRASS_BLOCK,
                    BlockType.DIRT,
                    BlockType.SAND,
                    BlockType.GRAVEL
                )
            ) {
                this.getWorld().scheduleBlockUpdate(this, 0)
            }
            return 0
        } else if (updateReason == UpdateReason.SCHEDULED) {
            this.breakNaturally()
        }
        return 0
    }

    override fun canPassThrough(): Boolean {
        return true
    }

    fun grow(block: Block): Boolean {
        val blockBamboo: BlockBamboo = Block.create<BlockBamboo>(BlockType.BAMBOO)
        if (getBambooStalkThickness() == BambooStalkThickness.THICK) {
            blockBamboo.setBambooStalkThickness(BambooStalkThickness.THICK, false)
            blockBamboo.setBambooLeafSize(BambooLeafSize.LARGE_LEAVES, false)
        } else {
            blockBamboo.setBambooLeafSize(BambooLeafSize.SMALL_LEAVES, false)
        }
        val blockGrowEvent = BlockGrowEvent(block, blockBamboo)
        JukeboxServer.getInstance().getPluginManager().callEvent(blockGrowEvent)
        if (!blockGrowEvent.isCancelled()) {
            val newState = blockGrowEvent.getNewState() as BlockBamboo
            newState.setLocation(
                Location(
                    this.getWorld(),
                    this.getBlockX(),
                    block.getLocation().getBlockY(),
                    this.getBlockZ()
                )
            )
            newState.placeBlock(
                JukeboxServer.getInstance().getPlayer("null")!!.toJukeboxPlayer(),
                this.getWorld(),
                Vector(0, 0, 0),
                newState.getLocation(),
                Vector(0.5f, 0.5f, 0.5f),
                Item.create(ItemType.AIR).toJukeboxItem(),
                BlockFace.DOWN
            )
            return true
        }
        return false
    }

    fun countHeight(): Int {
        var count = 0
        var opt: Optional<JukeboxBlock>
        var down: Block = this
        while (down.getRelative(BlockFace.DOWN).toJukeboxBlock().firstInLayer { b -> b.getType() === BlockType.BAMBOO }
                .also { opt = it }
                .isPresent) {
            down = opt.get()
            if (++count >= 16) {
                break
            }
        }
        return count
    }

    override fun hasAge(): Boolean {
        return this.getBooleanState("age_bit")
    }

    override fun setAge(value: Boolean): BlockBamboo {
        return this.setState("age_bit", value.toByte())
    }

    override fun getBambooStalkThickness(): BambooStalkThickness {
        return BambooStalkThickness.valueOf(this.getStringState("bamboo_stalk_thickness"))
    }

    override fun setBambooStalkThickness(value: BambooStalkThickness): BlockBamboo {
        return this.setState("bamboo_stalk_thickness", value.name.lowercase())
    }

    fun setBambooStalkThickness(value: BambooStalkThickness, update: Boolean): BlockBamboo {
        return this.setState("bamboo_stalk_thickness", value.name.lowercase(), update)
    }

    override fun getBambooLeafSize(): BambooLeafSize {
        return BambooLeafSize.valueOf(this.getStringState("bamboo_leaf_size"))
    }

    override fun setBambooLeafSize(value: BambooLeafSize): BlockBamboo {
        return this.setState("bamboo_leaf_size", value.name.lowercase())
    }

    fun setBambooLeafSize(value: BambooLeafSize, update: Boolean): BlockBamboo {
        return this.setState("bamboo_leaf_size", value.name.lowercase(), update)
    }
}