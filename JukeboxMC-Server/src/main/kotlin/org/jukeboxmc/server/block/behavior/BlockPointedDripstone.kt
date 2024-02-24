package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.PointedDripstone
import org.jukeboxmc.api.block.data.DripstoneThickness
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.extensions.toByte

class BlockPointedDripstone(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates), PointedDripstone {

   override fun isHanging(): Boolean {
       return this.getBooleanState("hanging")
   }

   override fun setHanging(value: Boolean): PointedDripstone {
       return this.setState("hanging", value.toByte())
   }

   override fun getDripstoneThickness(): DripstoneThickness {
       return DripstoneThickness.valueOf(this.getStringState("dripstone_thickness"))
   }

   override fun setDripstoneThickness(value: DripstoneThickness): PointedDripstone {
       return this.setState("dripstone_thickness", value.name.lowercase())
   }
}