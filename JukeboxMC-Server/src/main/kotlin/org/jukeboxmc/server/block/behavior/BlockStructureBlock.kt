package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.StructureBlock
import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.api.block.data.StructureBlockType
import org.jukeboxmc.api.blockentity.BlockEntity
import org.jukeboxmc.api.blockentity.BlockEntityType
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer
import org.jukeboxmc.server.world.JukeboxWorld

class BlockStructureBlock(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates),
    StructureBlock {

    override fun placeBlock(
        player: JukeboxPlayer,
        world: JukeboxWorld,
        blockPosition: Vector,
        placePosition: Vector,
        clickedPosition: Vector,
        itemInHand: JukeboxItem,
        blockFace: BlockFace
    ): Boolean {
        BlockEntity.create(BlockEntityType.STRUCTUREBLOCK, this)?.spawn()
        return super.placeBlock(player, world, blockPosition, placePosition, clickedPosition, itemInHand, blockFace)
    }

    override fun getStructureBlockType(): StructureBlockType {
        return StructureBlockType.valueOf(this.getStringState("structure_block_type"))
    }

    override fun setStructureBlockType(value: StructureBlockType): StructureBlock {
        return this.setState("structure_block_type", value.name.lowercase())
    }
}