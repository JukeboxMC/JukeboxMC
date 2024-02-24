package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.Attachment
import org.jukeboxmc.api.block.data.Direction

interface Bell : Block {

   fun getDirection(): Direction

   fun setDirection(value: Direction): Bell

   fun isToggle(): Boolean

   fun setToggle(value: Boolean): Bell

   fun getAttachment(): Attachment

   fun setAttachment(value: Attachment): Bell

}