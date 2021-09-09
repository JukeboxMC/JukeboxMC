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
public class AdventureSettingsPacket extends Packet {

    private int flags;
    private int commandPermission;
    private int flags2;
    private int playerPermission;
    private int customFlags;
    private long entityId;

    @Override
    public int getPacketId() {
        return Protocol.ADVENTURER_SETTINGS_PACKET;
    }

    @Override
    public void read( BinaryStream stream ) {
        super.read(stream);
        this.flags = stream.readUnsignedVarInt();
        this.commandPermission = stream.readUnsignedVarInt();
        this.flags2 = stream.readUnsignedVarInt();
        this.playerPermission = stream.readUnsignedVarInt();
        this.customFlags = stream.readUnsignedVarInt();
        this.entityId = stream.readLLong();
    }

    @Override
    public void write( BinaryStream stream ) {
        super.write(stream);
        stream.writeUnsignedVarInt( this.flags );
        stream.writeUnsignedVarInt( this.commandPermission );
        stream.writeUnsignedVarInt( this.flags2 );
        stream.writeUnsignedVarInt( this.playerPermission );
        stream.writeUnsignedVarInt( this.customFlags );
        stream.writeLLong( this.entityId );
    }
}
