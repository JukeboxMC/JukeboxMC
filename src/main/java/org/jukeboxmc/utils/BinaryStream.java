package org.jukeboxmc.utils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.charset.StandardCharsets;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BinaryStream {

    private ByteBuf buffer;

    public BinaryStream() {
        this.buffer = Unpooled.buffer( 0 );
    }

    public BinaryStream( ByteBuf buffer ) {
        this.buffer = buffer;
    }

    public BinaryStream( int maxSize ) {
        this.buffer = Unpooled.buffer( 0, maxSize );
    }

    public ByteBuf getBuffer() {
        return this.buffer;
    }

    public byte[] readRemaining() {
        ByteBuf byteBuf = this.buffer.readSlice( this.buffer.readerIndex() );
        ByteBuf duplicate = byteBuf.duplicate();
        byte[] array = new byte[duplicate.readableBytes()];
        duplicate.readBytes( array );
        return array;
    }

    public void setBuffer( ByteBuf buffer ) {
        this.buffer = buffer;
    }

    public byte readByte() {
        return this.buffer.readByte();
    }

    public int readUnsignedByte() {
        return this.buffer.readUnsignedByte();
    }

    public void readBytes( byte[] bytes ) {
        this.buffer.readBytes( bytes );
    }

    public ByteBuf readBytes( int value ) {
        return this.buffer.readBytes( value );
    }

    public void writeByte( int value ) {
        this.buffer.writeByte( value );
    }

    public void writeBytes( byte[] value ) {
        this.buffer.writeBytes( value );
    }

    public void writeBuffer( ByteBuf buffer ) {
        this.buffer.writeBytes( buffer );
    }

    public int readInt() {
        return this.buffer.readInt();
    }

    public void writeInt( int value ) {
        this.buffer.writeInt( value );
    }

    public int readLInt() {
        return this.buffer.readIntLE();
    }

    public void writeLInt( int value ) {
        this.buffer.writeIntLE( value );
    }

    public short readShort() {
        return this.buffer.readShort();
    }

    public void writeShort( short value ) {
        this.buffer.writeShort( value );
    }

    public short readLShort() {
        return this.buffer.readShortLE();
    }

    public void writeLShort( int value ) {
        this.buffer.writeShortLE( value );
    }

    public int readUnsignedShort() {
        return this.buffer.readUnsignedShort();
    }

    public float readFloat() {
        return this.buffer.readFloat();
    }

    public void writeFloat( float value ) {
        this.buffer.writeFloat( value );
    }

    public float readLFloat() {
        return this.buffer.readFloatLE();
    }

    public void writeLFloat( float value ) {
        this.buffer.writeFloatLE( value );
    }

    public double readDouble() {
        return this.buffer.readDouble();
    }

    public void writeDouble( double value ) {
        this.buffer.writeDouble( value );
    }

    public double readLDouble() {
        return this.buffer.readDoubleLE();
    }

    public void writeLDouble( double value ) {
        this.buffer.writeDoubleLE( value );
    }

    public long readLong() {
        return this.buffer.readLong();
    }

    public void writeLong( long value ) {
        this.buffer.writeLong( value );
    }

    public long readLLong() {
        return this.buffer.readLongLE();
    }

    public void writeLLong( long value ) {
        this.buffer.writeLongLE( value );
    }

    public boolean readBoolean() {
        return this.buffer.readBoolean();
    }

    public void writeBoolean( boolean value ) {
        this.buffer.writeBoolean( value );
    }

    public void writeLTried( int value ) {
        this.buffer.writeMediumLE( value );
    }

    public int readLTried() {
        return this.buffer.readMediumLE();
    }

    public int readUnsignedLTried() {
        return this.buffer.readUnsignedMediumLE();
    }

    public void writeString( String value ) {
        byte[] ascii = value.getBytes();
        this.writeUnsignedVarInt( ascii.length );
        this.buffer.writeBytes( ascii );
    }

    public String readString() {
        byte[] bytes = new byte[this.readLInt()];
        this.buffer.readBytes( bytes );
        return new String( bytes );
    }

    public int readUnsignedVarInt() {
        int value = 0;
        int i = 0;
        int b;

        while ( ( ( b = this.buffer.readByte() ) & 0x80 ) != 0 ) {
            value |= ( b & 0x7F ) << i;
            i += 7;
            if ( i > 35 ) {
                throw new RuntimeException( "VarInt too big" );
            }
        }

        return value | ( b << i );
    }

    public void writeUnsignedVarInt( int value ) {
        while ( ( value & 0xFFFFFF80 ) != 0L ) {
            this.buffer.writeByte( (byte) ( ( value & 0x7F ) | 0x80 ) );
            value >>>= 7;
        }

        this.buffer.writeByte( (byte) ( value & 0x7F ) );
    }

    public int readableBytes() {
        return this.buffer.readableBytes();
    }

    public byte[] getArray() {
        ByteBuf duplicate = this.buffer.duplicate();
        byte[] array = new byte[duplicate.readableBytes()];
        duplicate.readBytes( array );
        return array;
    }

    public BinaryStream readSlice(int size){
        this.buffer.readSlice( size );
        return this;
    }
}
