package org.jukeboxmc.network.raknet.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.jukeboxmc.utils.BinaryStream;
import org.jukeboxmc.network.raknet.utils.Reliability;

/**
 * @author LucGamesYT
 * @version 1.0
 */
@ToString ( exclude = "buffer" )
public class EncapsulatedPacket {

    @Getter
    @Setter
    public ByteBuf buffer = Unpooled.buffer( 0 );

    public int reliability;

    public int messageIndex = -1;
    public int sequenceIndex = -1;
    public int orderIndex = -1;
    public int orderChannel;

    public boolean split = false;

    public int splitCount;
    public int splitIndex;
    public int splitID;

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
        BinaryStream stream = new BinaryStream();
        int value = this.reliability << 5;
        if ( this.split ) {
            value |= 0x10;
        }
        stream.writeByte( value ); // flags
        stream.writeShort( (short) ( this.buffer.readableBytes() << 3 ) ); // size

        if ( Reliability.reliable( this.reliability ) ) {
            stream.writeLTried( this.messageIndex );
        }

        if ( Reliability.sequenced( this.reliability ) ) {
            stream.writeLTried( this.sequenceIndex );
        }

        if ( Reliability.sequencedOrOrdered( this.reliability ) ) {
            stream.writeLTried( this.orderIndex );
            stream.writeByte( this.orderChannel );
        }

        if ( this.split ) {
            stream.writeInt( this.splitCount );
            stream.writeShort( (short) this.splitID );
            stream.writeInt( this.splitIndex );
        }

        stream.writeBuffer( this.buffer );
        return stream.getArray();
    }

    public byte[] getArray() {
        ByteBuf duplicate = this.buffer.duplicate();
        byte[] array = new byte[duplicate.readableBytes()];
        duplicate.readBytes( array );
        return array;
    }

    public int getTotalLength() {
        return 3 + this.getArray().length + ( this.messageIndex != -1 ? 3 : 0 ) + ( this.orderIndex != -1 ? 4 : 0 ) + ( this.split ? 10 : 0 );
    }

}
