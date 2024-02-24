package org.jukeboxmc.api.block

import org.jukeboxmc.api.block.data.MonsterEggStoneType

interface MonsterEgg : Block {

   fun getMonsterEggStoneType(): MonsterEggStoneType

   fun setMonsterEggStoneType(value: MonsterEggStoneType): MonsterEgg

}