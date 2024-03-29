package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.BlockType
import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.server.UpdateReason
import org.jukeboxmc.server.block.JukeboxBlock

class BlockCarpet(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates) {

    override fun onUpdate(updateReason: UpdateReason): Long {
        val blockDown = this.getRelative(BlockFace.DOWN)
        if (updateReason == UpdateReason.NORMAL) {
            if (blockDown.getType() == BlockType.AIR || blockDown.getType() == BlockType.FIRE) { //TODO Add liquid check
                this.getWorld().setBlock(this.getLocation(), AIR)
                this.getWorld().dropItemNaturally(this.getLocation(), this.toItem())
            }
        }
        return -1
    }

}