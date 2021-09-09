package jukeboxmc.network.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jukeboxmc.math.Vector;
import org.jukeboxmc.utils.BinaryStream;

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
    public static final int FLAG_NO_GRAPHIC = 0b0100;
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
    public void write( BinaryStream stream ) {
        super.write(stream);
        stream.writeSignedVarInt( this.position.getBlockX() );
        stream.writeUnsignedVarInt( this.position.getBlockY() );
        stream.writeSignedVarInt( this.position.getBlockZ() );
        stream.writeUnsignedVarInt( this.blockId );
        stream.writeUnsignedVarInt( this.flags );
        stream.writeUnsignedVarInt( this.layer );
    }
}
