package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.Direction

interface Cocoa : Block {

   fun getDirection(): Direction

   fun setDirection(value: Direction): Cocoa

   fun getAge(): Int

   fun setAge(value: Int): Cocoa

}