package org.jukeboxmc.network.raknet.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Getter;
import lombok.ToString;
import org.jukeboxmc.network.raknet.utils.Reliability;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@ToString(exclude = "buffer")
public class EncapsulatedPacket {

    @Getter
    public ByteBuf buffer;

    public int reliability;

    public int messageIndex = -1;
    public int sequenceIndex = -1;
    public int orderIndex = -1;
    public int orderChannel = 0;

    public boolean split = false;

    public int splitCount = 0;
    public int splitIndex = 0;
    public int splitID = 0;

    public boolean needACK = false;

    public static EncapsulatedPacket fromBinary( ByteBuf buffer ) {
        EncapsulatedPacket packet = new EncapsulatedPacket();
        int flags = buffer.readByte();
        int value = ( flags & 0b11100000 ) >> 5;
        packet.reliability = value;
        packet.split = ( flags & 0b00010000 ) > 0;
        int size = ( buffer.readUnsignedShort() + 7 ) >> 3;

        if ( Reliability.reliable( value ) ) {
            packet.messageIndex = buffer.readUnsignedMediumLE();
        }

        if ( Reliability.sequenced( value ) ) {
            packet.sequenceIndex = buffer.readUnsignedMediumLE();
        }

        if ( Reliability.sequencedOrOrdered( value ) ) {
            packet.orderIndex = buffer.readUnsignedMediumLE();
            packet.orderChannel = buffer.readUnsignedByte();
        }

        if ( packet.split ) {
            packet.splitCount = buffer.readInt();
            packet.splitID = buffer.readUnsignedShort();
            packet.splitIndex = buffer.readInt();
        }

        packet.buffer = buffer.readSlice( size );
        return packet;
    }

    public byte[] toBinary() {
        ByteBuf stream = Unpooled.buffer( 0 );
        int value = this.reliability << 5;
        if ( split ) {
            value |= 0b00010000;
        }
        stream.writeByte( value ); // flags
        stream.writeShort( (short) ( buffer.readableBytes() << 3 ) ); // size

        if ( Reliability.reliable( value ) ) {
            stream.writeMediumLE( this.messageIndex );
        }

        if ( Reliability.sequenced( value ) ) {
            stream.writeMediumLE( this.sequenceIndex );
        }

        if ( Reliability.sequencedOrOrdered( value ) ) {
            stream.writeMediumLE( orderIndex );
            stream.writeByte( orderChannel );
        }

        if ( split ) {
            stream.writeInt( splitCount );
            stream.writeShort( (short) splitID );
            stream.writeInt( splitIndex );
        }

        stream.writeBytes( this.buffer, this.buffer.readerIndex(), this.buffer.readableBytes() );
        return stream.array();
    }

    public int getTotalLength() {
        return 3 + this.buffer.capacity() + (this.messageIndex != -1 ? 3: 0) + (this.orderIndex != -1 ? 4: 0) + (this.split ? 10 : 0);
    }

}
