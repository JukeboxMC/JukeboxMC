package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.SignDirection

interface StandingBanner : Block {

   fun getGroundSignDirection(): SignDirection

   fun setGroundSignDirection(value: SignDirection): StandingBanner

}