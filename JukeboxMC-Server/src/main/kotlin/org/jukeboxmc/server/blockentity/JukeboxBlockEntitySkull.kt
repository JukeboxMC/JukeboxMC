package org.jukeboxmc.server.blockentity

import org.cloudburstmc.nbt.NbtMap
import org.cloudburstmc.nbt.NbtMapBuilder
import org.jukeboxmc.api.blockentity.BlockEntitySkull
import org.jukeboxmc.api.blockentity.BlockEntityType
import org.jukeboxmc.server.block.JukeboxBlock

class JukeboxBlockEntitySkull(blockEntityType: BlockEntityType, block: JukeboxBlock) : JukeboxBlockEntity(blockEntityType, block), BlockEntitySkull {

    private var skullType: Byte = 0
    private var rotation: Byte = 0

    override fun fromCompound(compound: NbtMap) {
        super.fromCompound(compound)
        this.skullType = compound.getByte("SkullType")
        this.rotation = compound.getByte("Rot")
    }

    override fun toCompound(): NbtMapBuilder {
        val compound = super.toCompound()
        compound.putByte("SkullType", this.skullType)
        compound.putByte("Rot", this.rotation)
        return compound
    }

    override fun getSkullType(): Byte {
        return this.skullType
    }

    override fun setSkullType(skullType: Byte) {
        this.skullType = skullType
    }

    override fun getRotation(): Byte {
        return this.rotation
    }

    override fun setRotation(rotation: Byte) {
        this.rotation = rotation
    }

}