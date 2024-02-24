package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.Leaves
import org.jukeboxmc.api.block.data.LeafType
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.extensions.toByte

class BlockLeaves(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates), Leaves {

   override fun isUpdate(): Boolean {
       return this.getBooleanState("update_bit")
   }

   override fun setUpdate(value: Boolean): Leaves {
       return this.setState("update_bit", value.toByte())
   }

   override fun isPersistent(): Boolean {
       return this.getBooleanState("persistent_bit")
   }

   override fun setPersistent(value: Boolean): Leaves {
       return this.setState("persistent_bit", value.toByte())
   }

   override fun getOldLeafType(): LeafType {
       return LeafType.valueOf(this.getStringState("old_leaf_type"))
   }

   override fun setOldLeafType(value: LeafType): Leaves {
       return this.setState("old_leaf_type", value.name.lowercase())
   }
}