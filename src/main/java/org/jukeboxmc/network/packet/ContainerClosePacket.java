package org.jukeboxmc.network.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jukeboxmc.network.Protocol;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Data
@EqualsAndHashCode ( callSuper = true )
public class ContainerClosePacket extends Packet {

    private byte windowId;
    private boolean isServerInitiated;

    @Override
    public int getPacketId() {
        return Protocol.CONTAINER_CLOSE_PACKET;
    }

    @Override
    public void read() {
        super.read();
        this.windowId = this.readByte();
        this.isServerInitiated = this.readBoolean();
    }

    @Override
    public void write() {
        super.write();
        this.writeByte( this.windowId );
        this.writeBoolean( this.isServerInitiated );
    }
}
