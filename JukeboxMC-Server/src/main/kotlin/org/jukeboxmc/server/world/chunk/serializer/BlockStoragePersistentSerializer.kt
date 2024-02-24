package org.jukeboxmc.server.world.chunk.serializer

import org.cloudburstmc.nbt.NbtMap
import org.jukeboxmc.server.block.JukeboxBlock
import org.jukeboxmc.server.block.palette.PersistentDataSerializer
import org.jukeboxmc.server.util.BlockPalette

class BlockStoragePersistentSerializer: PersistentDataSerializer<JukeboxBlock> {

    override fun serialize(value: JukeboxBlock): NbtMap {
        return BlockPalette.getBlockNbt(value.getNetworkId())
    }
}