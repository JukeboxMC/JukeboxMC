package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.SignDirection

interface StandingSign : Block {

   fun getGroundSignDirection(): SignDirection

   fun setGroundSignDirection(value: SignDirection): StandingSign

}