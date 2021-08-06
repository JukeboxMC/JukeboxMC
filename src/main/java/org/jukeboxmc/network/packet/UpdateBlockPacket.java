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
@EqualsAndHashCode ( callSuper = true )
public class UpdateBlockPacket extends Packet {

    public static final int FLAG_NONE = 0b0000;
    public static final int FLAG_NEIGHBORS = 0b0001;
    public static final int FLAG_NETWORK = 0b0010;
    public static final int FLAG_NOGRAPHIC = 0b0100;
    public static final int FLAG_PRIORITY = 0b1000;
    public static final int FLAG_ALL = FLAG_NEIGHBORS | FLAG_NETWORK;
    public static final int FLAG_ALL_PRIORITY = FLAG_ALL | FLAG_PRIORITY;

    private Vector position;
    private int blockId;
    private int flags;
    private int layer;

    @Override
    public int getPacketId() {
        return Protocol.UPDATE_BLOCK_PACKET;
    }

    @Override
    public void write() {
        super.write();
        this.writeSignedVarInt( this.position.getBlockX() );
        this.writeUnsignedVarInt( this.position.getBlockY() );
        this.writeSignedVarInt( this.position.getBlockZ() );
        this.writeUnsignedVarInt( this.blockId );
        this.writeUnsignedVarInt( this.flags );
        this.writeUnsignedVarInt( this.layer );
    }
}
