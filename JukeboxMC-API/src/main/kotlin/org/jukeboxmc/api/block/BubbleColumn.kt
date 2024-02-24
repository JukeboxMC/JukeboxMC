package org.jukeboxmc.api.block

interface BubbleColumn : Block {

   fun isDragDown(): Boolean

   fun setDragDown(value: Boolean): BubbleColumn

}