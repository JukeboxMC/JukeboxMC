package org.jukeboxmc.network.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.jukeboxmc.network.Protocol;

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
    public void read() {
        super.read();
        this.requestTimestamp = this.readLLong();
        this.responseTimestamp = this.readLLong();
    }

    @Override
    public void write() {
        super.write();
        this.writeLLong( this.requestTimestamp );
        this.writeLLong( this.responseTimestamp );
    }
}
