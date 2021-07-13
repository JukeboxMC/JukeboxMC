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
    private boolean forceServerPacks;

    @Override
    public int getPacketId() {
        return Protocol.RESOURCE_PACKS_INFO_PACKET;
    }

    @Override
    public void write() {
        super.write();
        this.writeBoolean( this.forceAccept );
        this.writeBoolean( this.scripting );
        this.writeBoolean( this.forceServerPacks );
        this.writeLShort( 0 ); // Resource Packs amount = 0, because of no resource packs
        this.writeLShort( 0 ); // Behavior Packs amount = 0, because of no behaviour packs
    }
}
