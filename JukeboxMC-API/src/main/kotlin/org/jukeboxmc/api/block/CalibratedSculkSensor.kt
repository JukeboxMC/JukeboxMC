package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.Direction

interface CalibratedSculkSensor : Block {

   fun getCardinalDirection(): Direction

   fun setCardinalDirection(value: Direction): CalibratedSculkSensor

   fun getSculkSensorPhase(): Int

   fun setSculkSensorPhase(value: Int): CalibratedSculkSensor

}