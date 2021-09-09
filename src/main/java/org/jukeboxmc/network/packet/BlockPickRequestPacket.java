package org.jukeboxmc.network.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.utils.BinaryStream;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Data
@EqualsAndHashCode ( callSuper = true )
public class BlockPickRequestPacket extends Packet {

    private Vector position;
    private boolean addUserData;
    private int hotbarSlot;

    @Override
    public int getPacketId() {
        return Protocol.BLOCK_PICK_REQUEST_PACKET;
    }

    @Override
    public void read( BinaryStream stream ) {
        super.read( stream );
        this.position = new Vector( stream.readSignedVarInt(), stream.readSignedVarInt(), stream.readSignedVarInt() );
        this.addUserData = stream.readBoolean();
        this.hotbarSlot = stream.readByte();
    }
}
