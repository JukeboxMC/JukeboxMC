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
public class ResourcePackStackPacket extends Packet {

    private boolean mustAccept;

    @Override
    public int getPacketId() {
        return Protocol.RESOURCE_PACK_STACK_PACKET;
    }

    @Override
    public void write() {
        super.write();
        this.writeBoolean( this.mustAccept );

        //BehaviorPack
        this.writeUnsignedVarInt( 0 );

        //ResourcePack
        this.writeUnsignedVarInt( 0 );

        this.writeString( Protocol.MINECRAFT_VERSION );
        this.writeInt( 0 );
        this.writeBoolean( false );
    }
}
