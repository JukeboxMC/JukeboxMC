package org.jukeboxmc.server.world.chunk.serializer

import org.cloudburstmc.blockstateupdater.BlockStateUpdaters
import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.block.palette.PersistentDataDeserializer
import org.jukeboxmc.server.extensions.toJukeboxBlock
import org.jukeboxmc.server.util.BlockPalette

class BlockStoragePersistentDeserializer : PersistentDataDeserializer<JukeboxBlock> {

    override fun deserialize(nbtMap: NbtMap): JukeboxBlock {
        val blockState = BlockStateUpdaters.updateBlockState(nbtMap, 0)
        return BlockPalette.getBlockByNBT(blockState).toJukeboxBlock()
    }

}