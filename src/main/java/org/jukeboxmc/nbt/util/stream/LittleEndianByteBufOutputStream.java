package org.jukeboxmc.nbt.util.stream;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufOutputStream;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author Kaooot
 * @version 1.0
 */
public class LittleEndianByteBufOutputStream extends ByteBufOutputStream {

    private final ByteBuf buffer;

    public LittleEndianByteBufOutputStream(final ByteBuf buffer) {
        super(buffer);

        this.buffer = buffer;
    }

    @Override
    public void writeChar(final int value) throws IOException {
        this.buffer.writeChar(Character.reverseBytes((char) value));
    }

    @Override
    public void writeDouble(final double value) throws IOException {
        this.buffer.writeDoubleLE(value);
    }

    @Override
    public void writeFloat(final float value) throws IOException {
        this.buffer.writeFloatLE(value);
    }

    @Override
    public void writeShort(final int value) throws IOException {
        this.buffer.writeShortLE(value);
    }

    @Override
    public void writeLong(final long value) throws IOException {
        this.buffer.writeLongLE(value);
    }

    @Override
    public void writeInt(final int value) throws IOException {
        this.buffer.writeIntLE(value);
    }

    @Override
    public void writeUTF(final String s) throws IOException {
        final byte[] bytes = s.getBytes(StandardCharsets.UTF_8);

        this.writeShort(bytes.length);
        this.write(bytes);
    }
}