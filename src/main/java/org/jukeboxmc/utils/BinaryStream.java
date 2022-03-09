package org.jukeboxmc.utils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;
import org.jukeboxmc.block.direction.BlockFace;
import org.jukeboxmc.entity.metadata.MetadataFlag;
import org.jukeboxmc.entity.metadata.MetadataValue;
import org.jukeboxmc.item.Item;
import org.jukeboxmc.item.ItemType;
import org.jukeboxmc.nbt.NBTInputStream;
import org.jukeboxmc.nbt.NBTOutputStream;
import org.jukeboxmc.nbt.NbtMap;
import org.jukeboxmc.nbt.NbtUtils;
import org.jukeboxmc.nbt.util.stream.LittleEndianByteBufInputStream;
import org.jukeboxmc.nbt.util.stream.LittleEndianByteBufOutputStream;
import org.jukeboxmc.network.packet.type.CommandOrigin;
import org.jukeboxmc.player.skin.*;
import org.jukeboxmc.world.GameRule;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.UUID;

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

    public ByteBuf getBuffer() {
        return this.buffer;
    }

    public void setBuffer( ByteBuf buffer ) {
        this.buffer = buffer;
    }

    public byte readByte() {
        return this.buffer.readByte();
    }

    public short readUnsignedByte() {
        return this.buffer.readUnsignedByte();
    }

    public void writeByte( int value ) {
        this.buffer.writeByte( value );
    }

    public void readBytes( byte[] bytes ) {
        this.buffer.readBytes( bytes );
    }

    public void writeBytes( byte[] value ) {
        this.buffer.writeBytes( value );
    }

    public ByteBuf readBytes( int value ) {
        return this.buffer.readBytes( value );
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
        return Float.intBitsToFloat( this.readLInt() );
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

    public long readUnsignedVarLong() {
        long value = 0L;
        int i = 0;

        do {
            long b;
            if ( ( ( b = this.buffer.readByte() ) & 128L ) == 0L ) {
                return value | b << i;
            }

            value |= ( b & 127L ) << i;
            i += 7;
        } while ( i <= 63 );

        throw new RuntimeException( "VerLong too big" );
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

    public String readString16() {
        byte[] bytes = new byte[this.readLShort()];
        this.buffer.readBytes( bytes );
        return new String( bytes, StandardCharsets.UTF_8 );
    }

    public String readString() {
        byte[] bytes = new byte[this.readUnsignedVarInt()];
        this.readBytes( bytes );
        return new String( bytes, StandardCharsets.UTF_8 );
    }

    public void writeString( final String value ) {
        byte[] bytes = value.getBytes( StandardCharsets.UTF_8 );
        this.writeUnsignedVarInt( bytes.length );
        this.writeBytes( bytes );
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

    private int encodeZigZag32( int value ) {
        return value << 1 ^ value >> 31;
    }

    private int decodeZigZag32( int v ) {
        return v >> 1 ^ -( v & 1 );
    }

    public UUID readUUID() {
        return new UUID( this.readLLong(), this.readLLong() );
    }

    public void writeUUID( UUID uuid ) {
        this.writeLLong( uuid.getMostSignificantBits() );
        this.writeLLong( uuid.getLeastSignificantBits() );
    }

    public void release() {
        this.buffer.release();
    }

    public byte[] array() {
        ByteBuf duplicate = this.buffer.duplicate();
        byte[] array = new byte[duplicate.readableBytes()];
        duplicate.readBytes( array );
        return array;
    }

    // ========== Minecraft ==========

    private final int shildId = ItemType.SHIELD.getItem().getRuntimeId();

    public void writeGameRules( Map<GameRule<?>, Object> gamerules ) {
        if ( gamerules == null ) {
            this.writeUnsignedVarInt( 0 );
            return;
        }

        this.writeUnsignedVarInt( gamerules.size() );
        gamerules.forEach( ( gamerule, value ) -> {
            this.writeString( gamerule.getName().toLowerCase() );
            this.writeBoolean( true );

            if ( gamerule.getValue() == Boolean.class ) {
                this.writeByte( (byte) 1 );
                this.writeBoolean( (Boolean) value );
            } else if ( gamerule.getValue() == Integer.class ) {
                this.writeByte( (byte) 2 );
                this.writeUnsignedVarInt( (Integer) value );
            } else if ( gamerule.getValue() == Float.class ) {
                this.writeByte( (byte) 3 );
                this.writeLFloat( (Float) value );
            }
        } );
    }

    public Item readItem() {
        int networkId = this.readSignedVarInt();

        if ( networkId == 0 ) {
            return ItemType.AIR.getItem().setAmount( 0 );
        }

        byte amount = (byte) this.readLShort();
        int data = this.readUnsignedVarInt();

        NbtMap nbt = null;

        if ( this.readBoolean() ) { // hasNetId
            this.readSignedVarInt(); // netId
        }

        int blockRuntimeId = this.readSignedVarInt();

        int value = this.readUnsignedVarInt();

        ByteBuf userData = this.readBytes( value );
        BinaryStream binaryStream = new BinaryStream( userData );

        try ( LittleEndianByteBufInputStream stream = new LittleEndianByteBufInputStream( userData ) ) {
            int nbtSize = stream.readShort();

            if ( nbtSize > 0 ) {
                nbt = (NbtMap) NbtUtils.createReaderLE( stream ).readTag();
            } else if ( nbtSize == -1 ) {
                int tagCount = stream.readUnsignedByte();

                if ( tagCount != 1 ) {
                    throw new IllegalArgumentException( "Expected 1 tag but got " + tagCount );
                }
                nbt = (NbtMap) NbtUtils.createReaderLE( stream ).readTag();
            }
        } catch ( IOException e ) {
            e.printStackTrace();
        }

        int countPlacedOn = binaryStream.readLInt();
        for ( int i = 0; i < countPlacedOn; i++ ) {
            binaryStream.readString16();
        }

        int countCanBreak = binaryStream.readLInt();
        for ( int i = 0; i < countCanBreak; i++ ) {
            binaryStream.readString16();
        }

        Item item = ItemType.getItemFormNetwork( networkId, data );
        item.setMeta( data );
        item.setAmount( amount );
        item.setNBT( nbt );
        if ( nbt != null ) {
            item.setDurability( nbt.getInt( "Damage", 0 ) );
        }
        item.setBlockRuntimeId( blockRuntimeId );

        if ( networkId == this.shildId ) {
            binaryStream.readLLong();
        }

        return item;
    }

    public void writeIngredient( Item item ) {
        this.writeSignedVarInt( item.getRuntimeId() );
        this.writeSignedVarInt( item.getMeta() == -1 ? Short.MAX_VALUE : item.getMeta() );
        this.writeSignedVarInt( item.getAmount() );
    }

    public void writeItem( Item item ) {
        this.writeItem( item, false );
    }

    public void writeItem( Item item, boolean crafting ) {
        if ( item == null || item.getRuntimeId() == -158 ) {
            this.writeByte( 0 );
            return;
        }

        int runtimeId = item.getRuntimeId();
        int meta = item.getMeta();
        int amount = item.getAmount();

        this.writeSignedVarInt( runtimeId );

        this.writeLShort( amount );
        this.writeUnsignedVarInt( meta );

       if ( !crafting ) {
           this.writeBoolean( true );
           this.writeSignedVarInt( 1 );
       }

        this.writeSignedVarInt( item.getBlockRuntimeId() );

        ByteBuf userData = ByteBufAllocator.DEFAULT.ioBuffer();
        BinaryStream binaryStream = new BinaryStream( userData );

        try ( LittleEndianByteBufOutputStream stream = new LittleEndianByteBufOutputStream( userData ) ) {
            final NbtMap nbtData = item.toNetwork().build();

            if ( nbtData != null && !nbtData.isEmpty() ) {
                stream.writeShort( -1 );
                stream.writeByte( 1 );
                try (NBTOutputStream networkWriter = NbtUtils.createWriterLE( stream )){
                    networkWriter.writeTag( nbtData );
                }
            } else {
                userData.writeShortLE( 0 );
            }

            binaryStream.writeLInt( 0 );
            binaryStream.writeLInt( 0 );

            if ( runtimeId == this.shildId ) {
                binaryStream.writeLLong( 0 );
            }

            this.writeUnsignedVarInt( userData.readableBytes() );
            this.buffer.writeBytes( userData );
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }

    public void writeItemInstance( int runtimeId, int data, int blockRuntimeId, byte[] nbtData ) {
        if ( runtimeId == 0 ) {
            this.writeByte( 0 );
            return;
        }

        this.writeSignedVarInt( runtimeId );

        this.writeLShort( 1 ); //Amount
        this.writeUnsignedVarInt( data ); //Meta

        this.writeSignedVarInt( blockRuntimeId );

        ByteBuf userData = ByteBufAllocator.DEFAULT.ioBuffer();
        BinaryStream binaryStream = new BinaryStream( userData );

        try ( LittleEndianByteBufOutputStream stream = new LittleEndianByteBufOutputStream( userData ) ) {
            if ( nbtData.length > 0 ) {
                stream.writeShort( -1 );
                stream.writeByte( 1 );

                try (NBTOutputStream networkWriter = NbtUtils.createWriterLE( stream )){
                    ByteArrayInputStream inputStream = new ByteArrayInputStream( nbtData );
                    try (NBTInputStream readerLE = NbtUtils.createReaderLE( inputStream )){
                        networkWriter.writeTag( readerLE.readTag() );
                    }
                }
            } else {
                stream.writeShort( 0 );
            }

            binaryStream.writeLInt( 0 );
            binaryStream.writeLInt( 0 );

            if ( runtimeId == this.shildId ) {
                binaryStream.writeLLong( 0 );
            }

            this.writeUnsignedVarInt( userData.readableBytes() );
            this.buffer.writeBytes( userData );
        } catch ( IOException e ) {
            e.printStackTrace();
        }
    }

    public void writeEntityMetadata( Map<MetadataFlag, MetadataValue> metadata ) {
        this.writeUnsignedVarInt( metadata.size() );
        metadata.forEach( ( flag, value ) -> {
            this.writeUnsignedVarInt( flag.getId() );
            this.writeUnsignedVarInt( value.getFlagType().ordinal() );
            switch ( value.getFlagType() ) {
                case BYTE:
                    this.writeByte( (Byte) value.getValue() );
                    break;
                case FLOAT:
                    this.writeLFloat( (Float) value.getValue() );
                    break;
                case LONG:
                    this.writeSignedVarLong( (Long) value.getValue() );
                    break;
                case STRING:
                    this.writeString( (String) value.getValue() );
                    break;
                case SHORT:
                    this.writeLShort( (Short) value.getValue() );
                    break;
                default:
                    break;
            }
        } );
    }

    public void writeSkin( Skin skin ) {
        this.writeString( skin.getSkinId() );
        this.writeString( skin.getPlayFabId() );
        this.writeString( skin.getResourcePatch() );
        this.writeSkinImage( skin.getSkinData() );

        if ( skin.getSkinAnimations() != null ) {
            this.writeLInt( skin.getSkinAnimations().size() );

            for ( SkinAnimation skinAnimation : skin.getSkinAnimations() ) {
                this.writeSkinImage( skinAnimation.getImage() );
                this.writeLInt( skinAnimation.getType() );
                this.writeLFloat( skinAnimation.getFrames() );
                this.writeLInt( skinAnimation.getExpression() );
            }
        } else {
            this.writeLInt( 0 );
        }

        this.writeSkinImage( skin.getCapeData() );
        this.writeString( skin.getGeometryData() );
        this.writeString( skin.getGeometryDataEngineVersion() );
        this.writeString( skin.getAnimationData() );
        this.writeString( skin.getCapeId() );
        this.writeString( skin.getFullSkinId() );
        this.writeString( skin.getArmSize() );
        this.writeString( skin.getSkinColor() );


        if ( skin.getPersonaPieces() != null ) {
            this.writeLInt( skin.getPersonaPieces().size() );

            for ( PersonaPiece personaPiece : skin.getPersonaPieces() ) {
                this.writeString( personaPiece.getPieceId() );
                this.writeString( personaPiece.getPieceType() );
                this.writeString( personaPiece.getPackId() );
                this.writeBoolean( personaPiece.isDefault() );
                this.writeString( personaPiece.getProductId() );
            }
        } else {
            this.writeLInt( 0 );
        }

        if ( skin.getPersonaPieceTints() != null ) {
            this.writeLInt( skin.getPersonaPieceTints().size() );

            for ( PersonaPieceTint personaPieceTint : skin.getPersonaPieceTints() ) {
                this.writeString( personaPieceTint.getPieceType() );

                if ( personaPieceTint.getColors() != null ) {
                    this.writeLInt( personaPieceTint.getColors().size() );
                    for ( String color : personaPieceTint.getColors() ) {
                        this.writeString( color );
                    }
                } else {
                    this.writeUnsignedVarInt( 0 );
                }
            }
        } else {
            this.writeLInt( 0 );
        }

        this.writeBoolean( skin.isPremium() );
        this.writeBoolean( skin.isPersona() );
        this.writeBoolean( skin.isCapeOnClassic() );
        this.writeBoolean( skin.isPrimaryUser() );
    }

    public void writeSkinImage( Image image ) {
        this.writeLInt( image.getWidth() );
        this.writeLInt( image.getHeight() );
        this.writeUnsignedVarInt( image.getData().length );
        this.writeBytes( image.getData() );
    }

    public BlockFace readBlockFace() {
        int value = this.readSignedVarInt();
        switch ( value ) {
            case 0:
                return BlockFace.DOWN;
            case 1:
                return BlockFace.UP;
            case 2:
                return BlockFace.NORTH;
            case 3:
                return BlockFace.SOUTH;
            case 4:
                return BlockFace.WEST;
            case 5:
                return BlockFace.EAST;
            default:
                return null;
        }
    }

    public CommandOrigin readCommandOrigin() {
        CommandOrigin commandOrigin = new CommandOrigin();
        commandOrigin.setUnknown1( this.readByte() );
        commandOrigin.setUuid( this.readUUID() );
        commandOrigin.setUnknown2( this.readByte() );
        commandOrigin.setType( this.readByte() );
        return commandOrigin;
    }
}
