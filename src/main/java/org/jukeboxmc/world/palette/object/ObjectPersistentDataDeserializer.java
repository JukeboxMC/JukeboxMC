package org.jukeboxmc.world.palette.object;

import org.jukeboxmc.nbt.NbtMap;

public interface ObjectPersistentDataDeserializer<V> {

    V deserialize( NbtMap nbtMap );

}
