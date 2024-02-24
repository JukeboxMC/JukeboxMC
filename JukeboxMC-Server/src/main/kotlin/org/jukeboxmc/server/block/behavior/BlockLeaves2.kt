package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.Leaves2
import org.jukeboxmc.api.block.data.LeafType2
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.extensions.toByte

class BlockLeaves2(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates), Leaves2 {

   override fun isUpdate(): Boolean {
       return this.getBooleanState("update_bit")
   }

   override fun setUpdate(value: Boolean): Leaves2 {
       return this.setState("update_bit", value.toByte())
   }

   override fun getNewLeafType(): LeafType2 {
       return LeafType2.valueOf(this.getStringState("new_leaf_type"))
   }

   override fun setNewLeafType(value: LeafType2): Leaves2 {
       return this.setState("new_leaf_type", value.name.lowercase())
   }

   override fun isPersistent(): Boolean {
       return this.getBooleanState("persistent_bit")
   }

   override fun setPersistent(value: Boolean): Leaves2 {
       return this.setState("persistent_bit", value.toByte())
   }
}