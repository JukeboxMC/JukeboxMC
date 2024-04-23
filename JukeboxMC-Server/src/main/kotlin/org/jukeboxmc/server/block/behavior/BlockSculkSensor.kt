package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.SculkSensor
import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.api.blockentity.BlockEntity
import org.jukeboxmc.api.blockentity.BlockEntityType
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer
import org.jukeboxmc.server.world.JukeboxWorld

class BlockSculkSensor(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates),
    SculkSensor {

    override fun placeBlock(
        player: JukeboxPlayer,
        world: JukeboxWorld,
        blockPosition: Vector,
        placePosition: Vector,
        clickedPosition: Vector,
        itemInHand: JukeboxItem,
        blockFace: BlockFace
    ): Boolean {
        BlockEntity.create(BlockEntityType.SCULKSENSOR, this)?.spawn()
        return super.placeBlock(player, world, blockPosition, placePosition, clickedPosition, itemInHand, blockFace)
    }

    override fun getSculkSensorPhase(): Int {
        return this.getIntState("sculk_sensor_phase")
    }

    override fun setSculkSensorPhase(value: Int): SculkSensor {
        return this.setState("sculk_sensor_phase", value)
    }
}