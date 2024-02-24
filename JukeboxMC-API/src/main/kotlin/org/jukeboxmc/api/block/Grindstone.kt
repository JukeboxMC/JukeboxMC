package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.Attachment
import org.jukeboxmc.api.block.data.Direction

interface Grindstone : Block {

   fun getDirection(): Direction

   fun setDirection(value: Direction): Grindstone

   fun getAttachment(): Attachment

   fun setAttachment(value: Attachment): Grindstone

}