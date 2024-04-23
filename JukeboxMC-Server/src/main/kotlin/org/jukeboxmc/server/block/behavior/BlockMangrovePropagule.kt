package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.MangrovePropagule
import org.jukeboxmc.api.item.Item
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.extensions.toByte

class BlockMangrovePropagule(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates),
    MangrovePropagule {

    override fun isHanging(): Boolean {
        return this.getBooleanState("hanging")
    }

    override fun setHanging(value: Boolean): MangrovePropagule {
        return this.setState("hanging", value.toByte())
    }

    override fun getPropaguleStage(): Int {
        return this.getIntState("propagule_stage")
    }

    override fun setPropaguleStage(value: Int): MangrovePropagule {
        return this.setState("propagule_stage", value)
    }

    override fun getDrops(item: Item): MutableList<Item> {
        if (!this.isHanging()) {
            return this.createItemDrop(item, this.toItem())
        }
        return mutableListOf()
    }
}