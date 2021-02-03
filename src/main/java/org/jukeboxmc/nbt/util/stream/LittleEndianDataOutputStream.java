package org.jukeboxmc.nbt.util.stream;

import javax.annotation.Nonnull;
import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @author Cloudburst
 * @version 1.0
 */
public class LittleEndianDataOutputStream implements DataOutput, Closeable {
    protected final DataOutputStream stream;

    public LittleEndianDataOutputStream( OutputStream stream) {
        this.stream = new DataOutputStream(stream);
    }

    public LittleEndianDataOutputStream( DataOutputStream stream) {
        this.stream = stream;
    }

    @Override
    public void close() throws IOException {
        this.stream.close();
    }

    @Override
    public void write(int bytes) throws IOException {
        this.stream.write(bytes);
    }

    @Override
    public void write(@Nonnull byte[] bytes) throws IOException {
        this.stream.write(bytes);
    }

    @Override
    public void write(@Nonnull byte[] bytes, int offset, int length) throws IOException {
        this.stream.write(bytes, offset, length);
    }

    @Override
    public void writeBoolean(boolean value) throws IOException {
        this.stream.writeBoolean(value);
    }

    @Override
    public void writeByte(int value) throws IOException {
        this.stream.writeByte(value);
    }

    @Override
    public void writeShort(int value) throws IOException {
        this.stream.writeShort( Short.reverseBytes((short) value));
    }

    @Override
    public void writeChar(int value) throws IOException {
        this.stream.writeChar( Character.reverseBytes((char) value));
    }

    @Override
    public void writeInt(int value) throws IOException {
        this.stream.writeInt( Integer.reverseBytes(value));
    }

    @Override
    public void writeLong(long value) throws IOException {
        this.stream.writeLong( Long.reverseBytes(value));
    }

    @Override
    public void writeFloat(float value) throws IOException {
        this.stream.writeInt( Integer.reverseBytes( Float.floatToIntBits(value)));
    }

    @Override
    public void writeDouble(double value) throws IOException {
        this.stream.writeLong( Long.reverseBytes( Double.doubleToLongBits(value)));
    }

    @Override
    public void writeBytes(@Nonnull String string) throws IOException {
        this.stream.writeBytes(string);
    }

    @Override
    public void writeChars(@Nonnull String string) throws IOException {
        this.stream.writeChars(string);
    }

    @Override
    public void writeUTF(@Nonnull String string) throws IOException {
        byte[] bytes = string.getBytes( StandardCharsets.UTF_8);
        this.writeShort(bytes.length);
        this.write(bytes);
    }
}
