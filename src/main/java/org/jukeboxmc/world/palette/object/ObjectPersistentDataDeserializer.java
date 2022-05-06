package org.jukeboxmc.world.palette.object;

import com.nukkitx.nbt.NbtMap;

public interface ObjectPersistentDataDeserializer<V> {

    V deserialize( NbtMap nbtMap );

}
