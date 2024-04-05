package org.jukeboxmc.server.block.behavior

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.api.Identifier
import org.jukeboxmc.api.block.TurtleEgg
import org.jukeboxmc.api.block.data.CrackedState
import org.jukeboxmc.api.block.data.TurtleEggCount
import org.jukeboxmc.server.block.JukeboxBlock

class BlockTurtleEgg(identifier: Identifier, blockStates: NbtMap?) : JukeboxBlock(identifier, blockStates), TurtleEgg {

    override fun getCrackedState(): CrackedState {
        return CrackedState.valueOf(this.getStringState("cracked_state"))
    }

    override fun setCrackedState(value: CrackedState): TurtleEgg {
        return this.setState("cracked_state", value.name.lowercase())
    }

    override fun getTurtleEggCount(): TurtleEggCount {
        return TurtleEggCount.valueOf(this.getStringState("turtle_egg_count"))
    }

    override fun setTurtleEggCount(value: TurtleEggCount): TurtleEgg {
        return this.setState("turtle_egg_count", value.name.lowercase())
    }
}