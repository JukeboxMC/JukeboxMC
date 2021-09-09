package jukeboxmc.network.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jukeboxmc.network.packet.type.AnimationType;
import org.jukeboxmc.utils.BinaryStream;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Data
@EqualsAndHashCode ( callSuper = true )
public class AnimatePacket extends Packet {

    private long entityId;
    private AnimationType action;
    private float rowingTime;

    @Override
    public int getPacketId() {
        return Protocol.ANIMATE_PACKET;
    }

    @Override
    public void read( BinaryStream stream ) {
        super.read( stream );
        this.action = AnimationType.getActionFromId( stream.readSignedVarInt() );
        this.entityId = stream.readUnsignedVarLong();
        if ( ( this.action.getId() & 0x80 ) != 0 ) {
            this.rowingTime = stream.readLFloat();
        }
    }

    @Override
    public void write( BinaryStream stream ) {
        super.write( stream );
        stream.writeSignedVarInt( this.action.getId() );
        stream.writeUnsignedVarLong( this.entityId );

        if ( ( this.action.getId() & 0x80 ) != 0 ) {
            stream.writeLFloat( this.rowingTime );
        }
    }
}
