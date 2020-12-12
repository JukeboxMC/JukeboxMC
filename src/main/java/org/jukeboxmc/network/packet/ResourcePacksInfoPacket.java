package org.jukeboxmc.network.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.jukeboxmc.network.Protocol;

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
        return Protocol.RESOURCE_PACKS_INFO_PACKET;
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
