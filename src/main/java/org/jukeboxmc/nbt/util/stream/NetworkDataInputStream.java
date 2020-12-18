package org.jukeboxmc.nbt.util.stream;

import org.jukeboxmc.nbt.util.VarInts;

import javax.annotation.Nonnull;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class NetworkDataInputStream extends LittleEndianDataInputStream {

    public NetworkDataInputStream( InputStream stream) {
        super(stream);
    }

    public NetworkDataInputStream( DataInputStream stream) {
        super(stream);
    }

    @Override
    public int readInt() throws IOException {
        return VarInts.readInt(stream);
    }

    @Override
    public long readLong() throws IOException {
        return VarInts.readLong(stream);
    }

    @Override
    @Nonnull
    public String readUTF() throws IOException {
        int length = VarInts.readUnsignedInt(stream);
        byte[] bytes = new byte[length];
        this.readFully(bytes);
        return new String(bytes, StandardCharsets.UTF_8);
    }
}
