package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.CalibratedSculkSensor
import org.jukeboxmc.api.block.data.BlockFace
import org.jukeboxmc.api.block.data.Direction
import org.jukeboxmc.api.math.Vector
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.item.JukeboxItem
import org.jukeboxmc.server.player.JukeboxPlayer
import org.jukeboxmc.server.world.JukeboxWorld

class BlockCalibratedSculkSensor(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates),
    CalibratedSculkSensor {

    override fun placeBlock(
        player: JukeboxPlayer,
        world: JukeboxWorld,
        blockPosition: Vector,
        placePosition: Vector,
        clickedPosition: Vector,
        itemInHand: JukeboxItem,
        blockFace: BlockFace
    ): Boolean {
        this.setCardinalDirection(player.getDirection().opposite())
        return super.placeBlock(player, world, blockPosition, placePosition, clickedPosition, itemInHand, blockFace)
    }

    override fun getCardinalDirection(): Direction {
        return Direction.valueOf(this.getStringState("minecraft:cardinal_direction"))
    }

    override fun setCardinalDirection(value: Direction): BlockCalibratedSculkSensor {
        return this.setState("minecraft:cardinal_direction", value.name.lowercase())
    }

    override fun getSculkSensorPhase(): Int {
        return this.getIntState("sculk_sensor_phase")
    }

    override fun setSculkSensorPhase(value: Int): BlockCalibratedSculkSensor {
        return this.setState("sculk_sensor_phase", value)
    }
}