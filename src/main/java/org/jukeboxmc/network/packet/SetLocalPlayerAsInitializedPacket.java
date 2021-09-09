package org.jukeboxmc.network.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jukeboxmc.utils.BinaryStream;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Data
@EqualsAndHashCode ( callSuper = true )
public class SetLocalPlayerAsInitializedPacket extends Packet {

    private long entityRuntimeId;

    @Override
    public int getPacketId() {
        return Protocol.SET_LOCAL_PLAYER_AS_INITIALIZED_PACKET;
    }

    @Override
    public void read( BinaryStream stream ) {
        super.read(stream);
        this.entityRuntimeId = stream.readUnsignedVarLong();
    }
}
