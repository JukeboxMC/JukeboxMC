package org.jukeboxmc.block.palette.bitarray;

import com.nukkitx.network.VarInts;
import io.netty.buffer.ByteBuf;
import org.jetbrains.annotations.NotNull;

public interface BitArray {

    void set(int index, int value);

    int get(int index);

    default void writeSizeToNetwork(@NotNull ByteBuf buffer, int size) {
        VarInts.writeInt(buffer, size);
    }

    default int readSizeFromNetwork(@NotNull ByteBuf buffer) {
        return VarInts.readInt(buffer);
    }

    int getSize();

    int[] getWords();

    BitArrayVersion getVersion();

    BitArray copy();

}