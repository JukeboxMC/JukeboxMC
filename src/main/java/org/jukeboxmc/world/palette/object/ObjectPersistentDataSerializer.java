package org.jukeboxmc.world.palette.object;

import org.jukeboxmc.nbt.NbtMap;

public interface ObjectPersistentDataSerializer<V> {

    NbtMap serialize( V value );

}
