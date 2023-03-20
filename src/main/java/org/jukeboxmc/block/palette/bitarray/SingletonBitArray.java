package org.jukeboxmc.block.palette.bitarray;

import io.netty.buffer.ByteBuf;
import org.jetbrains.annotations.NotNull;

public final class SingletonBitArray implements BitArray {

    private static final int[] EMPTY_ARRAY = new int[0];

    @Override
    public void set(int index, int value) {

    }

    @Override
    public int get(int index) {
        return 0;
    }

    @Override
    public void writeSizeToNetwork(@NotNull ByteBuf buffer, int size) {

    }

    @Override
    public int getSize() {
        return 1;
    }

    @Override
    public int[] getWords() {
        return EMPTY_ARRAY;
    }

    @Override
    public @NotNull BitArrayVersion getVersion() {
        return BitArrayVersion.V0;
    }

    @Override
    public @NotNull SingletonBitArray copy() {
        return new SingletonBitArray();
    }

}
