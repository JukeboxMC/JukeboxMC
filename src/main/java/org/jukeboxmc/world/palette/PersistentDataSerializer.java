package org.jukeboxmc.world.palette;

import org.jukeboxmc.nbt.NbtMap;

public interface PersistentDataSerializer<V> {

    NbtMap serialize( V value );

}
