package org.jukeboxmc.server.blockentity

import org.cloudburstmc.nbt.NbtMap
import org.cloudburstmc.nbt.NbtMapBuilder
import org.cloudburstmc.nbt.NbtType
import org.cloudburstmc.protocol.bedrock.packet.ContainerSetDataPacket
import org.jukeboxmc.api.block.Block
import org.jukeboxmc.api.block.BlockType
import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.api.blockentity.BlockEntityFurnace
import org.jukeboxmc.api.blockentity.BlockEntityType
import org.jukeboxmc.api.inventory.FurnaceInventory
import org.jukeboxmc.api.inventory.InventoryHolder
import org.jukeboxmc.api.item.Burnable
import org.jukeboxmc.api.item.Item
import org.jukeboxmc.api.item.ItemType
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.server.JukeboxServer
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.block.behavior.BlockFurnace
import org.jukeboxmc.server.block.behavior.BlockLitFurnace
import org.jukeboxmc.server.extensions.toJukeboxPlayer
import org.jukeboxmc.server.inventory.JukeboxFurnaceInventory
import org.jukeboxmc.server.inventory.WindowId
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer
import kotlin.math.ceil

class JukeboxBlockEntityFurnace(blockEntityType: BlockEntityType, block: JukeboxBlock) :
    JukeboxBlockEntity(blockEntityType, block), BlockEntityFurnace, InventoryHolder {

    private val furnaceInventory = JukeboxFurnaceInventory(this)

    private val CONTAINER_PROPERTY_TICK_COUNT = 0
    private val CONTAINER_PROPERTY_LIT_TIME = 1
    private val CONTAINER_PROPERTY_LIT_DURATION = 2

    private var cookTime = 0
    private var burnTime = 0
    private var burnDuration = 0

    private var input = Item.create(ItemType.AIR)
    private var result = Item.create(ItemType.AIR)

    override fun update(currentTick: Long) {
        val input = this.furnaceInventory.getItem(0)
        val outputItem = this.furnaceInventory.getItem(2)

        if (input.getType() == ItemType.AIR) this.input = JukeboxItem.AIR

        if (this.input.getType() != input.getType()) {
            JukeboxServer.getInstance().getRecipeManager().getSmeltingRecipes().find { it.getInput().getType() == input.getType() }?.let {
                this.result = it.getOutput()
                this.setBurning(true)
            }
            this.input = input
        }

        if (this.result.getType() != ItemType.AIR && input.getType() != ItemType.AIR && outputItem.getAmount() < 64 && this.burnTime > 0) {
            this.cookTime++
            if (this.cookTime >= 200) {
                val itemStack = this.furnaceInventory.getItem(2)
                if (itemStack.getType() != this.result.getType()) {
                    this.furnaceInventory.setItem(2, this.result.apply { this.setAmount(1) })
                } else {
                    itemStack.setAmount(itemStack.getAmount() + 1)
                    this.furnaceInventory.setItem(2, itemStack)
                }
                this.furnaceInventory.setItem(0, input.apply { this.setAmount(this.getAmount() - 1) })
                this.cookTime = 0
                this.broadcastCookTime()
            } else if (this.cookTime % 20 == 0) {
                this.broadcastCookTime()
            }
        } else {
            if (this.cookTime > 0) {
                this.cookTime = 0
                this.broadcastCookTime()
            }
        }
        if (this.burnDuration > 0) {
            this.burnTime--
            var didRefuel = false
            if (this.burnTime == 0) {
                this.burnDuration = 0
                if (this.checkForRefuel()) {
                    didRefuel = true
                    this.broadcastFuelInfo()
                }
            }
            if (!didRefuel && (this.burnTime == 0 || this.burnTime % 20 == 0)) {
                this.broadcastFuelInfo()
            }
        } else {
            if (this.checkForRefuel()) {
                this.broadcastFuelInfo()
            } else {
                this.setBurning(false)
            }
        }
    }

    override fun interact(
        player: JukeboxPlayer,
        blockPosition: Vector,
        clickedPosition: Vector,
        blockFace: BlockFace,
        itemInHand: JukeboxItem
    ): Boolean {
        player.openInventory(this.furnaceInventory, blockPosition)
        this.sendDataProperties(player)
        return true
    }

    override fun fromCompound(compound: NbtMap) {
        super.fromCompound(compound)
        val items = compound.getList("Items", NbtType.COMPOUND)
        for (nbtMap in items) {
            val item = toItem(nbtMap)
            val slot = nbtMap.getByte("Slot", 127.toByte())
            if (slot.toInt() == 127) {
                this.furnaceInventory.addItem(false, item)
            } else {
                this.furnaceInventory.setItem(slot.toInt(), item, false)
            }
        }
    }

    override fun toCompound(): NbtMapBuilder {
        val compound = super.toCompound()
        val itemsCompoundList: MutableList<NbtMap> = ArrayList()
        for (slot in 0 until this.furnaceInventory.getSize()) {
            val itemCompound = NbtMap.builder()
            val item = this.furnaceInventory.getItem(slot)
            itemCompound.putByte("Slot", slot.toByte())
            fromItem(item, itemCompound)
            itemsCompoundList.add(itemCompound.build())
        }
        compound.putList("Items", NbtType.COMPOUND, itemsCompoundList)
        return compound
    }

    override fun getFurnaceInventory(): FurnaceInventory {
        return this.furnaceInventory
    }

    override fun getCookTime(): Int {
        return this.cookTime
    }

    override fun getBurnTime(): Int {
        return this.burnTime
    }

    override fun getBurnDuration(): Int {
        return this.burnDuration
    }

    override fun getResult(): Item {
        return this.result
    }

    private fun setBurning(burning: Boolean) {
        if (burning) {
            if (this.getBlock().getType() == BlockType.FURNACE) {
                val block = this.getBlock() as BlockFurnace
                val blockLitFurnace = Block.create<BlockLitFurnace>(BlockType.LIT_FURNACE).apply { this.setCardinalDirection(block.getCardinalDirection()) }
                this.getBlock().getWorld().setBlock(this.getBlock().getLocation(), blockLitFurnace)
                this.setBlock(blockLitFurnace)
            }
        } else if (this.getBlock().getType() == BlockType.LIT_FURNACE) {
            val block = this.getBlock() as BlockLitFurnace
            val blockFurnace =
                Block.create<BlockFurnace>(BlockType.FURNACE).apply { this.setCardinalDirection(block.getCardinalDirection()) }
            this.getBlock().getWorld().setBlock(this.getBlock().getLocation(), blockFurnace)
            this.setBlock(blockFurnace)
        }
    }

    private fun checkForRefuel(): Boolean {
        if (this.canProduceOutput()) {
            val fuelItem = this.furnaceInventory.getItem(1)
            if (fuelItem is Burnable) {
                val duration = fuelItem.getBurnTime()
                if (fuelItem.getAmount() > 0) {
                    this.furnaceInventory.setItem(1, fuelItem.apply {
                        this.setAmount(
                            this.getAmount()
                                    - 1
                        )
                    })
                    this.burnDuration = ceil(duration.toMillis() / 1.0).toInt()
                    this.burnTime = this.burnDuration
                    return true
                }
            }
        }
        return false
    }

    private fun canProduceOutput(): Boolean {
        if (this.result.getType() == ItemType.AIR) {
            return false
        }
        val input = this.furnaceInventory.getItem(0)
        if (input.getType() == ItemType.AIR || input.getAmount() == 0) {
            return false
        }
        val itemStack = this.furnaceInventory.getItem(2)
        return if (itemStack.getType() == this.result.getType()) {
            itemStack.getAmount() < itemStack.getMaxStackSize()
        } else {
            true
        }
    }

    private fun sendTickProgress(player: JukeboxPlayer) {
        val containerData = ContainerSetDataPacket()
        containerData.windowId = WindowId.OPEN_CONTAINER.getId().toByte()
        containerData.property = CONTAINER_PROPERTY_TICK_COUNT
        containerData.value = this.cookTime
        player.sendPacket(containerData)
    }

    private fun sendFuelInfo(player: JukeboxPlayer) {
        var containerData = ContainerSetDataPacket()
        containerData.windowId = WindowId.OPEN_CONTAINER.getId().toByte()
        containerData.property = CONTAINER_PROPERTY_LIT_TIME
        containerData.value = this.burnTime
        player.sendPacket(containerData)

        containerData = ContainerSetDataPacket()
        containerData.windowId = WindowId.OPEN_CONTAINER.getId().toByte()
        containerData.property = CONTAINER_PROPERTY_LIT_DURATION
        containerData.value = this.burnDuration
        player.sendPacket(containerData)
    }

    private fun sendDataProperties(player: JukeboxPlayer) {
        var containerData = ContainerSetDataPacket()
        containerData.windowId = WindowId.OPEN_CONTAINER.getId().toByte()
        containerData.property = CONTAINER_PROPERTY_TICK_COUNT
        containerData.value = this.cookTime
        player.sendPacket(containerData)
        containerData = ContainerSetDataPacket()
        containerData.windowId = WindowId.OPEN_CONTAINER.getId().toByte()
        containerData.property = CONTAINER_PROPERTY_LIT_TIME
        containerData.value = this.burnTime
        player.sendPacket(containerData)
        containerData = ContainerSetDataPacket()
        containerData.windowId = WindowId.OPEN_CONTAINER.getId().toByte()
        containerData.property = CONTAINER_PROPERTY_LIT_DURATION
        containerData.value = this.burnDuration
        player.sendPacket(containerData)
    }

    private fun broadcastCookTime() {
        for (viewer in this.furnaceInventory.getViewer()) {
            this.sendTickProgress(viewer.toJukeboxPlayer())
        }
    }

    private fun broadcastFuelInfo() {
        for (viewer in this.furnaceInventory.getViewer()) {
            this.sendFuelInfo(viewer.toJukeboxPlayer())
        }
    }

}