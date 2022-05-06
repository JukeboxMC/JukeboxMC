package org.jukeboxmc.world.palette.object;


import com.nukkitx.nbt.NbtMap;

public interface ObjectPersistentDataSerializer<V> {

    NbtMap serialize( V value );

}
