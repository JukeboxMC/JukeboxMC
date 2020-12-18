package org.jukeboxmc.nbt.util.stream;

import org.jukeboxmc.nbt.util.VarInts;

import javax.annotation.Nonnull;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class NetworkDataOutputStream extends LittleEndianDataOutputStream {

    public NetworkDataOutputStream( OutputStream stream) {
        super(stream);
    }

    public NetworkDataOutputStream( DataOutputStream stream) {
        super(stream);
    }

    @Override
    public void writeInt(int value) throws IOException {
        VarInts.writeInt(stream, value);
    }

    @Override
    public void writeLong(long value) throws IOException {
        VarInts.writeLong(stream, value);
    }

    @Override
    public void writeUTF(@Nonnull String string) throws IOException {
        byte[] bytes = string.getBytes( StandardCharsets.UTF_8);
        VarInts.writeUnsignedInt(stream, bytes.length);
        this.write(bytes);
    }
}
