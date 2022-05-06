package org.jukeboxmc.world.palette.integer;

import com.nukkitx.nbt.NbtMap;

public interface IntPersistentDataSerializer {

    NbtMap serialize( int value );

}
