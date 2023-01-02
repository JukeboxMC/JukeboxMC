package org.jukeboxmc.block.palette;

import com.nukkitx.nbt.NbtMap;

public interface PersistentDataSerializer<V> {

    NbtMap serialize(V value);

}
