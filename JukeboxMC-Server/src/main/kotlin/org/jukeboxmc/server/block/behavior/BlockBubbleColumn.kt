package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.BubbleColumn
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.extensions.toByte

class BlockBubbleColumn(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates),
    BubbleColumn {

    override fun canPassThrough(): Boolean {
        return true
    }

   override fun isDragDown(): Boolean {
       return this.getBooleanState("drag_down")
   }

   override fun setDragDown(value: Boolean): BlockBubbleColumn {
       return this.setState("drag_down", value.toByte())
   }
}