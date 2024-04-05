package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.ColoredTorchBp
import org.jukeboxmc.api.block.data.TorchFacing
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.extensions.toByte

class BlockColoredTorchBp(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates),
    ColoredTorchBp {

    override fun getTorchFacingDirection(): TorchFacing {
        return TorchFacing.valueOf(this.getStringState("torch_facing_direction"))
    }

    override fun setTorchFacingDirection(value: TorchFacing): BlockColoredTorchBp {
        return this.setState("torch_facing_direction", value.name.lowercase())
    }

    override fun isColor(): Boolean {
        return this.getBooleanState("color_bit")
    }

    override fun setColor(value: Boolean): BlockColoredTorchBp {
        return this.setState("color_bit", value.toByte())
    }
}