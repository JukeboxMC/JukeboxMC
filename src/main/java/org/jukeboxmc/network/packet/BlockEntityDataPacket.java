package org.jukeboxmc.network.packet;

import io.netty.buffer.ByteBufOutputStream;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jukeboxmc.math.BlockPosition;
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

    private BlockPosition blockPosition;
    private NbtMap nbt;

    @Override
    public int getPacketId() {
        return Protocol.BLOCK_ENTITY_DATA_PACKET;
    }

    @Override
    public void write() {
        super.write();
        this.writeSignedVarInt( this.blockPosition.getX() );
        this.writeUnsignedVarInt( this.blockPosition.getY() );
        this.writeSignedVarInt( this.blockPosition.getZ() );

        try {
            NBTOutputStream networkWriter = NbtUtils.createNetworkWriter( new ByteBufOutputStream( this.getBuffer() ) );
            networkWriter.writeTag( this.nbt );
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }
}
