package org.jukeboxmc.world.palette.bitarray;

import com.nukkitx.network.VarInts;
import io.netty.buffer.ByteBuf;

public interface BitArray {

    void set( int index, int value );

    int get( int index );

    default void writeSizeToNetwork( ByteBuf buffer, int size) {
        VarInts.writeInt( buffer, size );
    }

    default int readSizeFromNetwork( ByteBuf buffer ) {
        return VarInts.readInt( buffer );
    }

    int getSize();

    int[] getWords();

    BitArrayVersion getVersion();

    BitArray copy();

}