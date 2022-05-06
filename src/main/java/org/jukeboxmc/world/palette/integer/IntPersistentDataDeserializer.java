package org.jukeboxmc.world.palette.integer;

import com.nukkitx.nbt.NbtMap;

public interface IntPersistentDataDeserializer {

    int deserialize( NbtMap nbtMap );

}
