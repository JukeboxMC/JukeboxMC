package org.jukeboxmc.network.raknet.protocol;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.LinkedList;

/**
 * @author LucGamesYT
 * @version 1.0
 */

@Getter
@Setter
@ToString
public class DataRakNetPacket extends RakNetPacket {

    private LinkedList<Object> packets = new LinkedList<>();

    public int sequenceNumber;
    public long sendTime = System.currentTimeMillis();

    public DataRakNetPacket() {
        super( BitFlags.VALID );
    }

    @Override
    public void read() {
        super.read();
        this.sequenceNumber = this.buffer.readUnsignedMediumLE();

        while ( this.buffer.isReadable() ) {
            this.packets.add( EncapsulatedPacket.fromBinary( this.buffer ) );
        }
    }

    @Override
    public void write() {
        super.write();
        this.buffer.writeMediumLE( this.sequenceNumber );

        for ( Object object : this.packets ) {
            if ( object instanceof EncapsulatedPacket ) {
                EncapsulatedPacket encapsulatedPacket = (EncapsulatedPacket) object;
                this.buffer.writeBytes( encapsulatedPacket.toBinary() );
            } else {
                byte[] buffer = (byte[]) object;
                this.buffer.writeBytes( buffer );
            }
        }
    }

    public int length() {
        int length = 4;
        for ( Object object : this.packets ) {
            if ( object instanceof EncapsulatedPacket ) {
                EncapsulatedPacket packet = (EncapsulatedPacket) object;
                length += packet.getTotalLength();
            } else {
                byte[] buffer = (byte[]) object;
                length += buffer.length;
            }
        }
        return length;
    }
}
