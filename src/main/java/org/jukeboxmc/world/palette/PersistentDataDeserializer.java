package org.jukeboxmc.world.palette;

import org.jukeboxmc.nbt.NbtMap;

public interface PersistentDataDeserializer<V> {

    V deserialize( NbtMap nbtMap );

}
