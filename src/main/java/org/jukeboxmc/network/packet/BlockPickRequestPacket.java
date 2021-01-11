package org.jukeboxmc.network.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.network.Protocol;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BlockPickRequestPacket extends Packet {

    private Vector position;
    private boolean addUserData;
    private int hotbarSlot;

    @Override
    public int getPacketId() {
        return Protocol.BLOCK_PICK_REQUEST_PACKET;
    }

    @Override
    public void read() {
        super.read();
        this.position = new Vector( this.readSignedVarInt(), this.readSignedVarInt(), this.readSignedVarInt() );
        this.addUserData = this.readBoolean();
        this.hotbarSlot = this.readByte();
    }
}
