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
import org.jukeboxmc.network.Protocol;

import java.io.IOException;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BlockEntityDataPacket extends Packet {

    private Vector blockPosition;
    private NbtMap nbt;

    @Override
    public int getPacketId() {
        return Protocol.BLOCK_ENTITY_DATA_PACKET;
    }

    @Override
    public void read() {
        super.read();
        this.blockPosition = new Vector( this.readSignedVarInt(), this.readUnsignedVarInt(), this.readSignedVarInt() );
        try {
            NBTInputStream networkReader = NbtUtils.createNetworkReader( new ByteBufInputStream( this.getBuffer() ) );
            this.nbt = (NbtMap) networkReader.readTag();
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }

    @Override
    public void write() {
        super.write();
        this.writeSignedVarInt( this.blockPosition.getBlockX() );
        this.writeUnsignedVarInt( this.blockPosition.getBlockY() );
        this.writeSignedVarInt( this.blockPosition.getBlockZ() );

        try {
            NBTOutputStream networkWriter = NbtUtils.createNetworkWriter( new ByteBufOutputStream( this.getBuffer() ) );
            networkWriter.writeTag( this.nbt );
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }
}
