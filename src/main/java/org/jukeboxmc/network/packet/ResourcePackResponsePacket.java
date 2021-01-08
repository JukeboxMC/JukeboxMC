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
public class ResourcePackResponsePacket extends Packet {

    private Status responseStatus;

    @Override
    public int getPacketId() {
        return Protocol.RESOURCE_PACK_RESPONSE_PACKET;
    }

    @Override
    public void read() {
        super.read();
        this.responseStatus = Status.getStatus( this.readByte() );
        short entries = this.readLShort();
        for ( int i = 0; i < entries; i++ ) {
            this.readString(); //Ignore
        }
    }

    public enum Status {

        STATUS_REFUSED,
        STATUS_SEND_PACKS,
        STATUS_HAVE_ALL_PACKS,
        STATUS_COMPLETED;

        public static Status getStatus( int status ) {
            switch ( status ) {
                case 1:
                    return STATUS_REFUSED;
                case 2:
                    return STATUS_SEND_PACKS;
                case 3:
                    return STATUS_HAVE_ALL_PACKS;
                case 4:
                    return STATUS_COMPLETED;
                default:
                    return null;
            }
        }
    }
}
