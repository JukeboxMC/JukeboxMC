package org.jukeboxmc.world.palette.integer;

import org.jukeboxmc.nbt.NbtMap;

public interface IntPersistentDataSerializer {

    NbtMap serialize( int value );

}
