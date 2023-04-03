package org.jukeboxmc.block.palette;


import org.cloudburstmc.nbt.NbtMap;

public interface PersistentDataDeserializer<V> {

    V deserialize( NbtMap nbtMap);

}
