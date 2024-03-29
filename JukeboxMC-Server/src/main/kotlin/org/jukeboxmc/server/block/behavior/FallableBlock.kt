package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.Block
import org.jukeboxmc.api.block.BlockType
import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.server.UpdateReason
import org.jukeboxmc.server.block.JukeboxBlock

open class FallableBlock(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates) {

    private val blockAir = Block.create(BlockType.AIR)

    override fun onUpdate(updateReason: UpdateReason): Long {
        val blockDown = this.getRelative(BlockFace.DOWN)
        if (updateReason == UpdateReason.NORMAL) {
            if (blockDown.getType() == BlockType.AIR || blockDown.getType() == BlockType.FIRE) { //TODO Add liquid check
                this.getWorld().setBlock(this.getLocation(), this.blockAir)
                this.getWorld().dropItemNaturally(this.getLocation(), this.toItem())
            }
        }
        return -1
    }
}