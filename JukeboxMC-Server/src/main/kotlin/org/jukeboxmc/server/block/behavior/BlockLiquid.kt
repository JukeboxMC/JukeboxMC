package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.cloudburstmc.protocol.bedrock.data.SoundEvent
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.Block
import org.jukeboxmc.api.block.BlockType
import org.jukeboxmc.api.block.Liquid
import org.jukeboxmc.api.event.block.BlockFromToEvent
import org.jukeboxmc.api.event.block.LiquidFlowEvent
import org.jukeboxmc.api.item.Item
import org.jukeboxmc.api.item.ItemType
import org.jukeboxmc.server.JukeboxServer
import org.jukeboxmc.server.UpdateReason
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.entity.JukeboxEntityLiving
import org.jukeboxmc.server.extensions.toJukeboxBlock
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.util.Utils
import kotlin.math.min

abstract class BlockLiquid(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates),
    Liquid {

    protected val CAN_FLOW_DOWN: Byte = 1
    protected val CAN_FLOW: Byte = 0
    protected val BLOCKED: Byte = -1
    protected var adjacentSources = 0

    private val flowCostVisited: MutableMap<Long, Byte> = mutableMapOf()

    companion object {
        val blockWater = Block.create<BlockWater>(BlockType.WATER)
        val blockLava = Block.create<BlockLava>(BlockType.LAVA)
    }

    override fun getLiquidDepth(): Int {
        return this.getIntState("liquid_depth")
    }

    override fun setLiquidDepth(value: Int): Liquid {
        return this.setState("liquid_depth", value)
    }

    override fun canBeFlowedInto(): Boolean {
        return true
    }

    override fun canPassThrough(): Boolean {
        return true
    }

    override fun canBeReplaced(block: JukeboxBlock): Boolean {
        return true
    }

    override fun toItem(): Item {
        return Item.create(ItemType.AIR)
    }

    open fun tickRate(): Long {
        return 0
    }

    open fun getFlowDecayPerBlock(): Int {
        return 1
    }

    open fun checkForHarden() {

    }

    override fun onUpdate(updateReason: UpdateReason): Long {
        val world = this.getWorld()
        if (updateReason == UpdateReason.NORMAL) {
            this.checkForHarden()
            if (this.useWaterLogging() && this.getLayer() > 0) {
                val location = this.getLocation()
                val block = world.getBlock(location)
                if (block.getType() == BlockType.AIR) {
                    world.setBlock(location.getBlockX(), location.getBlockY(), location.getBlockZ(), 1, location.getDimension(), AIR, false)
                    world.setBlock(location.getBlockX(), location.getBlockY(), location.getBlockZ(), 0, location.getDimension(), AIR, false)
                } else if(block.getWaterLoggingLevel() <= 0 || block.getWaterLoggingLevel() == 1 && this.getLiquidDepth() > 0) {
                    world.setBlock(location, AIR)
                }
            }
            world.scheduleBlockUpdate(this, this.tickRate())
            return 0
        } else if (updateReason == UpdateReason.SCHEDULED) {
            val location = this.getLocation()
            var decay = this.getFlowDecay(this)
            val multiplier = this.getFlowDecayPerBlock()
            if (decay > 0) {
                var smallestFlowDecay = -100
                this.adjacentSources = 0
                smallestFlowDecay = this.getSmallestFlowDecay(
                    location.getWorld().getBlock(
                        location.getBlockX(),
                        location.getBlockY(), location.getBlockZ() - 1, 0, location.getDimension()
                    ), smallestFlowDecay
                )
                smallestFlowDecay = this.getSmallestFlowDecay(
                    location.getWorld().getBlock(
                        location.getBlockX(),
                        location.getBlockY(), location.getBlockZ() + 1, 0, location.getDimension()
                    ), smallestFlowDecay
                )
                smallestFlowDecay = this.getSmallestFlowDecay(
                    location.getWorld().getBlock(
                        location.getBlockX() - 1,
                        location.getBlockY(), location.getBlockZ(), 0, location.getDimension()
                    ), smallestFlowDecay
                )
                smallestFlowDecay = this.getSmallestFlowDecay(
                    location.getWorld().getBlock(
                        location.getBlockX() + 1,
                        location.getBlockY(), location.getBlockZ(), 0, location.getDimension()
                    ), smallestFlowDecay
                )
                var newDecay = smallestFlowDecay + multiplier
                if (newDecay >= 8 || smallestFlowDecay < 0) {
                    newDecay = -1
                }
                val topFlowDecay = this.getFlowDecay(
                    location.getWorld().getBlock(
                        location.getBlockX(), location.getBlockY() + 1,
                        location.getBlockZ(), 0, location.getDimension()
                    )
                )
                if (topFlowDecay >= 0) {
                    newDecay = topFlowDecay or 0x08
                }
                if (this.adjacentSources >= 2 && this is BlockWater) {
                    var bottomBlock = location.getWorld().getBlock(
                        location.getBlockX(), location.getBlockY() - 1, location.getBlockZ(), 0, location.getDimension()
                    )
                    if (bottomBlock.isSolid()) {
                        newDecay = 0
                    } else if (bottomBlock is BlockWater && bottomBlock.getLiquidDepth() == 0) {
                        newDecay = 0
                    } else {
                        bottomBlock = bottomBlock.getLocation().getWorld().getBlock(location, 1)
                        if (bottomBlock is BlockWater && bottomBlock.getLiquidDepth() == 0) {
                            newDecay = 0
                        }
                    }
                }
                if (newDecay != decay) {
                    decay = newDecay
                    val decayed = decay < 0
                    val to: Block = if (decayed) {
                        Block.create(BlockType.AIR)
                    } else {
                        this.getLiquidWithNewDepth(decay)
                    }
                    val event = BlockFromToEvent(this, to)
                    JukeboxServer.getInstance().getPluginManager().callEvent(event)
                    if (!event.isCancelled()) {
                        location.getWorld().setBlock(location, this.getLayer(), event.getBlockTo())
                        if (!decayed) {
                            world.scheduleBlockUpdate(this, this.tickRate())
                        }
                    }
                }
            }
            if (decay >= 0) {
                val bottomBlock = location.getWorld().getBlock(
                    location.getBlockX(), location.getBlockY() - 1, location.getBlockZ(), 0, location.getDimension()
                )
                this.flowIntoBlock(bottomBlock.toJukeboxBlock(), decay or 0x08)
                if (decay == 0 || !(if (this.useWaterLogging()) bottomBlock.canWaterloggingFlowInto() else bottomBlock.canBeFlowedInto())) {
                    val adjacentDecay: Int = if (decay >= 8) {
                        1
                    } else {
                        decay + multiplier
                    }
                    if (adjacentDecay < 8) {
                        val flags = this.getOptimalFlowDirections()!!
                        if (flags[0]) {
                            val block = location.getWorld().getBlock(
                                location.getBlockX() - 1,
                                location.getBlockY(), location.getBlockZ(), 0, location.getDimension()
                            ).toJukeboxBlock()
                            this.flowIntoBlock(
                                block, adjacentDecay
                            )
                        }
                        if (flags[1]) {
                            val block = location.getWorld().getBlock(
                                location.getBlockX() + 1,
                                location.getBlockY(), location.getBlockZ(), 0, location.getDimension()
                            ).toJukeboxBlock()
                            this.flowIntoBlock(
                                block, adjacentDecay
                            )
                        }
                        if (flags[2]) {
                            val block = location.getWorld().getBlock(
                                location.getBlockX(),
                                location.getBlockY(),
                                location.getBlockZ() - 1,
                                0,
                                location.getDimension()
                            ).toJukeboxBlock()
                            this.flowIntoBlock(
                                block, adjacentDecay
                            )
                        }
                        if (flags[3]) {
                            val block = location.getWorld().getBlock(
                                location.getBlockX(),
                                location.getBlockY(),
                                location.getBlockZ() + 1,
                                0,
                                location.getDimension()
                            ).toJukeboxBlock()
                            this.flowIntoBlock(
                                block, adjacentDecay
                            )
                        }
                    }
                }
                this.checkForHarden()
            }
        }
        return 0
    }

    open fun flowIntoBlock(block: JukeboxBlock, newFlowDecay: Int) {
        var blockValue = block
        if (this.canFlowInto(block) && block !is BlockLiquid) {
            val world = this.getWorld()
            if (this.useWaterLogging()) {
                val blockLayer = world.getBlock(block.getLocation(), 1).toJukeboxBlock()
                if (blockLayer is BlockLiquid) {
                    return
                }
                if (block.getWaterLoggingLevel() > 1) {
                    blockValue = blockLayer
                }
            }
            val event = LiquidFlowEvent(blockValue, this, newFlowDecay)
            JukeboxServer.getInstance().getPluginManager().callEvent(event)
            if (!event.isCancelled()) {
                if (block.getLayer() == 0 && blockValue.getType() != BlockType.AIR) {
                    world.setBlock(blockValue.getLocation(), AIR)
                    val drops = blockValue.getDrops(JukeboxItem.AIR)
                    if (drops.isNotEmpty()) {
                        world.dropItemNaturally(blockValue.getLocation(), drops[0])
                    }
                }
                world.setBlock(blockValue.getLocation(), blockValue.getLayer(), this.getLiquidWithNewDepth(newFlowDecay))
                world.scheduleBlockUpdate(blockValue, this.tickRate())
            }
        }
    }

    fun calculateFlowCost(
        blockX: Int,
        blockY: Int,
        blockZ: Int,
        accumulatedCost: Int,
        maxCost: Int,
        originOpposite: Int,
        lastOpposite: Int
    ): Int {
        val world = this.getWorld()
        val dimension = this.getLocation().getDimension()
        var cost = 1000
        for (j in 0..3) {
            if (j == originOpposite || j == lastOpposite) {
                continue
            }
            var x = blockX
            var z = blockZ
            when (j) {
                0 -> {
                    --x
                }

                1 -> {
                    ++x
                }

                2 -> {
                    --z
                }

                else -> {
                    ++z
                }
            }
            val hash: Long = Utils.blockHash(x, blockY, z, dimension)
            if (!this.flowCostVisited.containsKey(hash)) {
                val blockSide = world.getBlock(x, blockY, z).toJukeboxBlock()
                if (!canFlowInto(blockSide)) {
                    this.flowCostVisited[hash] = BLOCKED
                } else if (if (this.useWaterLogging()) world.getBlock(x, blockY - 1, z)
                        .canWaterloggingFlowInto() else world.getBlock(x, blockY - 1, z).canBeFlowedInto()
                ) {
                    this.flowCostVisited[hash] = CAN_FLOW_DOWN
                } else {
                    this.flowCostVisited[hash] = CAN_FLOW
                }
            }
            val status = this.flowCostVisited[hash]!!
            if (status == BLOCKED) {
                continue
            } else if (status == CAN_FLOW_DOWN) {
                return accumulatedCost
            }
            if (accumulatedCost >= maxCost) {
                continue
            }
            val realCost = calculateFlowCost(x, blockY, z, accumulatedCost + 1, maxCost, originOpposite, j xor 0x01)
            if (realCost < cost) {
                cost = realCost
            }
        }
        return cost
    }

    fun getOptimalFlowDirections(): BooleanArray? {
        val world = this.getWorld()
        val dimension = this.getLocation().getDimension()
        val flowCost = intArrayOf(
            1000,
            1000,
            1000,
            1000
        )
        var maxCost: Int = 4 / this.getFlowDecayPerBlock()
        for (j in 0 until 4) {
            var x = this.getBlockX()
            val y = this.getBlockY()
            var z = this.getBlockZ()
            when (j) {
                0 -> {
                    --x
                }

                1 -> {
                    ++x
                }

                2 -> {
                    --z
                }

                else -> {
                    ++z
                }
            }
            val block = world.getBlock(x, y, z).toJukeboxBlock()
            if (!canFlowInto(block)) {
                this.flowCostVisited[Utils.blockHash(x, y, z, dimension)] = BLOCKED
            } else if (if (this.useWaterLogging()) world.getBlock(x, y - 1, z)
                    .canWaterloggingFlowInto() else world.getBlock(x, y - 1, z).canBeFlowedInto()
            ) {
                this.flowCostVisited[Utils.blockHash(x, y, z, dimension)] = CAN_FLOW_DOWN
                maxCost = 0
                flowCost[j] = maxCost
            } else if (maxCost > 0) {
                this.flowCostVisited[Utils.blockHash(x, y, z, dimension)] = CAN_FLOW
                flowCost[j] = this.calculateFlowCost(x, y, z, 1, maxCost, j xor 0x01, j xor 0x01)
                maxCost = min(maxCost, flowCost[j])
            }
        }
        this.flowCostVisited.clear()
        var minCost = Double.MAX_VALUE
        for (i in 0..3) {
            val d = flowCost[i].toDouble()
            if (d < minCost) {
                minCost = d
            }
        }
        val isOptimalFlowDirection = BooleanArray(4)
        for (i in 0..3) {
            isOptimalFlowDirection[i] = flowCost[i].toDouble() == minCost
        }
        return isOptimalFlowDirection
    }

    fun useWaterLogging(): Boolean {
        return this is BlockWater
    }

    fun getFluidHeightPercent(): Float {
        var d = getLiquidDepth().toFloat()
        if (d >= 8) {
            d = 0f
        }
        return (d + 1) / 9f
    }

    fun getFlowDecay(block: Block): Int {
        return if (block is BlockLiquid) {
            block.getLiquidDepth()
        } else {
            val blockLayer: Block = block.getWorld().getBlock(block.getLocation(), 1)
            (blockLayer as? BlockLiquid)?.getLiquidDepth() ?: -1
        }
    }

    fun getSmallestFlowDecay(block: Block, decay: Int): Int {
        var blockDecay: Int = this.getFlowDecay(block)
        if (blockDecay < 0) {
            return decay
        } else if (blockDecay == 0) {
            ++adjacentSources
        } else if (blockDecay >= 8) {
            blockDecay = 0
        }
        return if (decay in 0..blockDecay) decay else blockDecay
    }

    abstract fun getLiquidWithNewDepth(depth: Int): Liquid

    fun onEntityCollide(entity: JukeboxEntityLiving) { //TODO
        entity.resetFallDistance()
    }

    fun liquidCollide(block: JukeboxBlock) {
        val blockFromToEvent = BlockFromToEvent(this, block)
        JukeboxServer.getInstance().getPluginManager().callEvent(blockFromToEvent)
        if (blockFromToEvent.isCancelled()) return
        val world = this.getWorld()
        val location = this.getLocation()
        world.setBlock(location, 0, blockFromToEvent.getBlockTo())
        world.setBlock(location, 1, Block.create(BlockType.AIR))
        world.playLevelSound(location.add(0.5f, 0.5f, 0.5f), SoundEvent.FIZZ)
    }

    fun canFlowInto(block: JukeboxBlock): Boolean {
        if (this.useWaterLogging()) {
            if (block.canWaterloggingFlowInto()) {
                val blockLayer = block.getWorld().getBlock(block.getLocation(), 1)
                return !(block is BlockLiquid && block.getLiquidDepth() == 0) && !(blockLayer is BlockLiquid && blockLayer.getLiquidDepth() == 0)
            }
        }
        return (!block.isWaterBlocking() || block.canBeFlowedInto()) && !(block is BlockLiquid && block.getLiquidDepth() == 0)
    }
}