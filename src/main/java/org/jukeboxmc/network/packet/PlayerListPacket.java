package org.jukeboxmc.network.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jukeboxmc.network.packet.type.TablistEntry;
import org.jukeboxmc.network.packet.type.TablistType;
import org.jukeboxmc.utils.BinaryStream;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Data
@EqualsAndHashCode (callSuper = true)
public class PlayerListPacket extends Packet {

    private TablistType type;
    private List<TablistEntry> entries = new ArrayList<>();

    @Override
    public int getPacketId() {
        return Protocol.PLAYER_LIST_PACKET;
    }

    @Override
    public void write( BinaryStream stream ) {
        super.write(stream);
        stream.writeByte( this.type.ordinal() );
        stream.writeUnsignedVarInt( this.entries.size() );

        if ( this.type == TablistType.ADD ) {
            for ( TablistEntry entry : this.entries ) {
                stream.writeUUID( entry.getUuid() );
                stream.writeSignedVarLong( entry.getEntityId() );
                stream.writeString( entry.getName() );

                stream.writeString( entry.getXboxId() );
                stream.writeString( entry.getDeviceInfo().getDeviceId() );
                stream.writeLInt( entry.getDeviceInfo().getDevice().getId() );

                stream.writeSkin( entry.getSkin() );

                stream.writeBoolean( false );
                stream.writeBoolean( false );
            }
        } else {
            for ( TablistEntry entry : this.entries ) {
                stream.writeUUID( entry.getUuid() );
            }
        }
    }
}
