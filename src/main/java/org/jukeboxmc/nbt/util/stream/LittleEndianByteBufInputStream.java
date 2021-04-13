package org.jukeboxmc.nbt.util.stream;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;

import java.io.IOException;

/**
 * @author Kaooot
 * @version 1.0
 */
public class LittleEndianByteBufInputStream extends ByteBufInputStream {

    private final ByteBuf buffer;

    public LittleEndianByteBufInputStream(final ByteBuf buffer) {
        super(buffer);

        this.buffer = buffer;
    }

    @Override
    public char readChar() throws IOException {
        return Character.reverseBytes(this.buffer.readChar());
    }

    @Override
    public double readDouble() throws IOException {
        return this.buffer.readDoubleLE();
    }

    @Override
    public float readFloat() throws IOException {
        return this.buffer.readFloatLE();
    }

    @Override
    public short readShort() throws IOException {
        return this.buffer.readShortLE();
    }

    @Override
    public int readUnsignedShort() throws IOException {
        return this.buffer.readUnsignedShortLE();
    }

    @Override
    public long readLong() throws IOException {
        return this.buffer.readLongLE();
    }

    @Override
    public int readInt() throws IOException {
        return this.buffer.readIntLE();
    }
}