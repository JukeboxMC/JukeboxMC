package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.Block
import org.jukeboxmc.api.block.BlockType
import org.jukeboxmc.api.block.DoublePlant
import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.api.block.data.PlantType
import org.jukeboxmc.api.math.Location
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.extensions.toByte
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer
import org.jukeboxmc.server.world.JukeboxWorld

class BlockDoublePlant(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates),
    DoublePlant {

    override fun placeBlock(
        player: JukeboxPlayer,
        world: JukeboxWorld,
        blockPosition: Vector,
        placePosition: Vector,
        clickedPosition: Vector,
        itemInHand: JukeboxItem,
        blockFace: BlockFace
    ): Boolean {
        val blockAbove = world.getBlock(placePosition.add(0F, 1F, 0F))
        val blockDown = world.getBlock(placePosition.subtract(0F, 1F, 0F))

        if (blockAbove.getType() == BlockType.AIR && (blockDown.getType() == BlockType.GRASS || blockDown.getType() == BlockType.DIRT)) {
            if (!this.isUpperBlock()) {
                val blockDoublePlant = Block.create(BlockType.DOUBLE_PLANT) as BlockDoublePlant
                blockDoublePlant.setLocation(Location(world, placePosition.add(0F, 1F, 0F)))
                blockDoublePlant.setDoublePlantType(this.getDoublePlantType())
                blockDoublePlant.setUpperBlock(true)
                world.setBlock(placePosition.add(0F, 1F, 0F), blockDoublePlant)
                world.setBlock(placePosition, this)
            } else {
                val blockDoublePlant = Block.create(BlockType.DOUBLE_PLANT) as BlockDoublePlant
                blockDoublePlant.setLocation(Location(world, placePosition))
                blockDoublePlant.setDoublePlantType(this.getDoublePlantType())
                blockDoublePlant.setUpperBlock(false)
                world.setBlock(placePosition, blockDoublePlant)
                world.setBlock(placePosition.add(0F, 1F, 0F), this)
            }
            return true
        }
        return false
    }

    override fun onBlockBreak(breakLocation: Vector) {
        if (isUpperBlock()) {
            this.getWorld().setBlock(this.getLocation().subtract(0F, 1F, 0F), AIR)
        } else {
            this.getWorld().setBlock(this.getLocation().add(0F, 1F, 0F), AIR)
        }
        this.getWorld().setBlock(this.getLocation(), AIR)
    }

    override fun canPassThrough(): Boolean {
        return true
    }

   override fun isUpperBlock(): Boolean {
       return this.getBooleanState("upper_block_bit")
   }

   override fun setUpperBlock(value: Boolean): BlockDoublePlant {
       return this.setState("upper_block_bit", value.toByte())
   }

   override fun getDoublePlantType(): PlantType {
       return PlantType.valueOf(this.getStringState("double_plant_type"))
   }

   override fun setDoublePlantType(value: PlantType): BlockDoublePlant {
       return this.setState("double_plant_type", value.name.lowercase())
   }
}