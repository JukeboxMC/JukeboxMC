package org.jukeboxmc.api.block

interface AzaleaLeavesFlowered : Block {

   fun isUpdate(): Boolean

   fun setUpdate(value: Boolean): AzaleaLeavesFlowered

   fun isPersistent(): Boolean

   fun setPersistent(value: Boolean): AzaleaLeavesFlowered

}