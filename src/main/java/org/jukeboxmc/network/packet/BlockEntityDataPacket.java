package org.jukeboxmc.network.packet;

import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.nbt.NBTInputStream;
import org.jukeboxmc.nbt.NBTOutputStream;
import org.jukeboxmc.nbt.NbtMap;
import org.jukeboxmc.nbt.NbtUtils;
import org.jukeboxmc.utils.BinaryStream;

import java.io.IOException;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Data
@EqualsAndHashCode (callSuper = true)
public class BlockEntityDataPacket extends Packet {

    private Vector blockPosition;
    private NbtMap nbt;

    @Override
    public int getPacketId() {
        return Protocol.BLOCK_ENTITY_DATA_PACKET;
    }

    @Override
    public void read( BinaryStream stream ) {
        super.read(stream);
        this.blockPosition = new Vector( stream.readSignedVarInt(), stream.readUnsignedVarInt(), stream.readSignedVarInt() );
        try {
            NBTInputStream networkReader = NbtUtils.createNetworkReader( new ByteBufInputStream( stream.getBuffer() ) );
            this.nbt = (NbtMap) networkReader.readTag();
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }

    @Override
    public void write( BinaryStream stream ) {
        super.write(stream);
        stream.writeSignedVarInt( this.blockPosition.getBlockX() );
        stream.writeUnsignedVarInt( this.blockPosition.getBlockY() );
        stream.writeSignedVarInt( this.blockPosition.getBlockZ() );

        try (NBTOutputStream networkWriter = NbtUtils.createNetworkWriter( new ByteBufOutputStream( stream.getBuffer() ) )){
            networkWriter.writeTag( this.nbt );
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }
}
