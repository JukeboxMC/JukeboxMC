package org.jukeboxmc.block.palette;

import org.cloudburstmc.nbt.NbtMap;

public interface PersistentDataSerializer<V> {

    NbtMap serialize( V value);

}
