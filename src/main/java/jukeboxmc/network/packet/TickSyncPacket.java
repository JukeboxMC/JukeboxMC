package jukeboxmc.network.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jukeboxmc.utils.BinaryStream;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@Data
@EqualsAndHashCode ( callSuper = true )
public class TickSyncPacket extends Packet {

    private long requestTimestamp;
    private long responseTimestamp;

    @Override
    public int getPacketId() {
        return Protocol.TICK_SYNC_PACKET;
    }

    @Override
    public void read( BinaryStream stream ) {
        super.read(stream);
        this.requestTimestamp = stream.readLLong();
        this.responseTimestamp = stream.readLLong();
    }

    @Override
    public void write( BinaryStream stream ) {
        super.write(stream);
        stream.writeLLong( this.requestTimestamp );
        stream.writeLLong( this.responseTimestamp );
    }
}
