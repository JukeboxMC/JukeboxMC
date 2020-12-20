package org.jukeboxmc.network.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jukeboxmc.network.Protocol;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
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
    public void read() {
        super.read();
        this.flags = this.readUnsignedVarInt();
        this.commandPermission = this.readUnsignedVarInt();
        this.flags2 = this.readUnsignedVarInt();
        this.playerPermission = this.readUnsignedVarInt();
        this.customFlags = this.readUnsignedVarInt();
        this.entityId = this.readLLong();
    }

    @Override
    public void write() {
        super.write();
        this.writeUnsignedVarInt( this.flags );
        this.writeUnsignedVarInt( this.commandPermission );
        this.writeUnsignedVarInt( this.flags2 );
        this.writeUnsignedVarInt( this.playerPermission );
        this.writeUnsignedVarInt( this.customFlags );
        this.writeLLong( this.entityId );
    }
}
