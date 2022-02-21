package org.jukeboxmc.world.palette.object;

public interface ObjectRuntimeDataDeserializer<V> {

    V deserialize( int id );

}
