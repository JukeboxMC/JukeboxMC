package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.MonsterEgg
import org.jukeboxmc.api.block.data.MonsterEggStoneType
import org.jukeboxmc.server.block.JukeboxBlock

class BlockMonsterEgg(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates),
    MonsterEgg {

    override fun getMonsterEggStoneType(): MonsterEggStoneType {
        return MonsterEggStoneType.valueOf(this.getStringState("monster_egg_stone_type"))
    }

    override fun setMonsterEggStoneType(value: MonsterEggStoneType): MonsterEgg {
        return this.setState("monster_egg_stone_type", value.name.lowercase())
    }
}