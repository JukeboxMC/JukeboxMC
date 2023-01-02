package org.jukeboxmc.block.palette;

import com.nukkitx.nbt.NbtMap;

public interface PersistentDataDeserializer<V> {

    V deserialize(NbtMap nbtMap);

}
