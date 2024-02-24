package org.jukeboxmc.api.block

interface SculkSensor : Block {

   fun getSculkSensorPhase(): Int

   fun setSculkSensorPhase(value: Int): SculkSensor

}