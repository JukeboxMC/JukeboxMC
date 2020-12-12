package org.jukeboxmc.network.protocol.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Data
@ToString
@EqualsAndHashCode ( callSuper = true )
public class ResourcePacksInfoPacket extends Packet {

    private boolean forceAccept;
    private boolean scripting;

    @Override
    public int getPacketId() {
        return 0x06;
    }

    @Override
    public void write() {
        super.write();
        this.writeBoolean( this.forceAccept );
        this.writeBoolean( this.scripting );
        this.writeLShort( 0 ); // Resource Packs amount = 0, weil keine Resource Packs
        this.writeLShort( 0 ); // Behavior Packs amount = 0, weil keine Behavior Packs
    }
}
