package org.jukeboxmc.world.palette.bitarray;

import org.jukeboxmc.utils.BinaryStream;

public interface BitArray {

    void set( int index, int value );

    int get( int index );

    default void writeSizeToNetwork( BinaryStream buffer, int size ) {
        buffer.writeSignedVarInt( size );
    }

    int getSize();

    int[] getWords();

    BitArrayVersion getVersion();

    BitArray copy();

}