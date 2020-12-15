package org.jukeboxmc.utils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.jukeboxmc.world.GameRule;

import java.math.BigInteger;
import java.util.Map;

/**
 * @author LucGamesYT
 * @version 1.0
 */
public class BinaryStream {

    private ByteBuf buffer;

    public BinaryStream() {
        this.buffer = Unpooled.buffer( 0 );
        this.buffer.retain();
    }

    public BinaryStream( ByteBuf buffer ) {
        this.buffer = buffer;
        this.buffer.retain();
    }

    public BinaryStream( int maxSize ) {
        this.buffer = Unpooled.buffer( 0, maxSize );
    }

    public BinaryStream release() {
        this.buffer.release();
        return this;
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
        this.writeLInt( Float.floatToRawIntBits( value ) );
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

    public void writeSignedVarLong( long value ) {
        this.writeVarBigInteger( this.encodeZigZag64( value ) );
    }

    public void writeUnsignedVarLong( long value ) {
        while ( ( value & 0xFFFFFFFFFFFFFF80L ) != 0L ) {
            this.writeByte( (byte) ( ( (int) value & 0x7F ) | 0x80 ) );
            value >>>= 7;
        }

        this.writeByte( (byte) ( (int) value & 0x7F ) );
    }

    public int readSignedVarInt() {
        return this.decodeZigZag32( this.readUnsignedVarInt() );
    }

    public void writeSignedVarInt( int value ) {
        this.writeUnsignedVarInt( this.encodeZigZag32( value ) );
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

    public BinaryStream readSlice( int size ) {
        this.buffer.readSlice( size );
        return this;
    }

    private void writeVarBigInteger( BigInteger value ) {
        BigInteger UNSIGNED_LONG_MAX_VALUE = new BigInteger( "FFFFFFFFFFFFFFFF", 16 );
        if ( value.compareTo( UNSIGNED_LONG_MAX_VALUE ) > 0 ) {
            throw new IllegalArgumentException( "The value is too big" );
        }

        value = value.and( UNSIGNED_LONG_MAX_VALUE );
        BigInteger i = BigInteger.valueOf( -128 );
        BigInteger x7f = BigInteger.valueOf( 0x7f );
        BigInteger x80 = BigInteger.valueOf( 0x80 );

        while ( !value.and( i ).equals( BigInteger.ZERO ) ) {
            this.writeByte( value.and( x7f ).or( x80 ).byteValue() );
            value = value.shiftRight( 7 );
        }

        this.writeByte( value.byteValue() );
    }

    private BigInteger encodeZigZag64( long value ) {
        BigInteger origin = BigInteger.valueOf( value );
        BigInteger left = origin.shiftLeft( 1 );
        BigInteger right = origin.shiftRight( 63 );
        return left.xor( right );
    }

    private int encodeZigZag32(int value) {
        return value << 1 ^ value >> 31;
    }

    private int decodeZigZag32(int v) {
        return v >> 1 ^ -(v & 1);
    }

    //Minecraft

    public void writeGameRules( Map<String, GameRule> gamerules ) {
        this.writeUnsignedVarInt( gamerules.size() );

        gamerules.forEach( (name, value) -> {

        } );
    }
}
