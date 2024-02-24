package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer
import org.jukeboxmc.server.world.JukeboxWorld

class BlockCraftingTable(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates) {

    override fun interact(player: JukeboxPlayer, world: JukeboxWorld, blockPosition: Vector, clickedPosition: Vector, blockFace: BlockFace, itemInHand: JukeboxItem): Boolean {
        player.openInventory(player.getCraftingTableInventory(), blockPosition)
        return true
    }
}