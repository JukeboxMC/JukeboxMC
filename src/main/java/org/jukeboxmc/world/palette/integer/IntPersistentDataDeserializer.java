package org.jukeboxmc.world.palette.integer;

import org.jukeboxmc.nbt.NbtMap;

public interface IntPersistentDataDeserializer {

    int deserialize( NbtMap nbtMap );

}
